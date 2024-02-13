package com.example.demo.dto;

import org.springframework.http.HttpStatus;

public record CustomError(HttpStatus status, String message) {}

