package com.marosmamrak.consumeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {

    private Integer userId;
    private String title;
    private String body;


}
