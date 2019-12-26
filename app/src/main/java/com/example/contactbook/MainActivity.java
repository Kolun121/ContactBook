package com.example.contactbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView contactsList;
    public Button addContact;

    public List<Contact> contacts;

    public DB dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DB(this);

        contactsList = findViewById(R.id.contacts_list);
        addContact = findViewById(R.id.add_contact_activity);

        contacts = new ArrayList<>();
        contacts = dbHandler.getAllContacts();

        String[] fullNames = new String[contacts.size()];

        for(int i = 0; i < contacts.size(); i++){
            fullNames[i] = contacts.get(i).getSurname() + " " + contacts.get(i).getName() + " " + contacts.get(i).getPatronymic();
            Log.d("++++++++++++++++",String.valueOf(i) );
            Log.d("wwwwwwwwwwwwwwww",contacts.get(i).getName() );
            Log.d("----------------",fullNames[i] );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fullNames);
        contactsList.setAdapter(adapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);
                Intent intent = new Intent(MainActivity.this, ViewContact.class);
                intent.putExtra("contactId", String.valueOf(contact.getId()));
                startActivity(intent);
                finish();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
