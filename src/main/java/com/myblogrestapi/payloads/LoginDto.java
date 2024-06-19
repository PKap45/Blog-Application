package com.myblogrestapi.payloads;

import lombok.Data;

@Data
public class LoginDto {

    private String usernameorEmail;
    private String password;

}
