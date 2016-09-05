package imageEditor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class Image {
	public Integer width = -1;
	public Integer height = -1;
	public Integer maxColorValue = -1;
	public Vector<Integer> red = new Vector<>();
	public Vector<Integer> green = new Vector<>();
	public Vector<Integer> blue = new Vector<>();
	
	public void readTextFile(String arg) throws FileNotFoundException
	{
		//make an image obect out of an input file
		Scanner sc = new Scanner(new FileReader(arg));
		String line;
		Integer i = 0;
		while(sc.hasNext())
		{		
			line = sc.next();
			if (!line.startsWith("#"))
			{
				if (line.equals("P3"))
				{}
				else if (width == -1)
				{
					width = Integer.parseInt(line);
				}
				else if (height == -1)
				{
					height = Integer.parseInt(line);
					i = -1;
				}
				else if (maxColorValue == -1)
				{
					maxColorValue = Integer.parseInt(line);
				}
				else
				{
					red.add(Integer.parseInt(line));
					line = sc.next();
					green.add(Integer.parseInt(line));		
					line = sc.next();
					blue.add(Integer.parseInt(line));		
					
				}
			}
			else
			{
				line = sc.nextLine();
			}
			i++;
		}
		sc.close();
	}
	public void invert()
	{
		for(int i = 0; i < red.size(); i++)
		{
			red.setElementAt(maxColorValue-red.elementAt(i), i);
			green.setElementAt(maxColorValue-green.elementAt(i), i);
			blue.setElementAt(maxColorValue-blue.elementAt(i), i);
		}
	}
	public void grayscale()
	{
		for(int i = 0; i < red.size(); i++)
		{
			Integer average = red.elementAt(i) + green.elementAt(i) + blue.elementAt(i);
			average = average/3;
			red.setElementAt(average, i);
			green.setElementAt(average, i);
			blue.setElementAt(average, i);
		}
	}
	public void emboss()
	{
		for(int i = red.size()-1; i >= 0; i--)
		{
			Integer Row = (i / width)+1;
			Integer Position = i - ((Row-1) * width);
			if((Row != 1) && (Position != 0))
			{
				Integer valuesToGet = ((Row - 2) * width) + (Position - 1);
				Integer difference1 = red.elementAt(i) - red.elementAt(valuesToGet);
				Integer difference2 = green.elementAt(i) - green.elementAt(valuesToGet);
				Integer difference3 = blue.elementAt(i) - blue.elementAt(valuesToGet);
				Integer biggestDifference;
				if (Math.abs(difference2) > Math.abs(difference1))
				{
					biggestDifference = difference2;
				}
				else
				{
					biggestDifference = difference1;
				}
				if (Math.abs(biggestDifference) < Math.abs(difference3))
				{
					biggestDifference = difference3;
				}
				Integer value = 128 + biggestDifference;
				if(value < 0)
				{
					value = 0;
				}
				if(value >maxColorValue)
				{
					value = maxColorValue;
				}
				red.setElementAt(value, i);
				green.setElementAt(value, i);
				blue.setElementAt(value, i);
			}
			else
			{
				red.setElementAt(128, i);
				green.setElementAt(128, i);
				blue.setElementAt(128, i);
			}
		}
	}
	public void motionblur(int num)
	{
		Integer redAverage = 0;
		Integer greenAverage = 0;
		Integer blueAverage = 0;
		if (num > 0)
		{
			for(int i = 0; i < red.size(); i++)
			{
				redAverage = 0;
				greenAverage = 0;
				blueAverage = 0;
				Integer Row = (i / width)+1;
				Integer endOfRow = Row * width;
				if ((i + (num-1)) < endOfRow)
				{
					for(int j = i; j <= (i + (num-1)); j++)
					{
						redAverage = redAverage + red.elementAt(j);
						greenAverage = greenAverage + green.elementAt(j);
						blueAverage = blueAverage + blue.elementAt(j);
					}
					redAverage = redAverage/num;
					greenAverage = greenAverage/num;
					blueAverage = blueAverage/num;
					
					red.setElementAt(redAverage, i);
					green.setElementAt(greenAverage, i);
					blue.setElementAt(blueAverage, i);
				}
				else
				{
					for(int j = i; j < endOfRow; j++)
					{
						if (num == 5)
						{
							System.out.println(red.elementAt(j));
						}
						redAverage = redAverage + red.elementAt(j);
						greenAverage = greenAverage + green.elementAt(j);
						blueAverage = blueAverage + blue.elementAt(j);
					}
					int divisor = endOfRow - i;
					redAverage = redAverage/divisor;
					greenAverage = greenAverage/divisor;
					blueAverage = blueAverage/divisor;
					
					red.setElementAt(redAverage, i);
					green.setElementAt(greenAverage, i);
					blue.setElementAt(blueAverage, i);
				}
			}
		}
	}
	public void exportFile(PrintWriter out)
	{
		
		
		out.print("P3\r");
		out.print(width + " " + height + "\r");
		out.print(maxColorValue + "\r");
		int i = 0;
		while(i < red.size())
		{
			out.print(red.elementAt(i) + "\r");
			out.print(green.elementAt(i) + "\r");
			out.print(blue.elementAt(i) + "\r");
			i++;
		}
		
		out.close();
	}
}
