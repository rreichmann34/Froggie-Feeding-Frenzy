///////////////////////////////////////////////////////////////////////////////
//
// Title: StrongBug is a Bug that has more health than a typical Bug. It only moves when it is hit
// once by the frog. A StrongBug is worth 500 points in this game.
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

public class StrongBug extends Bug implements Moveable {
  private int currentHealth; // If this value goes below 0, this StrongBug is considered dead.
  private final int MAX_HEALTH; // The maximum amount of health this StrongBug can have.
  private static final int POINTS = 500; // The amount of points that a StrongBug is worth

  /**
   * A generic constructor for a StrongBug object.
   * 
   * @param x      the x position of the center of this StrongBug
   * @param y      the y position of the center of this StrongBug
   * @param health the amount of health this bug has
   */
  public StrongBug(float x, float y, int health) {
    super(x, y, POINTS); // Calls the Bug constructor
    currentHealth = health;
    MAX_HEALTH = health;
    if (currentHealth < 1) {
      throw new IllegalArgumentException("The health of the strong bug cannot be less than 1!");
    }
  }

  /**
   * Keeps track of whether or not this StrongBug is dead
   * 
   * @return true if health is less than or equal to 0, false otherwise
   */
  public boolean isDead() {
    return currentHealth <= 0;
  }

  /**
   * Checks to see if this StrongBug has been hit by a frog. If this StrongBug has been hit by a
   * frog, reduce the image size by 0.75.
   * 
   * @param f the frog that might have hit this StrongBug
   * @return true if this StrongBug has been hit by a frog, false otherwise.
   */
  @Override
  public boolean isEatenBy(Frog f) {
    // First check if the hitboxes collide
    if (f.getTongueHitbox().doesCollide(this.getHitbox())) {
      image.resize((int) (image.width * 0.75), (int) (image.height * 0.75));
      this.getHitbox().changeDimensions(image.width, image.height);
      return true;
    }
    return false;
  }

  /**
   * Decreases this StrongBug's health
   */
  public void loseHealth() {
    currentHealth--;
  }

  /**
   * This StrongBug only moves if it should. If this object hits the right side of the window, set
   * the x position back at the left side of the window. If this object is not at the right side,
   * increase its x position by 3.
   */
  public void move() {
    if (shouldMove()) {
      if (this.getX() >= processing.width) {
        this.setX(0);
        this.moveHitbox(this.getX(), this.getY());
      } else {
        this.setX(this.getX() + 3);
        this.moveHitbox(this.getX(), this.getY());
      }
    }
  }

  /**
   * A StrongBug only moves if it has been hit by a frog.
   */
  public boolean shouldMove() {
    return MAX_HEALTH != currentHealth;
  }
}
