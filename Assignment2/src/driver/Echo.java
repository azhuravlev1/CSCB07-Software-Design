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
 * Prints string onto console of if redirection given add string onto file.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Echo {

	/**
	 * Get the user strings input
	 * @param arguments
	 * @return String[]
	 */
	private String[] getInput(String[] arguments) {
		String input = "";
		for (int i=0;i<arguments.length-1;i++) {
			if (arguments[i].equals(">") || arguments[i].equals(">>")) {
				return input.split(",");
			}
			input=arguments[i]+",";
		}
		return input.split(",");
	}
	
	/**
	 * If no redirection given print the string. Otherwise check if 
	 * redirection is possible then redirect the string.
	 * @param arguments
	 * @param currentDir
	 * @param root
	 */
	public void echo(String[] arguments, Directory currentDir, Directory root) {
		// If no redirection
		if (arguments.length==1) {
			System.out.println(arguments[0]);
			return;
		}
		if (arguments.length!=1 && arguments.length<3) {
			System.out.println("Error: Echo takes in one string");
			return;
		}
		String[] str = getInput(arguments);
		String[] redirect = {arguments[1], arguments[2]};
		Redirection r = new Redirection();
		// Redirection if valid
		if (r.isValidRedirection("echo", str, redirect, currentDir, root)) {
			r.redirection("echo", str, redirect, currentDir);
		}
		return;
		
	}
}
