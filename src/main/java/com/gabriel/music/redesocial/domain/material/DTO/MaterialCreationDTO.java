package com.gabriel.music.redesocial.domain.material.DTO;

import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import com.gabriel.music.redesocial.domain.enums.NivelEnum;
import com.gabriel.music.redesocial.domain.enums.TypeEnum;
import com.gabriel.music.redesocial.domain.user.User;

import java.util.List;

public record MaterialCreationDTO(
        String name,
        String description,
        Float price,
        List<InstrumentsEnum> instruments,
        List<GenreEnum> genres,
        NivelEnum nivelEnum,
        User createdMaterialsUser_id
) {
}
