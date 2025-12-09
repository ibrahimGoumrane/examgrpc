package org.example.mediaclient.mapper;


import org.example.lab.Creator;
import org.example.mediaclient.dto.CreatorDto;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {

    public static CreatorDto fromCreatorProtoToCreatorDto(Creator creator) {
        if (creator == null) return null;

        CreatorDto creatorDto = new CreatorDto();
        creatorDto.setId(creator.getId());
        creatorDto.setName(creator.getName());
        creatorDto.setEmail(creator.getEmail());

        return creatorDto;
    }
}
