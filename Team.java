public class Team 
{
	String teamName;
	boolean isHome;
	int GP;
	int W;
	int D;
	int L;
	int GF;
	int GA;
	int GD;
	int points;
	double GFpg;
	double GApg;
	
	public Team (String teamName, boolean isHome, int GP, int W, int D, int L, int GF, int GA)
	{
		this.teamName = teamName;
		this.isHome = isHome;
		this.GP = GP;
		this.W = W;
		this.D = D;
		this.L = L;
		this.GF = GF;
		this.GA = GA;
		this.GD = GF - GA;
		this.points = 3*W + D;
		GFpg = (double)GF / GP;
		GApg = (double)GA / GP;
	}
	
	public String toString()
	{
		return teamName + "," + GFpg + ","+ GApg;
	}
}