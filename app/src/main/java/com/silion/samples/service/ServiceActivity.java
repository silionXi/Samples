package com.silion.samples.service;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.silion.samples.R;

/**
 * Created by silion on 2015/11/6.
 */
public class ServiceActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Button startServiceButton = (Button) findViewById(R.id.startServiceButton);
        startServiceButton.setOnClickListener(this);
        Button stopServiceButton = (Button) findViewById(R.id.stopServiceButton);
        stopServiceButton.setOnClickListener(this);
        updateActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startServiceButton: {
                Intent service = new Intent();
                service.setClass(this, SamplesService.class);
                startService(service);
                break;
            }
            case R.id.stopServiceButton: {
                Intent service = new Intent();
                service.setClass(this, SamplesService.class);
                stopService(service);
                break;
            }
            default:
                break;
        }
    }

    public void updateActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
    }
}
