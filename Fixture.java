public class Fixture 
{
	String date;
	String homeTeam;
	String awayTeam;
	
	public Fixture (String date, String homeTeam, String awayTeam)
	{
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public String toString()
	{
		return date + "," + homeTeam + ","+ awayTeam;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == null || other == null)
		{
			return false;
		}
		return this.toString().equals(other.toString());
	}
}