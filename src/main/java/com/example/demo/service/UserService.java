package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> store = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public User create(User user) {
        user.setId(idGen.getAndIncrement());
        store.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public User findById(Long id) {
        return store.get(id);
    }

    public User update(Long id, User user) {
        user.setId(id);
        store.put(id, user);
        return user;
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
