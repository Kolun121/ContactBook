package com.example.contactbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {
    public EditText et_name, et_surname, et_patronymic, et_email, et_phone;
    public Button add_contact;


    DB dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHandler = new DB(this);

        et_name = findViewById(R.id.name);
        et_surname = findViewById(R.id.surname);
        et_patronymic = findViewById(R.id.patronymic);
        et_email = findViewById(R.id.email);
        et_phone = findViewById(R.id.phone);

        add_contact = findViewById(R.id.add_contact);

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String surname = et_surname.getText().toString();
                String patronymic = et_patronymic.getText().toString();
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();

                if(
                        !TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(surname)
                        && !TextUtils.isEmpty(patronymic)
                        && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(phone)
                )
                {
                    Contact contact = new Contact(name, surname, patronymic, email, phone);
                    dbHandler.addContact(contact);
                    startActivity(new Intent(AddContact.this, MainActivity.class));
                    finish();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddContact.this);
                    builder.setMessage("Заполните поля!")
                            .setNegativeButton("OK", null)
                            .show();
                }
            }
        });
    }
}
