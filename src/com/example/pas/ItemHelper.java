package com.example.pas;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

class ItemHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME="Itemlist.db";
  private static final int SCHEMA_VERSION=1;
  
  public ItemHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
    	"address TEXT," +" type TEXT, notes TEXT, feed TEXT, lat REAL, lon REAL, contacts TEXT );");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  
  }

  public Cursor getAll(String orderBy) {
    return(getReadableDatabase()
            .rawQuery("SELECT _id, name, address, type, notes, lat, lon," +
            		"contacts FROM items ORDER BY "+orderBy,null));
  }
  
  public Cursor getById(String id) {
    String[] args={id};

    return(getReadableDatabase()
            .rawQuery("SELECT _id, name, address, type, notes, feed, lat, lon," +
            		"contacts FROM items WHERE _ID=?",args));
  }
  
  public void insert(String name, String address,
                     String type, String notes,
                     String feed,String contacts) {
    ContentValues cv=new ContentValues();
          
    cv.put("name", name);
    cv.put("address", address);
    cv.put("type", type);
    cv.put("notes", notes);
    cv.put("feed", feed);
    cv.put("contacts", contacts);
    
    getWritableDatabase().insert("items", "name", cv);
  }
  
  public void update(String id, String name, String address,
                     String type, String notes, String feed,String contacts) {
    ContentValues cv=new ContentValues();
    String[] args={id};
    
    cv.put("name", name);
    cv.put("address", address);
    cv.put("type", type);
    cv.put("notes", notes);
    cv.put("feed", feed);
    cv.put("contacts", contacts);
    
    getWritableDatabase().update("items", cv, "_ID=?",
                                 args);
  }
  
  public void updateLocation(String id, double lat, double lon) {
    ContentValues cv=new ContentValues();
    String[] args={id};
    
    cv.put("lat", lat);
    cv.put("lon", lon);
    
    getWritableDatabase().update("items", cv, "_ID=?",
                                 args);
  }
  
  public String getName(Cursor c) {
    return(c.getString(1));
  }
  
  public String getAddress(Cursor c) {
    return(c.getString(2));
  }
  
  public String getType(Cursor c) {
    return(c.getString(3));
  }
  
  public String getNotes(Cursor c) {
    return(c.getString(4));
  }
  
  public String getFeed(Cursor c) {
    return(c.getString(5));
  }
  
  public double getLatitude(Cursor c) {
    return(c.getDouble(6));
  }
  
  public double getLongitude(Cursor c) {
    return(c.getDouble(7));
  }

public String getContact(Cursor c) {
	// TODO Auto-generated method stub
	return c.getString(8);
}
}
