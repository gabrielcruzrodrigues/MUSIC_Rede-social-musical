package com.gabriel.music.redesocial.domain.user.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhoneNumberRegistrationDTO(
        @NotNull @NotBlank(message = "O seu numero não pode estar nulo")
        String number,
        @NotNull @NotBlank(message = "O username do usuario não pode ser nulo")
        String username) {
}
