package com.example.virtualcontactmanager;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author optimus158
 * 
 *         Class for handling contacts details and UI
 */
public class ContactDetails extends ActionBarActivity implements
		OnClickListener, OnItemSelectedListener {

	private static final int STATUS_CAMERA = 1;
	private static final int STATUS_GALLERY = 2;

	private String option;
	private String[] addImage = { "-SELECT-", "Camera", "Gallery" };
	private int selection = 0;
	private String picturePath;
	private DBHelper helper;

	private TextView vTextView;
	private ImageView vPicture;
	private EditText vFirstName, vLastName, vPhoneNo;
	private Button vButtonAdd, vButtonEdit, vButtonDelete, vButtonSave,
			vButtonCancel;
	private Spinner vSpinner;

	private ContactsModel contactModel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cdetails);

		vSpinner = (Spinner) findViewById(R.id.spinnerContacts);
		vPicture = (ImageView) findViewById(R.id.imageViewContacts);
		vFirstName = (EditText) findViewById(R.id.editTextFNameContacts);
		vLastName = (EditText) findViewById(R.id.editTextLNameContacts);
		vPhoneNo = (EditText) findViewById(R.id.editTextPhoneNoContacts);
		vButtonAdd = (Button) findViewById(R.id.buttonAddContacts);
		vButtonEdit = (Button) findViewById(R.id.buttonEditContacts);
		vButtonDelete = (Button) findViewById(R.id.buttonDeleteContacts);
		vButtonSave = (Button) findViewById(R.id.buttonSaveContacts);
		vButtonCancel = (Button) findViewById(R.id.buttonCancelContacts);
		vTextView = (TextView) findViewById(R.id.textViewSelectImage);

		helper = new DBHelper(this);
		vButtonAdd.setOnClickListener(this);
		vButtonEdit.setOnClickListener(this);
		vButtonDelete.setOnClickListener(this);
		vButtonSave.setOnClickListener(this);
		vButtonCancel.setOnClickListener(this);

		ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, addImage);
		vSpinner.setAdapter(spinneradapter);
		vSpinner.setOnItemSelectedListener(this);

		Intent intent = getIntent();
		contactModel = (ContactsModel) intent
				.getSerializableExtra("selectedContact");
		option = intent.getStringExtra("option");

		// Changing UI views according to the selection
		if (option.equalsIgnoreCase("add")) {
			vButtonEdit.setVisibility(View.GONE);
			vButtonDelete.setVisibility(View.GONE);
			vButtonSave.setVisibility(View.GONE);
			vButtonCancel.setVisibility(View.GONE);
		} else if (option.equalsIgnoreCase("browse")) {
			vSpinner.setVisibility(View.GONE);
			vButtonAdd.setVisibility(View.GONE);
			vButtonSave.setVisibility(View.GONE);
			vButtonCancel.setVisibility(View.GONE);
			vTextView.setVisibility(View.GONE);
			vButtonEdit.setVisibility(View.VISIBLE);
			vButtonDelete.setVisibility(View.VISIBLE);
			vFirstName.setText(contactModel.getFirstName());
			vFirstName.setEnabled(false);
			vLastName.setText(contactModel.getLastName());
			vLastName.setEnabled(false);
			vPhoneNo.setText(contactModel.getPhoneNo());
			vPhoneNo.setEnabled(false);
			// if user not selected any picture setting the default image
			if (contactModel.getPicturePath().equals("")) {
				vPicture.setImageResource(R.drawable.default_image);
			} else {
				Bitmap bitmap = ThumbnailUtils
						.extractThumbnail(BitmapFactory.decodeFile(contactModel
								.getPicturePath()), 200, 200);
				vPicture.setImageBitmap(bitmap);
			}
		}

	}

	/*
	 * Method for handling button clicks
	 */
	public void onClick(View v) {
		// Handles add button functionality
		if (v.getId() == R.id.buttonAddContacts) {
			if (vFirstName.getText().toString().equals("")
					|| vLastName.getText().toString().equals("")
					|| vPhoneNo.getText().toString().equals("")) {
				Toast.makeText(this, "Field(s) cannot be Blank",
						Toast.LENGTH_SHORT).show();
				return;
			}

			ContactsModel contact = new ContactsModel();
			contact.setFirstName(vFirstName.getText().toString());
			contact.setLastName(vLastName.getText().toString());
			contact.setPhoneNo(vPhoneNo.getText().toString());
			if (picturePath == null) {
				contact.setPicturePath("");
			} else {
				contact.setPicturePath(picturePath);
			}

			int status = (int) helper.createContact(contact);
			if (status != -1) {
				startActivity(new Intent(this, MainActivity.class));
				Toast.makeText(getApplicationContext(),
						"Data Inserted Successfully !!!", Toast.LENGTH_SHORT)
						.show();
			}
			if (status == -1) {
				Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
			}
		}

		else if (v.getId() == R.id.buttonEditContacts) {
			// setting the UI changes
			vSpinner.setVisibility(View.VISIBLE);
			vFirstName.setFocusableInTouchMode(true);
			vFirstName.setEnabled(true);
			vLastName.setFocusableInTouchMode(true);
			vLastName.setEnabled(true);
			vPhoneNo.setFocusableInTouchMode(true);
			vPhoneNo.setEnabled(true);

			vButtonEdit.setVisibility(View.GONE);
			vButtonDelete.setVisibility(View.GONE);
			vButtonSave.setVisibility(View.VISIBLE);
			vButtonCancel.setVisibility(View.VISIBLE);
			vTextView.setVisibility(View.VISIBLE);
		}

		// Saves the updated changes
		else if (v.getId() == R.id.buttonSaveContacts) {
			if (vFirstName.getText().toString().equals("")
					|| vLastName.getText().toString().equals("")
					|| vPhoneNo.getText().toString().equals("")) {
				Toast.makeText(this, "Field(s) cannot be Blank",
						Toast.LENGTH_SHORT).show();
				return;
			}
			contactModel.setFirstName(vFirstName.getText().toString());
			contactModel.setLastName(vLastName.getText().toString());
			contactModel.setPhoneNo(vPhoneNo.getText().toString());

			if (contactModel.getPicturePath().equals("")&&picturePath==null) {
				contactModel.setPicturePath("");
			} else {
				contactModel.setPicturePath(picturePath);
			}

			int status = (int) helper.updateContact(contactModel);
			if (status != -1) {
				startActivity(new Intent(this, MainActivity.class));
				Toast.makeText(getApplicationContext(),
						"Data Updated Successfully !!!", Toast.LENGTH_SHORT)
						.show();
			}
			if (status == -1) {
				Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
			}
		}

		// Deletes the current contact
		else if (v.getId() == R.id.buttonDeleteContacts) {
			helper.deleteContact(contactModel);
			startActivity(new Intent(this, MainActivity.class));
		}

		else if (v.getId() == R.id.buttonCancelContacts)
			startActivity(new Intent(this, MainActivity.class));
	}

	/*
	 * Spinner option selection Camera or Gallery
	 */
	public void onItemSelected(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		selection = vSpinner.getSelectedItemPosition();
		if (vPhoneNo.getText().toString().equals("")) {
			Toast.makeText(
					this,
					"Contact Nunber to be entered before Selecting Picutre !!!",
					Toast.LENGTH_LONG).show();
			return;
		}
		switch (selection) {
		case 1:
			startCamera();
			break;
		case 2:
			startGallery();
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> paramAdapterView) {
	}

	/**
	 * This method starts gallery app for fetching image
	 */
	private void startGallery() {

		Intent galleryIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, STATUS_GALLERY);
	}

	/*
	 * Method called after returning from Gallery or Camera
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {

			if (requestCode == STATUS_GALLERY && resultCode == RESULT_OK
					&& null != data) {

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String imgDecodableString = cursor.getString(columnIndex);
				cursor.close();
				picturePath = imgDecodableString;

			}
		} catch (Exception e) {
			Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
					.show();
		}

		Bitmap bitmap = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(picturePath), 200, 200);
		vPicture.setImageBitmap(bitmap);
	}

	/**
	 * Method for starting the Camera for user Picture
	 */
	private void startCamera() {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {

			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (photoFile != null) {
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(cameraIntent, STATUS_CAMERA);
			}
		}
	}

	/**
	 * Method for creating file and setting its path according to the contact's
	 * phone no
	 */
	private File createImageFile() throws IOException {

		File storageDirectory = getExternalFilesDir("");
		File image = new File(storageDirectory, vPhoneNo.getText().toString()
				+ ".jpg");
		picturePath = image.getAbsolutePath();
		return image;
	}
}
