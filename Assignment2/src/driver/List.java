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

import java.util.ArrayList;
/**
 * Get the content of path. Path can be full or relative. If the path
 * gives a directory get its content if a file gets its name. If -R
 * is given as an argument get contents recursivly. Can redirect 
 * output to a file
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class List{
		
	/**
	 * Traverse the path by looking for an object in a directory's content
	 * @param currentObject
	 * @param content
	 * @param next
	 * @return currentObject
	 */
	private Object traversePath(Object currentObject, 
			ArrayList<Object> content, String next) {
		// Go over the content of the currentObject
		for (Object cont : content) { 
			// Look for the object in the path and assign this object 
			// to currentObject
			if (cont.getClass().getName() == "driver.File") {
				if (((File) cont).getFileName().equals(next)) {
					return cont;
				}
			}
			else if (cont.getClass().getName() == "driver.Directory") {
				if (((Directory) cont).getDirectoryName().equals(next)) {
					return cont;
				}
			}
		   }
		return null;
	}
	
	/**
	 * Print the content of a directory
	 * @param content
	 */
	private String printDirectoryContent(ArrayList<Object> content) {
		String s = "";
		for (Object cont : content) { 		      
			   if (cont.getClass().getName() == "driver.File") {
				   s=s+(((File) cont).getFileName()+" ");
			   }
			   else if (cont.getClass().getName() == "driver.Directory") {
				   s=s+(((Directory) cont).getDirectoryName()+" ");
			   }
		}
		return s;
	}
	
	/**
	 * Get full path to get to dir
	 * @param dir
	 * @return path
	 */
	private String getPath(Object dir) {
		if (((Directory) dir).getDirectoryName()=="") {
			return "/";
		}
		String path = ((Directory)dir).getDirectoryName();
		if (dir.getClass().getName() == "driver.File") {
			path = path+((File) dir).getFileDir().getDirectoryName();
			dir = ((File) dir).getFileDir();
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
		return "/"+path;
	}
	
	/**
	 * Check if object is a file or a directory and prints
	 * the content of the directory or the name of file
	 * @param currentObject
	 * @param path
	 * @param content
	 */
	private ArrayList<String> printContentCurrent(Object currentObject, 
			ArrayList<Object> content, boolean relative, ArrayList<String> s) {
		String p = "";
		if (currentObject.getClass().getName() == "driver.Directory") {
			p=getPath(currentObject)+": ";
			content = ((Directory) currentObject).getDirectoryContents();
			// Print content of the directory
	        p=p+printDirectoryContent(content);
		}
		// Print name of the file
		else if (currentObject.getClass().getName() == "driver.File") {
			p=(((File) currentObject).getFileName());
		}
		s.add(p);
		return s;
	}
	
	/**
	 * Get content of current working directory
	 * @param content
	 * @param R
	 * @param returnString
	 * @param currentObject
	 * @return
	 */
	private ArrayList<String> getContentCur(ArrayList<Object> content, 
			boolean R, ArrayList<String> returnString, Object currentObject, boolean relative) {
		if (content!=null && !R) {
			returnString=printContentCurrent(currentObject, content, relative, returnString);
		}
		//Recursively print content of current and of subdirectories
		else if (R) {
			RecursivelyTraverse<Object> l = new RecursivelyTraverse<>();
			returnString=l.print(currentObject);
		}
		return returnString;
	}
	

	
	/**
	 * Prints the content of a directory or a file in full path
	 * or current directory. If full path is given prints.
	 * @param currentDirectory
	 * @param path[]
	 */
	private ArrayList<String> printContent(Object currentObject, String path, boolean R) {
		ArrayList<String> returnString = new ArrayList<String>();
		int j = 0;
		boolean relative = true;
		//Check if path is full path
		if (path.length()>0 && path.charAt(0)=='/') {
			relative = false;
			j=1;
		}
		// Split path input
		String[] DIRinput;
		if (path.length()>0) {
			if (path.charAt(path.length()-1)=='/') {
				returnString.add("Error: path "+path+" does not exit");
				return returnString;
			}
			DIRinput = path.split("/");
		}
		//If no path is given
		else {
			ArrayList<Object> content = ((Directory) currentObject).getDirectoryContents();
			return getContentCur(content, R, returnString, currentObject, relative);
		}
		for (int i = j; i < DIRinput.length; i++) {
			ArrayList<Object> content = ((Directory) currentObject).getDirectoryContents();
			// Traverse path one object at a time
			if (DIRinput.length>0) {
				currentObject = traversePath(currentObject, content, DIRinput[i]);
				// If path does not exist
				if (currentObject==null) {
					returnString.add("Error: path "+path+" does not exit");
					return returnString;
				}
			}
			// If found object
			if (i == DIRinput.length-1 || DIRinput.length==0) {	
				if (!R) {
					printContentCurrent(currentObject, content, relative,returnString);
				}
				//If -R is given list recursively all subdirectories
				if (R) {
					RecursivelyTraverse<Object> l = new RecursivelyTraverse<>();
					return l.print(currentObject);
				}
		    }
		}
		return returnString;
	}
	
	/**
	 * Get input without redirection arguments
	 * @param arguments
	 * @return String[] of arguments
	 */
	private String[] getArg(String[] arguments) {
			String input = "";
			for (int i=0;i<arguments.length-1;i++) {
				if (arguments[i].equals(">") || arguments[i].equals(">>")) {
					return input.split(",");
				}
				else if (arguments[i].equals("-R")) {
					continue;
				}
				input=arguments[i]+",";
			}
			return input.split(",");
	}
	
	/**
	 * Get arguments for redirection
	 * @param arguments
	 * @return String[] of arugments
	 */
	private String[] redirectionArg(String[] arguments) {
		String input = "";
		boolean found = false;
		for (int i=0;i<arguments.length;i++) {
			if (arguments[i].equals(">") || arguments[i].equals(">>")) {
				found = true;
			}
			if (found) {
				input=input+arguments[i]+",";
			}
		}
		return input.split(",");
	}
	
	/**
	 * Get output to be redirected
	 * @param content
	 * @return String[] of output
	 */
	private String[] redirectionContent(ArrayList<ArrayList<String>> content) {	
		String output="";
		for (int i=0;i<content.size();i++) {
				for (int j=0;j<content.get(i).size();j++) {
					output=output+content.get(i).get(j)+",";
				}
			}
		return output.split(",");
		
	}
	
	public ArrayList<ArrayList<String>> getContents(String[] arguments, Object currentObject, 
			Directory currentDir, Directory root){
		ArrayList<ArrayList<String>> returnString = new ArrayList<ArrayList<String>>();
		JShellInput v = new JShellInput();
		Redirection r = new Redirection();
		boolean redirect = false;
		String[] arg = arguments;
		boolean R = false;{
			if (arguments[0].equals("-R")) {
				R = true;
			}
		}
		// Get argument without redirection argumnet if present
		if (r.isRedirection(arguments)) {
			redirect = true;
			arg = getArg(arguments);
		}
		int n = arg.length;
		// Get content of working directory if not arguments
		if (n == 0 ) {
			returnString.add(printContent(currentDir, "", false));
		}
		else if (R && n==1) {
			returnString.add(printContent(currentDir, "", true));
		}
		// Get contents non recursively
		else if (R && n>1) {
			for (int i = 1; i < n; i++) {
				if (!v.isValidArgument("ls", arg, currentDir, root, i)) {
					break;
				}
				returnString.add(printContent(currentDir, arg[i], R));
			}		
	    }
		// Get content recursively
		else {
			for (int i = 0; i < n; i++) {
			if (!v.isValidArgument("ls", arg, currentDir, root, i)) {
					break;
			}
			returnString.add(printContent(currentDir, arg[i], false));
			}
	     }
		// Redirect content if given redirection agrument
		if (redirect) {
	     	r.redirection("ls", redirectionContent(returnString), redirectionArg(arguments), currentDir);
			returnString=null;
		}
		return returnString;
	}
}