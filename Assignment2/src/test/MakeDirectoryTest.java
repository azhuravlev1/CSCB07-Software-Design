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

import java.util.ArrayList;

import driver.Directory;
import driver.MakeDirectory;

import org.junit.jupiter.api.Test;

class MakeDirectoryTest {

	/**
	 * Created root directory for testing
	 * @return root
	 */
	private Directory setRoot() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/**
	 * Test an empty input. No directory should be created.
	 */
	@Test
	void testEmpty() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {""};
		@SuppressWarnings("unused")
		boolean b = m.checkDirectory(input, root, 0, root);
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals(true, children.isEmpty());
	}
	
	/**
	 * Test a single valid input. Root should have one children with the name 
	 * of the new directory.
	 */
	@Test
	void testSingle() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
		@SuppressWarnings("unused")
		boolean b = m.checkDirectory(input, root, 0, root);
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals("a", ((Directory) children.get(0)).getDirectoryName());
	}

	/**
	 * Test an invalid name. No directory should be created.
	 */
	@Test
	void testInvalidName() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"%&?"};
		@SuppressWarnings("unused")
		boolean b = m.checkDirectory(input, root, 0, root);
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals(true,children.isEmpty());
	}
	
	/**
	 * Test multiple inputs with first on invalid. No directory should be created. 
	 */
	@Test
	void testFirstInvalid() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"?", "a"};
		for (int i=0;i<2;i++) {
			boolean b = m.checkDirectory(input, root, i, root);
			if (!b) {
				return;
			}
		}
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals(true,children.isEmpty());
	}
	
	/**
	 * Test multiple valid inputs. All directory should be created.
	 */
	@Test
	void testMulitpletValid() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a", "b", "c", "d"};
		for (int i=0;i<4;i++) {
			boolean b = m.checkDirectory(input, root, i, root);
			if (!b) {
				return;
			}
		}
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals(4,children.size());
	}
	
	/**
	 * Test last input being invalid. Should create all directories before the invalid input.
	 */
	@Test
	void testThirdInvalid() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a", "b", "?"};
		for (int i=0;i<3;i++) {
			boolean b = m.checkDirectory(input, root, i, root);
			if (!b) {
				return;
			}
		}
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals(2,children.size());
	}
	
	/**
	 * Test creating directory using an absolute path. Directory should be created in the path
	 */
	@Test
	void testAsbolutePath() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"/a"};
		@SuppressWarnings("unused")
		boolean b = m.checkDirectory(input, root, 0, root);
		ArrayList<Object> children = root.getDirectoryContents();
		assertEquals("a", ((Directory) children.get(0)).getDirectoryName());
	}
	
	/**
	 * Test created directories using absolute paths with one directory away from the root.
	 * Directories should be created in respective paths.
	 */
	@Test
	void testAsbolutePaths() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"/a", "/a/b"};
		for (int i=0;i<2;i++) {
			boolean b = m.checkDirectory(input, root, i, root);
			if (!b) {
				return;
			}
		}
		ArrayList<Object> children = root.getDirectoryContents();
		ArrayList<Object> childrenA = ((Directory) children.get(0)).getDirectoryContents();
		assertEquals("a", ((Directory) children.get(0)).getDirectoryName());
		assertEquals("b", ((Directory) childrenA.get(0)).getDirectoryName());
	}
}
