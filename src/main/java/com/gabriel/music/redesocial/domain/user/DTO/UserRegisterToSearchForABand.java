package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.enums.AvaliabityEnum;
import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;

import java.util.Date;
import java.util.List;

public record UserRegisterToSearchForABand(
        String cep,
        Date age,
        Integer shows,
        List<GenreEnum> genre,
        List<InstrumentsEnum> instruments,
        List<AvaliabityEnum> avaliabity
) {
}
