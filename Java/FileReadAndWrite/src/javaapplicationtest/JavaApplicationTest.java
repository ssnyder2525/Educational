/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplicationtest;

/**
 *
 * @author Stephen
 */

import java.io.*;
import java.util.*;


public class JavaApplicationTest {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)  {
        System.out.println("Hello");
        int i = 7;
        int j = 45;
        int k = add(i,j);
        System.out.println(k);
        
        
        
        
        // Stream to write file
		FileOutputStream fout;		

		try
		{
		    // Open an output stream
		    fout = new FileOutputStream ("C:/Users/Stephen/Desktop/input2.txt");

		    // Print a line of text
		    new PrintStream(fout).println ("hello world!");

		    // Close our output stream
		    fout.close();		
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to write to file");
			System.exit(-1);
		}
        
        
        
        
        
        Vector<String> Names = new Vector();
        
        Vector IDs = new Vector();
     // Stream to read file
		FileInputStream fin;		

		try
		{
		    // Open an input stream
		    fin = new FileInputStream ("C:/Users/Stephen/Desktop/input.txt");

		    // Read a line of text
                    String read;
                    DataInputStream DI = new DataInputStream(fin);
		    while((read = DI.readLine()) != null)
                    {
                        if (read != "")
                        {
                            Names.add(read);
                            read = DI.readLine();
                            IDs.add(read);
                        }
                    }

		    // Close our input stream
		    fin.close();		
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to read from file");
			System.exit(-1);
		}
                
        for (int m = 0; m < Names.size(); m++) {
            System.out.println(Names.get(m) +"\n"+ IDs.get(m)+"\n");
           
        }
        
    }
    
    public static int add(int i, int j)
    {
        return i + j;
    }
    
}

