package com.example.virtualcontactmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author optimus158
 * 
 *         This class shows the contacts list and an add button
 */
public class Contacts extends Fragment implements OnItemClickListener {

	private Button add;
	private DBHelper helper;
	private ListView vlistView;
	private List<ContactsModel> list;
	private ContactsAdapter listviewadapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.layout_fragment_contacts,
				container, false);
		vlistView = (ListView) view.findViewById(R.id.listViewContacts);
		add = (Button) view.findViewById(R.id.buttonContacts);
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ContactDetails.class);
				intent.putExtra("option", "add");
				startActivity(intent);
			}
		});
		helper = new DBHelper(getActivity());
		setupList();
		return view;
	}

	/**
	 * Displays the contacts list
	 */
	private void setupList() {
		list = helper.getAllContacts();
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			tempList.add(list.get(i).getFirstName() + " "
					+ list.get(i).getLastName() + "\n"
					+ list.get(i).getPhoneNo());
		}
		listviewadapter = new ContactsAdapter(getActivity(), list);
		vlistView.setOnItemClickListener(this);
		vlistView.setAdapter(listviewadapter);

	}

	/*
	 * Handles the list click
	 */
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		Intent intent = new Intent(getActivity(), ContactDetails.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("selectedContact", list.get(paramInt));
		intent.putExtras(bundle);
		intent.putExtra("option", "browse");
		startActivity(intent);

	}

}
