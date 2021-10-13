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

class SaveJShellTest {
  @Test
  void testSaveJShell() {
    File file = new File("file", "", "/");
    LinkedList<String> commandsList = new LinkedList<String>();
    LinkedList<String> argumentsList = new LinkedList<String[]>();
    DirectoryStack dirStack = new DirectoryStack();
    Directory curDir = new Directory("/", "file", "/");
    commandsList.add("tree");
    commandsList.add("exit");

    ArrayList<Object> fileSystem = new ArrayList<Object>();
    fileSystem.add(curDir);
    fileSystem.add(root);
    fileSystem.add(commandsList);
    fileSystem.add(argumentsList);
    fileSystem.add(dirStack);

    SaveJShell sjs = new SaveJShell();
    sjs.SaveShell("file", "/", dirStack, "/", commandsList, argumentsList);

    assertEquals(fileSystem.toString(), file.getFileContent());
  }
}
