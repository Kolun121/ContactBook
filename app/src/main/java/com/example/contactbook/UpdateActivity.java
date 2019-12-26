package com.example.contactbook;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    DB dbHandler;

    public EditText up_name, up_surname, up_patronymic, up_email, up_phone;
    public Button update_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHandler = new DB(this);

        Intent intent = getIntent();
        String stringId = intent.getStringExtra("contactId");

        final int contactId = Integer.valueOf(stringId);

        update_contact = findViewById(R.id.update_contact);

        up_name = findViewById(R.id.name_update);
        up_surname = findViewById(R.id.surname_update);
        up_patronymic = findViewById(R.id.patronymic_update);
        up_email = findViewById(R.id.email_update);
        up_phone = findViewById(R.id.phone_update);

        final Contact contact = dbHandler.getContact(contactId);

        up_name.setText(contact.getName());
        up_surname.setText(contact.getSurname());
        up_patronymic.setText(contact.getPatronymic());
        up_email.setText(contact.getEmail());
        up_phone.setText(contact.getPhone());

        update_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = up_name.getText().toString();
                String surname = up_surname.getText().toString();
                String patronymic = up_patronymic.getText().toString();
                String email = up_email.getText().toString();
                String phone = up_phone.getText().toString();

                if(
                        !TextUtils.isEmpty(name)
                                && !TextUtils.isEmpty(surname)
                                && !TextUtils.isEmpty(patronymic)
                                && !TextUtils.isEmpty(email)
                                && !TextUtils.isEmpty(phone)
                )
                {
                    Contact contactTemp = new Contact(contactId, name, surname, patronymic, email, phone);
                    dbHandler.updateContact(contactTemp);

                    Intent intent = new Intent(UpdateActivity.this, ViewContact.class);
                    intent.putExtra("contactId", String.valueOf(contact.getId()));
                    startActivity(intent);
                    finish();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage("Заполните поля!")
                            .setNegativeButton("OK", null)
                            .show();
                }
            }
        });
    }
}
