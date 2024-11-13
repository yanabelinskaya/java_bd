package com.example.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBadeHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_db";
    private static final int SCHEMA = 1;
    static final String TABLE_NAME = "books";
    public static final String COLUMN_NAME = "book_name";
    public static final String COLUMN_ID = "id_book";
    public static final String COLUMN_AUTHOR = "book_author";

    public DataBadeHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_NAME + " TEXT, "
        + COLUMN_AUTHOR + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long addBook(String bookName, String bookAuthor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName);
        values.put(COLUMN_AUTHOR, bookAuthor);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
    public Cursor getAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query( TABLE_NAME,null, null,null,null,null,null,null);
    }
    public int deleteBook(long bookId){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }
    public int updateBook(int id, String bookName, String bookAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName);
        values.put(COLUMN_AUTHOR, bookAuthor);

        int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
