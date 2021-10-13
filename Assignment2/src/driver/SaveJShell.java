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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
/**
 * Save the current session into a file.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class SaveJShell implements Serializable,Iterator <Object>{

	private Object current = new Object();
	private Stack<Object> objStack = new Stack<Object>();
	private static final long serialVersionUID = 1L;

	// Check for the valid arguments
	JShellInput v = new JShellInput();

	/**
	 * returns true is the stack is not empty
	 * @return boolean
	 */
	@Override
	public boolean hasNext() {
		return objStack.isEmpty();
	}

	/**
	 * gets and returns the next object in the stack
	 * @return Object
	 */
	@Override
	public Object next() {
		return objStack.pop();
	}
	
	/**
	 * Saves the current session into outPut file so that it can retrieved at
	 * a later time after the program has be quit.
	 * @param outPut
	 * @param root
	 * @param dirStack
	 * @param curDir
	 * @param commandsList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void SaveShell(String outPut, Directory root,
			DirectoryStack dirStack, Directory curDir,
			LinkedList<String> commandsList, LinkedList<String[]> argumentsList)
					throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outPut));
		ArrayList<Object> fileSystem = new ArrayList<Object>();
		// Save DirectoryStack, lists of past commands, current working directory
		// and root of the file system
		fileSystem.add(curDir);
		fileSystem.add(root);
		fileSystem.add(commandsList);
		fileSystem.add(argumentsList);
		fileSystem.add(dirStack);
		current = root;
		objStack.push(current);
		// Go over the file system and save each object (Directories and their children
		// in the output file
		while (hasNext()) {
			current = next();
			fileSystem.add(current);
			if (current.getClass().getName() == "driver.Directory") {
				for (Object child:((Directory)current).getDirectoryContents()) {
					objStack.push(child);
				}
			}
		}
		out.writeObject(fileSystem);
		out.close();
	}
}
