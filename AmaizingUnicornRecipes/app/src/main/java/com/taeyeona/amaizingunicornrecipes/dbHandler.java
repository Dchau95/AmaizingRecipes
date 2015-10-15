package com.taeyeona.amaizingunicornrecipes;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

/**
 * Database handler class
 *
 * <p> Creates a handle to create, store, and call from the database file ingredients.db.
 * Has globals: DATABASE_VERSION, DATABASE_NAME, TABLE_INGREDIENTS, COLUMN_ID,
 * COLUMN_INGREDIENTNAME to store relative fields. Connects to Ingredients class in order
 * to organize ingredients object.</p>
 *
 * @author Anna Sever
 * @version 1.0
 */
public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ingredients.db";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INGREDIENTNAME = "ingredientname";

    /**
     * Creates a basic handle for the database.
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Override for onCreate so query is created when app opened
     * @param db A SQLite database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_INGREDIENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INGREDIENTNAME + " TEXT " + ");";
        db.execSQL(query);

    }

    /**
     * Reads from db file when app reopened.
     * @param db SQLiteDatabase object
     * @param oldVersion int current table version
     * @param newVersion int updated table version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);

    }

    /**
     * Adds a row to table
     * @param ingredients Ingredients Used to store new ingredient
     */
    public void addIngredient(Ingredients ingredients){
        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENTNAME, ingredients.getIngredientName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INGREDIENTS, null, values);
        db.close();
    }

    /**
     * Delete a ingredient from a database.
     * @param ingredientName Name of ingredient to delete
     */
    public void deleteIngredient(String ingredientName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_INGREDIENTS + " WHERE " + COLUMN_INGREDIENTNAME + "=\""
                + ingredientName + "\";");
    }

    /**
     * Print out database as a string storage/passing
     * @return dbString Database stored as a string.
     */
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * FROM " + TABLE_INGREDIENTS + " WHERE 1";

        //cursor point to a location in your results
        Cursor cursor = db.rawQuery(query, null);

        //move to the first row in your results
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            if(cursor.getString(cursor.getColumnIndex("ingredientname")) != null) {
                dbString += cursor.getString(cursor.getColumnIndex("ingredientname"));
                dbString += "\n";
            }
            cursor.moveToNext();
        }
        db.close();
        return dbString;
    }
}
