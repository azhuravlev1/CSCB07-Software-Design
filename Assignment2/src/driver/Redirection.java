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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
/**
 * Redirect output into an outfile. Can overwrite of append to outfile. If 
 * outfile does not exist create outfile.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Redirection {	
	/**
	 * If OUTFILE doesn't exist, then create a new OUTFILE with the string.
	 * If OUTFILE exists, then checks if input contains ">" or ">>".
	 * @param command User input command
	 * @param stdin Input for string
	 * @param arguments Redirection options [>/>> OUTFILE]
	 * @param currentDir Current directory where user is
	 */
	public void redirection(String command, String[] stdin, String[] arguments, 
			Directory currentDir) {
		if (!isRedirectionCommand(command)) {
			return;
		}
		
		String str = arrayToString(Arrays.copyOfRange(stdin, 0, stdin.length));
		Object outfile = currentDir.getChildByName(arguments[1], false);
		
		// OUTFILE exists
		if (outfile instanceof File) {
			// Overwrite
			if (arguments[0].equals(">")) {
				overwrite((File) outfile, str);
			}
			// Append
			else if (arguments[0].equals(">>")) {
				append((File) outfile, str);
			}
		}
		// OUTFILE doesn't exist
		else {
			// Creates a new file
			createOutfile(arguments[1], str, currentDir);
		}
	}
	
	/**
	 * Redirects stdout of command.
	 * @param arguments User input arguments
	 * @param str String to redirect
	 * @param currentDir Current working directory
	 * @param root Root directory of the shell
	 */
	public void redirectCommand(String command, String[] arguments, String str, 
			Directory currentDir, Directory root) {
		Redirection r = new Redirection();
		int n = arguments.length;
		String[] newString = {str};
		String[] outfile = Arrays.copyOfRange(arguments, n - 2, n);
		// If valid redirection
		if (!r.isValidRedirection(command, newString, outfile, currentDir, root)) {
			return;
		}
		// Redirection
		r.redirection(command, newString, outfile, currentDir);
	}
	
	/**
	 * Checks if parsed format of redirection is valid.
	 * Usage: STRING >/>> OUTFILE
	 * @param command User input command
	 * @param str String to redirect
	 * @param redirect Arguments for redirection
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if redirection format is valid, false if invalid
	 */
	public boolean isValidRedirection(String command, String[] str, String[] redirect, 
			Directory currentDir, Directory root) {
		if (redirect.length == 0) {
			return false;
		}
		
		JShellInput v = new JShellInput();
		// If input has > or >>
		if ((redirect[0].equals(">") || redirect[0].equals(">>"))) {
			if (command.equals("echo") && 
					!v.isValidString(Arrays.copyOfRange(str, 0, str.length))) {
				System.out.println(command + ": Inappropriate string");
				return false;
			}
			// Check path before parse
			if (!v.isValidPathBeforeParse(redirect[1])) {
				return false;
			}
			return v.isValidPath(redirect[1], currentDir, root, false, true, true);
		}
		// Not a proper redirection
		else {
			Manual m = new Manual();
			System.out.println("Usage: " + m.getSynopsis(command));
			return false;
		}
	}
	
	/**
	 * Helper method for redirection method. 
	 * Checks if command is valid to redirect.
	 * @param command User input command
	 * @return returns true if command is valid, false if invalid
	 */
	private boolean isRedirectionCommand(String command) {
		String[] redirect = new String[]{"ls", "pwd", "cat", "echo", "man", 
				"history", "search", "tree"};
		if (Arrays.asList(redirect).contains(command)) {
			return true;
		}
		Manual m = new Manual();
		System.out.println("Usage: " + m.getSynopsis(command));
		return false;
	}
	
	/**
	 * Checks if arguments have redirection
	 * @param arguments User input arguments
	 * @return returns true if redirection, false if not a redirection
	 */
	public boolean isRedirection(String[] arguments) {
		int n = arguments.length;
		if (n >= 2) {
			// Check >/>>
			if (arguments[n - 2].equals(">") || arguments[n - 2].equals(">>")) {
				return true;
			}
		}
		return false;
	}
	
	public PrintStream getStandardOutput(boolean console, 
			ByteArrayOutputStream str) throws FileNotFoundException {
		PrintStream stdout;
		if (console) {
			// Creating a File object that represents the disk file. 
			stdout = new PrintStream(str);
		}
		else {
			// Store current System.out before assigning a new value 
			stdout = System.out;
		}
        
        return stdout;
	}
	
	/**
	 * Helper method for redirection method. Combines an array to a string.
	 * @param array Array of strings to combine
	 * @return returns combined string
	 */
	private String arrayToString(String[] array) {
		String str = array[0];
		for (int i = 1; i < array.length; i++) {
			str = str + " " + array[i];
		}
		return str;
	}
	
	/**
	 * Helper method for redirection method. Creates a new OUTFILE with a string.
	 * @param arguments User input arguments
	 * @param str String to put inside the OUTFILE
	 * @param currentDir Current directory where user is
	 */
	private void createOutfile(String arguments, String str, Directory currentDir) {
		@SuppressWarnings("unused")
		File newFile = null;
		newFile = new File(arguments, str, currentDir);
	}
	
	/**
	 * Overwrite the string onto OUTFILE.
	 * @param outfile File to overwrite on
	 * @param str String to overwrite with
	 */
	public void overwrite(File outfile, String str) {
		outfile.setFileContent(str);
	}
	
	/**
	 * Append the string onto the OUTFILE.
	 * @param outfile File to append on
	 * @param str String to append with
	 */
	private void append(File outfile, String str) {
		String newcontent = outfile.getFileContent() + "\n" + str;
		outfile.setFileContent(newcontent);
	}
}
