package com.chatApp.chat.backend;

import java.net.InetAddress;
import java.net.Socket;

public class Person {
    private InetAddress address;
    private String name;
    private Socket socket;

    public Person(InetAddress address, Socket socket) {
        this.address = address;
        this.socket = socket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

}
