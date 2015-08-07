package com.example.virtualcontactmanager;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author optimus158
 * 
 *         Adapter Class for populating the list of contacts
 */
public class ContactsAdapter extends BaseAdapter {

	private Context context;
	private List<ContactsModel> list;

	public ContactsAdapter(Context context, List<ContactsModel> list) {
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	/*
	 * Sets the UI components to view and returns it
	 */
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		ViewHolder holder = null;
		if (view == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.layout_listview_contacts, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) view
					.findViewById(R.id.imageViewContact);
			holder.textViewName = (TextView) view
					.findViewById(R.id.textViewNameContact);
			holder.textViewPhoneNo = (TextView) view
					.findViewById(R.id.textViewPhoneNoContact);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// setting the name and image
		if (list.get(arg0).getPicturePath().equals("")) {
			Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory
					.decodeResource(context.getResources(),
							R.drawable.default_image), 100, 100);
			holder.imageView.setImageBitmap(bitmap);
		} else {
			Bitmap bitmap = ThumbnailUtils.extractThumbnail(
					BitmapFactory.decodeFile(list.get(arg0).getPicturePath()),
					100, 100);
			holder.imageView.setImageBitmap(bitmap);
		}
		holder.textViewName.setText(list.get(arg0).getFirstName() + " "
				+ list.get(arg0).getLastName());
		holder.textViewPhoneNo.setText(list.get(arg0).getPhoneNo());
		return view;
	}

	/**
	 * @author optimus158
	 * 
	 *         ViewHolder class for facilitating the views
	 */
	static class ViewHolder {
		public ImageView imageView;
		public TextView textViewName;
		public TextView textViewPhoneNo;
	}

}
