package com.mayanktimbal.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mayank on 12/7/2018.
 */


public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    public   static ArrayList<String> list;

    ArrayList<String> data1 = new ArrayList<>();
    private Context context;
    String path;
    ArrayList<HashMap<String,String>> data= new ArrayList<>();


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;

        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
       return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    public Filter  getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                charSequence = charSequence.toString().toLowerCase();
                data1.clear();
                for (int i = 0; i < MainActivity.filename.size(); i++) {
                    if (MainActivity.filename.get(i).toLowerCase().startsWith(charSequence.toString())) {
                        data1.add(MainActivity.filename.get(i));
                    }
                }

                results.count = data1.size();
                results.values = data1;


                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
              if(charSequence.length()>0 && data1.size()<=0)
              {
                  Toast.makeText(context, "No Hisab Found", Toast.LENGTH_SHORT).show();
                  list=data1;
                  notifyDataSetChanged();
              }


                else if(data1.size()<=0)
              {
                  list=MainActivity.filename;
                  notifyDataSetChanged();
              }

              else {
                  list = data1;
                  notifyDataSetChanged();
              }
            }
        };
        return filter;
    }

public  void Onadd()
    {
     list=MainActivity.filename;
        notifyDataSetChanged();

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
TextView total= (TextView)view.findViewById(R.id.Total) ;
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        File filename1 = new File(path+"/notes/"+list.get(position));

            FileInputStream filenameIn = null;
            try {
                filenameIn = new FileInputStream(filename1);
                ObjectInputStream filein = new ObjectInputStream(filenameIn);
                data = (ArrayList<HashMap<String, String>>) filein.readObject();
                total.setText(data.get(0).get("Total"));
                filein.close();
                filenameIn.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            //Handle buttons and add onClickListeners
       ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);




        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        final File location = new File (path+"/notes");

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                builder.setTitle("delete Entry");
                builder.setMessage("Are you sure you want to delete "+list.get(position));
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int z) {
                        File filename1 = new File(path+"/notes/"+list.get(position));
                        filename1.delete();
                        File[] list1 =location.listFiles();
                        list.clear();

                        for(File file : list1)
                        {

                            if (file.isFile())
                            {
                                list.add(file.getName());

                            }
                        }
                        notifyDataSetChanged();



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int z) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                alertDialog.show();



            }
        });

              return view;
    }
}
