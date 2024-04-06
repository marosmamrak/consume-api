package com.marosmamrak.consumeapi.mapper;

import com.marosmamrak.consumeapi.entity.Post;
import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomMapper {

    PostDTO toDTO(Post post);


    Post toEntity(PostCreateDTO postDTO);
}
