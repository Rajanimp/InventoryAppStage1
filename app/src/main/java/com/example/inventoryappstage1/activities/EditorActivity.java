package com.example.inventoryappstage1.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventoryappstage1.R;
import com.example.inventoryappstage1.data.BookContract.BookEntry;
import com.example.inventoryappstage1.data.BookDbHelper;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    private EditText etxtBookName;
    private EditText etxtBookAuthor;
    private EditText etxtBookPrice;
    private EditText etxtBookQuantity;
    private EditText etxtSupplierName;
    private EditText etxtSupplierPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.tb_editor);
        toolbar.setTitle(R.string.toolbar_add_item);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etxtBookName = findViewById(R.id.etxt_book_name);
        etxtBookAuthor = findViewById(R.id.etxt_book_author);
        etxtBookPrice = findViewById(R.id.etxt_book_price);
        etxtBookQuantity = findViewById(R.id.etxt_book_quantity);
        etxtSupplierName = findViewById(R.id.etxt_supplier_name);
        etxtSupplierPhoneNumber = findViewById(R.id.etxt_supplier_number);
    }

    private void insertData() {
        String bookName = etxtBookName.getText().toString().trim();
        String bookAuthor = etxtBookAuthor.getText().toString().trim();
        int bookPrice = Integer.parseInt(etxtBookPrice.getText().toString().trim());
        int bookQuantity = Integer.parseInt(etxtBookQuantity.getText().toString().trim());
        String supplierName = etxtSupplierName.getText().toString().trim();
        String supplierPhoneNumber = etxtSupplierPhoneNumber.getText().toString().trim();

        BookDbHelper mDbHelper = new BookDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, bookName);
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, bookAuthor);
        values.put(BookEntry.COLUMN_BOOK_PRICE, bookPrice);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, bookQuantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error saving books details", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and display a toast with the row ID.
            Toast.makeText(this, "Book details saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                //Save data to database
                insertData();
                //Exit activity
                finish();
                return true;
            case R.id.action_delete:
                //Do nothing as of now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
