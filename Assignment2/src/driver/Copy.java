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

/**
 * Copy the the content of a path to a new path. If path is a directory, recursively
 * its content.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Copy {
	/**
	 * Checks validity of cp command.
	 * Usage: cp OLDPATH NEWPATH
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if cp is valid, false if invalid
	 */
	private boolean isValidCopy(String[] arguments, Directory currentDir, Directory root) {
		JShellInput valid = new JShellInput();
		// Check path before parse
		if (!valid.isValidPathBeforeParse(arguments[0]) || 
				!valid.isValidPathBeforeParse(arguments[1])) {
			return false;
		}
		// Check oldPath
		if (!valid.isValidPath(arguments[0], currentDir, root, true, true, false)) {
			return false;
		}
		// Check newPath
		if (!valid.isValidPath(arguments[1], currentDir, root, true, true, true)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Copies file oldPath to newPath.
	 * @param name Name of copied file
	 * @param oldPath File to copy
	 * @param newPath Directory to paste
	 */
	private void copyFile(String name, File oldPath, Directory newPath) {
		Object content = oldPath.getFileContent();
		@SuppressWarnings("unused")
		File newFile = null;
		newFile = new File(name, content, newPath);
	}
	
	
	/**
	 * Recursively copies contents of oldPath into newPath.
	 * @param oldPath Directory to copy
	 * @param newPath Directory to paste
	 */
	private void copyDirectory(Directory oldPath, Directory newPath, String name) {
		Directory dir;
		dir = new Directory(name, null, newPath);
		
		ArrayList<Object> contents = oldPath.getDirectoryContents();
		for (Object content : contents) {
			if (content instanceof File) {
				// Copy file
				File newFile = (File) content;
				copyFile(newFile.getFileName(), newFile, dir);
			}
			else if (content instanceof Directory) {
				// Recursively copy contents
				copyDirectory((Directory) content, dir, ((Directory) content).getDirectoryName());
			}
		}
	}
	
	/**
	 * Copies directory oldPath to newPath, if possible.
	 * @param oldPath Directory to copy
	 * @param newPath Path to paste
	 * @param newNewPath Parent directory to newPath
	 */
	private void copyOldDirectory(Directory oldPath, Object newPath, Object newNewPath, String name) {
		// Directory to new Directory
		if (newPath == null) {
			copyDirectory(oldPath, (Directory) newNewPath, name);
		}
		// Directory to File
		else if (newPath instanceof File) {
			System.out.println("cp: Can't copy directory to a file");
			return;
		}
		// Directory to Directory
		else if (newPath instanceof Directory) {
			copyDirectory(oldPath, (Directory) newPath, oldPath.getDirectoryName());
		}
	}
	
	/**
	 * Copies file oldPath to newPath, if possible.
	 * @param oldPath File to copy
	 * @param newPath Path to paste
	 * @param newNewPath Parent directory to newPath
	 */
	private void copyOldFile(File oldPath, Object newPath, Object newNewPath, String name) {
		// File to new File
		if (newPath == null) {
			copyFile(name, oldPath, (Directory) newNewPath);
		}
		// File to File
		else if (newPath instanceof File) {
			// Overwrites file
			Redirection r = new Redirection();
			r.overwrite((File) newPath, (String) oldPath.getFileContent());
		}
		// File to Directory
		else if (newPath instanceof Directory) {
			copyFile(oldPath.getFileName(), oldPath, (Directory) newPath);
		}
	}
	
	/**
	 * Copies oldPath to newPath, if possible.
	 * @param command User input command
	 * @param oldPath Path to copy
	 * @param newPath Path to paste
	 * @param newNewPath Parent directory to newPath
	 */
	private void copy(String command, Object oldPath, Object newPath, 
			Object newNewPath, String name) {
		// oldPath != null since checked in isValidPath
		if (oldPath instanceof File) {
			copyOldFile((File) oldPath, newPath, newNewPath, name);
		}
		else if (oldPath instanceof Directory) {
			copyOldDirectory((Directory) oldPath, newPath, newNewPath, name);
		}
		else {
			System.out.println(command + ": Can't copy");
			return;
		}
	}
	
	/**
	 * Returns Directory or File of the path if it exists.
	 * @param path Path of the content
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns Directory or File of the path
	 */
	public Object getObject(String path, Directory currentDir) {
		String name = path;
		// Check if it is a path or content name
		if (path.contains("/")) {
			// Cut path by the second-last part
			name = path.substring(path.lastIndexOf("/") + 1, path.length());
			path = path.substring(0, path.lastIndexOf("/"));
			// Update currentDir 
			ChangeDirectory cd = new ChangeDirectory();
			currentDir = cd.main(currentDir, path);
		}
		
		// Return content
		return currentDir.getChildByName(name, false);
	}
	
	/**
	 * Recursively copy contents of oldPath to newPath
	 * @param command User input command
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 */
	public void copyDirectory(String command, String[] arguments, Directory currentDir, 
			Directory root) {
		// Argument validity check
		if (!isValidCopy(arguments, currentDir, root)) {
			return;
		}
		
		Object oldPath = getObject(arguments[0], currentDir);
		Object newPath = getObject(arguments[1], currentDir);
		Object newNewPath = null;
		String name = "";
		
		// newPath is new
		if (newPath == null) {
			String path = arguments[1];
			if (arguments[1].contains("/")) {
				name = path.substring(path.lastIndexOf("/") + 1, path.length());
				newNewPath = getObject(path.substring(0, path.lastIndexOf("/")), currentDir);
			}
			// Parent directory is root
			else {
				name = path;
				newNewPath = root;
			}
		}
		
		copy(command, oldPath, newPath, newNewPath, name);
	}
}
