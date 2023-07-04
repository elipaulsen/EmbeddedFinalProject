public abstract class Sport {
  private String homeTeam, awayTeam;
  private int homeScore, awayScore;
  private int currPeriod,numPeriods;
  private String periodName;
  private boolean gameStatus;

  public Sport(String homeTeam, String awayTeam, int numPeriods, String periodName) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.numPeriods = numPeriods;
    this.periodName = periodName;
    currPeriod = 1;
    gameStatus = true;
  }

  public String getHomeTeam() {
    return homeTeam;
  }

  public String getAwayTeam() {
    return awayTeam;
  }

  public int getCurrPeriod() {
    return currPeriod;
  }

  public String getPeriodName() {
    return periodName;
  }

  public String getWinner() {
    if (homeScore > awayScore) {
      return homeTeam;
    }
    else if (awayScore > homeScore) {
      return awayTeam;
    }
    return "TIE";
  }

  public int getHomeScore() {
    return homeScore;
  }

  public int getAwayScore() {
    return awayScore;
  }

  public void updateHomeScore(int pts) {
    homeScore += pts;
  }

  public void updateAwayScore(int pts) {
    awayScore += pts;
  }


  public void nextPeriod() {
    currPeriod++;
    if (currPeriod > numPeriods) {
      gameStatus = false;
    }
  }

  public boolean getGameStatus() {
    return gameStatus;
  }

  public abstract void awayScore(int s);

  public abstract void homeScore(int s);

  public abstract String[] getPossibleEvents();


}
