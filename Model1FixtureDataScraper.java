import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Model1FixtureDataScraper 
{
	ArrayList<Fixture> scrape (String league, int gamesAhead) throws IOException
	{
		ArrayList<Fixture> result = new ArrayList<Fixture>();
		
		String url = "http://www.soccerstats.com/table.asp?league=" + league + "&tid=10";
		Document doc = Jsoup.connect( url ).ignoreContentType( true ).timeout(0).get();
		
        Element table = doc.select( "table#btable" ).first();
        for ( Element row : table.select( "tr" ) )
        {
            Elements tds = row.select( "td" );
            if (tds.size() > 1)
            {
                String teamUrl = "http://www.soccerstats.com/" + tds.get(1).select("a[href]").attr("href");
//                System.out.println(tds.text());
                scrapeTeamFixture(teamUrl, gamesAhead, result);
            }
        }
        return result;
	}
	
	private void scrapeTeamFixture(String url, int gamesAhead, ArrayList<Fixture> result) throws IOException
	{
		Document doc = Jsoup.connect( url ).ignoreContentType( true ).timeout(0).get();
		Elements tables = doc.select( "table" );
		int gameCnt = 0;
		for( Element table: tables )
		{
			if (table.select("tr").size() < 4)
			{
				continue;
			}
			Element a = table.select("tr").get(3);
			if (!a.text().equals("Opponent PPG"))
			{
				continue;
			}

			for ( Element row : table.select( "tr" ) )
	        {
	            Elements tds = row.select( "td" );
	            if (tds.size() > 3)
	            {
	            	gameCnt++;
//	            	System.out.println(tds.text());
	                String date = tds.get(0).text();
	                String homeTeam = tds.get(1).text();
	                String awayTeam = tds.get(3).text();
	                Fixture f = new Fixture(date, homeTeam, awayTeam);
	                if (!result.contains(f))
	                {
	                	result.add(f);
	                }
	            	if (gameCnt == gamesAhead)
	            	{
	            		return;
	            	}
	            }
	        }
		}
	}
}