///////////////////////////////////////////////////////////////////////////////
//
// Title: FrogGame runs the program. Before the game starts, the window is set to a specific size,
// a frog is added to the gameActors array and BUG_COUNT number of bugs are added to the
// gameActors array. The user also has the ability to restart the game by pressing 'r'.
//
// Course: CS 300 Fall 2023
//
// Author: Remington Reichmann
// Email: rreichmann@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

public class FrogGame extends PApplet {
  private ArrayList<GameActor> gameActors; // array list of the gameActors in the game
  private int score; // the player's current score
  private PImage backgroundImg; // the image to use for the background
  private boolean isGameOver; // keeps track if the game is over, is true if the game is over
  private Random randGen; // random number generator
  private static final int BUG_COUNT = 5; // how many bugs should be on the screen at all times

  /**
   * Runs the FrogGame in PApplet
   */
  public static void main(String[] args) {
    PApplet.main("FrogGame");
  }

  /**
   * Sets the size of the window
   */
  @Override
  public void settings() {
    size(800, 600); // Sets the size of the window to 800 pixels wide and 600 tall
  }

  /**
   * This method runs before anything is drawn. It sets processing for any file that needs it, sets
   * the title of the window, and makes the images draw from their center.
   */
  @Override
  public void setup() {
    this.getSurface().setTitle("Froggie Feeding Frenzie"); // set title of window
    this.imageMode(PApplet.CENTER); // images when drawn will use x,y as their center
    this.rectMode(PApplet.CENTER); // rectangles when drawn will use x,y as their
    this.textAlign(PApplet.CENTER); // text written to screen will have center alignment
    this.focused = true; // window is "active" upon start up
    this.textSize(30); // text is 30 pt


    randGen = new Random(); // Ensures the rest of this file can utilize Random() and its functions
    backgroundImg = loadImage("images" + File.separator + "background.jpg"); // Loads the background
                                                                             // image path

    // Set processing for every file that requires it
    Hitbox.setProcessing(this);
    GameActor.setProcessing(this);
    Tongue.setProcessing(this);

    initGame();
  }

  /**
   * This method runs continuously as the program executes. It is responsible for drawing all images
   * to the screen, including the background, the frog, and all bugs. This method is also
   * responsible for printing the health of the frog and the user's score.
   */
  @Override
  public void draw() {
    if (!isGameOver) {
      image(backgroundImg, width / 2, height / 2);

      for (int i = 0; i < gameActors.size(); i++) {
        if (gameActors.get(i) instanceof Frog) {
          ((Frog) findFrog()).draw();
        } else {
          image(gameActors.get(i).image, gameActors.get(i).getX(), gameActors.get(i).getY());
        }
        if (gameActors.get(i) instanceof Moveable) {
          ((Moveable) gameActors.get(i)).move();
        }
      }

      runGameLogicChecks();
      

      for (int i = 0; i < gameActors.size(); i++) {
        text("Health: " + ((Frog) findFrog()).getHealth(), 80, 40);
      }
      text("Score: " + score, 240, 40);
    } else {
      text("GAME OVER", width / 2, height / 2);
    }
  }

  /**
   * Adds a new bug of a random type to the gameActors array. There are 4 different types of bugs
   * that can be added: Bug, BouncingBug, CirclingBug, StrongBug.
   */
  private void addNewBug() {
    // This creates a bug of a random type and adds it to the list of GameActors.
    // (1) generate a random number in the range [0,4)
    // (2) generate a random x value in the range [0, windowWidth) for the bug
    // (3) generate a random y value in the range [0, windowHeight - 150) for the bug
    // (4) depending on the value generated in step (1)
    // create the following bug and add it to the arraylist
    // 0 -> a new regular Bug at (x,y) that is worth 25 points
    // 1 -> a new BouncingBug at (x,y) that has a dx of 2 and a dy of 5
    // 2 -> a new CirclingBug at (x,y) with a radius of 25 and a random set of RGB values [0,256)
    // 3 -> a new StrongBug at (x,y) with an initial health of 3
    // Generates a random position on the screen for the bug to be drawn to
    randGen = new Random();
    int randBug = randGen.nextInt(4);
    float randXValue = randGen.nextFloat(width);
    float randYValue = randGen.nextFloat(height - 150);

    try {
      // Add regular Bug
      if (randBug == 0) {
        gameActors.add(new Bug(randXValue, randYValue, 25));
      }
      // Add BouncingBug
      if (randBug == 1) {
        gameActors.add(new BouncingBug(randXValue, randYValue, 2, 5));
      }
      // Add CirclingBug
      if (randBug == 2) {
        gameActors.add(new CirclingBug(randXValue, randYValue, (float) (25),
            new int[] {randGen.nextInt(256), randGen.nextInt(256), randGen.nextInt(256)}));
      }
      // Add StrongBug
      if (randBug == 3) {
        gameActors.add(new StrongBug(randXValue, randYValue, 3));
      }
    } catch (Exception e) {
      e.getMessage();
    }

  }

  /**
   * Runs only when: 1) The user has pressed left click on their mouse. 2) The mouse is over the
   * frog.
   */
  @Override
  public void mousePressed() {
    if (mousePressed && ((Frog) findFrog()).isMouseOver()) {
      ((Frog) findFrog()).mousePressed();
    }
  }

  /**
   * Executes when the user stops holding down left click
   */
  @Override
  public void mouseReleased() {
    ((Frog) findFrog()).mouseReleased();
  }

  /**
   * If the user pressed the space key, the tongue launches forward. If the 'r' key is pressed, the
   * game resets to its initial state.
   */
  @Override
  public void keyPressed() {
    if (key == ' ') {
      ((Frog) findFrog()).startAttack();
    }
    if (key == 'r') {
      initGame();
    }
  }

  /**
   * Resets the game back to its initial state by reseting the score, setting isGameOver back to
   * false and filling the gameActors array with a frog and 5 bugs.
   */
  public void initGame() {
    // (1) set the score to 0
    // (2) make the game NOT over
    // (3) clear out the arraylist
    // (4) create and add Frog with 100 health to the list. Its x value should be half the
    // width of the screen. Its y value should be the height of the screen minus 100
    // (5) add new bugs (of random varieties) to the list UP TO the BUG_COUNT
    score = 0;
    isGameOver = false;
    gameActors = new ArrayList<GameActor>();
    gameActors.add(new Frog(width / 2, height / 2 + 100, 100));
    for (int i = 0; i < BUG_COUNT; i++) {
      addNewBug();
    }
  }

  /**
   * This method returns where the frog is within the gameActors array.
   * 
   * @return the instanceof Frog in the gameActors array and null if no Frog is found
   */
  private GameActor findFrog() {
    try {
      for (int i = 0; i < gameActors.size(); i++) {
        if (gameActors.get(i) instanceof Frog) {
          return gameActors.get(i);
        }
      }
      throw new Exception("The gameActors list does not contain a frog!");
    } catch (Exception e) {
      e.getMessage();
    }
    return null;
  }

  /**
   * This method checks the following: 1) If the tongue has hit the top of the window, stop the
   * attack. 2) If the tongue has hit any of the bugs, stop the attack, update the score, remove the
   * bug from the screen, and add a new bug. 3) Make the frog lose health if it is hit by a bug. 4)
   * Make the frog dead if it's health is less than or equal to 0.
   */
  private void runGameLogicChecks() {
    // (1) if the Frog's tongue hits the edge of the screen, then it stops attacking
    // (2) Check every bug to see if it has been hit by the Frog.
    // (a) if a non-StrongBug is hit do the following
    // (a1) stop the frog's attack
    // (a2) remove it from the game
    // (a3) update the score
    // (a4) add a new bug (of a random variety) to the game
    // (b) if a StrongBug is hit do the following
    // (b1) stop the frog's attack
    // (b2) the StrongBug takes damage and loses health
    // (b3) if the StrongBug is dead do steps a1 - a4
    // (3) check if the frog hits any of the bugs
    // (a) if it hit any of the bugs it takes damage and loses health
    // NOTE: it can be hit my multiple bugs at the same time loses health for each.
    // Ex. is hit by 2 different bugs simultaneously then should take 2 damage.
    // (b) if the frog is dead then update the game so it is over
    if (((Frog) findFrog()).tongueHitBoundary()) {
      ((Frog) findFrog()).stopAttack();
    }

    for (int i = 0; i < gameActors.size(); i++) { // Looping through gameActors array
      // Checking to make sure the hitbox of the tongue collides with the hitbox of the bug
      if (!(gameActors.get(i) instanceof Frog) && gameActors.get(i) instanceof Bug) {
        try {
          if (((Bug)(gameActors.get(i))).isEatenBy((Frog) findFrog())) {
            // If the bug is not a StrongBug
            if (!(gameActors.get(i) instanceof StrongBug)) {
              ((Frog) findFrog()).stopAttack();
              score += ((Bug) (gameActors.get(i))).getPoints();
              gameActors.remove(i);
              addNewBug();
            }
            // If the bug is a StrongBug
            else if (gameActors.get(i) instanceof StrongBug) {
              ((Frog) findFrog()).stopAttack();
              ((StrongBug) (gameActors.get(i))).loseHealth();
              if (((StrongBug) (gameActors.get(i))).isDead()) {
                ((Frog) findFrog()).stopAttack();
                score += ((Bug) (gameActors.get(i))).getPoints();
                gameActors.remove(i);
                addNewBug();
              }
            }
          }
        } catch(IllegalStateException e) {
          e.getMessage();
        }

        // Makes the frog lose health if it is hit by any of the bugs
        if (((Frog) findFrog()).isHitBy((Bug) (gameActors.get(i)))) {
          ((Frog) findFrog()).loseHealth();
        }
        // Runs if the frog's health is less than or equal to 0
        if (((Frog) findFrog()).isDead()) {
          isGameOver = true;
        }
      }
    }
  }
}
