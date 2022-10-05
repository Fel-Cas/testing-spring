package com.api.rest.dtos;

import java.time.LocalDateTime;

public record ResponseException(LocalDateTime date,
                                String message,
                                String details) {
}
