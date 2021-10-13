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
import java.util.Iterator;
import java.util.Stack;
/**
 * Recursvely traverse a directory's content
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class RecursivelyTraverse<E> implements Iterable<E> {

	private static Object current;
	
	/**
	 * Visit children on a directory recursively
	 * @author Iangola Andrianarison
     * @author Yena Lee
     * @author Andrey Zhuravlev
     * @author Su Tong Kong
	 * @param <E>
	 */
	private static class VisitChildren<E> implements Iterator<E> {

		Stack<E> stk;
		/**
		 * Visit children
		 * @param object
		 */
		public VisitChildren(E object) {
			stk = new Stack<>();
			if (object!=null) {
				stk.add(object);
			}
			
		}
        
		/**
		 * Check if stack is empty
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return !(stk.isEmpty());
		}

		/**
		 * Get next object in stack and push its children onto the stack
		 */
		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			E obj = null;
			if (hasNext()) {
				obj = stk.pop();
				if (obj.getClass().getName() == "driver.Directory") {
					for (Object child:((Directory) ((Object) obj)).getDirectoryContents()){
						stk.push((E) child);
					}
				}
			}
			return obj;
		}

	}
	
	/**
	 * Visit children recursively
	 * @return Iterator
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return (Iterator<E>)new VisitChildren<>(current);
	}
	
	/**
	 * Get name of object
	 * @param obj
	 * @return name
	 */
	private String getName(Object object) {
		String name = new String();
		if (object.getClass().getName() == "driver.Directory") {
			name = ((Directory) object).getDirectoryName();
		}
		else if (object.getClass().getName() == "driver.File") {
			name = ((File) object).getFileName();
		}
		return name;
		
	}
	
	/**
	 * Get content of an object
	 * @param content
	 * @return String
	 */
	private String printDirectoryContent(ArrayList<Object> content) {
		String c="";
		for (Object cont : content) { 		      
			   if (cont.getClass().getName() == "driver.File") {
				   c=c+(((File) cont).getFileName()+" ");
			   }
			   else if (cont.getClass().getName() == "driver.Directory") {
				  c=c+(((Directory) cont).getDirectoryName()+" ");
			   }
		}
		return c;
	}
	
	/**
	 * Get full path to get to Dir
	 * @param dir
	 * @return path
	 */
	private String getPath(Object dir) {
		String path = new String();
		if (dir.getClass().getName() == "driver.File") {
			return ((File) dir).getFileDir().getDirectoryName();
		}
		// Traverse backward until get to the root to get full path
		while (!(((Directory) dir).getParentDirectory().getDirectoryName().equals(""))) {
			if (((Directory) dir).getParentDirectory()!=null) {
				path = ((Directory) dir).getParentDirectory().getDirectoryName()+"/"+path;
				dir = ((Directory) dir).getParentDirectory();
			}
			else {
				break;
			}
		}
		return path;
	}
	
	/**
	 * Get content 
	 * @param dir
	 * @return content
	 */
	private String printContent(Object dir) {
		// Get content
		String content = "";
		if (dir.getClass().getName()=="driver.Directory") {
			if (((Directory) dir).getDirectoryName().equals("")) {
				content=("/: ");
			}
			else {
				content=("/"+getPath(dir)+getName(dir)+": ");
			}
			content = content + printDirectoryContent(((Directory) dir).getDirectoryContents())+"";
		}
		else {
			content = (((File) dir).getFileName());
		}
		return content;
	}
	
	/**
	 * Get content recursively of object obj
	 * @param obj
	 * @return content
	 */
	public ArrayList<String> print(Object obj) {
		ArrayList<String> content = new ArrayList<String>();
		RecursivelyTraverse<Object> listr = new RecursivelyTraverse<>();
		current=(Directory) obj;
		// Add contents to content list
		for (Object child:listr) {
			content.add(printContent(child));
		}
		return content;
		
		
	}
}