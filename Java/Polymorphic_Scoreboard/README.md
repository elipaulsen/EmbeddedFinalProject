# Polymorphic Scoreboard
a program that can act as a scoreboard for multiple sports using polymorphism.
#
### Developer Documentation

This program has an abstract class called Sport. and 6 different classes that extend Sport. All 6 sports use many basic methods and instance variables defined in the abstract sport class. The only things that are defined in the 6 derived sport classes are things unique to the sport, like how many periods, how many points awarded, and different scores etc.

![UML](../umls/scoreboard.png)

### User Documentation

Start by running the driver. Then in the command line you will see options to pick the sport and team names. The options are: Soccer, Football, Hockey, Basketball, Rugby, and Baseball. Depending on what sport you pick you will see different scoring options: Goal for soccer, Touchdown for football etc. This is where you get to decide what happens in the game and keep track of the score!