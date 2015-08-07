package com.example.virtualcontactmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Groups extends Fragment {

	private Button add;
	private DBHelper helper;
	private ListView vlistView;
	private List<GroupsModel> list;
	private ArrayAdapter<String> listviewadapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_groups,
				container, false);
		/*
		 * vlistView = (ListView) view.findViewById(R.id.listViewContacts); add
		 * = (Button) view.findViewById(R.id.buttonContacts);
		 * add.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { Intent intent = new
		 * Intent(getActivity(), GroupDetails.class); intent.putExtra("option",
		 * "add"); startActivity(intent); } });
		 * 
		 * helper = new DBHelper(getActivity());
		 * 
		 * setupList();
		 */
		return view;
	}

	private void setupList() {
		list = helper.getAllGroups();
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			tempList.add(list.get(i).getGroupName() + "\n"
					+ list.get(i).getGroupCount());
		}
		listviewadapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, tempList);
		// vlistView.setOnItemClickListener(this);
		vlistView.setAdapter(listviewadapter);

	}
}
