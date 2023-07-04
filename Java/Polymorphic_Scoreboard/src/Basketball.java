public class Basketball extends Sport{
  private static final String[] possibleEvents = new String[]{"2 POINTER", "3 POINTER", "FREE THROW"};


  public Basketball(String homeTeam, String awayTeam) {
    super(homeTeam, awayTeam, 4, "QUARTERS");
  }

  @Override
  public String[] getPossibleEvents() {
    return possibleEvents;
  }

  @Override
  public void homeScore(int s) {
    if (s==1) {
      updateHomeScore(2);
    }
    if (s==3) {
      updateHomeScore(3);
    }
    if (s==5) {
      updateHomeScore(1);
    }
  }

  @Override
  public void awayScore(int s) {
    if (s==2) {
      updateAwayScore(2);
    }
    if (s==4) {
      updateAwayScore(3);
    }
    if (s==6) {
      updateAwayScore(1);
    }
  }

}
