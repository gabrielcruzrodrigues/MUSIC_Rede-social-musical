package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.enums.AvaliabityEnum;
import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record UserRegisterToSearchForABand(
        @NotNull @NotBlank(message = "O seu nome não pode estar em branco")
        String name,
        @NotNull @NotBlank(message = "O seu username não pode estar em branco")
        String username,
        @NotNull @NotBlank(message = "O seu cep não pode estar em branco")
        String cep,
        @NotNull(message = "O seu age não pode estar em branco")
        LocalDate age,
        @NotNull(message = "Os seus shows não podem estar em branco")
        Integer shows,
        @NotNull(message = "Os seus generos não podem estar em branco")
        List<GenreEnum> genre,
        @NotNull(message = "Os seus instrumentos não podem estar em branco")
        List<InstrumentsEnum> instruments,
        @NotNull(message = "A sua disponibilidade não pode estar em branco")
        List<AvaliabityEnum> avaliabity
) {
}
