// EditBookActivity.java
package com.example.bd;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor;
    private Button buttonSave;
    private DataBadeHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextName = findViewById(R.id.editTextBookName);
        editTextAuthor = findViewById(R.id.editTextBookAuthor);
        buttonSave = findViewById(R.id.buttonSave);
        dbHelper = new DataBadeHelper(this);

        // Получение ID книги из Intent
        bookId = getIntent().getIntExtra("ITEM_ID", -1);
        loadBookDetails(bookId);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String author = editTextAuthor.getText().toString();
            if (dbHelper.updateBook(bookId, name, author) > 0) {
                Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                finish(); // Закрыть активность после сохранения
            } else {
                Toast.makeText(this, "Ошибка при обновлении книги", Toast.LENGTH_SHORT).show();
            }
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
                    editTextName.setText(name);
                    editTextAuthor.setText(author);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}