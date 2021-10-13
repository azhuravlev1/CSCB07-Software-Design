// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: leeyena7
// UT Student #: 1004932068
// Author: Yena Lee
//
// Student2:
// UTORID user_name: andrian6
// UT Student #: 1006431817
// Author: Iangola Andrianarison
//
// Student3:
// UTORID user_name: zhuravl1
// UT Student #: 1003148961
// Author: Andrey Zhuravlev
//
// Student4:
// UTORID user_name: kongsu
// UT Student #: 1004304790
// Author: Su Tong Kong
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.io.Serializable;
/**
 * Object file. A file content string and has a Directory as a parent.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class File implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private Object fileContent;
	private Directory fileDir;

	public File (String fileName, Object fileContent, Directory fileDir) {
		this.fileDir = fileDir;
		this.fileContent = fileContent;
		this.fileName = fileName;
		fileDir.add(this);
	}
	/**
	 * Returns the file name
	 * @return fileName
	 */
	public String getFileName() {
		return this.fileName;
	}
	/**
	 * Returns the file content
	 * @return fileContent
	 */
	public Object getFileContent() {
		return this.fileContent;	
	}
	/**
	 * Returns the file directory
	 * @return fileDir
	 */
	public Directory getFileDir() {
		return this.fileDir;
	}
	/**
	 * Changes file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Changes file content
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	/**
	 * Changes file directory
	 */
	public void setFileDir(Directory fileDir) {
		this.fileDir = fileDir;
	}
}	

