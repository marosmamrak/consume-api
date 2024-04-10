package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.exceptions.UserValidationException;
import com.marosmamrak.consumeapi.model.User;
import com.marosmamrak.consumeapi.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public void validateUser(Long userId) {
        try {
            var user = restTemplate.getForObject(BASE_URL + "users/" + userId, User.class);
            if (isNull(user)) {
                throw new UserValidationException("User with ID " + userId + " was not found in the external service.");
            }
        } catch (Exception e) {
            throw new UserValidationException("Failed to connect to the external service to verify user with ID " + userId + ".");
        }
    }

    public Post findPostById(Long id) {

        try {
            var post = restTemplate.getForObject(BASE_URL + "posts/" + id, Post.class);
            if (isNull(post)) {
                throw new UserValidationException("User with ID " + post + " was not found in the external service.");
            }
            return post;
        } catch (Exception e) {
            throw new UserValidationException("Failed to connect to the external service to verify user with ID " + id + ".");
        }

    }

}
