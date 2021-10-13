package driver;
/**
 * Presents the file system into a tree representation
 * @author Iangola Andrianarison
 * @author Yena Lee
 * @author Andrey Zhuravlev
 * @author Su Tong Kong
 */
public class Tree {
	public void main(String[] arguments, Directory root) {
		/* Initialize a string with only the root for the tree representation
		 */
		String tree_repr = "\\";
		/* Depth of the directory (number of tabs needed before Directory's name)
		 */
		int depth = 1;
		tree_repr = tree_repr + populateBranch(root, depth);

		// Checks if arguments need redirection and redirect if needed
		Redirection r = new Redirection();
		if (r.isRedirection(arguments)){
			r.redirectCommand("tree", arguments, tree_repr, null,  root);
		}
		else {
			System.out.println(tree_repr);
		}
	}

	// Check for the valid arguments
	JShellInput v = new JShellInput();

	/** Recursive method which populates string representation of the tree
	 * for a given branch of the tree.
	 * @param currentdir - root of the branch
	 * @param depth - depth of the branch
	 * @return
	 */
	private String populateBranch(Directory currentdir, int depth) {
		String branch_repr = ""; // branch representation
		/* Loop through the contents of the current directory
		*/
		for (Object filedir: currentdir.getDirectoryContents()) {
		/* Add directories (and their children) to the representation
		 */
			if (filedir.getClass().getName() == "driver.Directory") {
				Directory dir = (Directory) filedir;
				branch_repr += "\n" + "\t".repeat(depth) + dir.getDirectoryName();
				branch_repr += populateBranch(dir, depth+1); // children
			}
			/* Add files to the representation
			 */
			else if (filedir.getClass().getName() == "driver.File") {
				File file = (File) filedir;
				branch_repr += "\n" + "\t".repeat(depth) + file.getFileName();
			}
		}
		return branch_repr;
	}

}
