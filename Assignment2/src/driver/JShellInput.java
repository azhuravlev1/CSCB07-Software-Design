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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
/**
 * Check for validity of command given and their arguments
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class JShellInput {
	
	/**
	 * Checks if command is valid. If invalid, then print error message.
	 * @param command User input command
	 * @return returns true if command is valid, false if command is invalid
	 */
	public boolean isValidCommand(String command) {
		String[] commandList = {"rm", "exit", "mkdir", "cd", "ls",
								"pwd", "mv", "cp", "cat", "curl",
								"echo", "man", "pushd", "popd", "history",
								"saveJShell", "loadJShell", "search", "tree"};
		if (!Arrays.asList(commandList).contains(command)) {
			System.out.println("Invalid: Command not found");
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the number of arguments is valid. If invalid, then print error message.
	 * @param command User input command
	 * @param length Number of the arguments
	 * @return returns true if number of arguments are valid, false if invalid
	 */
	public boolean isValidNumArgument(String command, int length) {
		// Initialize hash map
		HashMap<String, String> commandHashMap = new HashMap<String, String>();
		initializeHashMapArgumentNum(commandHashMap);
		// Get value for input command from hash map
		String lengthRequired = commandHashMap.get(command);
		
		// -1 means 0 and 1
		if (lengthRequired.equals("-1")) {
			if (length < 2) {
				return true;
			}
			System.out.println(command + ": Too many arguments");
		}
		// -2 means greater than 0 and -3 means greater or equal to 0
		else if (lengthRequired.equals("-2") && length != 0 || lengthRequired.equals("-3")) {
			return true;
		}
		// -4 means greater or equal to 4
		else if (lengthRequired.equals("-4")) {
			if (length >= 4) {
				return true;
			}
			System.out.println(command + ": Not enough arguments");
		}
		else if (Integer.parseInt(lengthRequired) == length) {
			return true;
		}
		
		// If there're no arguments, then tell usage
		return tellUsage(command);
	}
	
	/**
	 * Checks if arguments are valid. If invalid, then print error message.
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @param count Index of arguments
	 * @return returns true if arguments are valid, false if invalid
	 */
	public boolean isValidArgument(String command, String[] arguments, 
			Directory currentDir, Directory root, int count) {
		// isValidNumArgument is already checked
		if (arguments.length == 0) {
			// exit, popd, tree doesn't need more check
			return true;
		}
		
		// Check if arguments are valid to their command
		else if (command.equals("echo")) {
			return isValidEcho(arguments, currentDir, root);
		}
		else if (command.equals("pushd")) {
			return isValidPushDirectory(arguments, currentDir, root);
		}
		else if (command.equals("saveJShell")) {
			return isValidSaveJShell(arguments, currentDir, root);
		}
		else if (command.equals("loadJShell")) {
			return isValidLoadJShell(arguments, currentDir, root);
		}
		return true;
	}
	
	/**
	 * Initializes Hash Map with number of arguments.
	 * @param commandHashMap Hash map of the proper number of arguments
	 */
	private static void initializeHashMapArgumentNum(HashMap<String, String> commandHashMap) {
		// pair each command with number of argument(s)
		// -1 means 0 and 1, -2 means > 0, -3 means >= 0, -4 means >= 4
		commandHashMap.put("rm", "1");
		commandHashMap.put("exit", "0");
		commandHashMap.put("mkdir", "-2");
		commandHashMap.put("cd", "1");
		commandHashMap.put("ls", "-3");
		commandHashMap.put("pwd", "-3");
		commandHashMap.put("mv", "2");
		commandHashMap.put("cp", "2");
		commandHashMap.put("cat", "-2");
		commandHashMap.put("curl", "1");
		commandHashMap.put("echo", "-2");
		commandHashMap.put("man", "-2");
		commandHashMap.put("pushd", "1");
		commandHashMap.put("popd", "0");
		commandHashMap.put("history", "-3");
		commandHashMap.put("saveJShell", "1");
		commandHashMap.put("loadJShell", "1");
		commandHashMap.put("search", "-4");
		commandHashMap.put("tree", "0");
	}
	
	/**
	 * Helper method for isValidArgument method. Checks if a string is valid.
	 * @param arguments User input arguments
	 * @return returns true if string is valid, false if invalid
	 */
	public boolean isValidString(String[] arguments) {
		String combine = getCombinedString(arguments);
		int n = combine.length();
		
		if (combine.charAt(0) == '"' || combine.charAt(n - 1) == '"') {
			// If string contains something
			if (n > 2) {
				// If the string contains "\"" in between
				if (combine.substring(2, n - 1).contains("\"")) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * Helper method for isValidArgument method. Checks if not parsed path is valid.
	 * @param path Input path
	 * @return returns true if path is valid, false if invalid
	 */
	public boolean isValidPathBeforeParse(String path) {
		// Repeated slashes
		if (path.contains("//")) {
			System.out.print(path);
			System.out.println(": Inappropriate file or directory name");
			return false;
		}
		return true;
	}
	
	/**
	 * Checks validity of rm command.
	 * Usage: rm DIR
	 * @param path Directory to remove
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if rm is valid, false if invalid
	 */
	public boolean isValidRemoveDirectory(String path, Directory currentDir, Directory root) {
		return isValidPath(path, currentDir, root, true, false, false);
	}
	

	
	/**
	 * Checks validity of echo command.
	 * Usage: echo STRING [>/>> OUTFILE]
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if echo is valid, false if invalid
	 */
	private boolean isValidEcho(String[] arguments, Directory currentDir, Directory root) {
		int n = arguments.length;
		
		if (n > 2) {
			// If input only have a string
			if (isValidString(Arrays.copyOfRange(arguments, 0, n))) {
				return true;
			}
			// If arguments[0:n-2] is not a string
			if (!isValidString(Arrays.copyOfRange(arguments, 0, n - 2))) {
				System.out.println("echo: Inappropriate string");
				return false;
			}
			String[] str = Arrays.copyOfRange(arguments, 0, n - 2);
			String[] redirect = Arrays.copyOfRange(arguments, n - 2, n);
			
			// Change first redirect to valid string to redirect
			Redirection r = new Redirection();
			return r.isValidRedirection("echo", str, redirect, currentDir, root);
		}
		// If input only have a string
		else {
			if (!isValidString(Arrays.copyOfRange(arguments, 0, n))) {
				System.out.println("echo: Inappropriate string");
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Checks validity of man command.
	 * Usage: man CMD [CMD2 ...]: if not commands
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param count Index of arguments
	 * @return returns true if man is valid, false if invalid
	 */
	public boolean isValidManual(String command, Directory currentDir) {
		String[] commandList = {"rm", "exit", "mkdir", "cd", "ls",
				"pwd", "mv", "cp", "cat", "curl",
				"echo", "man", "pushd", "popd", "history",
				"saveJShell", "loadJShell", "search", "tree"};
		// If one of the arguments is not a valid command
		if (!Arrays.asList(commandList).contains(command)) {
			System.out.println("man: No manual entry for " + command);
			return false;
		}
		return true;
	}
	
	/**
	 * Checks validity of pushd command.
	 * pushd DIR
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if pushd is valid, false if invalid
	 */
	private boolean isValidPushDirectory(String[] arguments, Directory currentDir, 
			Directory root) {
		// Check path before parse
		if (!isValidPathBeforeParse(arguments[0])) {
			return false;
		}
		return isValidPath(arguments[0], currentDir, root, true, false, false);
	}
	
	/**
	 * Checks validity of history command.
	 * Usage: history [number] [>/>> OUTFILE]
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @return returns true if history is valid, false if invalid
	 */
	public boolean isValidHistory(String number) {
		try {
	        @SuppressWarnings("unused")
			int num = Integer.parseInt(number);
	    } catch (NumberFormatException nfe) {
	    	// if argument is not an integer
	    	System.out.println("history: Numeric argument required");
	        return false;
	    }
		return true;
	}
	
	/**
	 * Checks validity of saveJShell command.
	 * Usage: saveJShell FileName
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if saveJShell is valid, false if invalid
	 */
	private boolean isValidSaveJShell(String[] arguments, Directory currentDir, 
			Directory root) {
		return isValidPath(arguments[0], currentDir, root, false, true, false);
	}
	
	/**
	 * Checks validity of loadJShell command.
	 * Usage: loadJShell FileName
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if loadJShell is valid, false if invalid
	 */
	private boolean isValidLoadJShell(String[] arguments, Directory currentDir, 
			Directory root) {
		return isValidPath(arguments[0], currentDir, root, false, true, false);
	}
	
	/**
	 * Checks validity of search command.
	 * Usage: search PATH ... -type f|d -name expression [>/>> OUTFILE]
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 * @return returns true if search is valid, false if invalid
	 */
	public boolean isValidSearch(String[] arguments) {
		// -type, File or Directory, -name, expression
		boolean type = false, fod = false, name = false, expression = false;
		
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals("-type")) {
				// If -type is placed in wrong location
				if (i == 0 || i == arguments.length - 1) {
					return tellUsage("search");
				}
				type = true;
			}
			else if (arguments[i].equals("f") || arguments[i].equals("d")) {
				// If -type is placed in wrong location
				if (i == 0 || i == arguments.length - 1 || !type) {
					return tellUsage("search");
				}
				fod = true;
			}
			else if (arguments[i].equals("-name")) {
				// If -name is placed in wrong location
				if (i == 0 || i == arguments.length - 1 || !fod) {
					return tellUsage("search");
				}
				name = true;
			}
			else if (name) {
				if (!isValidString(Arrays.copyOfRange(arguments, i, 
						arguments[i].lastIndexOf("\"") + 1))) {
					System.out.println("search: Inappropriate expression");
					return false;
				}
				expression = true;
				break;
			}
		}
		return type && name && expression;
	}
	
	/**
	 * Checks if parsed path is valid.
	 * @param path Input path
	 * @param current Current directory where user is
	 * @param root Root directory of the shell
	 * @param canDir If the path can be a directory
	 * @param canFile If the path can be a file
	 * @param newContent If the path can be a new file or a directory
	 * @return returns true if path is valid, false if invalid
	 */
	public boolean isValidPath(String path, Directory current, Directory root,
			boolean canDir, boolean canFile, boolean newContent) {
		Directory saveDir = current;
		Directory dir = saveDir;
		// If path is absolute
		if (path.charAt(0) == '/') {
			dir = root;
		}
		String[] parsedPath = getSlashFreePath(path).split("/");
		for (int i = 0; i < parsedPath.length; i++) {
			// Do nothing
			if (parsedPath[i].equals(".")) { }
			else {
				saveDir = pathCheck(path, parsedPath, dir, i, canDir, canFile, newContent);
				// Inappropriate path
				if (saveDir == null) {
					return false;
				}
				// New path
				if (saveDir == dir) {
					return true;
				}
				dir = saveDir;
			}
		}
		return true;
	}
	
	/**
	 * Helper method for isValidPath. Returns current directory.
	 * @param path Input path
	 * @param parsedPath Parsed input path
	 * @param dir Current directory
	 * @param i Index of parsedPath
	 * @param canDir If the path can be a directory
	 * @param canFile If the path can be a file
	 * @param newContent If the path can be a new file or a directory
	 * @return returns current directory, null if path is inappropriate
	 */
	private Directory pathCheck(String path, String[] parsedPath, Directory dir, int i,
			boolean canDir, boolean canFile, boolean newContent) {
		if (parsedPath[i].equals("..")) {
			// If saveDir is not a root
			if (dir.getParentDirectory() != null) {
				// Go to the parent directory
				dir = dir.getParentDirectory();
			}
			// If saveDir is a root
			else {
				System.out.println(getCombinedPath(parsedPath) + ": Inappropriate Path");
				return null;
			}
		}
		else {
			// Get desired content from dir
			Object content = dir.getChildByName(parsedPath[i], false);
			// If content doesn't exist
			if (content == null) {
				if (!isNewPath(parsedPath, i, newContent)) {
					return null;
				}
				return dir;
			}
			dir = pathInDir(content, path, parsedPath, dir, i, canDir, canFile);
		}
		return dir;
	}
	
	/**
	 * Helper method for isValidPath. Checks if it is valid to be new.
	 * @param parsedPath Parsed input path
	 * @param i Index of parsedPath
	 * @param newContent If the path can be a new file or a directory
	 * @return returns true if it is valid, false if invalid
	 */
	private boolean isNewPath(String[] parsedPath, int i, boolean newContent) {
		// If there shouldn't be a new content
		if (!newContent) {
			System.out.print(getCombinedPath(parsedPath));
			System.out.println(": No such file or directory");
			return false;
		}
		// Input has inappropriate character
		if (parsedPath[i].contains(".")) {
			// Inappropriate content name
			System.out.print(getCombinedPath(parsedPath));
			System.out.println(": Inappropriate file or directory name");
			return false;
		}
		return true;
	}
	
	/**
	 * Helper method for isValidPath. Returns current directory.
	 * @param content Content that has a same name with parsedPath[i]
	 * @param path Input path
	 * @param parsedPath Parsed input path
	 * @param dir Current directory
	 * @param i Index of parsedPath
	 * @param canDir If the path can be a directory
	 * @param canFile If the path can be a file
	 * @return returns current directory, null if path is inappropriate
	 */
	private Directory pathInDir(Object content, String path, String[] parsedPath, Directory dir, 
			int i, boolean canDir, boolean canFile) {
		// If content is a directory
		if (content instanceof Directory) {
			// If path can't be a directory
			if (i == parsedPath.length - 1 && !canDir) {
				System.out.println(path + ": Is a directory");
				return null;
			}
			dir = (Directory) content;
		}
		// If content is a file
		else if (content instanceof File) {
			// If path can't be a file
			if (!canFile) {
				System.out.println(path + ": Is a file");
				return null;
			}
			// Middle of the path can't be a file
			if (i < parsedPath.length - 1) {
				System.out.println(path + ": Inappropriate path");
				return null;
			}
			return dir;
		}
		return dir;
	}
	
	/**
	 * Checks if format of URL is valid.
	 * @param command User input command
	 * @param inputURL URL to load
	 * @return returns true if URL format is valid, false if invalid
	 */
	public boolean isValidURL(String command, String inputURL) {
		URL url;
		try {
			url = new URL(inputURL);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
			
			// HTTP request has succeeded
			if (huc.getResponseCode() == 200) {
				return true;
			}
			// HTTP request has failed
			else {
				System.out.println(command + ": Invalid URL");
				return false;
			}
		} catch (MalformedURLException e) {
			// If URL is malformed
			System.out.println(command + ": Invalid URL");
			return false;
		} catch (IOException e) {
			// If URL is invalid
			System.out.println(command + ": Invalid URL");
			return false;
		}
	}
	
	/**
	 * Helper method for isValidString method. Combines parsed string.
	 * @param parsedPath Parsed input path
	 * @return returns string of combined string.
	 */
	private String getCombinedString(String[] parsedString) {
		String path = "";
		for (int i = 0; i < parsedString.length; i++) {
			path = path + " " + parsedString[i];
		}
		return path;
	}
	
	/**
	 * Helper method for isValidPath method. Combines parsed path.
	 * @param parsedPath Parsed input path
	 * @return returns string of combined path.
	 */
	private String getCombinedPath(String[] parsedPath) {
		String path = parsedPath[0];
		for (int i = 1; i < parsedPath.length; i++) {
			path = path + "/" + parsedPath[i];
		}
		return path;
	}
	
	/**
	 * Helper method for isValidPath method. Removes leading and lagging slash.
	 * @param parsedPath Parsed input path
	 * @return returns string of slash-removed path.
	 */
	private String getSlashFreePath(String parsedPath) {
		String path = parsedPath;
		if (path.length() == 1) {
			if (path.equals("/")) {
				return "";
			}
		}
		else {
			// Leading slash
			if (path.charAt(0) == '/') {
				path = path.substring(1, path.length());
			}
			// Lagging slash
			if (path.charAt(path.length() - 1) == '/') {
				path = path.substring(0, parsedPath.length() - 1);
			}
		}
		return path;
	}
	
	/**
	 * Tells usage of the command and returns false.
	 * @param command User input command
	 * @return returns false.
	 */
	private boolean tellUsage(String command) {
		Manual m = new Manual();
		System.out.println("Usage: " + m.getSynopsis(command));
		return false;
	}
}
