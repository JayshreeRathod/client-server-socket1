package com.example.clientserversocket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.clientserversocket.R;
import com.example.clientserversocket.common.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onHostBtnClicked(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra(Constants.KEY_APPLICATION_MODE, Constants.HOST_MODE);
        startActivity(intent);
    }

    public void onUserBtnClicked(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra(Constants.KEY_APPLICATION_MODE, Constants.USER_MODE);
        startActivity(intent);
    }
}
