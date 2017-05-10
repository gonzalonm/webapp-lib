package com.lalosoft.webapptemplate.demo;

import android.widget.Toast;

import com.lalosoft.webapptemplate.WebAppActivity;

public class MainActivity extends WebAppActivity {

    @Override
    public String getRootUrl() {
        return "http://www.google.com.ar";
    }

    @Override
    public void onNetworkConnectionDisabled() {
        Toast.makeText(this, "There is no network connection", Toast.LENGTH_SHORT).show();
    }
}
