package com.example.clientserversocket.client;

import android.app.Activity;

import com.example.clientserversocket.common.Constants;


/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class ClientConnection {

    private Activity _context ;
    private ClientSocketThread _clientTask = null;

    public ClientConnection(Activity context) {
        _context = context;
    }

    public void start() {

        _clientTask = new ClientSocketThread(_context, Constants.SERVER_IP, Constants.SERVER_PORT);
        _clientTask.start();
    }

    public void stop() {
        if(_clientTask != null) {
            _clientTask.stopThread();
            _clientTask = null;
        }
    }
}
