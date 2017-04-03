import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Model1CompleteDataScraper 
{
	public static void main(String args[]) throws IOException
    {
		String league = "england";
		String outputFile = "Model1Input.csv";

        PrintWriter out = new PrintWriter(new FileWriter(outputFile));
		
    	Model1TeamDataScraper teamScraper = new Model1TeamDataScraper();
    	HashMap<String, Team>[] teamData = teamScraper.scrape(league);
    	Model1FixtureDataScraper fixtureScraper = new Model1FixtureDataScraper();
    	ArrayList<Fixture> fixtureData = fixtureScraper.scrape(league, 2);
    	
    	out.println("Date,Home Team,Home GFpG,Home GApG,Away Team,Away GFpG,Away GApG,League GFpG,League GApG");
    	
    	double league_homeGFpg = 0;
    	double league_homeGApg = 0;
    	for (String team: teamData[0].keySet())
    	{
    		league_homeGFpg += teamData[0].get(team).GFpg;
    		league_homeGApg += teamData[0].get(team).GApg;
    	}
    	league_homeGFpg /= teamData[0].size();
    	league_homeGApg /= teamData[0].size();

    	for (Fixture f: fixtureData)
    	{
    		Team home = teamData[0].get(f.homeTeam);
    		Team away = teamData[1].get(f.awayTeam);
    		out.println(f.date + "," + home + "," + away + "," + league_homeGFpg + "," + league_homeGApg);
    		out.flush();
    	}
    	out.close();
    }
	
	static void printTeamData(HashMap<String, Team>[] teamData)
	{
		for (int i = 0; i <= 1; i++)
		{
			for (String team: teamData[0].keySet())
	    	{
				System.out.println(team);
	    	}
		}
	}
	
	static void printFixtureData(ArrayList<Fixture> fixtureData)
	{
		for (Fixture f: fixtureData)
    	{
			System.out.println(f);
    	}
	}
}