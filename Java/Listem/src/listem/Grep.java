package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends FileProcessor implements IGrep {

	public Grep() {
		
	}

	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) 
	{
		Map<File, List<String>> answer = new HashMap<File, List<String>>();
		ArrayList<File> filesToCheck = new ArrayList<File>();
		filesToCheck = getFileList(directory, fileSelectionPattern, recursive);
		Pattern p = Pattern.compile(substringSelectionPattern);
		for(int i = 0; i < filesToCheck.size(); i++)
		{
			getMatches(answer, p, filesToCheck.get(i));
		}
		return answer;
	}
	
	public void getMatches(Map<File, List<String>> answer, Pattern p, File file)
	{
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{
				String currentString = sc.nextLine();
				Matcher m = p.matcher(currentString);
				if(m.find())
				{
					if(answer.containsKey(file))
					{
						answer.get(file).add(currentString);
					}
					else
					{
						List<String> newlist = new ArrayList<String>();
						newlist.add(currentString);
						answer.put(file, newlist);
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
