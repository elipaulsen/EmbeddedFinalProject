public class Baseball extends Sport {
  private static final String[] possibleEvents = new String[]{"HOMERUN","GRAND SLAM"};


  public Baseball(String homeTeam, String awayTeam) {
    super(homeTeam, awayTeam, 9, "INNING");
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
    if (s==3) {
      updateHomeScore(4);
    }
  }

  @Override
  public void awayScore(int s) {
    if (s==2) {
      updateAwayScore(1);
    }
    if (s==4) {
      updateAwayScore(4);
    }
  }

}
