import java.util.Scanner;

public class ScoreboardDriver {
  public static void main(String[] args) {
    Sport game = null;
    String home,away;
    String sport;
    Scanner input = new Scanner(System.in);

    System.out.println("*** SCOREBOARD SETUP ***\n");
    System.out.println("SELECT A SPORT");
    System.out.println("1. SOCCER");
    System.out.println("2. FOOTBALL");
    System.out.println("3. HOCKEY");
    System.out.println("4. BASKETBALL");
    System.out.println("5. BASEBALL");
    System.out.println("6. RUGBY");


    sport = input.nextLine();
    while (Integer.parseInt(sport) < 1 || Integer.parseInt(sport) > 6) {
      System.out.println("SELECT A SPORT");
      System.out.println("1. SOCCER");
      System.out.println("2. FOOTBALL");
      System.out.println("3. HOCKEY");
      System.out.println("4. BASKETBALL");
      System.out.println("5. BASEBALL");
      System.out.println("6. RUGBY");
      sport = input.nextLine();
    }


    System.out.println("ENTER HOME TEAM NAME");
    home = input.nextLine().toUpperCase();
    System.out.println("ENTER AWAY TEAM NAME");
    away = input.nextLine().toUpperCase();

    switch (sport) {
      case "1":
        game = new Soccer(home, away);
        break;
      case "2":
        game = new Football(home,away);
        break;
      case "3":
        game = new Hockey(home,away);
        break;
      case "4":
        game = new Basketball(home,away);
        break;
      case "5":
        game = new Baseball(home, away);
        break;
      case "6":
        game = new Rugby(home,away);
        break;
    }


    int choice;
    while (game.getGameStatus()) {
      System.out.println("************************");
      System.out.println("* P O L Y M O R P H I C");
      System.out.println("*  S C O R E B O A R D");
      System.out.println("* "+game.getHomeTeam() + " " + game.getHomeScore() + " : " + game.getAwayScore() + " " + game.getAwayTeam());
      System.out.println("* "+game.getPeriodName() + " " + game.getCurrPeriod());
      System.out.println("************************");

      System.out.println("\nSCOREBOARD MENU");
      int i = 1;
      for (String event : game.getPossibleEvents()) {
        System.out.println(i + ". " + home +" "+ event);
        i++;
        System.out.println(i + ". " + away +" "+ event);
        i++;
      }

      System.out.println((game.getPossibleEvents().length*2)+1 + ". END " + game.getPeriodName());
      choice = input.nextInt();

      if(choice>0 && choice%2 != 0 && choice < game.getPossibleEvents().length*2 +1){
        game.homeScore(choice);
        System.out.println("*** "+game.getHomeTeam()+ " HAVE SCORED A "+game.getPossibleEvents()[choice / 2]+" ***");
      }
      if(choice>0 && choice%2 == 0 && choice < game.getPossibleEvents().length*2 +1){
        game.awayScore(choice);
        System.out.println("*** "+game.getAwayTeam()+ " HAVE SCORED A "+game.getPossibleEvents()[choice / 2 -1]+" ***");
      }
      if(choice == (2*game.getPossibleEvents().length)+1){
        game.nextPeriod();
        System.out.println("*** "+ game.getPeriodName() + " " + (game.getCurrPeriod()-1) +" HAS ENDED ***");
      }
    }
    input.close();

    System.out.println("*** GAME RESULT ***");
    if (game.getWinner().equals("TIE")) {
      System.out.println("THE GAME HAS ENDED IN A TIE");
    }
    else {
      System.out.println(game.getWinner() + " WON THE GAME");
    }
    System.out.println(game.getHomeTeam() + " " + game.getHomeScore() + " : " + game.getAwayScore() + " " + game.getAwayTeam());
  }

}
