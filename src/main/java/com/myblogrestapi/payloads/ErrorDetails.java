package com.myblogrestapi.payloads;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamps;
    private String message;
    private String details;

    
    public ErrorDetails(Date timestamps, String message, String details) {
        this.timestamps = timestamps;
        this.message = message;
        this.details = details;
    }
}
