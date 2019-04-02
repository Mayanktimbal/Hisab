package com.mayanktimbal.notes;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.FileReader;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    TextView nameplate;
    String data;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ImageView share;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        nameplate = (TextView) findViewById(R.id.textView6);
        share = (ImageView) findViewById(R.id.imageView2);
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
       bundle = getIntent().getExtras();
        nameplate.setText((CharSequence) bundle.get("name"));
         data = "";
        try {
            FileReader Reader = new FileReader((String) bundle.get("path"));
            BufferedReader bufferedReader = new BufferedReader(Reader);
            String temp;

            while ((temp = bufferedReader.readLine()) != null) {
data+=(temp+"\n");
                list.add(temp);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareit(data);
            }
        });

    }


    private void shareit(String path) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT,path);
        startActivity(Intent.createChooser(sharingIntent,"share file with"));

    }
}