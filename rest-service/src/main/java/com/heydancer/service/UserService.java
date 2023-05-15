package com.heydancer.service;

import com.heydancer.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    void save(User user, String username, Map<String, String> form);
}
