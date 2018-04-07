package com.example.task2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class contacts extends AppCompatActivity{
    ArrayList<String> StoreContacts ;
    String name, number ;
    Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        Intent i = getIntent();
        StoreContacts = new ArrayList<String>();
        context = getBaseContext();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                 null,null, null, null);

        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String s = name + "      -----      " + number;
            StoreContacts.add(s);
        }

        cursor.close();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.item_list, R.id.name, StoreContacts);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        CSVWriter writer;
        final LinearLayout linLayout = (LinearLayout) findViewById(R.id.linearLayout);
        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv");
        try
        {
            writer = new CSVWriter(new FileWriter(csv), ',');
            String[] storeArray = new String[StoreContacts.size()];
            storeArray = StoreContacts.toArray(storeArray);
            writer.writeNext(storeArray);
            Snackbar snackbar1 = Snackbar.make(linLayout, "File Successfully Created in Interna Storage", Snackbar.LENGTH_LONG);
            snackbar1.show();
            writer.close();
        }
        catch (IOException e)
        {
            Log.e("csv",e.getMessage());
        }
    }
}
