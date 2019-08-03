
package entities;

/**
 * Class MstJapan là Java Bean chứa các thuộc tính của bảng mst_japan trong db
 * 
 */
public class MstJapan {
	private String codeLevel;
	private String nameLevel;

	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	public String getNameLevel() {
		return nameLevel;
	}

	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

}
