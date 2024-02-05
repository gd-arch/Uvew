package com.flux.uvew.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.LinkedList;
import java.util.Set;

@Document(value = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @Field("first_name")
    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name cannot exceed 255 characters")
    private String firstName;
    @Field("last_name")
    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name cannot exceed 255 characters")
    private String lastName;
    @Field("full_name")
    @NotBlank(message = "Full name is required")
    @Size(max = 500, message = "Full name cannot exceed 500 characters")
    private String fullName;
    @Email(message = "Invalid email format")
    private String email;
    private String sub;
    @Field("subscribed_to_users")
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    @Field("video_history")
    private LinkedList<String> videoHistory;
    @Field("liked_videos")
    private Set<String> likedVideos;
    @Field("disliked_videos")
    private Set<String> dislikedVideos;

}
