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
 * Create directories in the specified path. If no path is given then create in
 * current working directory.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class MakeDirectory {
	/**
	 * Checks if the directory was successfully created
	 * @param parent
	 * @param DIR
	 * @return returns true if the directory exits, else returns false
	 */
	private boolean createdSuccessfully(Directory parent, String DIR) {
		if (((Directory) parent.getChildByName(DIR, true)).getDirectoryName().equals(DIR)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a directory DIR in directory currentDirectory
	 * @param name of the new directory
	 * @param currentDirectory
	 * @return created directory
	 */
	private Directory makeDirectory(String DIR, Directory currentDirectory) {
		// Create DIR in specified path
		Directory newDIR = null;
		newDIR = new Directory(DIR, null, currentDirectory);
		
		// If DIR isn't successfully created
		if (!createdSuccessfully(currentDirectory, DIR)) {
			System.out.println("Error: "+DIR + " could not be created.");
			return null;
		}
		return newDIR;
	}
	
	/**
	 * Check if the directory is a legal name
	 * @param input
	 * @return boolean
	 */
	private boolean valid(String input) {
		String invalid = "/. !@#$^*()[]~|><?";
		boolean valid = true;
		for (int i=0;i<invalid.length();i++) {
			for (int j=0;j<input.length();j++) {
				if (input.charAt(j) ==invalid.charAt(i)) {
					valid = false;
				}
			}
		}
		return valid;
	}
	
	/**
	 * Helper method for checkDirectory for creating the directory in 
	 * the given path
	 * @param relative
	 * @param DIRinput
	 * @param currentDir
	 * @param root
	 * @param i
	 * @param count
	 * @param input
	 * @return boolean
	 */
	private boolean createDir(boolean relative, String[] DIRinput, Directory currentDir,
			Directory root, int i, int count, String input) {
		if (!relative && DIRinput.length==2) {
			if (valid(DIRinput[1])) {
				makeDirectory(DIRinput[1], root);
				return true;
			}
			System.out.println("Error: Illegal directory name, cannot contain any those"
					+ "characters /. !@#$^*()[]~|><?");
		}
		else if (i == DIRinput.length - 1 && relative || 
				i == DIRinput.length -1 && !relative) {
			if (valid(DIRinput[i])) {
				makeDirectory(DIRinput[i], currentDir);
				return true;
			}
			System.out.println("Error: Illegal directory name");
		}
		else {
			System.out.println("Error: Illegal directory name, cannot contain any those"
					+ "characters /. !@#$^*()[]~|><?");
			return false;
		}
		return false;
	}
	
	/**
	 * Checks path and creates directory if it doesn't exist
	 * @param input
	 * @param currentDirectory
	 */
	public boolean checkDirectory(String input[], Directory currentDir,
			int count, Directory root) {
		if (input[count]=="") {
			return false;
		}
		int j = 0;
		String[] DIRinput;
		Boolean relative = true;
		// Split input for both arguments 
		DIRinput = input[count].split("/");
		if (input[count].charAt(0)==('/')) {
			if (DIRinput.length==0) {
				DIRinput = new String[] {"/"};
			}
			else {
				DIRinput[0]="/";
				relative = false;
				currentDir = root;
				j=1;
			}
		}
		
		for (int i = j; i < DIRinput.length; i++) {
			Directory child = (Directory) currentDir.getChildByName(DIRinput[i], false);
			// If directory content doesn't exist in currentDirectory
			if (child == null) {
				return createDir(relative, DIRinput, currentDir, root, i, count, input[count]); 
			}
			// If directory already exist
			else if (i == DIRinput.length - 1 &&
					child.getDirectoryName().equals(DIRinput[i])) {
				System.out.println("Error: Directory "
					+DIRinput[i]+" already exists"+ " in that path");
				return false;
			}
			currentDir= child;
		}
		return true;
	}
	
	public void runMakeDirectory(String[] arguments, Directory currentDir, Directory root) {
		JShellInput v = new JShellInput();
		boolean previousDIR;
		for (int i = 0; i < arguments.length; i++) {
			if (!v.isValidArgument("mkdir", arguments, currentDir, root, i)) {
				break;
			}
			previousDIR = checkDirectory(arguments, currentDir, i, root);
			if(!previousDIR) {
				break;
			}
		}
	}
}
