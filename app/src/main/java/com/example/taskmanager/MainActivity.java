package com.example.taskmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText itemText;
    Button addBtn;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemText = findViewById(R.id.editTextTextPersonName2);
        addBtn = findViewById(R.id.button2);
        listView = findViewById(R.id.listView);

        try {
            itemList = FileHelper.ReadData(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.custom_textview,R.id.mytextview,itemList);

        listView.setAdapter(arrayAdapter);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (itemText.getText().toString().isEmpty()) {
                    return;
                }
                String item = itemText.getText().toString();
                itemList.add(item);
                itemText.setText("");

                //Toast.makeText(MainActivity.this, "message " + itemText.getText().toString(), Toast.LENGTH_SHORT).show();
                try {
                    FileHelper.WriteData(itemList, getApplicationContext());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                arrayAdapter.notifyDataSetChanged();


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item?");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        try {
                            FileHelper.WriteData(itemList, getApplicationContext());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });


    }
}