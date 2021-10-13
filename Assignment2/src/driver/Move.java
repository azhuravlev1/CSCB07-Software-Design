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

/**
 * Move a path into a new path.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Move {
	/**
	 * Checks validity of mv command.
	 * Usage: mv OLDPATH NEWPATH
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if mv is valid, false if invalid
	 */
	private boolean isValidMove(String[] arguments, Directory currentDir, Directory root) {
		JShellInput valid = new JShellInput();
		// Check path before parse
		if (!valid.isValidPathBeforeParse(arguments[0]) || 
				!valid.isValidPathBeforeParse(arguments[1])) {
			return false;
		}
		// Check path
		if (!valid.isValidPath(arguments[0], currentDir, root, true, true, false)) {
			return false;
		}
		if (!valid.isValidPath(arguments[1], currentDir, root, true, true, true)) {
			return false;
		}
		return true;
	}
	
	/**
	 * recursively move contents of oldPath to newPath 
	 * @param oldPath
	 * @param newPath
	 */
	public void moveDirectory(String[] arguments, Directory currentDir, Directory root) {
		// Argument validity check
		if (!isValidMove(arguments, currentDir, root)) {
			return;			
		}
		
		Copy cp = new Copy();
		cp.copyDirectory("mv", arguments, currentDir, root);
		Object oldPath = cp.getObject(arguments[0], currentDir);
		
		if (oldPath instanceof Directory) {
			RemoveDirectory rm = new RemoveDirectory();
			rm.removeDir(arguments[0], currentDir);
		}
		else if (oldPath instanceof File) {
			((File) oldPath).getFileDir().delete(oldPath);
			((File) oldPath).setFileDir(null);
		}
	}
}