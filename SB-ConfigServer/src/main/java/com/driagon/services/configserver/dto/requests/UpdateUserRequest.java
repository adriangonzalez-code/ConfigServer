package com.driagon.services.configserver.dto.requests;

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
public class UpdateUserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2576025351115485544L;

    @NotBlank
    private String firstName;

    private String lastName;

    private boolean active;

    @NotBlank
    @Mask
    private String email;

    private Long roleId;
}