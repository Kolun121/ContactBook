package com.example.contactbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {
    DB dbHandler;

    public TextView v_name, v_surname, v_patronymic, v_email, v_phone;
    public Button update_contact, delete_contact, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        dbHandler = new DB(this);

        Intent intent = getIntent();
        String stringId = intent.getStringExtra("contactId");

        int contactId = Integer.valueOf(stringId);

        update_contact = findViewById(R.id.update_contact_activity);
        delete_contact = findViewById(R.id.delete_contact);
        back = findViewById(R.id.back);

        v_name = findViewById(R.id.name_view);
        v_surname = findViewById(R.id.surname_view);
        v_patronymic = findViewById(R.id.patronymic_view);
        v_email = findViewById(R.id.email_view);
        v_phone = findViewById(R.id.phone_view);

        final Contact contact = dbHandler.getContact(contactId);

        v_name.setText(contact.getName());
        v_surname.setText(contact.getSurname());
        v_patronymic.setText(contact.getPatronymic());
        v_email.setText(contact.getEmail());
        v_phone.setText(contact.getPhone());

        update_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContact.this, UpdateActivity.class);
                intent.putExtra("contactId", String.valueOf(contact.getId()));
                startActivity(intent);
                finish();
            }
        });

        delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteContact(contact);
                startActivity(new Intent(ViewContact.this, MainActivity.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewContact.this, MainActivity.class));
                finish();
            }
        });

    }
}
