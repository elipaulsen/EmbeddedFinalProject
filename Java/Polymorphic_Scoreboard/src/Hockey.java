public class Hockey extends Sport{
  private static final String[] possibleEvents = new String[]{"GOAL"};


  public Hockey(String homeTeam, String awayTeam) {
    super(homeTeam, awayTeam, 3, "PERIOD");
  }

  @Override
  public String[] getPossibleEvents() {
    return possibleEvents;
  }

  @Override
  public void homeScore(int s) {
    if (s==1) {
      updateHomeScore(1);
    }
  }

  @Override
  public void awayScore(int s) {
    if (s==2) {
      updateAwayScore(1);
    }
  }

}
