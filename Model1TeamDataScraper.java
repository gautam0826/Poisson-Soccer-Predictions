import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Model1TeamDataScraper 
{
	HashMap<String, Team>[] scrape (String league) throws IOException
	{
		HashMap<String, Team>[] result = new HashMap[2];
		result[0] = new HashMap<String, Team>();
		result[1] = new HashMap<String, Team>();
		
		String url = "http://www.soccerstats.com/homeaway.asp?league=" + league;
		Document doc = Jsoup.connect( url ).ignoreContentType( true ).timeout(0).get();
		
        Elements tables = doc.select( "table#btable" );  
        for ( int i = 0; i < 2; i++ )
        {
        	Element table = tables.get(i);
        	//System.out.println(table.firstElementSibling().text());
        	boolean isHome = (i % 2 == 0);
            for ( Element row : table.select( "tr" ) )
            {
                Elements tds = row.select( "td" );
                if (tds.size() > 0)
                {
                    String teamName = tds.get(1).text();
                    int GP = Integer.parseInt(tds.get(2).text());
                    int W = Integer.parseInt(tds.get(3).text());
                    int D = Integer.parseInt(tds.get(4).text());
                    int L = Integer.parseInt(tds.get(5).text());
                    int GF = Integer.parseInt(tds.get(6).text());
                    int GA = Integer.parseInt(tds.get(7).text());
                    Team t = new Team(teamName, isHome, GP, W, D, L, GF, GA);
                    result[i].put(teamName, t);
                    //System.out.println(tds.text());
                }
            }
        }
        return result;
	}
}