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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import driver.Directory;
import driver.Echo;
import driver.List;
import driver.MakeDirectory;

class ListTest {

	/**
	 * Create root directory for testing
	 * @return root
	 */
	private Directory setRoot() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/**
	 * Test an empty input. The output should be "/: ". 
	 */
	@Test
	void testEmpty() {
		Directory root = setRoot();
		List l = new List();
		String[] arg = {""};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/: ", list.get(0).get(0));
	}
	
	/**
	 * Test one invalid input. The output should be an error message saying 
	 * the path does not exit.
	 */
	@Test
	void testOneInvalid() {
		Directory root = setRoot();
		List l = new List();
		String[] arg = {"a"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("Error: path a does not exit", list.get(0).get(0));
	}
	
	/**
	 * Test one valid input with one invalid. The output should be the content of the first
	 * directory then an error message. 
	 */
	@Test
	void testSecondInvalid() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
 		m.checkDirectory(input, root, 0, root);
		List l = new List();
		String[] arg = {"a", "b"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/a: ", list.get(0).get(0));
		assertEquals("Error: path b does not exit", list.get(1).get(0));
	}
	
	/**
	 * Test fullPath. Output should be the content of the specified directory.
	 */
	@Test
	void testFullPath() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input = {"a"};
 		m.checkDirectory(input, root, 0, root);
		List l = new List();
		String[] arg = {"/a"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/a: ", list.get(0).get(0));
	}
	
	/**
	 * Test -R no path given. Output should be the content of the root and all its
	 * subdirectories contents.
	 */
	@Test
	void testR() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input1 = {"a"};
 	    m.checkDirectory(input1, root, 0, root);
 		String[] input2 = {"b"};
 		m.checkDirectory(input2, (Directory)root.getChildByName("a", false),
 				0, (Directory)root.getChildByName("a", false));
		List l = new List();
		String[] arg = {"-R"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/: a ", list.get(0).get(0));
		assertEquals("/a: b ", list.get(0).get(1));
		assertEquals("/a/b: ", list.get(0).get(2));
	}
	
	@Test
	void testRMultipleInputs() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input1 = {"a"};
 	    m.checkDirectory(input1, root, 0, root);
 		String[] input2 = {"b"};
 		m.checkDirectory(input2, (Directory)root.getChildByName("a", false),
 				0, (Directory)root.getChildByName("a", false));
		List l = new List();
		String[] arg = {"-R", "a", "a/b"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/a: b ", list.get(0).get(0));
		assertEquals("/a/b: ", list.get(0).get(1));
		assertEquals("/a/b: ", list.get(1).get(0));
	}
	
	@Test
	void testMultipleInputs() {
		Directory root = setRoot();
		MakeDirectory m = new MakeDirectory();
		String[] input1 = {"a"};
 	    m.checkDirectory(input1, root, 0, root);
 		String[] input2 = {"b"};
 		m.checkDirectory(input2, (Directory)root.getChildByName("a", false),
 				0, (Directory)root.getChildByName("a", false));
		List l = new List();
		String[] arg = {"a", "a/b"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("/a: b ", list.get(0).get(0));
		assertEquals("/a/b: ", list.get(1).get(0));
	}
	
	@Test
	void testFile() {
		Directory root = setRoot();
		Echo e = new Echo();
		List l = new List();
		String[] input = {"\"hello\"", ">", "file1"};
		e.echo(input, root, root);
		String[] arg = {"file1"};
		ArrayList<ArrayList<String>> list = l.getContents(arg, root, root, root);
		assertEquals("file1", list.get(0).get(0));
	}

}
