package com.example.grishmaraypandeya.myclient;

import android.os.Handler;
import android.provider.Contacts;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static com.example.grishmaraypandeya.myclient.MainActivity.SERVERPORT;

/**
 * Created by Grishma Ray Pandeya on 04.06.2017.
 */

public class TcpClientConnection implements Runnable {

    private static Socket mySocket;
    public static final int SERVERPORT = 4444;
    public static final String SERVERIP = "134.28.191.88"; //your computer IP address

    private BufferedReader input;
    Handler UIHandler;

    EditText eText;
    EditText eText1;

    TcpClientConnection(EditText e, EditText e1) {
        eText = e;
        eText1 = e1;
        UIHandler = new Handler();
    }

    @Override
    public void run(){
        try{
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            mySocket = new Socket(serverAddr, SERVERPORT);
            GlobalSocket.setSocket(mySocket);
        } catch(IOException e) {
            e.printStackTrace();
        }


        // you need to continously keep listening the server as long as the server is online
        // and the client is connected
        receiveMessage RM1 = new receiveMessage();
        Thread thread1 = new Thread(RM1);
        thread1.start();

    }

    /*
    class sendMessage implements Runnable {
        String myStr = eText1.getText().toString();

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
                    UIHandler.post(new updateUI("client says:" + myStr));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }*/

    class receiveMessage implements Runnable {
        @Override
        public void run() {
            while(mySocket.isConnected()) {
                //TODO: keep listening to the server
                try {
                    input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                    String read = input.readLine();
                    if(read != null) {
                        //update the UI:
                        UIHandler.post(new updateUI(read));
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    class updateUI implements Runnable {
        private String msg;

        public updateUI(String str) {this.msg = str;}

        public void run() {
            eText.setText(eText.getText().toString()+"Server says: " + msg + "\n");
            //eText.setText("");
        }
    }
}
