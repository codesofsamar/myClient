package com.example.grishmaraypandeya.myclient;

import android.os.Handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Grishma Ray Pandeya on 05.06.2017.
 */

public class SendMessage implements Runnable {
    String myStr;
    Handler localHandler;

    SendMessage(String str) {
        myStr = str;
        //localHandler = h;
    }
    Socket mySocket = GlobalSocket.getSocket();
    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(mySocket.getOutputStream())
            ), true);
            if (out != null && !out.checkError()) {
                //out.println(myStr);
                out.write(myStr);
                out.write("\n");
                out.flush();
                //localHandler.post(new TcpClientConnection.updateUI("client says:" + myStr));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
