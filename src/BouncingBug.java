///////////////////////////////////////////////////////////////////////////////
//
// Title: BouncingBug is a Bug that bounces around the screen like the DVD player logo.
// A BouncingBug is worth 100 points in this game.
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

import java.util.Random;

public class BouncingBug extends Bug implements Moveable {
  private boolean goDown; // True if the bug is moving down, false if moving up
  private boolean goLeft; // True if the bug is moving left, false if moving right
  private static final int POINTS = 100; // The amount of points that a BouncingBug is worth
  private Random randGen; // Used to set the bug to a random direction when initialized
  private int[] speedNums; // The amount of pixels to move in the x and y direction

  /**
   * A generic constructor for a BouncingBug
   * 
   * @param x  the x position of this BouncingBug
   * @param y  the y position of this BouncingBug
   * @param dx stored at index 0 in speedNums
   * @param dy stored at index 1 in speedNums
   */
  public BouncingBug(float x, float y, int dx, int dy) {
    super(x, y, POINTS); // Calls the Bug constructor
    speedNums = new int[] {dx, dy}; // The amount of pixels this BouncingBug will move

    randGen = new Random();
    // Setting the vertical component of this CirclingBug's movement
    if (randGen.nextInt(2) == 0) {
      goDown = true;
    }
    if (randGen.nextInt(2) == 1) {
      goDown = false;
    }
    // Setting the horizontal component of this CirclingBug's movement
    if (randGen.nextInt(2) == 0) {
      goLeft = true;
    }
    if (randGen.nextInt(2) == 1) {
      goLeft = false;
    }
  }

  /**
   * Moves this BouncingBug around the screen. If this BouncingBug hits an edge of the screen, it
   * will change its direction accordingly.
   */
  public void move() {
    if (shouldMove()) {
      // moving down
      if (goDown == true) {
        // can keep moving down
        if (this.getY() + speedNums[1] <= processing.height) {
          // moving left
          if (goLeft == true) {
            // can keep moving left
            if (this.getX() - speedNums[0] >= 0) {
              // BUG MOVES HERE
              this.setX(this.getX() - speedNums[0]);
              this.setY(this.getY() + speedNums[1]);
              this.moveHitbox(this.getX(), this.getY());
            }
            // If the bug cannot keep moving left, switch directions
            else {
              goLeft = false;
            }
          }
          // moving right
          if (goLeft == false) {
            // can keep moving right
            if (this.getX() + speedNums[0] <= processing.width) {
              // BUG MOVES HERE
              this.setX(this.getX() + speedNums[0]);
              this.setY(this.getY() + speedNums[1]);
              this.moveHitbox(this.getX(), this.getY());
            }
            // If the bug cannot keep moving right, switch directions
            else {
              goLeft = true;
            }
          }
        }
        // If the bug cannot move down, then switch directions
        else {
          goDown = false;
        }
      }
      // moving up
      if (goDown == false) {
        // can keep moving up
        if (this.getY() - speedNums[1] >= 0) {
          // moving left
          if (goLeft == true) {
            // can keep moving left
            if (this.getX() - speedNums[0] >= 0) {
              // BUG MOVES HERE
              this.setX(this.getX() - speedNums[0]);
              this.setY(this.getY() - speedNums[1]);
              this.moveHitbox(this.getX(), this.getY());
            }
            // If the bug cannot keep moving left, switch directions
            else {
              goLeft = false;
            }
          }
          // moving right
          if (goLeft == false) {
            // can keep moving right
            if (this.getX() + speedNums[0] <= processing.width) {
              // BUG MOVES HERE
              this.setX(this.getX() + speedNums[0]);
              this.setY(this.getY() - speedNums[1]);
              this.moveHitbox(this.getX(), this.getY());
            }
            // If the bug cannot keep moving right, switch directions
            else {
              goLeft = true;
            }
          }
        }
        // If the bug cannot move down, then switch directions
        else {
          goDown = true;
        }
      }
    }
  }

  /**
   * BouncingBug always moves, so this method always returns true
   */
  public boolean shouldMove() {
    return true;
  }
}
