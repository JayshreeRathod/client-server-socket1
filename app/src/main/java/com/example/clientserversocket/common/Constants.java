package com.example.clientserversocket.common;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class Constants {

    /**
     * TAG used for logging
     */
    public static final String APP_TAG = "Socket_Demo";

    /**
     * Key text used for application mode settings
     */
    public static final String KEY_APPLICATION_MODE = "app_mode";

    /**
     * USER MODE specifies that application will be running as a CLIENT
     */
    public static final String USER_MODE    = "user_mode";

    /**
     * HOST MODE specifies that application will be running as server
     */
    public static final String HOST_MODE    = "host_mode";

    /**
     * Server will be receiving the connections on this port
     */
    public static final int SERVER_PORT = 8080;

    /**
     * Hardcoded server IP address
     */
    public static final String SERVER_IP = "10.2.11.97";

    /**
     * Buffer size used for client & server threads for streaming
     */
    public static final int BUFFER_SIZE = 1024;

    public static final String DELIMITER_STRING = "\n";

}
