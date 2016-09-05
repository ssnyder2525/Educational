package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LineCounter extends FileProcessor implements ILineCounter {

	public LineCounter() {
		
	}

	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) 
	{
		Map<File, Integer> answer = new HashMap<File, Integer>();
		ArrayList<File> filesToCheck = new ArrayList<File>();
		filesToCheck = getFileList(directory, fileSelectionPattern, recursive);
		for(int i = 0; i < filesToCheck.size(); i++)
		{
			getLineCount(answer, filesToCheck.get(i));
		}
		return answer;
	}

	public void getLineCount(Map<File, Integer> answer, File file)
	{
		int numLines = 0;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{	
				sc.nextLine();
				numLines++;
			}
			sc.close();
			answer.put(file, numLines);
		}
		catch(FileNotFoundException e)
		{
			
		}
	}
}
