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
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import driver.PresentWorkingDirectory;
import driver.ChangeDirectory;
import driver.Directory;
import driver.File;

/**
 * Test main() in PresentWorkingDirectory class.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class PresentWorkingDirectoryTest {

	public PresentWorkingDirectory pwd = new PresentWorkingDirectory();
	
	/**
	 * Sets root directory
	 * @return returns root directory
	 */
	private Directory setRootDirectory() {
		Directory root = new Directory("/", null, null);
		return root;
	}
	
	/**
	 * Adds content in current directory
	 * @return returns root directory
	 */
	private Object addDir(Directory currentDir, Directory root, String name, String type) {
		if (type.equals("f")) {
			File f = new File(name, name, currentDir);
			currentDir.add(f);
			return f;
		}
		else if (type.equals("d")) {
			Directory d = new Directory(name, null, currentDir);
			currentDir.add(d);
			return d;
		}
		return null;
	}
	
	/**
	 * Checks if any content is added to given directory.
	 * @param dir Input directory
	 * @param path Path of the directory
	 * @param name Name of the content to check
	 * @param type Type of the content to check
	 * @return returns true if nothing is added
	 */
	private boolean checkAdded(Directory dir, String path, String name, char type) {
		ChangeDirectory cd = new ChangeDirectory();
		Object add = cd.main(dir, path).getChildByName(name, false);
		// If content is added
		if (add != null) {
			// If added content is a file as desired
			if (type == 'f' && add instanceof File) {
				return true;
			}
			// If added content is a directory as desired
			else if (type == 'd' && add instanceof Directory) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Check the behavior of main() on root with no contents.
	 * Expected: prints empty path
	 */
	@Test
	public void testPresentWorkingDirectoryRootEmpty() {
		// Set a root directory
		Directory root = setRootDirectory();
		// Copy newFile1 >> newDir1
		String[] arguments = {};
		
		// Checks if path is "//"
		String str = pwd.main(arguments, root, root);
		assertEquals(str, "//");
	}
	
	/**
	 * Check the behavior of main() on root with contents.
	 * Expected: prints empty path
	 */
	@Test
	public void testPresentWorkingDirectoryRootFilled() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Copy newFile1 >> newDir1
		String[] arguments = {};
		
		// Create a new directory
		addDir(root, root, "File1", "f");
		addDir(root, root, "Dir1", "d");
		
		// Checks if path is "//"
		String str = pwd.main(arguments, root, root);
		assertEquals(str, "//");
	}
	
	/**
	 * Check the behavior of main() with contents.
	 * Expected: prints empty path
	 */
	@Test
	public void testPresentWorkingDirectoryNotRoot() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Copy newFile1 >> newDir1
		String[] arguments = {};
		
		// Create a new directory
		Directory Dir1 = (Directory) addDir(root, root, "Dir1", "d");
		Directory Dir2 = (Directory) addDir(Dir1, root, "Dir2", "d");
		addDir(Dir2, root, "Dir3", "d");
		
		ChangeDirectory cd = new ChangeDirectory();
		Directory currentDir = cd.main(root, "/Dir1/Dir2/Dir3");
		
		// Checks if path is "///Dir1/Dir2/Dir3"
		String str = pwd.main(arguments, currentDir, root);
		assertEquals(str, "///Dir1/Dir2/Dir3");
	}
	
	/**
	 * Check the behavior of main() when there's argument that isn't redirection.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testPresentWorkingDirectoryExtraArgument() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Copy newFile1 >> newDir1
		String[] arguments = {"extra"};
		
		// Create a new directory
		Directory Dir1 = (Directory) addDir(root, root, "Dir1", "d");
		Directory Dir2 = (Directory) addDir(Dir1, root, "Dir2", "d");
		addDir(Dir2, root, "Dir3", "d");
		
		ChangeDirectory cd = new ChangeDirectory();
		Directory currentDir = cd.main(root, "/Dir1/Dir2/Dir3");
		
		// Checks if path is ""
		String str = pwd.main(arguments, currentDir, root);
		assertEquals(str, "");
	}
	
	/**
	 * Check the behavior of main() when there's argument that is a redirection.
	 * Expected: returns path but also redirects it to outfile
	 */
	@Test
	public void testPresentWorkingDirectoryRedirection() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Copy newFile1 >> newDir1
		String[] arguments = {">>", "outfile"};
		
		// Create a new directory
		Directory Dir1 = (Directory) addDir(root, root, "Dir1", "d");
		Directory Dir2 = (Directory) addDir(Dir1, root, "Dir2", "d");
		
		ChangeDirectory cd = new ChangeDirectory();
		Directory currentDir = cd.main(root, "/Dir1/Dir2");
		
		// Checks if path is "///Dir1/Dir2"
		String str = pwd.main(arguments, currentDir, root);
		assertEquals(str, "///Dir1/Dir2");
		
		// Checks if content is copied
		assertTrue(checkAdded(Dir2, "", "outfile", 'f'));
	}
}
