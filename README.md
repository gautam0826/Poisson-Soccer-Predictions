# Poisson Soccer Predictions

This uses two differing strategies to estimate λ. This just uses a simple Poisson model without any adjustments like Dixon & Coles.

 * Model 1 uses the method described [here](https://www.sbo.net/strategy/football-prediction-model-poisson-distribution/), estimating λ with teams' home and away average goals scored and conceded.
 * Model 2 uses the method described [here](http://pena.lt/y/2014/11/02/predicting-football-using-r/), estimating λ with a generalized linear model based on match results.

The web scraping programs are written in Java using the HTML parser Jsoup, which can be downloaded [here](https://jsoup.org/). The models are written in R.

In order to change the league, go into the web scraping programs and change the league variable(s). Check soccerstats.com to check what to replace league with. For example, to scrape MLS data, change league to "usa" because soccerstats.com's MLS page is located at http://www.soccerstats.com/latest.asp?league=usa. To scrape English Championship data, change league to "england2" because soccerstats.com's Championship page is located at http://www.soccerstats.com/latest.asp?league=england2.

### Instructions:

 * Model 1: First run Model1CompleteDataScraper.java, then run Model1.R
 * Model 2: First run Model2DataScraper.java, then run Model2.R

Remember if running from command line to add the jsoup jar file to the classpath.