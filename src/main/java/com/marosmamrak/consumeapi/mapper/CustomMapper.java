package com.marosmamrak.consumeapi.mapper;

import com.marosmamrak.consumeapi.entity.Post;
import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomMapper {

    PostDTO toDTO(Post post);


    Post toEntity(PostCreateDTO postDTO);
}
