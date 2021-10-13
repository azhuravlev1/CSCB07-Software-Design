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
 * Change directory to specified directory. Directory can be in relative or full path.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class ChangeDirectory {

	public Directory main(Directory currentdir, String path) {
		if (path=="") {
			return currentdir;
		}
		Boolean fullpath = checkIfStartsWithRoot(path);
		if (fullpath) {
			currentdir = getRoot(currentdir);
			currentdir = getDirectory(path, currentdir);
		}
		else {
			currentdir = getDirectory(path, currentdir);
		}
		return currentdir;
	}

	/**
	 * Checks if the given string representation of path is full path or relative
	 * relative path
	 * @param path
	 * @return - returns true if full path is given, false otherwise
	 */
	private Boolean checkIfStartsWithRoot(String path) {
		return path.substring(0, 1).equals("/");
	}

	/**
	 * Given a relative path to the current directory, returns the directory
	 * specified in that path. If the given path does not exist, returns null.
	 * @param path - string representation of the relative path
	 * @param currentdir - current directory
	 * @return new current directory
	 */
	private Directory getDirectory(String path, Directory currentdir) {
		Directory root = currentdir;
		String[] directories = path.split("/");
		for (String dir : directories) {
			if(dir.trim().contentEquals("")) {
				continue;
			}
			if (dir.equals(".")) {
			}
			else if (dir.equals("..")) {
				if (currentdir.getParentDirectory() != null) {
					currentdir = currentdir.getParentDirectory();
				}
			}
			else {
				if (currentdir.getChildByName(dir, false) != null) {
					currentdir = (Directory)currentdir.getChildByName(dir, false);
				}
				else {
					System.out.println(path + ": No such file or directory");
					return root;
				}
			}
		}
		return currentdir;
	}

	/**
	 *  Returns the root
	 * @param currentdir - current directory
	 * @return 
	 */
	private Directory getRoot(Directory currentdir) {
		Directory root = currentdir;
		while (root.getParentDirectory() != null) {
			root = root.getParentDirectory();
		}
		return root;
	}
}