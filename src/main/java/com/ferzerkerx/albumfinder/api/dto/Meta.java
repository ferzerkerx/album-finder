package com.ferzerkerx.albumfinder.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Meta {
    private final int status;
    private final String errorMessage;
}
