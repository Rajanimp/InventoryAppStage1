package com.example.inventoryappstage1.data;

import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }

    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME = "book_name";
        public static final String COLUMN_BOOK_AUTHOR = "book_author";
        public static final String COLUMN_BOOK_PRICE = "book_price";
        public static final String COLUMN_BOOK_QUANTITY = "book_quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
    }
}
