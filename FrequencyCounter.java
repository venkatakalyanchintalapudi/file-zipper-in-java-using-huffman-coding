package filezipper;

import java.io.*;
import java.util.HashMap;
public class FrequencyCounter {
	HashMap<Character,Integer> fmap;
	FileReader freader;
	char[] c;
	File f;
	char character;
	void readFile(String fname) throws IOException
	{
		f=new File(fname);
		freader=new FileReader(f);
		fmap=new HashMap<Character,Integer>();
		c=new char[(int)f.length()];
		freader.read(c);
		for(char i:c )
		{
			if(fmap.containsKey(i))
				fmap.put(i,fmap.get(i)+1);
			else
				fmap.put(i,1);
		}
		freader.close();
	}
	public HashMap<Character,Integer> getFrequencyMap()
	{
		return fmap;
	}
}
