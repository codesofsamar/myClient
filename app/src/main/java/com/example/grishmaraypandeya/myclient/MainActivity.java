package com.example.grishmaraypandeya.myclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;

import java.io.BufferedReader;
import android.app.Activity;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import static com.example.grishmaraypandeya.myclient.R.id.edittext1;

//public class MainActivity extends Activity implements View.OnClickListener {
    public class MainActivity extends Activity {

    Handler UIHandler;
    Handler UIHandler2;
    Socket mySocket;
    Thread Thread1 = null;

    private EditText EDITTEXT;
    private EditText WRITETEXT;
    Button mButton;
    private EditText EDITTEXT1;

    Socket socket = null;

    ToggleButton myConnectionButton;




    public static final int SERVERPORT = 4444;
    public static final String SERVERIP = "134.28.191.88"; //your computer IP address



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EDITTEXT = (EditText) findViewById(R.id.edittext);
        EDITTEXT1 = (EditText) findViewById(R.id.edittext1);

        mButton = (Button)findViewById(R.id.button);
        myConnectionButton = (ToggleButton) findViewById(R.id.connection);
        final TcpClientConnection tcp = new TcpClientConnection(EDITTEXT, EDITTEXT1);





        UIHandler = new Handler();
        UIHandler2 = new Handler();

        // toggle button listener to connect and disconnect the connection with the server
        myConnectionButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // setup the connection
                    Thread newThread = new Thread(tcp);
                    newThread.start();
                } else {
                    // disconnect the connection
                    SendMessage SM = new SendMessage("SHUT_DOWN_COMMAND");
                    Thread T1 = new Thread(SM);
                    T1.start();
                    GlobalSocket.setSocket(null);
                    /*
                    GlobalSocket gs = new GlobalSocket();
                    GlobalSocket.deleteSocket ds = gs.new deleteSocket();
                    Thread closingThread = new Thread(ds);
                    closingThread.start();
                     */

                }
            }
        });

    }

    public void onClick(View view) {
        String str = EDITTEXT1.getText().toString();
        SendMessage SM = new SendMessage(str);
        Thread T1 = new Thread(SM);
        T1.start();
        UIHandler2.post(new updateUI2(str));
    }


    class updateUI2 implements Runnable {
        private String msg;

        public updateUI2(String str) {this.msg = str;}

        public void run() {
            EDITTEXT.setText(EDITTEXT.getText().toString()+"Client says: " + msg + "\n");
            EDITTEXT1.setText("");

        }
    }
}


