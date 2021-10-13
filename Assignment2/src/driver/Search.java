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
import java.util.Arrays;
/**
 * Search for a directory or a file in the file system
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Search {
	
	/**
	 * Searches directories that contains the given name
	 * @param contents The array of all objects in the directory
	 * @param name The name want to search
	 * @return returns the list of directories that contains given name
	 */
	private ArrayList<Directory> searchDirectory(ArrayList<Object> contents, String name) {
		ArrayList<Directory> dirList = new ArrayList<Directory>();
		name = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\"") - 1);
		// Loop through the array with contents
		for (int i = 0; i < contents.size(); i++) {
			Object filedir = contents.get(i);
			// Check if the i-th object is Directory
			// Add the object to ArrayList if it contains the given name
			if (filedir.getClass().getName().equals("driver.Directory")) {
				if (((Directory) filedir).getDirectoryName().contains(name)) {
					dirList.add((Directory) filedir);
				}
			}
		}
		return dirList;
	}
	
	/**
	 * Searches files that contains the given name.
	 * @param contents The array of all objects in the directory
	 * @param name The name want to search
	 * @return returns the list of files that contains given name
	 */
	private ArrayList<File> searchFile(ArrayList<Object> contents, String name) {
		ArrayList<File> fileList = new ArrayList<File>();
		name = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\"") - 1);
		// Loop through the array with contents
		for (int i = 0; i < contents.size(); i++) {
			Object filedir = contents.get(i);
			// Check if the i-th object is File
			// Add the object to ArrayList if it contains the given name
			if (filedir.getClass().getName().equals("driver.File")) {
				if (((File) filedir).getFileName().contains(name)) {
					fileList.add((File) filedir);
				}
			}
		}
		return fileList;
	}
	
	/**
	 * Returns directory which the path indicates.
	 * @param path User input path
	 * @param currentDir Current directory where user is
	 * @return returns directory where path indicates
	 */
	private Directory pathReader(String path, Directory currentDir) {
		ChangeDirectory cd = new ChangeDirectory();
		return cd.main(currentDir, path);
	}
	
	/**
	 * Returns search outcomes onto shell.
	 * @param searchList
	 * @param type
	 * @param path User input path
	 * @param name The name want to search
	 * @return returns search outcomes
	 */
	private String printMessage(Object searchList, char type, String path, String name) {
		String str = "";
		// Print the list of searched directories
		if (type == 'd') {
			@SuppressWarnings("unchecked")
			ArrayList<Directory> contentList = (ArrayList<Directory>) searchList;
			str = str + path + ": Directories name with " + name + " are:" + '\n';
			for (int i = 0; i < contentList.size(); i++) {
				str = str + contentList.get(i).getDirectoryName() + '\n';
			}
		}
		// Print the list of searched files
		else {
			@SuppressWarnings("unchecked")
			ArrayList<File> contentList = (ArrayList<File>) searchList;
			str= str + path + ": Files name with \"" + name + "\" are:" + '\n';
			for (int i = 0; i < contentList.size(); i++) {
				str = str + contentList.get(i).getFileName() + '\n';
			}
		}
		return str;
	}
	
	/**
	 * Searches files or directories which name contains searching name.
	 * @param path User input path
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @param doRedirect Does input requires redirection
	 */
	private void searchOption(String path, String[] options, String[] arguments,
			Directory currentDir, Directory root, boolean doRedirect) {
		// Search path
		Directory newCurrent = pathReader(path, currentDir);
		String str = "";
		
		// Search all directories
		if (options[0].equals("d")) {
			// search /users/Desktop -type d -name "abc"
			ArrayList<Directory> dirList = new ArrayList<Directory>();
			dirList = searchDirectory(newCurrent.getDirectoryContents(), options[2]);
			// Display the output of the search command
			str = printMessage(dirList, 'd', path, options[2]);
		}
		// Search all files
		else if (options[0].equals("f")) {
			// search /users/Desktop -type f -name "xyz"
			ArrayList<File> fileList = new ArrayList<File>();
			fileList = searchFile(newCurrent.getDirectoryContents(), options[2]);
			// Display the output of the search command
			str = printMessage(fileList, 'f', path, options[2]);
		}
		
		if (doRedirect) {
			Redirection r = new Redirection();
			r.redirectCommand("search", arguments, str, currentDir,  root);
		}
		else {
			System.out.println(str);
		}
	}
	
	/**
	 * Loops the paths to search.
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 */
	public void search(String[] arguments, Directory currentDir, Directory root) {
		// Checks if arguments need redirection
		Redirection r = new Redirection();
		boolean doRedirect = r.isRedirection(arguments);
		
		// Argument validity check
		JShellInput valid = new JShellInput();
		if (!valid.isValidSearch(arguments)) {
			return;
		}
		
		// Adjust amount of search loop
		int n = Arrays.asList(arguments).indexOf("-type");
		String[] pathArray = Arrays.copyOfRange(arguments, 0, n);
		String[] options = Arrays.copyOfRange(arguments, n + 1, arguments.length);
		// Input only one path at each call
		for (String path : pathArray) {
			// Argument validity check
			if (!valid.isValidPath(path, currentDir, root, true, true, false)) {
				return;
			}
			searchOption(path, options, arguments, currentDir, root, doRedirect);
		}
	}
}
