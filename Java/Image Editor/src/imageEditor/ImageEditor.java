package imageEditor;
import java.io.*;
import java.util.*;
public class ImageEditor {
	public static void main(String[] args) throws FileNotFoundException 
	{
		Image newimage = new Image();
		newimage.readTextFile(args[0]);
			
		String n = args[2];
					
		if (n.equals("invert"))
		{
			newimage.invert();
		}
		if (n.equals("grayscale"))
		{
			newimage.grayscale();
		}
		if (n.equals("emboss"))
		{
			newimage.emboss();
		}
		if (n.equals("motionblur"))
		{
			String numStr = args[3];
			Integer num = Integer.parseInt(numStr);
			newimage.motionblur(num);
		}
		
		//print out a new .ppm file
		try
		{
			PrintWriter out = new PrintWriter(args[1]);
			newimage.exportFile(out);
			out.close();
		}
		
		catch(IOException e)
		{
			System.out.println(e);
		}		
	}
}
