package com.gits.sami.billmanagement.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gits.sami.billmanagement.model.Bill;
import com.gits.sami.billmanagement.others.Utility;

import java.util.ArrayList;
import java.util.Date;

import static com.gits.sami.billmanagement.others.Utility.BillTableName;
import static com.gits.sami.billmanagement.others.Utility.DbName;
import static com.gits.sami.billmanagement.others.Utility.DbVersion;
public class DbHelper extends SQLiteOpenHelper {
	public DbHelper(Context context) {
		super(context, DbName, null, DbVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Bill> getAllElectricity(Utility.billType billType, Date month) {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		// Rest Index Of Spinner from database
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor;
		if (!billType.equals(Utility.billType.All)){
			if (!month.equals(Utility.ErrorDate)){
				cursor = db.query(BillTableName, null, "billType=? and billingMonth=?", new String[]{billType.toString(),Utility.getDateAsString(month, Utility.myDateFormat.yyyy_MM_dd)}, null, null, null);
			}
			else {
				cursor = db.query(BillTableName, null, "billType=? ", new String[]{billType.toString()}, null, null, null);
			}
		}else {
			if (!month.equals(Utility.ErrorDate)){
				cursor = db.query(BillTableName, null, "billingMonth=?", new String[]{Utility.getDateAsString(month, Utility.myDateFormat.yyyy_MM_dd)}, null, null, null);
			}else {
				cursor = db.query(BillTableName, null, null, null, null, null, null);
			}
		}

		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Bill bill = new Bill();
				bill.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
				bill.billType = Utility.billType.valueOf(cursor.getString(cursor.getColumnIndex("billType")));
				bill.serialNo = cursor.getString(cursor.getColumnIndex("serialNo"));
				bill.meterNo = cursor.getString(cursor.getColumnIndex("meterNo"));
				bill.amount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount")));
				bill.billingMonth = Utility.getDate(cursor.getString(cursor.getColumnIndex("billingMonth")),Utility.myDateFormat.yyyy_MM_dd);
				bill.paymentDate = Utility.getDate(cursor.getString(cursor.getColumnIndex("paymentDate")),Utility.myDateFormat.yyyy_MM_dd);
				bill.isLate = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isLate")));
				bill.fineAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("fineAmount")));
				bills.add(bill);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return bills;
	}
	public boolean insertElectricity(Bill bill) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("billType", String.valueOf(bill.billType));
		contentValues.put("serialNo", bill.serialNo);
		contentValues.put("meterNo", bill.meterNo);
		contentValues.put("amount", (Double) bill.amount);
		contentValues.put("billingMonth", Utility.getDateAsString(bill.billingMonth, Utility.myDateFormat.yyyy_MM_dd));
		contentValues.put("paymentDate", Utility.getDateAsString(bill.paymentDate, Utility.myDateFormat.yyyy_MM_dd));
		contentValues.put("fineAmount", (Double) bill.fineAmount);
		SQLiteDatabase db = getReadableDatabase();
		long row = db.insert(BillTableName,null,contentValues);
		db.close();
		return row>0;
	}

}
