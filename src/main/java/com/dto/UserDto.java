package com.dto;

import com.validator.ValidPhone;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @ValidPhone
    private String phone;
}
