package com.example.pomodoro_study;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ProjectDataBaseHelper extends SQLiteOpenHelper {

    public static class Flashcard{
        public String category;
        public String question;
        public String answer;
    }

    // Corrected the constructor by adding the missing parameters and curly brace.
    public ProjectDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Add the SQL statement to create your tables
        String CREATE_FLASHCARD_TABLE = "CREATE TABLE flashcards (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TEXT," +
                "question TEXT," +
                "answer TEXT " +
                ")";

        db.execSQL(CREATE_FLASHCARD_TABLE);

        // Insert flashcard data into the flashcards table is being called (:
        insertFlashcards(db);
    }

    // This method is the array of flashcard data
    private void insertFlashcards(SQLiteDatabase db) {
        // Array of flashcard data
        Object[][] flashcards = {
                {"Computer Science", "What is a function in programming?", "A function is a block of organized, reusable code that is used to perform a single, related action."},
                {"Computer Science", "What does HTML stand for?", "Hyper Text Markup Language."},
                {"Law", "What is the principle of \"Double Jeopardy\"?", "A legal doctrine that prevents an accused person from being tried again on the same (or similar) charges following a legitimate acquittal or conviction."},
                {"Law", "What is a tort?", "A tort is an act or omission that gives rise to injury or harm to another and amounts to a civil wrong for which courts impose liability."},
                {"Biology", "What is photosynthesis?", "Photosynthesis is the process by which green plants and some other organisms use sunlight to synthesize foods from carbon dioxide and water."},
                {"Biology", "What is an ecosystem?", "An ecosystem is a community of living organisms in conjunction with the nonliving components of their environment, interacting as a system."},
                {"Business", "What is market segmentation?", "Market segmentation is the process of dividing a market of potential customers into groups, or segments, based on different characteristics."},
                {"Business", "What is a balance sheet?", "A balance sheet is a financial statement that reports a company's assets, liabilities, and shareholders' equity at a specific point in time."}
        };


        // Putting flashcard data into sqlite
        // Gets the data repository in write mode
        db.beginTransaction();
        try {
            for (Object[] flashcard : flashcards) {
                ContentValues values = new ContentValues();
                values.put("category", (String) flashcard[0]);
                values.put("question", (String) flashcard[1]);
                values.put("answer", (String) flashcard[2]);
                db.insert("flashcards", null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called when the database needs to be upgraded
    }

    /**
     * Crud operations
     *  Read, implement methods to query flashcards(category)
     *  Create an arraylist of categories ... like computer science first
     *
     *  method will query SQLite database to retrieve all unique categories
     *  from the flashcards table and return them in an ArrayList<String>.
     */
    @SuppressLint("Range")
    public ArrayList<String> getCategories(){
        //Create arraylist categories
        ArrayList<String> categories = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT CATEGORY FROM flashcards";
        // Open the sql db with this -> getReadableDatabase()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                //Extracting category and adding to the list
                categories.add(cursor.getString(cursor.getColumnIndex("category")));
                // Print if the arraylist has been populated with logcat
                // To log you need to add a TAG and MSG
                Log.d("DatabaseDebug", "Category added:" + categories.size());
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // Print total number of categories fetched
        Log.d("DataDebug","Total number of categories:" + categories.size());
        return categories;
    }


    public ArrayList<Flashcard> getFlashcardByCategory(String category){
        return null;
    }


}
