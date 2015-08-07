package com.example.virtualcontactmanager;

/**
 * @author optimus158
 * 
 *         Model class for the Contacts Details
 */
public class GroupsModel {

	String groupName;
	String picturePath;
	int groupCount;
	int groupId;

	// getters and setters
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
