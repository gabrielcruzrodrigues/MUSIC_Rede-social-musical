package com.gabriel.music.redesocial.domain.user.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WhatsAppUpdateDTO(@NotNull @NotBlank(message = "o seu whatsapp não pode estar em branco") String whatsapp) {
}
