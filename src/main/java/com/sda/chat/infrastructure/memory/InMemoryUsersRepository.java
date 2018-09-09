package com.sda.chat.infrastructure.memory;

import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryUsersRepository implements UsersRepository {
    private List<ChatUser> users;

    public InMemoryUsersRepository() {
        this.users = new ArrayList<>();
    }

    public ChatUser addUser(ChatUser user) {
        users.add(user);
        return user;
    }

    @Override
    public ChatUser find(String address) {
        for (ChatUser user : users) {
            if (address.equals(user.getAddress())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<ChatUser> findAll() {
        return new ArrayList<>(users);
    }
}
