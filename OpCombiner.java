import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;
public class OpCombiner 
{
	//**Warning: This does not account for months.
	public static void main(String[] args) throws IOException
	{
		//Create new file with both plans
		File result = new File("Nuke Launches Combined.txt");
		
		//Read in both plans
		BufferedReader reader1 = new BufferedReader(new FileReader("Nuke Launches N.txt"));
		BufferedReader reader2 = new BufferedReader(new FileReader("Nuke Launches S.txt"));
		
		String records = "[table][**]Launch at[||]From[||]To[||]Speed[||]Type[||]Link[||]Fake?[/**] \n\n";
		int i = 0;
		int lines = 0;
		
		String currentLine1 = reader1.readLine();
		String currentLine2 = reader2.readLine();
		
		while (currentLine1 != null || currentLine2 != null)
		{
			if (currentLine2 != null) 
			{
				currentLine2 = currentLine2.replace("[|]No[/*]", "[|]Yes[/*]");
			}
			i++;
			if (i > 39)
			{
				records = records + "[/table]\n\n";
				records = records + "[table][**]Launch at[||]From[||]To[||]Speed[||]Type[||]Link[||]Fake?[/**] \n\n";
				i = 0;
			}
			else if (currentLine1 == null)
			{
				records = records + currentLine2 + "\n";
				currentLine2 = reader2.readLine();
				lines = lines + 1;
			}
			else if (currentLine2 == null)
			{
				records = records + currentLine1 + "\n";
				currentLine1 = reader1.readLine();
				lines = lines + 1;
			}
			else 
			{
				//Extract date and time for each line
				String dateTime1 = currentLine1.substring(currentLine1.indexOf("b"), currentLine1.indexOf("/b"));
				String dateTime2 = currentLine2.substring(currentLine2.indexOf("b"), currentLine2.indexOf("/b"));
				dateTime1 = dateTime1.substring(2, dateTime1.length()-1);
				dateTime2 = dateTime2.substring(2, dateTime2.length()-1);
				
				//Test days
				String[] date1 = dateTime1.split("\\s+");
				String[] date2 = dateTime2.split("\\s+");
				int day1 = Integer.parseInt(date1[1].substring(0,date1[1].indexOf("/")));
				int day2 = Integer.parseInt(date2[1].substring(0,date2[1].indexOf("/")));
				if (day1 < day2)
				{
					records = records + currentLine1 + "\n";
					currentLine1 = reader1.readLine();
					lines = lines + 1;
				}
				else if (day2 < day1)
				{
					records = records + currentLine2 + "\n";
					currentLine2 = reader2.readLine();
					lines = lines + 1;
				}
				else
				{
					//Test time
					int hour1 = Integer.parseInt(date1[0].substring(0,date1[0].indexOf(":")));
					int hour2 = Integer.parseInt(date2[0].substring(0,date2[0].indexOf(":")));
					if (hour1 < hour2)
					{
						records = records + currentLine1 + "\n";
						currentLine1 = reader1.readLine();
						lines = lines + 1;
					}
					else if (hour2 < hour1)
					{
						records = records + currentLine2 + "\n";
						currentLine2 = reader2.readLine();
						lines = lines + 1;
					}
					else
					{
						//Test minute
						int minute1 = Integer.parseInt(date1[0].substring(date1[0].indexOf(":") + 1,date1[0].indexOf(":", date1[0].indexOf(":") + 1)));
						int minute2 = Integer.parseInt(date2[0].substring(date2[0].indexOf(":") + 1,date2[0].indexOf(":", date2[0].indexOf(":") + 1)));
						if (minute1 < minute2)
						{
							records = records + currentLine1 + "\n";
							currentLine1 = reader1.readLine();
							lines = lines + 1;
						}
						else if (minute2 < minute1)
						{
							records = records + currentLine2 + "\n";
							currentLine2 = reader2.readLine();
							lines = lines + 1;
						}
						else
						{
							//Test seconds
							int seconds1 = Integer.parseInt(date1[0].substring(date1[0].lastIndexOf(":") + 1, date1[0].length()));
							int seconds2 = Integer.parseInt(date1[0].substring(date2[0].lastIndexOf(":") + 1, date2[0].length()));
							if (seconds1 < seconds2)
							{
								records = records + currentLine1 + "\n";
								currentLine1 = reader1.readLine();
								lines = lines + 1;
							}
							else
							{
								records = records + currentLine2 + "\n";
								currentLine2 = reader2.readLine();
								lines = lines + 1;
							}
						}
					}
				}
			}
		}
		
		records = records + "[/table]\n\n";

		//Insert into new file (40 lines per table)
		BufferedWriter writer = new BufferedWriter(new FileWriter(result.getName()));
	    writer.write(records);
		
		System.out.println("Number of lines: " + lines);
		
		writer.close();
		reader1.close();
		reader2.close();
	}
}
