package com.example.clientserversocket.server;

import android.app.Activity;
import android.app.admin.SystemUpdatePolicy;
import android.util.Log;

import com.example.clientserversocket.common.Constants;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class ServerConnection {

    Activity _activity = null;
    ServerSocketThread _socketServerThread = null;


    public ServerConnection(Activity activity) {
        _activity = activity;
    }

    public void start() {
        _socketServerThread = new ServerSocketThread();
        _socketServerThread.start();
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += " "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        //ip += Utils.getWifiAddress(this);
        //ip += "\n";
        //ip += Utils.getIPAddress(false);
        return ip;
    }

    public void broadcastMessage(String message) {
        Log.d(Constants.APP_TAG, "Message :: " + message);

        if(_socketServerThread != null) {
            _socketServerThread.notifyAllClients(message);
        }
    }

    public void stop() {
        if(_socketServerThread != null) {
            _socketServerThread.stopThread();
            _socketServerThread = null;
        }
    }
}
