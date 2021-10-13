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

import driver.ChangeDirectory;
import driver.Copy;
import driver.Directory;
import driver.File;

/**
 * Test copyDirectory() in Copy class.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class CopyTest {
	
	public Copy cp = new Copy();
	
	/**
	 * Sets root directory
	 * @return returns root directory
	 */
	private Directory setRootDirectory() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/**
	 * Adds content in current directory
	 * @return returns root directory
	 */
	private void addDir(Directory currentDir, Directory root, String name, String type) {
		if (type.equals("f")) {
			File f = new File(name, name, currentDir);
			currentDir.add(f);
		}
		else if (type.equals("d")) {
			Directory d = new Directory(name, null, currentDir);
			currentDir.add(d);
		}
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
	 * Check the behavior of copyDirectory() on new OLDPATH to NEWPATH.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testCopyDirectoryNewToNew() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Copy newFile1 >> newDir1
		String[] arguments = {"newFile1", "newDir1"};
		cp.copyDirectory("cp", arguments, root, root);
		// Checks if content is not added
		assertFalse(checkAdded(root, "newDir1", "newFile1", 'f'));
	}
	
	/**
	 * Check the behavior of copyDirectory() on new OLDPATH to NEWPATH.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testCopyDirectoryNewToExist() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Create a new directory
		addDir(root, root, "File1", "f");
		addDir(root, root, "Dir1", "d");
		
		// Copy newFile1 >> File1
		String[] arguments1 = {"newFile1", "File1"};
		cp.copyDirectory("cp", arguments1, root, root);
		// Checks if content is not added
		assertFalse(checkAdded(root, "", "newFile1", 'f'));
		
		// Copy newFile1 >> Dir1
		String[] arguments2 = {"newDir1", "Dir1"};
		cp.copyDirectory("cp", arguments2, root, root);
		// Checks if content is not added
		assertFalse(checkAdded(root, "Dir1", "newDir1", 'd'));
	}
	
	/**
	 * Check the behavior of copyDirectory() on OLDPATH to new NEWPATH.
	 * Expected: newFile1 is created with contents of File1
	 *           newDir1 is created with contents of Dir1
	 */
	@Test
	public void testCopyDirectoryExistToNew() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Create a new directory
		addDir(root, root, "File1", "f");
		addDir(root, root, "Dir1", "d");
		addDir(root, root, "Dir2", "d");
		
		// Copy File1 >> newFile1
		String[] arguments1 = {"File1", "Dir2/newFile1"};
		cp.copyDirectory("cp", arguments1, root, root);
		// Checks if content is renamed
		assertTrue(checkAdded(root, "Dir2", "newFile1", 'f'));
		assertFalse(checkAdded(root, "Dir2", "File1", 'f'));
		
		// Copy Dir1 >> newDir1
		String[] arguments2 = {"Dir1", "Dir2/newDir1"};
		cp.copyDirectory("cp", arguments2, root, root);
		// Checks if content is added
		assertTrue(checkAdded(root, "Dir2", "newDir1", 'd'));
		assertFalse(checkAdded(root, "Dir2", "Dir1", 'd'));
	}
	
	/**
	 * Check the behavior of copyDirectory() on file to directory.
	 * Expected: File1 copies to Dir1
	 * @throws IOException
	 */
	@Test
	public void testCopyDirectoryFileToDir() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Create a new directory
		addDir(root, root, "File1", "f");
		addDir(root, root, "Dir1", "d");
		
		// Copy File1 >> Dir1
		String[] arguments = {"File1", "Dir1"};
		cp.copyDirectory("cp", arguments, root, root);
		// Checks if content is copied
		assertTrue(checkAdded(root, "", "File1", 'f'));
		assertTrue(checkAdded(root, "Dir1", "File1", 'f'));
	}
	
	/**
	 * Check the behavior of copyDirectory() on file to file.
	 * Expected: File1 overwrites onto newFile1
	 */
	@Test
	public void testCopyDirectoryFileToFile() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Create a new directory
		addDir(root, root, "File1", "f");
		addDir(root, root, "File2", "f");
		
		// Copy File1 >> File2
		String[] arguments = {"File1", "File2"};
		cp.copyDirectory("cp", arguments, root, root);
		// Checks if content is overwritten
		assertTrue(checkAdded(root, "", "File1", 'f'));
		assertTrue(checkAdded(root, "", "File2", 'f'));
	}
	
	/**
	 * Check the behavior of copyDirectory() on directory to directory.
	 * Expected: Dir1 copies to Dir2
	 */
	@Test
	public void testCopyDirectoryDirToDir() {
		// Set a root directory
		Directory root = setRootDirectory();
		
		// Create a new directory
		addDir(root, root, "Dir1", "d");
		addDir(root, root, "Dir2", "d");
		
		// Copy File1 >> File2
		String[] arguments = {"Dir1", "Dir2"};
		cp.copyDirectory("cp", arguments, root, root);
		// Checks if content is copied
		assertTrue(checkAdded(root, "", "Dir1", 'd'));
		assertTrue(checkAdded(root, "Dir2", "Dir1", 'd'));
	}
}
