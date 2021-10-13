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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Load a previous session
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class LoadJShell implements Serializable{

	// Check for the valid arguments
		JShellInput v = new JShellInput();
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayList<Object> loadShell(String location) throws IOException, 
	ClassNotFoundException {
		FileInputStream file = new FileInputStream(location); 
		ObjectInputStream in = new ObjectInputStream(file); 
		@SuppressWarnings("unchecked")
		ArrayList<Object> fileSystem = (ArrayList<Object>)in.readObject(); 
		in.close(); 
        file.close();
        return fileSystem;
	}
	
	
}
