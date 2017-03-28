package com.example.clientserversocket.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clientserversocket.activity.SubActivity;
import com.example.clientserversocket.common.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
class ClientSocketThread extends Thread {

    private String dstAddress;
    private int dstPort;
    private String response = "";
    private Activity _activity;
    private boolean _isRunning = false;
    private InputStream _inputStream = null;

    public ClientSocketThread(Activity context, String addr, int port){
        _activity = context;
        dstAddress = addr;
        dstPort = port;
        _isRunning = true;
    }

    public void stopThread() {
        _isRunning = false;
        if(_inputStream != null) {
            try {
                _inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            /**
             * Make a call to connect to server socket.
             * It's a blocking call.
             */
            socket = new Socket(dstAddress, dstPort);
            Log.d(Constants.APP_TAG , "Connected to Server :: " + dstAddress +" : " + dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Constants.BUFFER_SIZE);
            byte[] buffer = new byte[Constants.BUFFER_SIZE];

            int bytesRead;
            _inputStream = socket.getInputStream();

            Log.d(Constants.APP_TAG, "Waiting for data...");

            /*
             * notice:
             * inputStream.read() will block if no data return
             */
            while (_isRunning && ((bytesRead = _inputStream.read(buffer)) != -1)) {

                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response = byteArrayOutputStream.toString("UTF-8");
                Log.d(Constants.APP_TAG, "Response received :: " + response);
                //response = response.substring(0, response.indexOf(Constants.DELIMITER_STRING));
                final String[] msgList = response.split(Constants.DELIMITER_STRING);
                Log.d(Constants.APP_TAG, "Response received Trimmed :: " + msgList.toString());

                /**
                 * Assumed that response received to scoll the page by position.
                 */
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for(String responseStr : msgList) {
                                ((SubActivity) _activity).scrollPage(Integer.parseInt(responseStr));
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                /**
                 * It's required to read the new response, else new response will be appended.
                 */
                Arrays.fill(buffer, (byte) 0);
                byteArrayOutputStream.reset();
            }


        } catch (UnknownHostException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();

        }
        finally {
            Log.d(Constants.APP_TAG, "ClientTask :: Finally");
            if(socket != null){
                try {
                    socket.close();
                    Log.d(Constants.APP_TAG, "ClientTask :: Socket is closed now");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}