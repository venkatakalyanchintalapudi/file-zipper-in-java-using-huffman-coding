package filezipper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import filezipper.FrequencyCounter;
import filezipper.Huffman;
@SuppressWarnings("unused")
public class Main 
{
	public static void main(String[] args) throws IOException {
		FrequencyCounter fc=new FrequencyCounter();
	    Huffman huffman=new Huffman();
	    fc.readFile("input.txt");
	    huffman.huffer(fc.getFrequencyMap());
	    File f1=new File("input.txt"); 
	    File f2=new File("compressed.txt");
	    File f3=new File("decompressed.txt");
	    System.out.println("Actual File Size : "+f1.length()+" bytes");
	    System.out.println("Started Compressing..");
	    huffman.CompressToFile("input.txt","compressed.txt");
	    System.out.println("File Compressed!!");
	    System.out.println("Compressed File Size : "+f2.length()+" bytes");
	    System.out.println("Decompressing the File..");
	    huffman.dehuffer("compressed.txt","decompressed.txt");
	    System.out.println("File Decompressed!!");
	    System.out.println("Decompressed File Size : "+f3.length()+" bytes");
	}
}
