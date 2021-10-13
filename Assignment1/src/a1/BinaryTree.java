package a1;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.Queue;
// **********************************************************
// Assignment1:
// Student1:
// UTORID user_name: zhuravl1
// UT Student #: 1003148961
// Author: Andrey Zhuravlev
//
// Student2:
// UTORID user_name: kongsu
// UT Student #: 1004304790
// Author: Kong Su Tong
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

/*
 * the provided starter code may contain warnings of raw type. 
 * This is OK, because we have'nt yet learned generics. After having
 * learnt generics, we will revisit this code, and remove the warnings out. 
 */
public class BinaryTree {

	private Node root;	
	
	/* DO NOT MODIFY THIS METHOD
	 * WE HAVE ALREADY COMPLETED THIS METHOD
	 * FOR YOU.
	 * adds data inside a binary
	 * tree level by level starting
	 * from left to right. 
	 */
	public void addData(int d)
	{
		Node toAdd=new Node(d);
		if (root==null)
		{
			root=toAdd;
		}
		else
		{
			LinkedList ll=new LinkedList();
			ll.add(root);
			while(!(ll.isEmpty()))
			{
				Node currentNode=(Node)ll.poll();
				if (currentNode.getLeftNode()==null)
				{
					currentNode.setLeftNode(toAdd);
					break;
				}
				else if (currentNode.getRightNode()==null)
				{
					currentNode.setRightNode(toAdd);
					break;
				}
				else
				{
					/*
					 * remember, the queue is FIFO, and due to this
					 * we add first the left node followed by the right
					 * node.
					 */
					ll.add(currentNode.getLeftNode());
					ll.add(currentNode.getRightNode());
				}
			}
		}
	}
	
	/*
	 * TODO:
	 * Do not modify the signature of this method. 
	 * You must complete this method so that you return 
	 * back a textual representation of the binary tree. The
	 * textual representation of the binary tree is returning
	 * back a string such that the string contains all the ints
	 * of the binary tree level by level and moving from left 
	 * to right. You will find the expected output of this method
	 * in the main function. 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		/*
		 * Do not use recursion for this method
		 */
		if (root == null)
			return "";
		
		Queue<Node> queue = new LinkedList<Node>();
		Node node;
		
		ArrayList values = new ArrayList();
		
		String croppedString;
		String delim = " ";

		int nodeCount;
		
		queue.add(root);
		
		while(true) {
			nodeCount = queue.size();
			if(nodeCount == 0)
				break;
			
			while(nodeCount > 0) {
				node = queue.peek();
				values.add(node.getData());
				
				queue.remove();
				if(node.getLeftNode() != null)
					queue.add(node.getLeftNode());
				if(node.getRightNode() != null)
					queue.add(node.getRightNode());
				nodeCount--;
			}
		}
		
		
		croppedString = (String) values.stream()
							.map(Object::toString)
							.collect(Collectors.joining(delim));
		
		return croppedString;
	}
	
	/*
	 * Do not modify the signature of this method.
	 * The ArrayList that is returned back from this method
	 * is an ArrayList that contains ints (i.e., the data of each of 
	 * the Node of the BinaryTree). The toList method in turn 
	 * calls the method addSubTree (private method). 
	 * The ArrayList that is returned back from this method
	 * contains ints of the BinaryTree in Inorder traversal.
	 */
	public ArrayList toList()
	{
		/* Create the ArrayList which will be populated with
		 * ints of the BinaryTree in Inorder traversal
		 */
		ArrayList values = new ArrayList();
		addSubTree((Node)root, values);
		return values;
	}
	
	/* This method uses recursion to populate the ArrayList using 
	 * inOrder traversal of the tree.
	 */
	private void addSubTree(Node temp, ArrayList values)
	{
		/* Check if the node exists
		 */
		if (temp != null) {
			/* First, traverse the left subtree
			 */
			addSubTree(temp.getLeftNode(), values);
			/* Add the value of the root
			 */
			values.add(temp.getData());
			/* Then, traverse the right subtree
			 */
			addSubTree(temp.getRightNode(), values);
		}
		return;
		
	}
	
	/*
	 * You can modify the main function in any way you like
	 * we will not mark your main function. We have provided some
	 * sample code inside the main function that may make it easier 
	 * for you to debug your code and know what the expected output 
	 * is.  
	 */
	public static void main(String[] args)
	{
		BinaryTree bt=new BinaryTree();
		/*
		 * adding the following ints in a binary tree. Remember, the addData
		 * adds the ints level by level and from left to right. I will first 
		 * ask you to run the starter code and debug out the addData so that 
		 * you are familiar with how it works and trace the creation of the 
		 * tree using pen and paper. 
		 */
		bt.addData(1);
		bt.addData(2);
		bt.addData(3);
		bt.addData(4);
		bt.addData(5);
		bt.addData(6);
		bt.addData(7);
		
		System.out.println("tree: " + bt + "\n"); //must print 1 2 3 4 5 6 7 
		System.out.println("toList: " + bt.toList() + "\n");
		
		/*
		 * Printing the list of the binary
		 * tree. Remember the list of the binary
		 * tree must contain the ints in inOrder
		 * traversal. The for loop below will 
         * give you back a null pointer exception
         * when trying to run the starer code,
         * this is because toList() method inside
         * the BinaryTree returns back a null.
		 */
		for (Object d:bt.toList())
		{
			System.out.println((int)d);
		}
		/*
		 * the above loop will print the following:
		 * 4
		   2
		   5
           1
           6
           3
           7
		 */
	}
}
