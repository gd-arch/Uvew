package com.flux.uvew.controller;

import com.flux.uvew.dto.ChannelInfoDto;
import com.flux.uvew.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        log.error("Register called");
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return userService.registerUser(jwt.getTokenValue());
    }
    @GetMapping("/user/{subId}")
    @ResponseStatus(HttpStatus.OK)
    public String getUserNameBySubId(@PathVariable  String subId) {
        return userService.getUserNameBySubId(subId);
    }

    @GetMapping("/subscribe/{userSubId}")
    public ResponseEntity<Map<String, String>> subscribeToUser(@PathVariable String userSubId){
        userService.subscribeToVideoCreator(userSubId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/unsubscribe/{userSubId}")
    public ResponseEntity<Map<String, String>> unsubscribeFromUser(@PathVariable String userSubId){
        userService.unsubscribeToVideoCreator(userSubId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/channel/{userSubId}")
    public ResponseEntity<ChannelInfoDto> getChannelInfo(@PathVariable String userSubId){
        return ResponseEntity.ok(userService.getChannelInfo(userSubId));
    }


}
