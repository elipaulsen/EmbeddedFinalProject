public class Rugby extends Sport {
  private static final String[] possibleEvents = new String[]{"TRY","CONVERSION","PENALTY KICK"};


  public Rugby(String homeTeam, String awayTeam) {
    super(homeTeam, awayTeam, 2, "HALF");
  }

  @Override
  public String[] getPossibleEvents() {
    return possibleEvents;
  }

  @Override
  public void homeScore(int s) {
    if (s==1) {
      updateHomeScore(5);
    }
    if (s==3) {
      updateHomeScore(2);
    }
    if (s==5) {
      updateHomeScore(3);
    }
  }

  @Override
  public void awayScore(int s) {
    if (s==2) {
      updateAwayScore(5);
    }
    if (s==4) {
      updateAwayScore(2);
    }
    if (s==6) {
      updateAwayScore(3);
    }

  }

}
