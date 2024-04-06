package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.exceptions.UserValidationException;
import com.marosmamrak.consumeapi.model.User;
import com.marosmamrak.consumeapi.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public void validateUser(Integer userId) {
        try {
            User user = restTemplate.getForObject(BASE_URL + "users/" + userId, User.class);
            if (user == null) {
                throw new UserValidationException("User with ID " + userId + " was not found in the external service.");
            }
        } catch (RestClientException e) {
            throw new UserValidationException("Failed to connect to the external service to verify user with ID " + userId + ".");
        }

    }

    public Post findPostById(Integer id) {
        return restTemplate.getForObject(BASE_URL + "posts/" + id, Post.class);
    }

}
