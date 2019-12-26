package com.example.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_Contacts";
    private static final String DB_TABLE = "contacts";

    public DB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DB_TABLE +
                " (id INTEGER PRIMARY KEY autoincrement, name TEXT, surname TEXT, patronymic TEXT, email TEXT, phone TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE " + DB_TABLE;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", contact.getName());
        values.put("surname", contact.getSurname());
        values.put("patronymic", contact.getPatronymic());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());

        db.insert(DB_TABLE, null, values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                DB_TABLE,
                new String[]{"id", "name", "surname", "patronymic", "email", "phone"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        Contact contact;

        if(cursor != null){
            cursor.moveToFirst();
            contact = new Contact(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            db.close();
            cursor.close();
            return contact;
        }else {
            db.close();
            cursor.close();
            return null;
        }
    }

    public List<Contact> getAllContacts(){
        SQLiteDatabase db = getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        String query = "SELECT * FROM " + DB_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();

            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setSurname(cursor.getString(2));
                contact.setPatronymic(cursor.getString(3));
                contact.setEmail(cursor.getString(4));
                contact.setPhone(cursor.getString(5));
                contacts.add(contact);
            }while(cursor.moveToNext());


        }

        return contacts;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("surname", contact.getSurname());
        values.put("patronymic", contact.getPatronymic());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());

        return db.update(DB_TABLE, values, "id = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(DB_TABLE, "id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getContactCount(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}
