package com.blinder.api.user.security.dto;

import com.blinder.api.location.dto.CreateLocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String nickname;
    private String phoneNumber;
    private String genderId;
    private Date birthDate;
    private List<String> images;
    private CreateLocationDto location;
}
