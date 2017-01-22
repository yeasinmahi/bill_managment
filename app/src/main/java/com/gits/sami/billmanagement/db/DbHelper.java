package com.gits.sami.billmanagement.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gits.sami.billmanagement.model.Electricity;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

	private static final String dbName = "billManagement.sqlite";
	private static final int version = 1;
	

	public DbHelper(Context context) {
		super(context, dbName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Electricity> getAllElectricity() {
		ArrayList<Electricity> electricities = new ArrayList<Electricity>();
		// Rest Index Of Spinner from database
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("electricity", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Electricity electricity = new Electricity();
				electricity.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				electricity.serialNo = cursor.getString(cursor.getColumnIndex("serialNo"));
				electricity.meterNo = cursor.getString(cursor.getColumnIndex("meterNo"));
				electricity.amount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount")));
				electricity.billingMonth = Utility.getDate(cursor.getString(cursor.getColumnIndex("billingMonth")));
				electricity.paymentDate = Utility.getDate(cursor.getString(cursor.getColumnIndex("paymentDate")));
				electricity.isLate = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isLate")));
				electricity.fineAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("fineAmount")));
				electricities.add(electricity);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return electricities;
	}
	public boolean insertElectricity(Electricity electricity) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("serialNo",electricity.serialNo);
		contentValues.put("meterNo",electricity.meterNo);
		contentValues.put("amount",electricity.amount);
		contentValues.put("billingMonth", String.valueOf(electricity.billingMonth));
		contentValues.put("paymentDate", String.valueOf(electricity.paymentDate));
		contentValues.put("fineAmount",electricity.fineAmount);
		SQLiteDatabase db = getReadableDatabase();
		long row = db.insert("electricity",null,contentValues);
		db.close();
		return row>0;
	}

}
