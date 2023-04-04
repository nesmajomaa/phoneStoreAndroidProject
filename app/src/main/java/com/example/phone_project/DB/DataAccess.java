package com.example.phone_project.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.phone_project.Model.Phone;


import java.util.ArrayList;
import java.util.Currency;

public class DataAccess extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DataAccess(@Nullable Context context) {
        super(context, "StudentDB", null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Phone.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Phone.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //---------------------------------------------------------

    public boolean insertStudent(int id,String name, String descriptions,int quantity,double price,String image) {
        ContentValues cv = new ContentValues();
        cv.put(Phone.COL_ID, id);
        cv.put(Phone.COL_NAME, name);
        cv.put(Phone.COL_DESCRIPTION, descriptions);
        cv.put(Phone.COL_QUANTITY, quantity);
        cv.put(Phone.COL_PRICE, price);
        cv.put(Phone.COL_IMAGE, image);
        return db.insert(Phone.TABLE_NAME, null, cv) > 0;
    }


    @SuppressLint("Range")
    public ArrayList<Phone> getAllPhones() {
        ArrayList<Phone> data = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Phone.TABLE_NAME, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Phone phone = new Phone();
            phone.setId(c.getInt(c.getColumnIndex(Phone.COL_ID)));
            phone.setName(c.getString(c.getColumnIndex(Phone.COL_NAME)));
            phone.setDescription(c.getString(c.getColumnIndex(Phone.COL_DESCRIPTION)));
            phone.setQuantity(c.getInt(c.getColumnIndex(Phone.COL_QUANTITY)));
            phone.setPrice(c.getDouble(c.getColumnIndex(Phone.COL_PRICE)));
            phone.setImg(c.getString(c.getColumnIndex(Phone.COL_IMAGE)));
            data.add(phone);
            c.moveToNext();
        }
        c.close();
        return data;
    }

    public boolean deleteStudent() {
        //return db.delete(Student.TABLE_NAME, "id = ? and name = ?",new String[]{String.valueOf(id),name}) > 0;
        return db.delete(Phone.TABLE_NAME, null, null) > 0;
    }

    public boolean updateStudent(int OldId, String name, String descriptions,int quantity,double price,String image) {
        ContentValues cv = new ContentValues();
        cv.put(Phone.COL_NAME, name);
        cv.put(Phone.COL_DESCRIPTION, descriptions);
        cv.put(Phone.COL_QUANTITY, quantity);
        cv.put(Phone.COL_PRICE, price);
        cv.put(Phone.COL_IMAGE, image);
        return db.update(Phone.TABLE_NAME, cv, "id = " + OldId, null) > 0;
    }
}
