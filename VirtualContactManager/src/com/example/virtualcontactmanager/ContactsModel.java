package com.example.virtualcontactmanager;

import java.io.Serializable;

/**
 * @author optimus158
 * 
 *         Model class for the Contacts Details
 */
public class ContactsModel implements Serializable {

	private static final long serialVersionUID = 0L;

	String firstName;
	String lastName;
	String phoneNo;
	String picturePath;
	int groupId;
	
	//	Getters and Setters
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

}
