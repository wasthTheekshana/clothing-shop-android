package com.theekshana.onlineshop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbname="ProductCart";

    public DBHelper(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Product(id integer primary key autoincrement,pId TEXT, name TEXT , price TEXT,qty TEXT,image TEXT)");
    }
//ProductCart
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Product");
        onCreate(DB);
    }

    public Boolean insertuserdata(String pId ,String name, String contact,String qty,String image) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pId", pId);
        contentValues.put("name", name);
        contentValues.put("price", contact);
        contentValues.put("qty", qty);
        contentValues.put("image", image);
        long result = DB.insert("Product", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


//    public Boolean updateuserdata(String name, String contact) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("price", contact);
//        Cursor cursor = DB.rawQuery("Select * from ProductCart where pId = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//            long result = DB.update("ProductCart", contentValues, "pId=?", new String[]{name});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }
    public boolean delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE  from Product");
        return true;
    }

    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Product where pId = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Product", "pId=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Product", null);
        return cursor;

    }
}