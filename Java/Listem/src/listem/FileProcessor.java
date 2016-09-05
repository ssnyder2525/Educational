package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor 
{
	
	public ArrayList<File> getFileList(File file, String pattern, boolean recursive)
	{
		Pattern p = Pattern.compile(pattern);
		ArrayList<File> files = new ArrayList<File>();
		if(file.isDirectory())
		{
			getMatching(file, files, recursive, p);
		}
		if(file.isFile())
		{
			Matcher m = p.matcher(file.getName());
			if(m.matches())
				getFile(files, file);
		}
		return files;
	}
	
	public void getMatching(File file, ArrayList<File> files, boolean recursive, Pattern p)
	{
		File[] dirFiles = file.listFiles();
		for(int i = 0; i < dirFiles.length; i++)
		{
			if(dirFiles[i].isDirectory() && recursive == true)
			{
				getMatching(dirFiles[i], files, recursive, p);
			}
			if(dirFiles[i].isFile())
			{
				Matcher m = p.matcher(dirFiles[i].getName());
				if(m.matches())
				{
					getFile(files, dirFiles[i]);
				}
			}
		}
	}
	
	public void getFile(ArrayList<File> files, File file)
	{
		files.add(file);
	}
}
