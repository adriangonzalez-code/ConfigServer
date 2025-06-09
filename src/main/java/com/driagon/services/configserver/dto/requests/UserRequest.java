package com.driagon.services.configserver.dto.requests;

import com.driagon.services.logging.annotations.Exclude;
import com.driagon.services.logging.annotations.Mask;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6098312281190306904L;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Mask
    private String email;

    @Exclude
    private String password;

    private Long roleId;
}