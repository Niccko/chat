package com.chatApp.chat.backend;

import com.sun.java.accessibility.util.AccessibilityListenerList;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port = 9000;
    private ServerSocket serverSocket;
    private final int MAX = 10;
    private List<Person> persons = new ArrayList<>();

    private char[] buf = new char[512];

    public Server() {
        try {
            serverSocket = new ServerSocket(port,MAX);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void broadcast(String name, char[] msg) throws IOException {
        for (Person p: persons) {
            Socket client = p.getSocket();
            send(client,name+": "+new String(msg));
        }
    }

    private void clientCommunication(Person person){
        Socket client = person.getSocket();
        System.out.println("[CONNECTION] "+client.getInetAddress()+" joined the server.");
        try {
            client.getOutputStream().write("HEY HEY".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void acceptConnections(){
        while (true){
            try {
                Socket connection = serverSocket.accept();
                var person = new Person(connection.getInetAddress(),connection);
                persons.add(person);
                Thread comThread = new Thread(() -> clientCommunication(person));
                comThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void start(){
        System.out.println("[START] Waiting for connections...");
        Thread acceptThread = new Thread(this::acceptConnections);
        acceptThread.start();
    }

    private void send(Socket socket,String msg) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(msg);
        out.flush();
    }

    public static void main(String[] args) {
        new Server().start();
    }

}
