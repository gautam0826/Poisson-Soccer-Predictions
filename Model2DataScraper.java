import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Model2DataScraper 
{
	ArrayList<Object>[] scrape (String league) throws IOException
	{
		ArrayList<Object> teamData = new ArrayList<Object>();
		ArrayList<Object> fixtureData = new ArrayList<Object>();
		
		String url = "http://www.soccerstats.com/results.asp?league=" + league;
		Document doc = Jsoup.connect( url ).ignoreContentType( true ).timeout(0).get();
		
        Elements tables = doc.select( "table#btable" );  
        for ( int i = 0; i < tables.size(); i++ )
        {
        	Element table = tables.get(i);
            for ( Element row : table.select( "tr" ) )
            {
                Elements tds = row.select( "td" );
                if (tds.size() > 1)
                {
                    String score = tds.get(3).text();
                    if (score.contains(" - ") && Character.isDigit(score.charAt(0)))
                    {
                    	String date = tds.get(0).text();
                    	String time = tds.get(1).text();
                        String teams = tds.get(2).text();
                        String[] teamArr = teams.split(" - ");
                        String homeTeam = teamArr[0].substring(1);
                        String awayTeam = teamArr[1];
                        String[] teamScores = score.split(" - ");
                        int homeGoals = Integer.parseInt(teamScores[0]);
                        int awayGoals = Integer.parseInt(teamScores[1]);
                        Result r = new Result(date, time, homeTeam, awayTeam, homeGoals, awayGoals);
                        teamData.add(r);
                    }
                    else if (!score.equals("pp."))
                    {
                    	String date = tds.get(0).text();
                    	String time = tds.get(1).text();
                        String teams = tds.get(2).text();
                        String[] teamArr = teams.split(" - ");
                        String homeTeam = teamArr[0].substring(1);
                        String awayTeam = teamArr[1];                    
                        Fixture f = new Fixture(date, homeTeam, awayTeam);
                        fixtureData.add(f);
                    }
                }
            }
        }
        ArrayList<Object>[] result = new ArrayList[2];
        result[0] = teamData;
        result[1] = fixtureData;
        return result;
	}
	
	public static void main(String[] args) throws IOException
	{
		String outputFile = "Model2Input.csv";
		String outputFile2 = "Model2Fixtures.csv";

        PrintWriter out = new PrintWriter(new FileWriter(outputFile));
        out.println("Date,Team,Opponent,Goals,Home");
        PrintWriter out2 = new PrintWriter(new FileWriter(outputFile2));
        out2.println("Date,Team,Opponent,Home");

		Model2DataScraper r = new Model2DataScraper();
		ArrayList<Object>[] result = r.scrape("england");
		for (Object o: result[0])
		{
			out.println(o);
			out.flush();
		}
		for (Object o: result[1])
		{
			Fixture f = (Fixture)o;
			out2.println(f.date + "," + f.homeTeam + "," + f.awayTeam + ",1");
			out2.println(f.date + "," + f.awayTeam + "," + f.homeTeam + ",0");
			out2.flush();
		}
		out.close();
		out2.close();
	}
}