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

import java.util.LinkedList;
/**
 * Prints the history of commands including syntaxtical errors. If redirection
 * is given can redirecte old commands to a file.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class History {
	
	/**
	 * Converts the arguments stored in an array into a string
	 */
	private String ArrayToString(String[] array) {
		String s = "";
		for (int i=0;i<array.length;i++) {
			s = s+" "+array[i];
		}
		return s;
	}

	/**
	 * Take as an input a list of recent commands, a list of the commands
	 * arguments stored in arrays, and and the command number from which
	 * the user want to start printing. Returns strings to print.
	 * @param commandsList List of commands
	 * @param argumentsList List of arguments
	 * @param N Number of histories to print
	 * @return returns strings to print
	 */
	public String printRecentCommands(LinkedList<String> commandsList, 
			LinkedList<String[]> argumentsList, int N) {
		int i;
		// If there is no argument or too large argument
		if (N == 0 || N > commandsList.size()) {
			i = 0;
		}
		// If there is an argument
		else {
			i = commandsList.size()- N;
		}
		
		String str = "";
		// Go over the list of commands and the list of arguments 
		// to print recent commands with their respective argument
		while (i < commandsList.size()) {
			str = str + String.valueOf(i + 1) + ". " + commandsList.get(i) 
			+ ArrayToString((String[]) argumentsList.get(i)) + '\n';
			i++;
		}
		
		return str;
	}
	
	/**
	 * Checks input path and redirection.
	 * @param arguments User input arguments
	 * @param commandsList List of commands
	 * @param argumentsList List of arguments
	 * @param currentDir Current directory where user is
	 * @param root Root directory of the shell
	 */
	public void history(String[] arguments, LinkedList<String> commandsList, 
			LinkedList<String[]> argumentsList, Directory currentDir, Directory root) {
		// Checks if arguments need redirection
		Redirection r = new Redirection();
		boolean doRedirect = r.isRedirection(arguments);
		
		// If number is given, convert to integer
		int N = 0;
		if (arguments.length > 0) {
			N = Integer.parseInt(arguments[0]);
			// Argument validity check
			JShellInput valid = new JShellInput();
			if (!valid.isValidHistory(arguments[0])) {
				return;
			}
		}
		if (N < 0) {
			System.out.println("Argument must be greater or equal to 0");
			return;
		}
		
		String str = printRecentCommands(commandsList, argumentsList, N);
		
		// If redirects
		if (doRedirect) {
			r.redirectCommand("man", arguments, "\"" + str + "\"", currentDir, root);
		}
		else {
			System.out.println(str);
		}
	}
}
