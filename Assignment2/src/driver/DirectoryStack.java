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
import java.util.Stack;
/**
 * Stack that contains a directory. Can pop and push directories.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class DirectoryStack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Stack<Directory> dirStack = new Stack<Directory>();
	private static DirectoryStack DirectoryStk = new DirectoryStack();

	private DirectoryStack() {}
	
	public static DirectoryStack getInstance() {
		return DirectoryStk;
	}

	public void push(Directory curdir) {
		dirStack.push(curdir);
	}

	/**
	 * Removes the top entry from the directory	stack
	 */
	public Directory pop() {
		return dirStack.pop();
	}
	
	/**
	 * Returns true if stack is empty
	 */
	public boolean emptyStack() {
		if (dirStack.empty()) {
			return true;
		}
		return false;
	}
}
