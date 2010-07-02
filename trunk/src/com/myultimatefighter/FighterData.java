package com.myultimatefighter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.sax.StartElementListener;
import android.util.Log;

public class FighterData {
	// tag for log entries
	private static final String TAG = "FighterData";

	// database name and version
	private static final String DB = "FighterData";
	private static final int VERSION = 1;

	// SQL variables
	public static final String FIGHTER_TABLE_NAME = "fighters";
	// public static final String COL_NUMBER = "number";
	public static final String COL_FIGHTER_NAME = "name";
	public static final String COL_FIGHTER_AVATAR = "avatar";
	public static final String COL_FIGHTER_BUTTON_ID = "button_id";
	public static final String COL_FIGHTER_LVL = "lvl";
	public static final String COL_FIGHTER_HP = "hp";
	public static final String COL_FIGHTER_PATTACK = "pa";
	public static final String COL_FIGHTER_PDEFENSE = "pd";
	public static final String COL_FIGHTER_SATTACK = "sa";
	public static final String COL_FIGHTER_SDEFENSE = "sd";
	public static final String COL_FIGHTER_SKILL_POINTS = "sp";	
	public static final String COL_FIGHTER_EXP = "exp";
//	public static final String COL_FIGHTER_DISPLAY_NAME_ID = "name_id";
//	public static final String COL_FIGHTER_BUTTON_TAG = "button_tag";
//	public static final String COL_FIGHTER_DISPLAY_NAME_TAG = "name_tag";
	public static final String CREATE_TABLE = "CREATE TABLE " + FIGHTER_TABLE_NAME
			+ " (_id integer not null primary key autoincrement, "
			+ COL_FIGHTER_NAME + " text, " + COL_FIGHTER_AVATAR + " integer, "
			+ COL_FIGHTER_BUTTON_ID + " integer, "+ COL_FIGHTER_LVL+" integer, "
			+ COL_FIGHTER_HP+" integer, "+COL_FIGHTER_PATTACK+" integer, "
			+ COL_FIGHTER_PDEFENSE+" integer, "+COL_FIGHTER_SATTACK+" integer, "+COL_FIGHTER_SDEFENSE+" integer, "
			+ COL_FIGHTER_SKILL_POINTS+" integer, "+ COL_FIGHTER_EXP+" integer"+");";

	private FighterDataHelper dbHelper;
	private SQLiteDatabase db;

	private final Context dataContext;

	//class to help manage the SQL database
	private static class FighterDataHelper extends SQLiteOpenHelper {

		public FighterDataHelper(Context context) {
			super(context, DB, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "onCreate of SQL Database");
			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	public FighterData(Context ctx) {
		this.dataContext = ctx;
	}

	public FighterData open() throws SQLException {
		dbHelper = new FighterDataHelper(dataContext);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long addFighter(Fighter fighter) {
		ContentValues values = new ContentValues();
		values.put(COL_FIGHTER_NAME, fighter.getFighterName());
		values.put(COL_FIGHTER_AVATAR, fighter.getFighterAvatar());
		values.put(COL_FIGHTER_BUTTON_ID, fighter.getButtonID());

		return db.insert(FIGHTER_TABLE_NAME, null, values);
	}
	
	public long newFighter(int avatar) {
		Log.d(TAG, "in newFighter");				
		ContentValues values = new ContentValues();
		values.put(COL_FIGHTER_NAME, "Fighter");
		values.put(COL_FIGHTER_AVATAR, avatar);
		values.put(COL_FIGHTER_BUTTON_ID, 0);
		values.put(COL_FIGHTER_LVL, 1);
		values.put(COL_FIGHTER_HP, 100);
		values.put(COL_FIGHTER_PATTACK, 10);
		values.put(COL_FIGHTER_PDEFENSE, 10);
		values.put(COL_FIGHTER_SATTACK, 10);
		values.put(COL_FIGHTER_SDEFENSE, 10);
		values.put(COL_FIGHTER_SKILL_POINTS, 10);	
		values.put(COL_FIGHTER_EXP, 0);
		
		return db.insert(FIGHTER_TABLE_NAME, null, values);
		}
	
	/*public void saveName(int fighterID, String fighterName) {
		Log.d(TAG, "trying to save fighter name");
		db.execSQL("UPDATE "+FIGHTER_TABLE_NAME+" SET "+COL_FIGHTER_NAME+"='"+fighterName+
				"' WHERE _id='"+fighterID+"'");
	}*/
	
	public void saveFighter(int fighterID, int fighterAvatar, String fighterName, int fighterLevel,
			int fighterHP, int fighterPA, int fighterPD, int fighterSA, int fighterSD, int fighterSP, int fighterEXP) {
		Log.d(TAG, "trying to save fighter");
		db.execSQL("UPDATE "+FIGHTER_TABLE_NAME+" SET "+COL_FIGHTER_NAME+"='"+fighterName+"', "
				+COL_FIGHTER_AVATAR+"='"+fighterAvatar+"', "+COL_FIGHTER_HP+"='"+fighterHP+"', "
				+COL_FIGHTER_LVL+"='"+fighterLevel+"', "+COL_FIGHTER_PATTACK+"='"+fighterPA+"', "
				+COL_FIGHTER_PDEFENSE+"='"+fighterPD+"', "+COL_FIGHTER_SATTACK+"='"+fighterSA+"', "
				+COL_FIGHTER_SDEFENSE+"='"+fighterSD+"', "+COL_FIGHTER_SKILL_POINTS+"='"+fighterSP+"', "
				+COL_FIGHTER_EXP+"='"+fighterEXP+					
				"' WHERE _id='"+fighterID+"'");
	}
	
	public int getLastFighterID() {
		Cursor c = getFighters();
		c.moveToLast();
		return c.getInt(c.getColumnIndex("_id"));
	}

	public Cursor getFighters() {

		return db.query(FIGHTER_TABLE_NAME, new String[] { "_id",
				COL_FIGHTER_NAME, COL_FIGHTER_AVATAR, COL_FIGHTER_BUTTON_ID, COL_FIGHTER_HP, COL_FIGHTER_LVL,
				COL_FIGHTER_PATTACK, COL_FIGHTER_PDEFENSE, COL_FIGHTER_SATTACK, COL_FIGHTER_SDEFENSE, COL_FIGHTER_SKILL_POINTS,
				COL_FIGHTER_EXP}, 
				null, null, null, null, null);
	}

	public void deleteFighters() {
		db.execSQL("DROP TABLE " + FIGHTER_TABLE_NAME);
		db.execSQL(CREATE_TABLE);
	}
	
//	public Cursor getFighterRowByButtonID(int ButtonID) {
//		return db.query(FIGHTER_TABLE_NAME, new String[] { "_id", COL_FIGHTER_NAME,  COL_FIGHTER_AVATAR, COL_FIGHTER_BUTTON_ID, COL_FIGHTER_DISPLAY_NAME_ID }, 
//				COL_FIGHTER_BUTTON_ID, null, null, having, orderBy)
//	}
}