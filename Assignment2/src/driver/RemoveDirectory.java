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

import java.util.ArrayList;
import java.util.Stack;
/**
 * Remove directory and its contents and subdirectories
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class RemoveDirectory{
	/**
	 * Stack of objects
	 */
	private Stack<Object> stk = new Stack<Object>();
	
	/**
	 * Delete and object by removing it from its parent Directory's conten
	 * @param rm
	 */
	private void delete(Object rm) {
		Directory parent;
		if (rm.getClass().getName()=="driver.Directory") {
			parent=((Directory) rm).getParentDirectory();
		}
		else {
			parent=((File) rm).getFileDir();
		}
    	parent.delete(rm);
    }
	
	/**
	 * Get a object from traversing the path
	 * @param path
	 * @param current
	 * @return Ojbect
	 */
	private Object getDir(String path, Directory current) {
		String[] input = path.split("/");
		Directory obj = null;
		for (int i=0;i<input.length;i++) {
			if(input[i].trim().equals("")) {
				continue;
			}
			current=(Directory) current.getChildByName(input[i], false);
		}
		obj=current;
		return obj;
	}
	
	/**
	 * Removing an objet located at a path
	 * @param path
	 * @param current
	 */
	public void removeDir(String path, Directory current) {
		// If no arguments given
		if (path=="") {
			System.out.println("Error:rm takes in one argument.");
			return;
		}
		// If trying to delete root
		if (path=="/") {
			System.out.println("Error:cannot delete root.");
			return;
		}
		Object toDelete = getDir(path, current);
		// Invalid input
		if (toDelete==null) {
			System.out.println("Error: path "+path+" does not exist or trying to delete"
					+ "parent directory.");
			return;
		}
		stk.push(toDelete);
		// Delete everything recursively from the direcotry to delete
		while(!stk.isEmpty()) {
			toDelete=stk.pop();
			if (toDelete.getClass().getName() == "driver.Directory") {
				ArrayList< Object> children = ((Directory) toDelete).getDirectoryContents();
				for (Object child:children) {
					stk.push(child);
				}
			}
			delete(toDelete);
		}
	}
}