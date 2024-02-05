package com.flux.uvew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class UserInfoDto {

    private String id;

    @NotBlank(message = "sub cannot be blank")
    @JsonProperty("sub")
    private String sub;

    @NotBlank(message = "givenName cannot be blank")
    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @URL(message = "picture url should be valid")
    private String picture;

    @Email(message = "Invalid email format")
    private String email;

}
