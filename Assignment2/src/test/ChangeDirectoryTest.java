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
import driver.ChangeDirectory;
import driver.Directory;
import driver.MakeDirectory;

import org.junit.jupiter.api.Test;

class ChangeDirectoryTest {

	/**
	 * Create root directory for testing
	 * @return root
	 */
	private Directory setRoot() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/**
	 * Test an empty input. currentDir should not change.
	 */
	@Test
	void testEmpty() {
		Directory root = setRoot();
		Directory currentDir = root;
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "");
		assertEquals(root,currentDir);
	}
	
	/**
	 * Test a valid input. currentDir should change.
	 */
	@Test
	void testValid() {
		Directory root = setRoot();
		Directory currentDir = root;
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
		m.checkDirectory(input, currentDir, 0, root);
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "a");
		assertEquals(currentDir,root.getChildByName("a", false));
	}
	
	/**
	 * Test a invalid input. currentDir should not change.
	 */
	@Test
	void testInvalid() {
		Directory root = setRoot();
		Directory currentDir = root;
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "a");
		assertEquals(currentDir,root);
	}
	
	/**
	 * Test a valid full path input. currentDir should change.
	 */
	@Test
	void testFullPath() {
		Directory root = setRoot();
		Directory currentDir = root;
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
		m.checkDirectory(input, currentDir, 0, root);
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "/a");
		assertEquals(currentDir,root.getChildByName("a", false));
	}
	
	/**
	 * Test a valid path input. currentDir should change.
	 */
	@Test
	void testPath() {
		Directory root = setRoot();
		Directory currentDir = root;
		MakeDirectory m = new MakeDirectory();
		String[] input1 = {"a"};
		m.checkDirectory(input1, currentDir, 0, root);
		String[] input2 = {"b"};
		m.checkDirectory(input2, (Directory)root.getChildByName("a", false), 
				0, (Directory)root.getChildByName("a", false));
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "a/b");
		assertEquals(currentDir,((Directory) root.getChildByName("a", false)).getChildByName("b", false));
	}

	/**
	 * Test a invalid path input. currentDir should not change.
	 */
	@Test
	void testInvalidPath() {
		Directory root = setRoot();
		Directory currentDir = root;
		MakeDirectory m = new MakeDirectory();
		String[] input1 = {"a"};
		m.checkDirectory(input1, currentDir, 0, root);
		String[] input2 = {"b"};
		m.checkDirectory(input2, (Directory)root.getChildByName("a", false), 
				0, (Directory)root.getChildByName("a", false));
		ChangeDirectory c = new ChangeDirectory();
		currentDir = c.main(currentDir, "a/c/b");
		assertEquals(currentDir,root);
	}
}
