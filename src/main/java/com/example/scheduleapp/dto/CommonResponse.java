package com.example.scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {

    private boolean success;
    private String message;
    private T data;
}
