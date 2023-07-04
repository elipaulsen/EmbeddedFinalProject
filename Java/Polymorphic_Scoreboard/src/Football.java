public class Football extends Sport{
  private static final String[] possibleEvents = new String[]{"TOUCHDOWN","EXTRA POINT","FIELD GOAL","SAFETY"};

  public Football(String homeTeam, String awayTeam) {
    super(homeTeam, awayTeam, 4, "QUARTER");
  }

  @Override
  public void awayScore(int s) {
    if (s==2) {
      updateAwayScore(6);
    }
    if (s==4) {
      updateAwayScore(1);
    }
    if (s==6) {
      updateAwayScore(3);
    }
    if (s==8) {
      updateAwayScore(2);
    }
  }

  @Override
  public void homeScore(int s) {
    if (s==1) {
      updateHomeScore(6);
    }
    if (s==3) {
      updateHomeScore(1);
    }
    if (s==5) {
      updateHomeScore(3);
    }
    if (s==7) {
      updateHomeScore(2);
    }
  }

  @Override
  public String[] getPossibleEvents() {
    return possibleEvents;
  }
}
