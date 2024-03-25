package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.dto.User;
import com.marosmamrak.consumeapi.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String baseUrl = "https://jsonplaceholder.typicode.com/";

    public User validateUser(Integer userId) {
        return restTemplate.getForObject(baseUrl + "users/" + userId, User.class);
    }

    public Post findPostById(Integer id) {
        return restTemplate.getForObject(baseUrl + "posts/" + id, Post.class);
    }

}
