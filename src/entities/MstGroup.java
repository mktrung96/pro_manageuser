

package entities;

/**
 * Class MstGroup là Java Bean chứa các thuộc tính của bảng mst_group trong db
 * 
 *
 */
public class MstGroup {
	
	private int groupID;
	private String groupName;
	
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
