package com.ferzerkerx.albumfinder.domain;

import lombok.NonNull;
import org.springframework.lang.Nullable;

public record Album(@NonNull Integer id, @NonNull String title, @NonNull String year, @Nullable Artist artist) {

}

