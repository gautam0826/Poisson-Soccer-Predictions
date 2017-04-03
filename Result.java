public class Result 
{
	String date;
	String time;
	String homeTeam;
	String awayTeam;
	int homeGoals;
	int awayGoals;
	
	public Result (String date, String time, String homeTeam, String awayTeam, int homeGoals, int awayGoals)
	{
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
	}
	
	public String toString()
	{
		String result = date + "," + homeTeam + ","+ awayTeam + "," + homeGoals + ",1";
		result += "\n" + date + "," + awayTeam + ","+ homeTeam + "," + awayGoals + ",0";
		return result;
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