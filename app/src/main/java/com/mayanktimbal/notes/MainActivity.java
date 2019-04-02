package com.mayanktimbal.notes;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
String path;
    EditText edit;
    EditText search;
    TextView text ;
    ListView notes;
    static ArrayList<String> filename;
    ArrayList<String> delete;
    ArrayList<HashMap<String,String>> data= new ArrayList<>();
   static MyCustomAdapter customAdapter;
    File location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename= new ArrayList<>();
delete= new ArrayList<>();
        data = new ArrayList<>();
text= (TextView) findViewById(R.id.textView);
        notes = (ListView) findViewById(R.id.note);
        edit = (EditText) findViewById(R.id.editText);
  search= (EditText) findViewById(R.id.Searchtext);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);




        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

         location = new File (path+"/notes");
        if(!location.exists())
        {location.mkdir();}





        File[] list =location.listFiles();
        for(File file : list)
        {

             if (file.isFile())
            {
                filename.add(file.getName());

            }
        }

        customAdapter= new MyCustomAdapter(filename,getApplicationContext());
          notes.setAdapter(customAdapter);

notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        File filename1 = new File(path+"/notes/"+MyCustomAdapter.list.get(i));
        try {
            FileInputStream filenameIn = new FileInputStream(filename1);
            ObjectInputStream filein = new ObjectInputStream(filenameIn);
            data = (ArrayList<HashMap<String, String>>) filein.readObject();
            filein.close();
            filenameIn.close();

            Intent main = new Intent(getApplicationContext(),Main2Activity.class);
            main.putExtra("name",data.get(0));
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(main);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
});


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void AddNew(View view) throws IOException {


String na = String.valueOf(edit.getText());

        if(edit.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();
        }

else {
            File hisab = new File(path + "/notes/" + na);
            if (!hisab.exists()) {
                ArrayList<HashMap<String, String>> hash = new ArrayList<>();
                HashMap<String, String> list1 = new HashMap<>();
                list1.put("name", na);
                list1.put("Total", "0");
                hash.add(list1);

                FileOutputStream filenameOut = new FileOutputStream(hisab.getAbsolutePath());

                ObjectOutputStream nameout = new ObjectOutputStream(filenameOut);

                nameout.writeObject(hash);

                nameout.close();

                filenameOut.close();

                Toast.makeText(getApplicationContext(), "New Hisab  Created", Toast.LENGTH_SHORT).show();
                File[] list = location.listFiles();
                filename.clear();
                delete.clear();
                for (File file : list) {

                    if (file.isFile()) {
                        filename.add(file.getName());

                    }
                }
                customAdapter.Onadd();

                edit.setText("");
            }
            else {
                Toast.makeText(getApplicationContext(), "Hisab Already exists!", Toast.LENGTH_SHORT).show();
                edit.setText("");
            }
        }



    }


    public void calc(View view) {

        Intent main = new Intent(getApplicationContext(),calcultor.class);

        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(main);
    }
}


