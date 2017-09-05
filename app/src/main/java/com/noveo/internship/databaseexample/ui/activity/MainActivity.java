package com.noveo.internship.databaseexample.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.noveo.internship.databaseexample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_button_ormlite)
    public void onOrmLiteButtonClick(View view) {
        startActivity(new Intent(MainActivity.this, ORMLiteActivity.class));
    }

    @OnClick(R.id.main_button_sqlite)
    public void onSqlButtonClick(View view) {
        startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
    }
}
