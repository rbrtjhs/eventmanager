package com.robertjuhas.dto.request;

import javax.validation.constraints.NotBlank;

public record CreateUserRequestDTO(@NotBlank String username, @NotBlank String password) {
}
