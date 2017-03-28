package com.example.clientserversocket.model;

import com.example.clientserversocket.common.Constants;

/**
 * created by Jayshree.Rathod on 4/11/16.
 */
public class Message {

    public String get_message() {
        return _message+ Constants.DELIMITER_STRING;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    private String _message;
}
