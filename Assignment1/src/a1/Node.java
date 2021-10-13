package a1;

public class Node {

		private int data;
		private Node left;
		private Node right;
		
		public Node()
		{
			
		}
		public Node(int d)
		{
			data=d;
			left=null;
			right=null;
		}
		public Node getLeftNode()
		{
			return left;
		}
		public Node getRightNode()
		{
			return right;
		}
		
		public void setLeftNode(Node n)
		{
			left=n;
		}
		public void setRightNode(Node n)
		{
			right=n;
		}
		public int getData()
		{
			return data;
		}
		public String toString()
		{
			return Integer.toString(data);
		}

}
