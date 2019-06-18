package com.mayanktimbal.notes;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.mayanktimbal.notes.MainActivity.customAdapter;

public class Main2Activity extends AppCompatActivity {
TextView Nametext;
    TextView devana;
    TextView levana;
    File root;
    String path;
    TextView current;
    EditText lev;
    EditText dev;
    EditText com;
    int total;
    String name;
    String special;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Nametext = (TextView) findViewById(R.id.textView);
        levana= (TextView) findViewById(R.id.textView2);
        devana= (TextView) findViewById(R.id.textView3);
        current = (TextView) findViewById(R.id.textView5);
com= (EditText) findViewById(R.id.editText5);
        lev=(EditText) findViewById(R.id.editText2);
        dev=(EditText) findViewById(R.id.editText3);
        Bundle bundle = getIntent().getExtras();
            HashMap<String,String> data = (HashMap<String, String>) bundle.get("name");
        Nametext.setText(data.get("name"));
        special=data.get("name");
        name=data.get("name");
        current.setText(data.get("Total"));
        total= Integer.parseInt(data.get("Total"));

        lev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
if(!lev.getText().toString().isEmpty()) {

    dev.setEnabled(false);
    dev.setVisibility(View.GONE);
    devana.setVisibility(View.GONE);
    dev.setText("");
}

                else if(lev.getText().toString().isEmpty())
{
    devana.setVisibility(View.VISIBLE);
    dev.setEnabled(true);
    dev.setVisibility(View.VISIBLE);
}

            }
        });

        dev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!dev.getText().toString().isEmpty()) {

                    lev.setEnabled(false);
                    lev.setVisibility(View.GONE);
                    levana.setVisibility(View.GONE);
                    lev.setText("");
                }

                else if(lev.getText().toString().isEmpty())
                {
                    levana.setVisibility(View.VISIBLE);
                    lev.setEnabled(true);
                    lev.setVisibility(View.VISIBLE);
                }
            }
        });





    }

    public void Add(View view) throws IOException {
        String category="";
        String rupee="";

        if ( !lev.getText().toString().matches("")
                && !dev.getText().toString().matches("")   )
        {
            Toast.makeText(getApplicationContext(),"Ek samaye Ekj WORk" , Toast.LENGTH_SHORT).show();
        }

        else if(String.valueOf(lev.getText()).equals("") && String.valueOf(dev.getText()).equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter levana or devana" , Toast.LENGTH_SHORT).show();
        }
        else if(String.valueOf(com.getText()).equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Comment!" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!String.valueOf(lev.getText()).equals(""))
            {
                total = total + Integer.parseInt(lev.getText().toString());
                category="levana";
                rupee=String.valueOf(lev.getText());
            }
            else if(!String.valueOf(dev.getText()).equals(""))
            {
                total = total - Integer.parseInt(dev.getText().toString());
                category="devana";
                rupee=String.valueOf(dev.getText());
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Kaik to Vandho chhe" , Toast.LENGTH_SHORT).show();
            }
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";



            ArrayList<HashMap<String,String>> hash =  new ArrayList<>();
            HashMap<String,String> list1 = new HashMap<>();
            list1.put("name",name);
            list1.put("Total",Integer.toString(total));
            hash.add(list1);

            FileOutputStream filenameOut = new FileOutputStream(path+"/notes/"+name);

            ObjectOutputStream nameout = new ObjectOutputStream(filenameOut);

            nameout.writeObject(hash);

            nameout.close();

            filenameOut.close();

            Toast.makeText(getApplicationContext(),"Hisab Updated" , Toast.LENGTH_SHORT).show();

            current.setText(Integer.toString(total));
            customAdapter.Onadd();
            lev.setText("");
            dev.setText("");




            String Entrydate=new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());

            String comment= com.getText().toString();
            String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
boolean Append;
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
root = new File(path+"/notes/historydata/"+special); // logfile path
         File   dir= new File(path+"/notes/historydata");  // logfiles patrh
            if(!dir.exists() )
            {
               dir.mkdirs();
            }

            if(root.exists())
            {

                Append=true;
            }

            else
            {
              root.createNewFile();
                Append=false;
            }


                FileWriter writer = new FileWriter(path + "/notes/historydata/" + name, Append);
                writer.append(category + ": " + rupee + " for " + comment + " at " + Entrydate + " " + time + " then total is " + total + "\n");
                writer.close();
            com.setText("");

        }
    }

    public void history(View view) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
       root = new File(path+"notes/historydata/"+ special);    // logfile             pathj

        if(!root.exists())
        {
            Toast.makeText(getApplicationContext(),"No History Available!" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent main = new Intent(this,Main3Activity.class);
            main.putExtra("path",root.getAbsolutePath());
            main.putExtra("name",name);
            startActivity(main);
        }


    }
}
