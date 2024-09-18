///////////////////////////////////////////////////////////////////////////////
//
// Title: A Bug is a GameActor with a specified amount of points. A Bug can also be eaten by a Frog.
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

public class Bug extends GameActor {
  private static final String IMG_PATH = "images" + File.separator + "bug.png"; // Loads the bug
                                                                                // image
  private int points; // the points this bug object is worth

  /**
   * Constructor for a basic Bug
   * 
   * @param x      the x position of this bug on the screen
   * @param y      the y position of this bug on the screen
   * @param points the amount of points that this bug is worth
   */
  public Bug(float x, float y, int points) {
    super(x, y, IMG_PATH); // Calls the GameActor constructor
    this.points = points;
  }

  /**
   * Gets the amount of points that this bug is worth
   * 
   * @return points
   */
  public int getPoints() {
    return points;
  }

  /**
   * Checks if the bug has been hit by the frog. The tongue must be active for this method to return
   * true.
   * 
   * @param f the frog to check if this bug has been hit
   * @return true if this bug has been hit by the frog's hitbox, false otherwise.
   */
  public boolean isEatenBy(Frog f) {
    try {
      if (f.getTongueHitbox().doesCollide(this.getHitbox())) {
        return true;
      }
    } catch(IllegalStateException e) {
      e.getMessage();
      return false;
    }
    return false;
  }
}
