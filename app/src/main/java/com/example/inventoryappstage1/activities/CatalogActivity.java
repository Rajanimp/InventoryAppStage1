package com.example.inventoryappstage1.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.inventoryappstage1.R;
import com.example.inventoryappstage1.data.BookContract.BookEntry;
import com.example.inventoryappstage1.data.BookDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Toolbar toolbar = findViewById(R.id.tb_catalog);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fab.setTooltipText(getString(R.string.fab_tooltip));
        }
        //To open EditorActivity to add items
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BookDbHelper(this);

        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        Cursor cursor = queryData();
        int number = cursor.getCount();
        TextView displayView = findViewById(R.id.tv_display);
        displayView.setMovementMethod(new ScrollingMovementMethod());
        displayView.setText(String.format(getResources().getQuantityString((R.plurals.display_text), number, number)));

        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int bookNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int bookAuthorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int bookPriceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int bookQuantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        try {
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentBookName = cursor.getString(bookNameColumnIndex);
                String currentBookAuthor = cursor.getString(bookAuthorColumnIndex);
                int currentBookPrice = cursor.getInt(bookPriceColumnIndex);
                int currentBookQuantity = cursor.getInt(bookQuantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

                displayView.append("\n" + BookEntry._ID + "  : " + currentID + "\n" + BookEntry.COLUMN_BOOK_NAME + "  : " + currentBookName + "\n" + BookEntry.COLUMN_BOOK_AUTHOR + "  : " + currentBookAuthor + "\n" + BookEntry.COLUMN_BOOK_PRICE + "  : " + currentBookPrice + "\n" + BookEntry.COLUMN_BOOK_QUANTITY + "  : " + currentBookQuantity + "\n" + BookEntry.COLUMN_SUPPLIER_NAME + "  : " + currentSupplierName + "\n" + BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "  : " + currentSupplierPhoneNumber + "\n");
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor queryData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {BookEntry._ID, BookEntry.COLUMN_BOOK_NAME, BookEntry.COLUMN_BOOK_AUTHOR, BookEntry.COLUMN_BOOK_PRICE, BookEntry.COLUMN_BOOK_QUANTITY, BookEntry.COLUMN_SUPPLIER_NAME, BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER};

        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Get id of item selected on Menu
        int id = item.getItemId();
        if (id == R.id.action_delete_all_entries) {
            //Do nothing as of now
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Refresh after adding data
        displayDatabaseInfo();
    }
}
