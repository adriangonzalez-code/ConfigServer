package com.driagon.services.configserver.dto.requests;

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
@ToString(exclude = "password")
@Builder
public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6098312281190306904L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long roleId;
}