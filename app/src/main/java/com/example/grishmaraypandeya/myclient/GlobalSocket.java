package com.example.grishmaraypandeya.myclient;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Grishma Ray Pandeya on 04.06.2017.
 */

public class GlobalSocket {
    private static Socket globalSocket;

    public static Socket getSocket() {
        return globalSocket;
    }

    public static void setSocket(Socket s) {
        globalSocket = s;
    }

    class deleteSocket implements Runnable {
        public void run() {
            try {
                globalSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
