package com.myblogrestapi.payloads;


import lombok.Data;

@Data
public class SignUpDto {

    private long id;
    private String name;
    private String email;
    private String username;
    private String password;


}
