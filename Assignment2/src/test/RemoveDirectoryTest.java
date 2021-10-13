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

import static org.junit.jupiter.api.Assertions.*;
import driver.RemoveDirectory;
import driver.Directory;
import driver.MakeDirectory;
import org.junit.jupiter.api.Test;

class RemoveDirectoryTest {

	/**
	 * Create root directory for testing
	 * @return root
	 */
	private Directory setRoot() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/**
	 * Create child directory for root for testing
	 * @param root
	 */
	private void createSingle(Directory parent) {
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
		m.checkDirectory(input, parent, 0, parent);
	}
	
	/**
	 * Create multiple directories for root for testing
	 * @param root
	 */
	private void createMultiple(Directory parent) {
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a","b", "a/b", "a/b/c"};
		for (int i=0;i<4;i++) {
			m.checkDirectory(input, parent, i, parent);
		}
	}
	
	/**
	 * Test an empty input. Child on current directory should remain the same. 
	 */
	@Test
	void testEmpty() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createSingle(root);
		rm.removeDir("", root);
		assertEquals("a",((Directory) root.getDirectoryContents().get(0)).getDirectoryName());
	}
	
	/**
	 * Test trying to delete root. Should not delete anything. 
	 */
	@Test
	void testRoot() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createSingle(root);
		rm.removeDir("/", root);
		assertEquals("a",((Directory) root.getDirectoryContents().get(0)).getDirectoryName());
	}
	
	/**
	 * Test trying to delete a parent directory. Should not delete anything. 
	 */
	@Test
	void testParent() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createMultiple(root);
		rm.removeDir("a",(Directory)((Directory) root.getChildByName("a", false)).getChildByName("b", false));
		assertEquals("a",((Directory) root.getDirectoryContents().get(0)).getDirectoryName());
		assertEquals("b",((Directory) root.getDirectoryContents().get(1)).getDirectoryName());
	}


	/**
	 * Test trying to delete a directory with children. Should delete directory
	 *  and all subdirectories. 
	 */
	@Test
	void testMultiple() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createMultiple(root);
		rm.removeDir("a",root);
		assertEquals("b",((Directory) root.getDirectoryContents().get(0)).getDirectoryName());
	}
	
	/**
	 * Test trying to delete a directory using full path. Should delete directory
	 *  successfully. 
	 */
	@Test
	void testFullPath() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createMultiple(root);
		rm.removeDir("/a",root);
		assertEquals("b",((Directory) root.getDirectoryContents().get(0)).getDirectoryName());
	}
	
	/**
	 * Test trying to delete a directory using relative path. Should delete directory
	 *  successfully. 
	 */
	@Test
	void testRelativePath() {
		Directory root = setRoot();
		RemoveDirectory rm = new RemoveDirectory();
		createMultiple(root);
		rm.removeDir("a/b/c",root);
		Directory current = (Directory) ((Directory) root.getChildByName("a", false)).getChildByName("b",false);
		assertEquals(true,current.getDirectoryContents().isEmpty());
	}
}
