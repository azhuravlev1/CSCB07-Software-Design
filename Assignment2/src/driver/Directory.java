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
import java.util.ArrayList;
/**
 * Object Directory can contain directories or a files and is parent to those contents.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Directory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dirname;
	private ArrayList<Object> contents = new ArrayList<Object>();
	private Directory parentdir;

	public Directory(String directoryname, ArrayList<Object> contents, Directory directory) {
		this.dirname = directoryname;
		this.contents = contents;
		this.parentdir = directory;
		/*
		 * if the parent directory is given, add the directory being initialized to it
		 */
		if (this.parentdir != null) {
			this.parentdir.add(this);
		}
		/*
		 * if the contents are not given, create an empty list to store them
		 */
		if (this.contents == null) {
			this.contents = new ArrayList<Object>();
		}
		/*
		 * if the directory name is not given, issue a warning
		 */
		if (this.dirname == null) {
			System.out.println("Warning: directory was created without a name");
		}
	}

	/**
	 * Returns the parent directory
	 * @return parentdir - Parent Directory
	 */
	public Directory getParentDirectory() {
		return this.parentdir;
	}

	/**
	 * Returns the name of the directory
	 * @return name
	 */
	public String getDirectoryName() {
		return this.dirname;
	}

	/**
	 * Returns the array of all objects in the directory
	 * @return contents - array of all objects in the directory
	 */
	public ArrayList<Object> getDirectoryContents() {
		return this.contents;
	}

	/**
	 * Adds a new file or a new directory to the directory
	 * @param filedir - a File or a Directory
	 */
	public void add(Object filedir) {
		/*
		 * Check if the type of the object is File or Directory
		 */
		if (filedir.getClass().getName() == "driver.File") {
			/* If the type is File, set file directory to this directory
			 * and add the file to the list of contents
			 */
			((File) filedir).setFileDir(this);
			this.contents.add(filedir);
		}
		else if (filedir.getClass().getName() == "driver.Directory") {
			/* If the type is Directory, set its parent directory to this directory
			 * and add that directory to the list of contents of this directory
			 */
			((Directory)filedir).setParentDir(this);
			this.contents.add(filedir);
		}
		else {
			/* If the type of the given object is not FIle or Directory, print
			 * the following message
			 */
			System.out.println("The input object should be of type File or Directory");
		}
	}

	/**
	 * Changes the parent directory
	 * @param parentdir - new parent directory
	 */
	public void setParentDir(Directory directory) {
		this.parentdir = directory;
	}

	/**
	 * Deletes a specified file or a directory from the directory
	 * @param objtodel - a File or a Directory to be deleted
	 */
	public void delete(Object objtodel) {
		this.contents.remove(objtodel);

	}

	/**
	 * Returns File or Directory with the given name
	 * If no such file or directory exists, returns null
	 * @param name
	 */
	public Object getChildByName(String name, boolean printMessage) {
		/* Loop through the array with contents
		 */
		for (int i = 0; i < this.contents.size(); i++) {
			Object filedir = contents.get(i);
			/* Check if the i-th object is File or Directory
			 * and return the object if it has the given name
			 */
			if (filedir.getClass().getName().equals("driver.File")) {
				if (((File) filedir).getFileName().equals(name)) {
					return filedir;
				}
			}
			else if (filedir.getClass().getName().equals("driver.Directory")) {
				if (((Directory) filedir).getDirectoryName().equals(name)) {
					return filedir;
				}
			}
		}
		
		if (printMessage) {
			System.out.println(name + ": No such file or directory");
		}
		return null;
	}

	/**
	 * Returns a string with the name of the root directory
	 * @return stringpath - string representing path
	 */
	public String getRootName() {
		PresentWorkingDirectory pwd = new PresentWorkingDirectory();
		String stringpath = pwd.getStringPath(this);
		return stringpath.split("/")[1];
	}
}
