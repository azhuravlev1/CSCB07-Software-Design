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
 * Present the current working directory onto the console
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class PresentWorkingDirectory {
	
	/**
	 * Checks validity of pwd command.
	 * Usage: pwd [>/>> OUTFILE]
	 * @param arguments User input arguments
	 * @return returns true if pwd is valid, false if invalid
	 */
	private boolean isValidPresentWorkingDirectory(String[] arguments, Redirection r) {
		// Check path before parse
		if (!r.isRedirection(arguments)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Populates the string representing path
	 * @param path - string representing path
	 * @param currentdir - current directory
	 * @return path as a string
	 */
	private String populatePath(String path, Directory currentdir) {
		/* Check if the current working directory is the root
		 */
		if (currentdir.getParentDirectory() != null) {
			/* if the parent directory exists, add its name
			 * to the path along with '/'
			 */
			currentdir = currentdir.getParentDirectory();
			path = "/" + currentdir.getDirectoryName() + path;
			/* repeat the same process for the parent
			 */
			path = populatePath(path, currentdir);

		}
		return path;
	}
	
	/**
	 * Redirects stdout of pwd command.
	 * @param arguments User input arguments
	 * @param path Path of the current working directory
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @param r Redirection variable
	 */
	private void redirectWorkingDirectory(String[] arguments, String path, 
			Directory currentDir, Directory root, Redirection r) {
		String[] newString = {"\"" + path + "\""};
		// If valid redirection
		if (!r.isValidRedirection("pwd", newString, arguments, currentDir, root)) {
			return;
		}
		// Redirection
		r.redirection("pwd", newString, arguments, currentDir);
	}
	
	/**
	 * Creates a string representing path and add the name of
	 * the current working directory to it (along with /)
	 * @param currentDir The current working directory (object of Directory type)
	 * @return returns string representing path
	 */
	public String getStringPath(Directory currentDir) {
		String path = "/" + currentDir.getDirectoryName();
		path = populatePath(path, currentDir);
		return path;
	}

	/**
	 * Prints out the current working directory
	 * @param arguments User input arguments
	 * @param currentDir The current working directory (object of Directory type)
	 * @param root Root directory of the shell
	 */
	public String main(String[] arguments, Directory currentDir, Directory root) {
		Redirection r = new Redirection();
		if (arguments.length > 0 && !isValidPresentWorkingDirectory(arguments, r)) {
			Manual m = new Manual();
			System.out.println("Usage: " + m.getSynopsis("pwd"));
			return "";
		}
		
		// Change first redirect to valid string to redirect
		boolean doRedirect;
		doRedirect = r.isRedirection(arguments);
		
		// Create a string representing path
		String path = getStringPath(currentDir);
		
		// If redirects
		if (doRedirect) {
			redirectWorkingDirectory(arguments, path, currentDir, root, r);
		}
		// If doesn't redirect
		else {
			System.out.println(path);
		}
		
		return path;
	}

}
