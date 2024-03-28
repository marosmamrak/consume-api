package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.dto.User;
import com.marosmamrak.consumeapi.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public void validateUser(Integer userId) {
        restTemplate.getForObject(BASE_URL + "users/" + userId, User.class);
    }

    public Post findPostById(Integer id) {
        return restTemplate.getForObject(BASE_URL + "posts/" + id, Post.class);
    }

}
