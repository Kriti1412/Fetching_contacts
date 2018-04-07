package com.example.task2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button fetching;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = new Intent(MainActivity.this, contacts.class);

        fetching = (Button) findViewById(R.id.fetch);
        fetching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissionToReadUserContacts();
            }
        });
    }
    private static final int MULTIPLE_PERMISSIONS_REQUEST = 1;

    public void getPermissionToReadUserContacts() {
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                 != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MULTIPLE_PERMISSIONS_REQUEST);
            return;
        }
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS_REQUEST) {
            if (grantResults.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(this, "Read Contacts & write storage permission granted", Toast.LENGTH_SHORT).show();
                startActivity(i);
            } else {
                Toast.makeText(this, "Read Contacts & write storage permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
