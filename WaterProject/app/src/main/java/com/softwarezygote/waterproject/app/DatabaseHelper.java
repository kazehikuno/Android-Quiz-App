package com.softwarezygote.waterproject.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {  // has the same methods as SQLiteOpenHelper

    private static String DB_NAME = "waterQuestions.db";  // name of the supplied database
    public String DB_PATH;  // string that will hold the path to the DB


    // Table Variable Names
    private static String TABLE_NAME = "questions";  // KEYS that are used for the SQL
    private static String KEY_ID = "_id";            // used as parameters
    private static String KEY_ANS = "ans";
    private static String KEY_QUES = "ques";
    private static String KEY_A = "a";
    private static String KEY_B = "b";
    private static String KEY_C = "c";
    private static String KEY_D = "d";


    public SQLiteDatabase myDataBase;  // an object of SQLiteDatabase

    private Context myContext;  // a context variable

    // Constructor
    public DatabaseHelper(Context context){  // a context will come in as a parameter

        super(context, DB_NAME, null, 1);  // calls parent constructor as well
        this.myContext = context;  // assign the context so it can be used

        if(android.os.Build.VERSION.SDK_INT >= 4.2){  // check OS version
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else  // set the path to the DB accordingly
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

    }

    // Create an empty database and rewrite it with the given one
    public void createDatabase(){

        boolean dbExist = checkDatabase();  // call method described below

        if(dbExist){  // if true
            // do nothing, database exists
        }
        else{
            // create an empty database
            this.getReadableDatabase();  // creates an empty one if nothing is there

            try{
                copyDataBase();  // call method described below
            } catch (IOException e){  // when something goes wrong
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database exists to avoid copying every time the app is opened
    private boolean checkDatabase(){

        SQLiteDatabase checkDB = null;  // initialize a DB object as null

        try{
            String myPath = DB_PATH + DB_NAME;  // see if there is a DB that matches our name and path
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
              // if so, checkDB will be populated with the one found
        }catch(SQLiteException e){  // if there was no match
            // database doesn't exist yet
        }

        if(checkDB != null){  // if one was found
            checkDB.close();  // close it
        }

        return checkDB != null;  // return true if one was found
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring bytestream.
     * */
    private void copyDataBase() throws IOException{  // copies, throws a warning if things go wrong

        // open local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // path to the newly created empty db
        String outFileName = DB_PATH + DB_NAME;

        // open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        // close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase(){

        //open the database
        String myPath = DB_PATH + DB_NAME;  // open our DB, an object of SQLiteDatabase
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){  // close the DB object if it exists

        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    // returns the length of the database
    public long count(){

        return DatabaseUtils.queryNumEntries(myDataBase, "questions");  // (dbObject, nameOfTable)
    }

        // Read a single question
    public Question getNext(int id) {  // desired row comes in as an integer

        Cursor cursor = myDataBase.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_ANS, KEY_QUES, KEY_A, KEY_B, KEY_C, KEY_D }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

                // the cursor is matched to the row id

        if (cursor != null)
            cursor.moveToFirst();  // move the cursor to the matching row

        Question question2 = new Question(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));

                // grab the integers and strings as a function of cursor position

        // return question
        return question2;
    }


    // mandatory functions as a result from extending the class
    @Override
    public void onCreate(SQLiteDatabase db){


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
