package com.example.virtualcontactmanager;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;

	private static final String DB_NAME = "ContactManager_DB";
	private static final String DB_TABLE_CONTACTS = "Contacts_Info";
	private static final String DB_TABLE_GROUPS = "Groups_Info";
	private static final String CONTACT_FIRSTNAME = "FirstName";
	private static final String CONTACT_LASTNAME = "LastName";
	private static final String CONTACT_PHONENO = "PhoneNo";
	private static final String CONTACT_PICTURE = "ContactPicture";
	private static final String GROUP_NAME = "GroupName";
	private static final String GROUP_COUNT = "GroupCount";
	private static final String GROUP_PICTURE = "GroupPicture";
	private static final String GROUP_ID = "GroupId";

	private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
			+ DB_TABLE_CONTACTS + " (" + CONTACT_FIRSTNAME + " VARCHAR(255),"
			+ " " + CONTACT_LASTNAME + " VARCHAR(255)," + " " + CONTACT_PHONENO
			+ " VARCHAR(255)," + " " + CONTACT_PICTURE + " VARCHAR(255)," + " "
			+ GROUP_ID + " INT DEFAULT 0);";
	private static final String CREATE_TABLE_GROUPS = "CREATE TABLE "
			+ DB_TABLE_GROUPS + " (" + GROUP_NAME + " VARCHAR(255)," + " "
			+ GROUP_COUNT + " INT," + " " + GROUP_PICTURE + " VARCHAR(255));";

	/**
	 * Constructor for getting the Context
	 */
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/*
	 * Method for setting up Database and creating the Tables
	 */
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_CONTACTS);
		db.execSQL(CREATE_TABLE_GROUPS);

	}

	/*
	 * Method for recreating the Database if new Version is passed
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_GROUPS);
		this.onCreate(db);
	}

	/**
	 * Method for inserting a new contact to the Table
	 */
	public int createContact(ContactsModel model) {

		SQLiteDatabase db = getWritableDatabase();

		ContentValues content = new ContentValues();
		content.put(CONTACT_FIRSTNAME, model.getFirstName());
		content.put(CONTACT_LASTNAME, model.getLastName());
		content.put(CONTACT_PHONENO, model.getPhoneNo());
		content.put(CONTACT_PICTURE, model.getPicturePath());
		content.put(GROUP_ID, model.getGroupId());

		int status = (int) db.insert(DB_TABLE_CONTACTS, null, content);
		db.close();

		return status;
	}

	/**
	 * Method for inserting a new group to the Table
	 */
	public int createGroup(GroupsModel model) {

		SQLiteDatabase db = getWritableDatabase();

		ContentValues content = new ContentValues();
		content.put(GROUP_NAME, model.getGroupName());
		content.put(GROUP_COUNT, model.getGroupCount());
		content.put(GROUP_PICTURE, model.getPicturePath());

		int status = (int) db.insert(DB_TABLE_GROUPS, null, content);
		db.close();

		return status;
	}

	/**
	 * Method for getting all the contacts from the Table
	 */
	public List<ContactsModel> getAllContacts() {
		List<ContactsModel> contacts = new LinkedList<ContactsModel>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_CONTACTS, null);

		ContactsModel contact;

		if (cursor.moveToFirst()) {
			do {
				contact = new ContactsModel();
				contact.setFirstName(cursor.getString(0));
				contact.setLastName(cursor.getString(1));
				contact.setPhoneNo(cursor.getString(2));
				contact.setPicturePath(cursor.getString(3));
				contact.setGroupId(cursor.getInt(4));
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		return contacts;
	}

	/**
	 * Method for getting all the groups from the Table
	 */
	public List<GroupsModel> getAllGroups() {
		List<GroupsModel> groups = new LinkedList<GroupsModel>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT rowID,* FROM " + DB_TABLE_GROUPS,
				null);

		GroupsModel group;

		if (cursor.moveToFirst()) {
			do {
				group = new GroupsModel();
				group.setGroupName(cursor.getString(1));
				group.setGroupCount(Integer.parseInt(cursor.getString(2)));
				group.setPicturePath(cursor.getString(3));
				group.setGroupId(Integer.parseInt(cursor.getString(0)));
				groups.add(group);
			} while (cursor.moveToNext());
		}
		return groups;
	}

	/**
	 * @param contact
	 * 
	 *            Method for deleting a specific contact
	 */
	public void deleteContact(ContactsModel contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(DB_TABLE_CONTACTS, CONTACT_PHONENO + " = ?",
				new String[] { String.valueOf(contact.getPhoneNo()) });
		db.close();
	}

	/**
	 * @param group
	 * 
	 *            Method for deleting a specific group
	 */
	public void deleteGroup(GroupsModel group) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(DB_TABLE_GROUPS, GROUP_NAME + " = ?",
				new String[] { String.valueOf(group.getGroupName()) });
		db.close();
	}

	/**
	 * @param contact
	 * @return
	 * 
	 *         Method for updating a contact
	 */
	public int updateContact(ContactsModel contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CONTACT_FIRSTNAME, contact.getFirstName());
		values.put(CONTACT_LASTNAME, contact.getLastName());
		values.put(CONTACT_PHONENO, contact.getPhoneNo());
		values.put(CONTACT_PICTURE, contact.getPicturePath());

		// updating row
		return db.update(DB_TABLE_CONTACTS, values, CONTACT_PHONENO + " = ?",
				new String[] { String.valueOf(contact.getPhoneNo()) });
	}

	/**
	 * Method for deleting the whole contact table values
	 */
	public void deleteContactsTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(DB_TABLE_CONTACTS, null, null);
	}

	/**
	 * Method for deleting the whole group table values
	 */
	public void deleteGroupsTable() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(DB_TABLE_GROUPS, null, null);
	}
}
