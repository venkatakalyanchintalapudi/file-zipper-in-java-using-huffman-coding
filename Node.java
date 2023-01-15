package filezipper;
public class Node 
{	
	char character;
	int frequency;
	public Node left;
	public Node right;
	Node(char character)
	{
		this.character=character;
		this.left=null;
		this.right=null;
	}
	Node(char character,int frequency)
	{
		this.character=character;
		this.left=null;
		this.right=null;
		this.frequency=frequency;
	}
	int getFrequency()
	{
		return frequency;
	}
	char getCharacter()
	{
		return character;
	}
	public Node getLeft()
	{
		return this.left;
	}
	public Node getRight()
	{
		return this.right;
	}
	public void setLeft(Node left)
	{
		this.left=left;
	}
	public void setRight(Node right)
	{
		this.right=right;
	}
}
