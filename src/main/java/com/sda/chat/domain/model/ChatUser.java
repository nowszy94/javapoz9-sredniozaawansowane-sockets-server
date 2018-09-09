package com.sda.chat.domain.model;

import java.util.Objects;

public class ChatUser {
    private String name;
    private String address;

    public ChatUser(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatUser chatUser = (ChatUser) o;
        return Objects.equals(name, chatUser.name) &&
                Objects.equals(address, chatUser.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
