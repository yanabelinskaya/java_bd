// DetailActivity.java
package com.example.bd;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewAuthor;
    private Button deleteButton;
    private DataBadeHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        deleteButton = findViewById(R.id.delete_button);
        dbHelper = new DataBadeHelper(this);

        // Получение ID книги из Intent
        bookId = getIntent().getIntExtra("ITEM_ID", -1);
        loadBookDetails(bookId);

        // Обработка клика на кнопку удаления
        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteBook(bookId);
            finish(); // Закрыть DetailActivity после удаления
        });
    }

    private void loadBookDetails(int id) {
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor.moveToFirst()) {
            do {
                int currentId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBadeHelper.COLUMN_ID));
                if (currentId == id) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBadeHelper.COLUMN_NAME));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBadeHelper.COLUMN_AUTHOR));
                    textViewTitle.setText(name);
                    textViewAuthor.setText(author);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}