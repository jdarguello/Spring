package com.Bancolombia.InversionVirtual.config;

import java.time.LocalDateTime;

public record ErrorModel(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path
) { }
