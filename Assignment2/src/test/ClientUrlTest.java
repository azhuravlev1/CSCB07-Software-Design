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

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import driver.ClientUrl;
import driver.Directory;
import driver.File;
import driver.MakeDirectory;

/**
 * Test main() in ClientUrl class.
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class ClientUrlTest {
	
	/*
	 * Sets root directory
	 * @return returns root directory
	 */
	private Directory setRootDirectory() {
		Directory root = new Directory("", null, null);
		return root;
	}
	
	/*
	 * Adds content in current directory
	 * @return returns root directory
	 */
	private void addContent(Directory currentDir, Directory rootDir) {
		MakeDirectory mkdir = new MakeDirectory();
		String[] input = {"Directory1"};
		@SuppressWarnings("unused")
		boolean madeDir = mkdir.checkDirectory(input, currentDir, 0, rootDir);
	}
	
	/*
	 * Gets contents of the current directory
	 * @param currentDir Current working directory
	 * @return returns contents in current directory
	 */
	private ArrayList<Object> getContents(Directory currentDir) {
		ArrayList<Object> contents = currentDir.getDirectoryContents();
		return contents;
	}
	
	/*
	 * Gets content of the file at given index.
	 * @param contents Contents in the current directory
	 * @param count Index of the content
	 * @return returns content at index of count
	 */
	private Object getContent(ArrayList<Object> contents, int count) {
		return contents.get(count);
	}
	
	/*
	 * Gets the name of the file.
	 * @param file Added file by curl command
	 * @return returns name of the added file
	 */
	private String getAddedFileName(File file) {
		return file.getFileName();
	}
	
	/*
	 * Check the behavior of main() on empty string.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testMainEmpty() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("", root);
		
		// Checks if content is not added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 0);
	}
	
	/*
	 * Check the behavior of main() on invalid url.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testMainInvalid() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("invalid.url@email.com", root);
		
		// Checks if content is not added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 0);
	}
	
	/*
	 * Check the behavior of main() on the url that doesn't exist.
	 * Expected: prints error message and shell calls for a new input
	 */
	@Test
	public void testMainDoesNotExist() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("https://www.page.does.not.exist/", root);
		
		// Checks if content is not added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 0);
	}
	
	/*
	 * Check the behavior of main() on the url that is a pdf file.
	 * Expected: "UTSC_Campus_Map_2015pdf" file is added
	 *           and shell calls for a new input
	 */
	@Test
	public void testMainPdf() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("https://www.utsc.utoronto.ca/home/sites/utsc.utoronto.ca.home/"
				+ "files/docs/UTSC_Campus_Map_2015.pdf", root);
		
		// Checks if content is added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 1);
		
		// Checks if added content is a file
		Object content = getContent(contents, 0);
		assertTrue(content instanceof File);
		
		// Checks if added file has an appropriate name
		String name = getAddedFileName((File) content);
		assertEquals(name, "homesitesutscutorontocahomefilesdocsUTSC_Campus_Map_2015pdf");
	}
	
	/*
	 * Check the behavior of main() on the url that is a txt file.
	 * Expected: "073txt" file is added and shell calls for a new input
	 */
	@Test
	public void testMainTxt() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt", root);
		
		// Checks if content is added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 1);
		
		// Checks if added content is a file
		Object content = getContent(contents, 0);
		assertTrue(content instanceof File);
		
		// Checks if added file has an appropriate name
		String name = getAddedFileName((File) content);
		assertEquals(name, "~spokgrimmtmp073txt");
	}
	
	/*
	 * Check the behavior of main() on the url that is a html file.
	 * Expected: "mapshtml" file is added and shell calls for a new input
	 */
	@Test
	public void testMainHtml() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		ClientUrl curl = new ClientUrl();
		curl.main("https://www.utsc.utoronto.ca/maps.html", root);
		
		// Checks if content is added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 1);
		
		// Checks if added content is a file
		Object content = getContent(contents, 0);
		assertTrue(content instanceof File);
		
		// Checks if added file has an appropriate name
		String name = getAddedFileName((File) content);
		assertEquals(name, "mapshtml");
	}

	/*
	 * Check the behavior of main() on the url when 
	 * there is more than one content in current directory.
	 * Expected: "mapshtml" file is added 
	 *           and shell calls for a new input
	 */
	@Test
	public void testMainExtraContents() throws IOException {
		// Set a root directory
		Directory root = setRootDirectory();
		// Add a content
		addContent(root, root);
		
		ClientUrl curl = new ClientUrl();
		curl.main("https://www.utsc.utoronto.ca/maps.html", root);
		
		// Checks if content is added
		ArrayList<Object> contents = getContents(root);
		assertTrue(contents.size() == 2);
		
		// Checks if added content is a file
		Object content = getContent(contents, 1);
		assertTrue(content instanceof File);
		
		// Checks if added file has an appropriate name
		String name = getAddedFileName((File) content);
		assertEquals(name, "mapshtml");
	}
}
