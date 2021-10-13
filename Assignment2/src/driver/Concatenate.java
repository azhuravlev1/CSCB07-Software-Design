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

import java.util.Arrays;
/**
 * Disply the content of a file or multiple files onto  the console. Can be in
 * relative or full path. If redirection is specified redirect the content into
 * an outfile instead of printing.
 * current working directory.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Concatenate {
	
	/**
	 * Checks validity of cat command.
	 * Usage: cat FILE ... [>/>> OUTFILE]
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @param count Index of arguments
	 * @return returns true if cat is valid, false if invalid
	 */
	private boolean isValidConcatenate(String[] arguments, Directory currentDir, 
			Directory root, int count) {
		JShellInput valid = new JShellInput();
		// Check path before parse
		if (!valid.isValidPathBeforeParse(arguments[count])) {
			return false;
		}
		// Check path
		return valid.isValidPath(arguments[0], currentDir, root, false, true, false);
	}
	
	/**
	 * Redirects stdout of cat command.
	 * @param arguments User input arguments
	 * @param str String to redirect
	 * @param currentDir Current working directory
	 * @param root Root directory of the shell
	 * @param r Redirection variable
	 */
	private void redirectConcatenate(String[] arguments, String str, 
			Directory currentDir, Directory root, Redirection r) {
		int n = arguments.length;
		String[] newString = {str};
		String[] outfile = Arrays.copyOfRange(arguments, n - 2, n);
		// If valid redirection
		if (!r.isValidRedirection("cat", newString, outfile, currentDir, root)) {
			return;
		}
		// Redirection
		r.redirection("cat", newString, outfile, currentDir);
	}

	/**
	 * Given a string input from a user, runs cat command for that input
	 * (displays the content(s) of file(s) concatenated in the shell)
	 * @param arguments User input arguments
	 * @param filename String containing file names separated by spaces
	 * @param currentDir Current working directory
	 * @param root Root directory of the shell
	 * @param doRedirect Does input requires redirection
	 * @param r Redirection variable
	 */
	public String concatenate(String[] arguments, String filename, Directory currentDir,
			Directory root, boolean doRedirect, Redirection r) {
		String str = "";
		
		// For each file name get the corresponding file and print out its content
		File file = (File)currentDir.getChildByName(filename, true);
		if (file != null) {
			if (doRedirect) {
				String fileContent = "";
				fileContent = fileContent + file.getFileContent();
				fileContent = fileContent.substring(0, fileContent.length() - 1);
				str = fileContent + '\n' + '\n' + '\n' + "\"";
			}
			else {
				System.out.println(file.getFileContent());
				// Separate file contents using empty lines
				System.out.println("\n\n");
			}
		}
		
		// If redirects
		if (doRedirect) {
			redirectConcatenate(arguments, str, currentDir, root, r);
		}
		
		return str;
	}
	
	/**
	 * Given a string input from a user, runs cat command for that input
	 * (displays the content(s) of file(s) concatenated in the shell)	
	 * @param filename String containing file names separated by spaces
	 * @param currentDir Current working directory
	 * @param root Root directory of the shell
	 */
	public String main(String[] arguments, Directory currentDir, Directory root) {
		// Checks if arguments need redirection
		Redirection r = new Redirection();
		boolean doRedirect = r.isRedirection(arguments);
		
		// Adjust amount of concatenate loop
		int n = arguments.length;
		if (doRedirect)
			n = n - 2;
		
		String str = "";
		for (int i = 0; i < n; i++) {
			// Argument validity check
			if (!isValidConcatenate(arguments, currentDir, root, i)) {
				return "";
			}
			str = str + concatenate(arguments, arguments[i], 
					currentDir, root, doRedirect, r) + '\n';
		}
		return str;
	}

}
