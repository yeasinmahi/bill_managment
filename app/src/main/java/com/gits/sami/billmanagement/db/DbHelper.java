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

	public ArrayList<Electricity> getAllElecticity() {
		ArrayList<Electricity> electicities = new ArrayList<Electricity>();
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
				electicities.add(electricity);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		db.close();
		return electicities;
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


	/*
	 * public long insertStudent(Student student){ SQLiteDatabase db=
	 * this.getWritableDatabase(); ContentValues values= new ContentValues();
	 * values.put(nameField, student.getName()); values.put(emailField,
	 * student.getEmail()); values.put(phoneField, student.getPhone()); long
	 * inserted=db.insert(tableName, null, values); db.close(); return inserted;
	 * } public ArrayList<Student> getAllStudent(){ ArrayList<Student> students
	 * = new ArrayList<Student>(); SQLiteDatabase db=
	 * this.getReadableDatabase(); Cursor cursor = db.query(tableName, null,
	 * null, null, null, null, null); if(cursor!=null && cursor.getCount()>0){
	 * cursor.moveToFirst(); for (int i = 0; i < cursor.getCount(); i++) { int
	 * id = cursor.getInt(cursor.getColumnIndex(idField)); String name=
	 * cursor.getString(cursor.getColumnIndex(nameField)); String email=
	 * cursor.getString(cursor.getColumnIndex(emailField)); String phone=
	 * cursor.getString(cursor.getColumnIndex(phoneField)); Student student =
	 * new Student(id, name, email, phone); students.add(student);
	 * cursor.moveToNext(); } } cursor.close(); return students; } public
	 * ArrayList<Student> searchByName(String name){ ArrayList<Student> students
	 * = new ArrayList<Student>(); SQLiteDatabase db= getReadableDatabase();
	 * Cursor cursor = db.query(tableName, null, " name like '%"+name+"%'",
	 * null, null, null, null); if(cursor!=null && cursor.getCount()>0){
	 * cursor.moveToFirst(); for (int i = 0; i < cursor.getCount(); i++) { int
	 * id = cursor.getInt(cursor.getColumnIndex(idField)); String name1=
	 * cursor.getString(cursor.getColumnIndex(nameField)); String email=
	 * cursor.getString(cursor.getColumnIndex(emailField)); String phone=
	 * cursor.getString(cursor.getColumnIndex(phoneField)); Student student =
	 * new Student(id, name1, email, phone); students.add(student);
	 * cursor.moveToNext(); } } cursor.close(); return students; } public int
	 * updateName(int id, String newName){ SQLiteDatabase db=
	 * getWritableDatabase(); ContentValues values= new ContentValues();
	 * values.put(nameField, newName); int updated = db.update(tableName,
	 * values, idField+"=?", new String[]{""+id}); return updated; } public int
	 * delete(int id){ SQLiteDatabase db= getWritableDatabase(); int deleted=
	 * db.delete(tableName, idField+"=?", new String[]{""+id}); return deleted;
	 * }
	 */

}
