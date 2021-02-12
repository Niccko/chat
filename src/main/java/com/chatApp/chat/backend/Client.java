package com.chatApp.chat.backend;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.Scanner;

public class Client {
    private InetAddress host;
    private final int port = 9000;
    private Socket socket;

    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    private char[] buf = new char[512];

    public Client(String name) {
        try {

            host = InetAddress.getLocalHost();
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Thread receiveThread = new Thread(this::receive);
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while (true) {
            try {
                if (in.ready()) {
                    in.read(buf);
                    System.out.println(buf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String msg) {
        try {
            out.write(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client c = new Client("a");
        Scanner sc = new Scanner(System.in);
        while (true) {
            c.send(sc.nextLine());
        }
    }

}
