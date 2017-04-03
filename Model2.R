# read input csv files
results <- read.csv(file="Model2Input.csv", header=TRUE, sep=",")
fixtures <- read.csv(file="Model2Fixtures.csv", header=TRUE, sep=",")
predictions <- data.frame()

# create glm that takes into account team/opponent's strength and home field advantage
model <- glm(Goals ~ Team + Opponent + Home, family=poisson(link=log), data=results)

# given goal estimations adds mode score and win, over/under, clean sheet, mode score probabilities to predictions data frame
poisson_model <- function(predictions, date, home_team, away_team, x_home, x_away)
{
  # get probabilities per goal
  home_goals <- dpois(0:10, x_home) 
  away_goals <- dpois(0:10, x_away)
  
  # convert probability vectors into score matrix
  score_matrix <- home_goals %o% away_goals
  
  # get probabilities for home, draw, away win
  home_prob <- sum(score_matrix[lower.tri(score_matrix)])
  away_prob <- sum(score_matrix[upper.tri(score_matrix)])
  draw_prob <- sum(diag(score_matrix))
  
  # get clean sheet probabilities
  home_CS <- sum(score_matrix[,1])
  away_CS <- sum(score_matrix[1,])
  
  # get under 2.5 goals probability, and mode score
  under_prob <- 0
  mode_home <- 0
  mode_away <- 0
  mode_prob <- 0
  
  # loop through score matrix
  for (i in 1:nrow(score_matrix))
  {
    for (j in 1:ncol(score_matrix))
    {
      if (score_matrix[i,j] > mode_prob)
      {
        mode_prob = score_matrix[i,j]
        mode_home <- i - 1 # rows/columns indexed starting at 1
        mode_away <- j - 1 # so decrement to get actual goal numbers
      }
      if (i + j - 2 < 2.5)
      {
        under_prob <- under_prob + score_matrix[i,j]
      }
    }
  }
  # probability of over is just 1 - under
  over_prob <- 1 - under_prob
  
  # add to data frame
  predictions <- rbind(predictions, data.frame(date, home_team, away_team, home_prob, draw_prob, away_prob, mode_home, mode_away, mode_prob, over_prob, under_prob, home_CS, away_CS))
  
  return(predictions)
}

# loop through fixtures and add probabilities to predictions dataframe. Two rows are one game to increment by 2
for (i in seq(1,nrow(fixtures), 2)) 
{
  date = fixtures[i,1]
  home_team = fixtures[i,2]
  away_team = fixtures[i,3]
  
  # calculate estimations for goals scored
  x_home <- predict(model, fixtures[i,], type = "response")
  x_away <- predict(model, fixtures[i+1,], type = "response")
  
  #run poisson model and store results
  predictions <- poisson_model(predictions, date, home_team, away_team, x_home, x_away)
}
# write predictions to csv file
write.csv(predictions, file = "Model2Results.csv",row.names=FALSE)

# write glm's attack and defence strengths to csv file
strengths <- coef(model)
write.csv(strengths, file = "Model2AttackDefenceStrengths.csv",row.names=TRUE)