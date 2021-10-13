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
import java.net.URL; 

/**
 * Retrieve the file at URL and add to the current working directory.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class ClientUrl {
	/**
	 * Checks validity of curl command.
	 * Usage: curl URL
	 * @param arguments User input arguments
	 * @param currentDir Current directory where user is
	 * @return returns true if curl is valid, false if invalid
	 */
	private boolean isValidClientURL(String fileaddress, Directory currentDir) {
		// Check URL
		JShellInput valid = new JShellInput();
		return valid.isValidURL("curl", fileaddress);
	}
	
	/**
	 * Retrieves the file at given URL and adds it to the current working directory
	 * @param fileaddress
	 * @param currentdir
	 * @throws IOException 
	 */
	public void main(String fileaddress, Directory currentDir) throws IOException {
		if (!isValidClientURL(fileaddress, currentDir)) {
			return;
		}
		
		URL address =new URL(fileaddress);
		/* Get the name of the file
		 */
		String name = address.getFile();
		/* Remove prohibited characters
		 */
		name = name.replaceAll("[/.]", "");
		/* Get the contents of the file
		 */
		Object contents = address.getContent();
		/* If the file with the given name does not exist, create a new file in
		 * the current directory with the file name and contents obtained from
		 * the URL
		 */
		if (currentDir.getChildByName(name, false) == null) {
			new File(name, contents, currentDir);
		}
		else {
			System.out.println("Error: file with name "+ name +" already exists");
		}
	}

}
