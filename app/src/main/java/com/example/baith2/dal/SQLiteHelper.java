package com.example.baith2.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.baith2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ChiTieu.db";
    private  static  int Database_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, Database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT,category TEXT,price TEXT,date TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sq = getReadableDatabase();
        String order =  "date DESC";
        Cursor rs = sq.query("items",null,null,null,null, null,order );
        while (rs !=null && rs.moveToNext()) {
            int id  = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);

            String price = rs.getString(3);

            String date = rs.getString(4);
             list.add(new Item( id, title,category,price,date));
        }
        return  list;
    }

    public Long insertToDB(Item item) {
        SQLiteDatabase sq = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.getTitle());
        contentValues.put("category", item.getCategory());
        contentValues.put("price", item.getPrice());
        contentValues.put("date", item.getDate());
        return sq.insert("items", null, contentValues);
    }

    public List<Item> getItemByDate(String date) {
        Log.i("Date",date);

        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items", null, whereClause, whereArgs,null,null,null);
        while (rs != null && rs.moveToNext()) {
            int id  = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item( id, title,category,price,date));
        }
        return  list;
    }

}
