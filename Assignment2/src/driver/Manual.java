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
import java.util.HashMap;
/**
 * Print the documentation for the given command
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Manual {
	
	/**
	 * Prints documentation for each of the given commands
	 * @param command User input command
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 */
	public void manual(String command, String[] arguments, Directory currentDir, Directory root) {
		if (arguments.length>1) {
			System.out.println("Error: Man takes only one arguments");
		    return;
		}
		// Checks if arguments need redirection
		Redirection r = new Redirection();
		boolean doRedirect = r.isRedirection(arguments);
		
		// Argument validity check
		JShellInput valid = new JShellInput();
		
		// Adjust amount of concatenate loop
		int n = arguments.length;
		if (doRedirect)
			n = n - 2;
		
		String[] manCommands = Arrays.copyOfRange(arguments, 0, n);
		for (String cmd : manCommands) {
			// Argument validity check
			if (!valid.isValidManual(cmd, currentDir)) {
				return;
			}
			getDocumentation(cmd, arguments, currentDir, root, doRedirect);
		}
	}
	
	/**
	 * Prints documentation
	 * @param command User input command
	 * @param arguments
	 * @param currentDir
	 */
	public void getDocumentation(String command, String[] arguments, Directory currentDir, 
			Directory root, boolean doRedirect) {
		// Initialize hash map for synopsis
		HashMap<String, String> synopsisHashMap = new HashMap<String, String>();
		initializeHashMapSynopsis(synopsisHashMap);
		// Initialize hash map for description
		HashMap<String, String> descriptionHashMap = new HashMap<String, String>();
		initializeHashMapDescription(descriptionHashMap);
		// Initialize hash map for description
		HashMap<String, String> exampleHashMap = new HashMap<String, String>();
		initializeHashMapExample(exampleHashMap);
		
		String str = "======================================================\n"
				+ "NAME:\n" + command + '\n'
				+ "SYNOPSIS:\n" + synopsisHashMap.get(command) + '\n'
				+ "DESCRIPTION:\n" + descriptionHashMap.get(command) + '\n'
				+ "EXAMPLES:\n" + exampleHashMap.get(command) + '\n'
				+ "======================================================\n";
		
		// If redirects
		if (doRedirect) {
			Redirection r = new Redirection();
			r.redirectCommand("man", arguments, "\"" + str + "\"", currentDir,  root);
		}
		else {
			System.out.println(str);
		}
	}
	
	/**
	 * @param command
	 * @return returns synopsis of given command
	 */
	public String getSynopsis(String command) {
		HashMap<String, String> synopsisHashMap = new HashMap<String, String>();
		initializeHashMapSynopsis(synopsisHashMap);
		
		return synopsisHashMap.get(command);
	}
	
	/**
	 * Initializes Hash Map with synopsis
	 * @param synopsisHashMap
	 */
	private static void initializeHashMapSynopsis(HashMap<String, String> synopsisHashMap) {
		// pair each command with synopsis
		synopsisHashMap.put("rm", "rm DIR");
		synopsisHashMap.put("exit", "exit");
		synopsisHashMap.put("mkdir", "mkdir DIR1 ...");
		synopsisHashMap.put("cd", "cd DIR");
		synopsisHashMap.put("ls", "ls -R [PATH ...] [>/>> OUTFILE]");
		synopsisHashMap.put("pwd", "pwd [>/>> OUTFILE]");
		synopsisHashMap.put("mv", "mv OLDPATH NEWPATH");
		synopsisHashMap.put("cp", "cp OLDPATH NEWPATH");
		synopsisHashMap.put("cat", "cat FILE ... [>/>> OUTFILE]");
		synopsisHashMap.put("curl", "curl URL");
		synopsisHashMap.put("pushd", "pushd DIR");
		synopsisHashMap.put("popd", "popd");
		synopsisHashMap.put("history", "history [number]");
		synopsisHashMap.put("echo", "echo STRING [>/>> OUTFILE]");
		synopsisHashMap.put("man", "man CMD [CMD2 ...] [>/>> OUTFILE]");
		synopsisHashMap.put("saveJShell", "saveJShell FILE");
		synopsisHashMap.put("loadJShell", "loadJShell FILE");
		synopsisHashMap.put("search", "search PATH [PATH2...] -type [FILE/DIR] "
				+ "-name [NAME] [>/>> OUTFILE]");
		synopsisHashMap.put("tree", "tree");
	}
	
	/**
	 * Initializes Hash Map with description 
	 * @param descriptionHashMap
	 */
	private static void initializeHashMapDescription(HashMap<String, String> descriptionHashMap) {
		// pair each command with synopsis
		descriptionHashMap.put("rm", "The rm command removes DIR from the file sytem and\n"
                + "acts recursivly to also remove all the children of DIR.");
		descriptionHashMap.put("exit", "The exit command quits the working program.\n");
		descriptionHashMap.put("mkdir", "The mkdir command takes at least one argument, DIR. It\n"
				+ "creates DIR in the current working directory or in the\n"
				+ "given path and if and only if DIR is successfully\n"
				+ "created the next directory is created. An error is\n"
				+ "given at any moment a invalid argument is encountered.");
		descriptionHashMap.put("cd", "The mkdir command take two arguments, DIR1 and DIR2.\n"
				+ "The cd command change the current working directory\n"
				+ "to DIR.DIR can be in the current directory or in any\n"
				+ "given path. .. means parent directory and . means\n"
				+ "current working directory. The directory must be /,\n"
				+ "forward slash. The root of the file system is a \n"
				+ "single slash /.");
		descriptionHashMap.put("ls", "The ls command lists files and directories, if path\n"
				+ "is a file, prints the file; if path is a directory,\n"
				+ "prints the contents of the directory with a new line\n"
				+ "following each of the content and an extra new line;\n"
				+ "if there is no path given, prints contents of the\n"
				+ "current directory with a new ling following each of\n"
				+ "the content. Otherwise, prints error message\n"
				+ "\"No such file or directory.\" The following options\n"
				+ "are available:\n"
				+ "> -R		Recursively lists all subdirectories\n"
				+ "		of a given directory.\n"
				+ "> OUTFILE	Erases old contents inside of OUTFILE\n"
				+ "		and replaces it with the contents in\n"
				+ "		a given directory. If OUTFILE does not\n"
				+ "		exist, then creates a new file with\n"
				+ "		name OUTFILE and places contents list\n"
				+ "		inside.\n"
				+ ">> OUTFILE	Appends the whole list of the contents\n"
				+ "		in a given directory onto old contents\n"
				+ "		in OUTFILE. If OUTFILE does not exist,\n"
				+ "		then creates OUTFILE and puts the list\n"
				+ "		into it.");
		descriptionHashMap.put("pwd", "The pwd command prints the current working directory,\n"
				+ "including the whole path. The following options are\n"
				+ "available:\n"
				+ "> OUTFILE	Erases old contents inside of OUTFILE\n"
				+ "		and replaces it with the whole path\n"
				+ "		of the current working directory. If\n"
				+ "		OUTFILE does not exist, then creates\n"
				+ "		a new file with name OUTFILE and\n"
				+ "		places the path inside.\n"
				+ ">> OUTFILE	Appends the whole path of the current\n"
				+ "		working directory onto old contents\n"
				+ "		in OUTFILE. If OUTFILE does not exist,\n"
				+ "		then creates OUTFILE and puts the path\n"
				+ "		into it.");
		descriptionHashMap.put("mv", "The mv command moves an item OLDPATH to NEWPATH, if\n"
				+ "OLDPATH is a file. If OLDPATH is a directory,\n"
				+ "recursively moves contents of OLDPATH to NEWPATH.\n"
				+ "OLDPATH and NEWPATH can be relative to the current\n"
				+ "directory or may be full paths.");
		descriptionHashMap.put("cp", "The cp command copies an item OLDPATH to NEWPATH, if\n"
				+ "OLDPATH is a file. If OLDPATH is a directory,\n"
				+ "recursively copies contents of OLDPATH to NEWPATH.\n"
				+ "OLDPATH and NEWPATH can be relative to the current\n"
				+ "directory or may be full paths.");
		descriptionHashMap.put("cat", "The cat command prints out content(s) of a specified\n"
				+ "OUTFILE is not given. Otherwise, command puts STRING\n"
				+ "file or several specified files. The following options\n"
				+ "are available:\n"
				+ "> OUTFILE	Erases old contents inside of OUTFILE\n"
				+ "		and replaces it with content(s) of a\n"
				+ "		specified file or several files. If\n"
				+ "		OUTFILE does not exist, then creates\n"
				+ "		a new file with name OUTFILE and\n"
				+ "		places the content(s) inside.\n"
				+ ">> OUTFILE	Appends content(s) of a specified\n"
				+ "		file or several files onto old contents\n"
				+ "		in OUTFILE. If OUTFILE does not exist,\n"
				+ "		then creates OUTFILE and puts content(s)\n"
				+ "		into it.");
		descriptionHashMap.put("curl", "The curl command retrieves the file at given URL\n"
				+ "and add it to the current working directory.");
		descriptionHashMap.put("pushd", "The pushd command saves the current working directory\n"
				+ "and then changes the new current working directory to\n"
				+ "DIR.");
		descriptionHashMap.put("popd", "The popd command changes current working directory to\n"
				+ "the last saved directory in the directory stack and\n"
				+ "then removes the last saved directory from the\n"
				+ "directory stack.");
		descriptionHashMap.put("history", "If the redirection operator is not given,\n"
				+ "the history command gets a list of recent commands\n"
				+ "and prints it, one command per line. If an optional\n"
				+ "integer argument N is given, prints out only the last\n"
				+ "N commands. The following options are available:\n"
				+ "> OUTFILE	Erases old contents inside of OUTFILE\n"
				+ "		and appends the list to it. If OUTFILE\n"
				+ "		does not exist, then creates a new\n"
				+ "		file with name OUTFILE and places the\n"
				+ "		list inside.\n"
				+ ">> OUTFILE	Appends the list to the old contents\n"
				+ "		of the OUTFILE. If OUTFILE does not\n"
				+ "		exist, then creates a new file with\n"
				+ "		name OUTFILE and places the list\n"
				+ "		inside.");
		descriptionHashMap.put("echo", "The echo command outputs STRING onto shell, if\n"
				+ "OUTFILE is not given. Otherwise, command puts STRING\n"
				+ "into the OUTFILE. The following options are available:\n"
				+ "> OUTFILE	Erases old contents inside of\n"
				+ "		OUTFILE and replaces it with STRING.\n"
				+ "		If OUTFILE does not exist, then\n"
				+ "		creates a new file with name OUTFILE\n"
				+ "		and places STRING inside.\n"
				+ ">> OUTFILE	Appends STRING onto old contents in\n"
				+ "		OUTFILE. If OUTFILE does not exist,\n"
				+ "		then creates OUTFILE and puts STRING\n"
				+ "		into it.");
		descriptionHashMap.put("man", "If the redirection operator is not given, the man\n"
				+ "command prints documentation for the given CMD. The\n"
				+ "following options are available:\n"
				+ "> OUTFILE	Erases old contents inside of OUTFILE\n"
				+ "		and appends the documentation to it.\n"
				+ "		If OUTFILE does not exist, then\n"
				+ "		creates a new file with name OUTFILE\n"
				+ "		and places documentation inside.\n"
				+ ">> OUTFILE	Appends the documentation to the old\n"
				+ "		contents of the OUTFILE. If OUTFILE\n"
				+ "		does not exist, then creates a new\n"
				+ "		file with name OUTFILE and places\n"
				+ "		documentation inside.");
		descriptionHashMap.put("saveJShell", "The saveJShell command saves current session onto a\n"
				+ "given file, if the file is not given, create that file.");
		descriptionHashMap.put("loadJShell", "The loadJShell command loads a previous session\n"
				+ "that is saved on a file. Disabled after any other"
				+ "command is executed.");
		descriptionHashMap.put("search", "The search command searches for a file or directory\n"
				+ "in the specified path(s). The following options\n"
				+ "are available:"
				+ "> OUTFILE	Erases old contents inside of\n"
				+ "		OUTFILE and appends the searched\n"
				+ "		contents to it. If OUTFILE does not \n"
				+ "		exist, then creates a new file with\n"
				+ "		name OUTFILE and places searched\n"
				+ "		contents inside.\n"
				+ ">> OUTFILE	Appends the searched contents to the\n"
				+ "		old contents of the OUTFILE. If\n"
				+ "		OUTFILE does not exist, then creates\n"
				+ "		a new file with name OUTFILE and\n"
				+ "		places searched contents inside.");
		descriptionHashMap.put("tree", "The tree command displays the entire file system\n"
				+ "in shell.");
	}
	
	/**
	 * Initializes Hash Map with Example
	 * @param exampleHashMap
	 */
	private static void initializeHashMapExample(HashMap<String, String> exampleHashMap) {
		// pair each command with examples
		exampleHashMap.put("rm", "rm Desktop\n"
				+ "rm project1");
		exampleHashMap.put("exit", "exit");
		exampleHashMap.put("mkdir", "mkdir project1 project2\n"
				+ "mkdir Desktop/project1 Desktop/project2\n"
				+ "mkdir Desktop/project1 project2\n"
				+ "mkdir project1\n"
				+ "mkdir project1 project2 project3");
		exampleHashMap.put("cd", "cd dektop\n"
				+ "cd desktop/project1\n"
				+ "cd ..\n"
				+ "cd .\n"
				+ "cd /.");
		exampleHashMap.put("ls", "ls\n"
				+ "ls Desktop\n"
				+ "ls Desktop/example\n"
				+ "ls -R Desktop\n"
				+ "ls -R Desktop > outfile\n"
				+ "ls -R Desktop >> outfile");
		exampleHashMap.put("pwd", "pwd\n"
				+ "pwd > outfile\n"
				+ "pwd >> outfile");
		exampleHashMap.put("mv", "mv /Desktop/dir1 dir2\n"
				+ "mv /Desktop/file1 /Desktop/dir1\n");
		exampleHashMap.put("cp", "cp /Desktop/dir1 dir2\n"
				+ "cp /Desktop/file1 /Desktop/dir1\n");
		exampleHashMap.put("curl", "curl http://www.java.edu/Manual/commands/synopsis.html");
		exampleHashMap.put("cat", "cat test\n"
				+ "cat test1 test2 > outfile\n"
				+ "cat test1 test2 test3 >> outfile");
		exampleHashMap.put("pushd", "pushd Desktop");
		exampleHashMap.put("popd", "popd");
		exampleHashMap.put("history", "history\n"
				+ "history 4\n"
				+ "history > outfile\n"
				+ "history 3 >> outfile");
		exampleHashMap.put("echo", "echo \"hello world\"\n"
				+ "echo \"hello world\" > outfile\n"
				+ "echo \"hello world\" >> outfile");
		exampleHashMap.put("man", "man CMD\n"
				+ "man CMD CMD2 > outfile\n"
				+ "man CMD CMD2 CMD3 CMD4 >> outfile");
		exampleHashMap.put("saveJShell", "saveJShell /Users/User1/Desktop/save\n"
				+ "saveJShell save.txt");
		exampleHashMap.put("loadJShell", "loadJShell /Users/User1/Desktop/save\n"
				+ "loadJShell save.txt");
		exampleHashMap.put("search", "search /users/Desktop -type f -name 'file'\n"
				+ "search /Desktop /Desktop2 -type d -name 'directory'\n"
				+ "search /Desktop -type d -name 'directory' > outfile\n"
				+ "search /Desktop -type f -name 'file' >> outfile");
		exampleHashMap.put("tree", "tree\n");
	}
}
