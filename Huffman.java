package filezipper;

import java.io.*;
import java.util.*;

import filezipper.Node;
@SuppressWarnings("unused")
public class Huffman 
{
	HashMap<Character,String> codemap;
	HashMap<Character,String> codemap1;
	Huffman()
	{
		codemap=new HashMap<Character,String>();
		codemap1=new HashMap<Character,String>();
	}
	void huffer(HashMap<Character,Integer> fmap)
	{
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new ownComparator());
		String temp="";
		Node leftnode,rightnode,newnode;
		for (Map.Entry<Character,Integer> entry : fmap.entrySet()) 
			pq.add(new Node(entry.getKey(),entry.getValue()));
		pq.add(new Node('©',1));
		while(pq.size()!=1)
		{
			leftnode=pq.peek();
			pq.poll();
			rightnode=pq.peek();
			pq.poll();
			newnode=new Node('€',leftnode.getFrequency() + rightnode.getFrequency());
			pq.add(newnode);
			newnode.setLeft(leftnode);
			newnode.setRight(rightnode);
		}
		encodeCharacters(pq.peek(),temp);
	}
	void encodeCharacters(Node root,String code)
	{
		if(root==null)
			return;
		if(root.getCharacter()!='€')
			codemap.put(root.getCharacter(),code);
		encodeCharacters(root.getLeft(), code+"0");
	    encodeCharacters(root.getRight(), code+"1");
	}
	void CompressToFile(String inputfile,String outputfile) throws IOException
	{
		String file=new String();
		File f=new File(inputfile);
		FileReader fr=new FileReader(f);
		FileOutputStream fw=new FileOutputStream(outputfile);
		writeheader(fw);
		char[] c=new char[(int)f.length()];
		fr.read(c);
		for(char c1:c)
		{
			file+=codemap.get(c1);
		}
		fr.close();
		file+=codemap.get('©');
		long remainder = (file.length()) % 7;
	    for (int i = 0; i < 7 - remainder; ++i)
	        file += '0';
	    String s1="";
	    for(int i=0;i<=file.length()-7;i+=7)
	    {
	    	String temp=file.substring(i,i+7);
	    	int n=Integer.parseInt(temp,2);
	    	fw.write(n);
	    }
	    fw.flush();
	    fw.close();
	}
	void writeheader(FileOutputStream fw) throws IOException
	{
		String s="";
		for (Map.Entry<Character,String> entry : codemap.entrySet())
		{
			fw.write((""+entry.getKey()).getBytes());
			fw.write((""+'…').getBytes());
			fw.write(entry.getValue().getBytes());
			fw.write((""+'ƒ').getBytes());
		}
		fw.write((""+'„').getBytes());
	}
	void dehuffer(String compressfile,String decompressfile) throws IOException
	{
		String codeString="";
		File f=new File(compressfile);
		FileInputStream fr=new FileInputStream(f);
		byte[] k=new byte[(int)f.length()];
		fr.read(k);
		fr.close();
		String s1=new String(k);
		int i=0;
		char c1=s1.charAt(i);
		char key=' ';
		while(c1!='„')
		{
			if(c1=='…')
			{
				c1=s1.charAt(++i);
				String s="";
				while(c1!='ƒ')
				{
					s+=c1;
					c1=s1.charAt(++i);
				}
				codemap1.put(key, s);
			}
			else
				key=c1;
			c1=s1.charAt(++i);
		}
		int k1=0;
		for(int j=i+1;j<s1.length();j++)
		{
			c1=s1.charAt(j);
			k1=(byte)c1;		
			String s=Integer.toString(k1,2);
			int len=s.length();
			for (int m = 0; m <7 - len; ++m)
				s='0'+s;
			codeString+=s;
		}
		Node rootNode=buildDecodingTree(codemap1);
		decompressToFile(codeString,rootNode,decompressfile);
	}
	Node buildDecodingTree(HashMap<Character,String> encodingMap)
	{
		Node rootNode=new Node('€');
		Node prevNode;
		for (Map.Entry<Character,String> entry1 : encodingMap.entrySet()) 
		{
			prevNode=rootNode;
			Node newNode=new Node(entry1.getKey());
			String out=(String)entry1.getValue();
			for(int i=0;i<out.length();i++)
			{
				if(out.charAt(i)=='0')
				{
					if (i ==out.length() - 1)
	                    prevNode.setLeft(newNode);
	                else {
	                    if (prevNode.getLeft()==null) 
	                    {
	                        prevNode.setLeft(new Node('€'));
	                        prevNode = prevNode.getLeft();
	                    } 
	                    else 
	                    	prevNode = prevNode.getLeft();
	                }
				}
				else 
				{
	                if (i == out.length() - 1)
	                    prevNode.setRight(newNode);
	                else 
	                {
	                    if (prevNode.getRight()==null)
	                    {
	                        prevNode.setRight(new Node('€'));
	                        prevNode = prevNode.getRight();
	                    } 
	                    else 
	                    	prevNode = prevNode.getRight();
	                }
				}
			}
		}
		return rootNode;	
	}
	void decompressToFile(String codeString,Node rootNode,String decompressfile) throws IOException
	{
		FileWriter fw=new FileWriter(decompressfile);
		String s="";
		Node temp=rootNode;
		for(int i=0;i<codeString.length();i++)
		{
			if(codeString.charAt(i)=='0')
				temp=temp.getLeft();
			else
				temp=temp.getRight();
			if(temp.getCharacter()!='€')
			{
					if(temp.getCharacter()=='©')
						break;
					s+=temp.getCharacter();
					temp=rootNode;
			}	
		}
		fw.write(s);
		fw.close();
	}
} 
class ownComparator implements Comparator<Node>{
    public int compare(Node n1, Node n2) {
           if(n1.getFrequency()>n2.getFrequency())
        	   return 1;
           else if(n1.getFrequency()<n2.getFrequency())
        	   return -1;
           else 
        	   return 0;
       }
}