package com.example.clientserversocket.server;

import android.util.Log;

import com.example.clientserversocket.common.Constants;
import com.example.clientserversocket.model.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class ServerSocketThread extends Thread {

    private ServerSocket _serverSocket ;
    private List<Socket> _listConnectedClients = new ArrayList<>();
    private Message _syncToken = null;

    @Override
    public void run() {

        try {

            _syncToken = new Message();
            /**
             * Create a socket with port number. It'll start listening on the same port.
             */
            _serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Log.d(Constants.APP_TAG, "Waiting for connections");

            while (true) {

                Socket socket = _serverSocket.accept();
                Log.d(Constants.APP_TAG, "Accepted connection from :" + socket.getInetAddress() + ":" + socket.getPort());

                //Let's add client to array list
                //_listConnectedClients.add(socket);
                new ServerSocketSenderThread(socket,_syncToken).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Socket> getConnectedClients() {
        return _listConnectedClients;
    }

    public void stopThread() {

        if (_serverSocket != null) {
            try {
                _serverSocket.close();
                Log.d(Constants.APP_TAG, "Server Socket is closed.");
                _serverSocket = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void notifyAllClients(String message) {
        try {
            synchronized (_syncToken) {
                if (_syncToken != null) {
                    _syncToken.set_message(message);
                    _syncToken.notifyAll();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}