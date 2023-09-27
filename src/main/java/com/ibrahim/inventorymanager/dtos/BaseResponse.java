package com.ibrahim.inventorymanager.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse {

    private boolean success;

    private String message;
}
