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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
/**
 * Prompt the user for command and arguments to excecute. 
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class JShell {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		String command = "";
		String arguments[];
		LinkedList<String> commandsList = new LinkedList<String>();
		LinkedList<String[]> argumentsList = new LinkedList<String[]>();
		DirectoryStack dirStack = DirectoryStack.getInstance();
		
		// Root directory
		Directory currentDir = new Directory("", null, null);
		Directory root = currentDir;
		// Open scanner
		Scanner in = new Scanner(System.in);
		
		// Scan user input until user quits the program
		while (Objects.equals(command, new String("exit")) == false) {
			// Prompt the user for input
			System.out.println("/" + currentDir.getDirectoryName() + ": ");
			String input = in.nextLine();

			// Parse the user input for proper keywords
			String delimeter = "[ ]+";
			String parsedInput[] = input.split(delimeter);
			
			// Split parsedInput into command and arguments
			command = parsedInput[0];
			arguments = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
			
			// Add command in list of recent commands including syntactical errors and 
			// invalid outputs
			commandsList.add(command);
			argumentsList.add(arguments);
			
			// Check for valid input
			JShellInput v = new JShellInput();
			
			if (v.isValidCommand(command) && v.isValidNumArgument(command, arguments.length)) {
				// If input is valid, execute given command
				JShellExecute ex = new JShellExecute(); 
				ArrayList<Object> array = new ArrayList<Object>();
				array = ex.executeCommand(command, arguments, commandsList, argumentsList,
						currentDir, root, dirStack);
				currentDir = (Directory) array.get(0);
				if (array.size()>1) {
					root = (Directory) array.get(1);
					commandsList = (LinkedList<String>) array.get(2);
					argumentsList = (LinkedList<String[]>) array.get(3);
					dirStack = (DirectoryStack) array.get(4);
				}
			}
		}
		
		// Close scanner
		in.close();
		
		// Quit the program if command given is exit
		Exit exit = new Exit();
		exit.exitProgram();
  }

}
