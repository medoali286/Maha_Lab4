package com.cst2335.maha_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et_email=findViewById(R.id.et_email);


        Button btn=findViewById(R.id.login_button);
        btn.setOnClickListener(c->{

Intent nextPage=new Intent(this,SecondActivity.class);
nextPage.putExtra("EmailAddress",et_email.getText().toString());
startActivity(nextPage);

        });

    }





}