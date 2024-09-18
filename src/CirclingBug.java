///////////////////////////////////////////////////////////////////////////////
//
// Title: CirclingBug is a bug that has revolves around a point in a circular path. A CirclingBug is
// worth 200 points.
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

public class CirclingBug extends Bug implements Moveable {
  private float[] circleCenter; // The point that this CirclingBug will rotate around
  private static final int POINTS = 200; // The amount of points a CirclingBug is worth
  private double radius; // The radius for the circle a CirclingBug will have
  private double ticks; // Used in draw to make sure the movement of a CirclingBug looks like it
                        // travels in a circle
  private int[] tintColor; // An array with a length of 3 that keeps track of what color this
                           // CirclingBug should be

  /**
   * Basic constructor for a CirclingBug object
   * 
   * @param circleX   the x position for the center of the circle
   * @param circleY   the y position for the center of the circle
   * @param radius    the radius this CirclingBug will have
   * @param tintColor // the color this CirclingBug will be
   */
  public CirclingBug(float circleX, float circleY, double radius, int[] tintColor) {
    super((float) (radius * Math.cos(0.0) + circleX), (float) (radius * Math.sin(0.0) + circleY),
        POINTS); // Calls the Bug constructor
    ticks = 0.0; // This value is 0.0 by default
    circleCenter = new float[] {circleX, circleY};
    this.radius = radius;
    this.tintColor = tintColor;
  }

  /**
   * Draws this CirclingBug to the screen with the specified tint.
   */
  public void draw() {
    processing.tint(tintColor[0], tintColor[1], tintColor[2]);
    super.draw();
    processing.tint(255, 255, 255); // Resets processing's image to stop drawing with a tint
  }

  /**
   * Calls this CirclingBug to move if it should move.
   */
  public void move() {
    if (shouldMove()) {
      ticks += 0.05;
      this.setX((float) (radius * Math.cos(ticks) + circleCenter[0])); // Calculates the new x
                                                                       // position for this
                                                                       // CirclingBug
      this.setY((float) (radius * Math.sin(ticks) + circleCenter[1])); // Calculates the new y
                                                                       // position for this
                                                                       // CirclingBug
      this.moveHitbox(this.getX(), this.getY()); // Moves the hitbox along with this CirclingBug
    }
  }

  /**
   * CirclingBug's always move, so this method will always return true.
   */
  public boolean shouldMove() {
    return true;
  }
}
