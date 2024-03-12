package com.flux.uvew.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flux.uvew.dto.ChannelInfoDto;
import com.flux.uvew.dto.UserDto;
import com.flux.uvew.dto.UserInfoDto;
import com.flux.uvew.exceptions.ResourceNotFoundException;
import com.flux.uvew.model.User;
import com.flux.uvew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Value("${keycloak.userinfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String registerUser(String tokenValue) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDTO = objectMapper.readValue(body, UserInfoDto.class);

            Optional<User> userBySubject = userRepository.findBySub(userInfoDTO.getSub());
            if(userBySubject.isPresent()){
                return userBySubject.get().getId();
            } else {
                User user = new User();
                user.setFirstName(userInfoDTO.getGivenName());
                user.setLastName(userInfoDTO.getFamilyName());
                user.setFullName(userInfoDTO.getName());
                user.setEmail(userInfoDTO.getEmail());
                user.setSub(userInfoDTO.getSub());
                return userRepository.save(user).getId();
            }

        } catch (Exception exception) {
            throw new RuntimeException("Exception occurred while registering user", exception);
        }

    }

    public User getCurrentUser() {
        String sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
        return userRepository.findBySub(sub)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with sub - " + sub));
    }

    public void updateUser(User user){
        if(user != null){
            userRepository.save(user);
        }
    }
    public String getUserNameBySubId(String subId){
        Optional<User> userOptional = userRepository.findBySub(subId);
        if(userOptional.isPresent()){
            return userOptional.get().getFullName();
        }
        return "";
    }

    public boolean isUserValid(String subId) {
        return userRepository.findBySub(subId).isPresent();
    }
    private User findUserBySubId(String subId) {
        return userRepository.findBySub(subId).orElseThrow(()->new ResourceNotFoundException("Could not find user"));
    }
    public void subscribeToVideoCreator(String userSubId) {
        //check if userId is valid
        if(isUserValid(userSubId)) {
            //get current User
            User user = getCurrentUser();
            //add userId to current user subscription
            Set<String> subscribedToUsers = user.getSubscribedToUsers();
            if(subscribedToUsers == null){
                user.setSubscribedToUsers(new HashSet<>());
                subscribedToUsers = user.getSubscribedToUsers();
            }
            subscribedToUsers.add(userSubId);
            //find the user that current user subscribed and add the current user to that channel/user
            User channelOwner = findUserBySubId(userSubId);
            Set<String> channelSubs = channelOwner.getSubscribers();
            if(channelSubs == null){
                channelOwner.setSubscribers(new HashSet<>());
                channelSubs = channelOwner.getSubscribers();
            }
            channelSubs.add(userSubId);
            updateUser(user);
            updateUser(channelOwner);
        }
    }
    public void unsubscribeToVideoCreator(String userSubId) {
        //check if userId is valid
        if(isUserValid(userSubId)) {
            //get current User
            User user = getCurrentUser();
            //add userId to current user subscription
            Set<String> subscribedToUsers = user.getSubscribedToUsers();
            if(subscribedToUsers != null){
                subscribedToUsers.remove(userSubId);
                updateUser(user);
            }
            //find the user that current user subscribed and remove the current user from that channel/user
            User channelOwner = findUserBySubId(userSubId);
            Set<String> channelSubs = channelOwner.getSubscribers();
            if(channelSubs != null){
                channelSubs.remove(userSubId);
                updateUser(channelOwner);
            }
        }
    }
    public UserDto findByUserSubId(String userSubId){
        User user = findUserBySubId(userSubId);
        return userToUserDto(user);
    }
    private UserDto userToUserDto(User user){
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .sub(user.getSub())
//                .subscribedToUsers(user.getSubscribedToUsers())
//                .subscribers(user.getSubscribers())
//                .videoHistory(user.getVideoHistory())
//                .likedVideos(user.getLikedVideos())
//                .dislikedVideos(user.getDislikedVideos())
                .build();
    }

    public ChannelInfoDto getChannelInfo(String userSubId) {
        //create channel entity
        User user = findUserBySubId(userSubId);
        return ChannelInfoDto.builder()
                .channelName(user.getFullName())
                .ownerId(userSubId)
                .totalSubscribers(user.getSubscribers().size())
//                .videos(user.ge().size())
                .build();

    }
}
