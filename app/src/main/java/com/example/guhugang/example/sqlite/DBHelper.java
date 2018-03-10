package com.example.guhugang.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "faceplus.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		//CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS facepicture" +
				"(pictureid VARCHAR PRIMARY KEY ,path VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS facetoken" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT,pictureid VARCHAR UNIQUE,path VARCHAR,faceid VARCHAR,category INTEGER," +
				"face_left INTEGER,face_top INTEGER,face_width INTEGER,face_height INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS collection"+"(pid VARCHAR PRIMARY KEY,src_path VARCHAR,dest_path VARCHAR)");

		db.execSQL("CREATE TABLE IF NOT EXISTS imagetag_data"+"(tagged_id VARCHAR PRIMARY KEY,tagged_path VARCHAR,image_tag VARCHAR,location VARCHAR)");

	}

	//如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
	}
}
