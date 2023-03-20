package com.example.taskmanager;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {
    public static final String FILENAME = "Listinfo.dat";
    public  static void WriteData(ArrayList<String> item, Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
        ObjectOutputStream obj = new ObjectOutputStream(fos);
        obj.writeObject(item);
        obj.close();
    }
    public  static  ArrayList<String> ReadData(Context context) throws IOException, ClassNotFoundException {
        ArrayList<String> itemList = null;
        try
        {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream obs = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) obs.readObject();
        }
        catch (FileNotFoundException e)
        {
            itemList = new ArrayList<>();
        }

        return  itemList;


    }
}
