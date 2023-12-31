package com.flux.uvew.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(value = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private List<String> videoHistory;
    private List<String> likedVideos;
    private List<String> dislikedVideos;

}
