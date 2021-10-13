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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Execute the given command
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class JShellExecute {

	/**
	 * Executes input commands
	 * @param command User input command
	 * @param arguments User input arguments
	 * @param commandsList List of the commands user entered
	 * @param argumentsList List of the arguments user entered
	 * @param currentDir Current directory where user is
	 * @return returns updated current directory
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<Object> executeCommand(String command, String[] arguments, 
			LinkedList<String> commandsList, LinkedList<String[]> argumentsList, 
			Directory currentDir, Directory root, DirectoryStack dirStack) 
					throws FileNotFoundException, IOException, ClassNotFoundException {		
		
		// Check for the valid arguments
		JShellInput v = new JShellInput();
		int n = arguments.length;
		ArrayList<Object> array = new ArrayList<Object>();
		
		// Execute given command
		if (command.equals("rm")) {
			RemoveDirectory r = new RemoveDirectory();
			r.removeDir(arguments[0], currentDir);
		}
		else if (command.equals("mkdir")) {
			MakeDirectory mkdir = new MakeDirectory();
			mkdir.runMakeDirectory(arguments, currentDir, root);
		}
		else if (command.equals("ls")) {
			ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
			List list = new List();
			output = list.getContents(arguments, currentDir, currentDir,root);
			if (output!=null) {
				for (int i=0;i<output.size();i++) {
					for (int j=0;j<output.get(i).size();j++) {
						System.out.println(output.get(i).get(j));
					}
				}
			}
		}
		else if (command.equals("pwd")) {
			PresentWorkingDirectory pwd = new PresentWorkingDirectory();
			pwd.main(arguments, currentDir, root);
		}
		else if (command.equals("cp")) {
			Copy cp = new Copy();
			cp.copyDirectory("cp", arguments, currentDir, root);
		}
		else if (command.equals("mv")) {
			Move mv = new Move();
			mv.moveDirectory(arguments, currentDir, root);
		}
		else if (command.equals("cat")) {
			Concatenate cat = new Concatenate();
			cat.main(arguments, currentDir, root);
		}
		else if (command.equals("curl")) {
			ClientUrl curl = new ClientUrl();
			for (int i = 0; i < n; i++) {
				if (!v.isValidArgument(command, arguments, currentDir, root, i)) {
					break;
				}
				curl.main(arguments[i], currentDir);
			}
		}
		else if (command.equals("echo")) {
			Echo e = new Echo();
			e.echo(arguments, currentDir, root);
		}
		else if (command.equals("man")) {
			Manual m = new Manual();
			m.manual(command, arguments, currentDir, root);
		}
		else if (command.equals("history")) {
			History h = new History();
			h.history(arguments, commandsList, argumentsList, currentDir, root);
		}
		else if (command.equals("search")) {
			Search s = new Search();
			s.search(arguments, currentDir, root);
		}
		// Commands with arguments only need to check argument in the beginning
		else if (v.isValidArgument(command, arguments, currentDir, root, -1)) {
			if (command.equals("cd")) {
				ChangeDirectory cd = new ChangeDirectory();
				if (n > 1) {
					System.out.println("cd: too many arguments");
				}
				else {
					if (arguments[0].equals("/")) {
						currentDir = root;
					}
					Directory dir = cd.main(currentDir, arguments[0]);
					if (dir != null) {
						currentDir = dir;
					}
				}
			}
			else if (command.equals("pushd")) {
				PushDirectory pushd = new PushDirectory();
				ChangeDirectory cd = new ChangeDirectory();
				for (int i = 0; i < n; i++) {
				if (!v.isValidArgument(command, arguments, currentDir, root, i)) {
					break;
				}
				pushd.saveCurrentDirectory(dirStack, currentDir);
				currentDir = cd.main(currentDir, arguments[0]);
			}
			}
			else if (command.equals("popd")) {
				PopDirectory popd = new PopDirectory();
				String newDir = popd.removeTopEntryStack(dirStack);
				if (newDir!=null) {
					ChangeDirectory cd = new ChangeDirectory();
					currentDir = cd.main(currentDir, newDir);
				}
			}
			else if (command.equals("tree")) {
				Tree tree = new Tree();
				tree.main(arguments, root);
			}

			else if (command.equals("saveJShell")) {
				SaveJShell s = new SaveJShell();
				s.SaveShell(arguments[0], root, dirStack, currentDir,
						commandsList, argumentsList);
			}
			
			else if (command.equals("loadJShell")) {
				LoadJShell l = new LoadJShell();
				return l.loadShell(arguments[0]);
			}
		}
		array.add(currentDir);
		return array;
	}
}
