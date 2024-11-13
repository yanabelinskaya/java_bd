package com.example.bd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bd.DataBadeHelper;
import com.example.bd.MainActivity;
import com.example.bd.R;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextName, editTextAuthor;
    private Button addButton;
    private DataBadeHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        addButton = findViewById(R.id.add);

        dbHelper = new DataBadeHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToDatabase();
            }
        });
    }

    private void addBookToDatabase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addBook(bookName, bookAuthor);

        if (result > 0) {
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddBookActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Ошибка добавления книги", Toast.LENGTH_SHORT).show();
        }
    }
}