package com.desmond.citypicker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.desmond.citypicker.tools.SysUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/4/28
 * @Pacakge com.chinasoftinc.support_library.db
 */

public class AddressDBHelper extends SQLiteOpenHelper
{

    private SQLiteDatabase mDataBase;
    private Context mContext;


    private static String DATABASE_PATH ;


    public AddressDBHelper(Context context,String name,int viersion)
    {
        super(context, name, null, viersion);
        this.mContext = context;
        DATABASE_PATH = "/data/data/" + SysUtil.getAppId(mContext) + "/databases/";
        try
        {

            createDataBase();

            this.getReadableDatabase();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws Exception
    {

        boolean dbExist = checkDataBase();

        if (dbExist) return;


        copyDataBase();

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DATABASE_PATH + getDatabaseName();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (checkDB != null)
        {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws Exception
    {
        // Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(getDatabaseName());

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + getDatabaseName();

        File f = new File(DATABASE_PATH);
        if(!f.exists())
            f.mkdirs();


        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);



        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase()
    {
        // Open the database
        String myPath = DATABASE_PATH + getDatabaseName();
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close()
    {
        if (mDataBase != null)
            mDataBase.close();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion == 1 && newVersion == 2)
        {
            try
            {
                copyDataBase();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        this.getReadableDatabase();
    }
}