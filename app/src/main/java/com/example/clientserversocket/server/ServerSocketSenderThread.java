package com.example.clientserversocket.server;

import com.example.clientserversocket.model.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class ServerSocketSenderThread extends Thread {

    private Socket _connectedSocket;
    //private String message;
    private Message _syncToken;

    ServerSocketSenderThread(Socket socket, Message token) {
        _connectedSocket = socket;
        _syncToken = token;
    }

    @Override
    public void run() {

        try {
            OutputStream outputStream;
            outputStream = _connectedSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            while(true) {

                synchronized (_syncToken) {
                    _syncToken.wait();
                }

                printStream.print(_syncToken.get_message());
                printStream.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //message += "Something wrong! " + e.toString() + "\n";
        } catch (Exception e) {

        }
    }
}