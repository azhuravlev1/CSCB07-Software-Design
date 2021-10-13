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
 * Get the top entry of the directory stack.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class PopDirectory {
	
	/**
	 * Get full path to get to dir
	 * @param dir
	 * @return path
	 */
	private String getPath(Object dir) {
		String path = ((Directory)dir).getDirectoryName();
		if (dir.getClass().getName() == "driver.File") {
			return ((File) dir).getFileDir().getDirectoryName();
		}
		// Traverse backward to get full path
		while (!(((Directory) dir).getParentDirectory().getDirectoryName().equals(""))) {
			if (((Directory) dir).getParentDirectory()!=null) {
				path = ((Directory) dir).getParentDirectory().getDirectoryName()+"/"+path;
				dir = ((Directory) dir).getParentDirectory();
			}
			else {
				break;
			}
		}
		return path;
	}
	
	/**
	 * Removes the top entry in the directory stack and returns
	 * that directory.
	 * @return Directory that was on top of the stack
	 */
	public String removeTopEntryStack(DirectoryStack dirStack) {
		// Print message is stack is empty
		if (dirStack.emptyStack()) {
			System.out.println("The directory stack is currently empty.");
			return null;
		}
		// Remove top directory in stack and return that directory
		return getPath(dirStack.pop());
	}
	
}
