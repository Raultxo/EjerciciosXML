package com.example.ejerciciosxml;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ejercicio1(View view) {
        Intent intent = new Intent(MainActivity.this, Ejercicio1.class);
        startActivity(intent);
    }

    public void ejercicio2(View view) {
        Intent intent = new Intent(MainActivity.this, Ejercicio2.class);
        startActivity(intent);
    }
}