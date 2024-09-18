///////////////////////////////////////////////////////////////////////////////
//
// Title: Frog is a GameActor that tries to eat the Bug(s) on the screen. It has the ability to be
// dragged around the screen, it can shoot out a tongue to eat a bug, and it can take damage
// if it is hit by a bug.
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

public class Frog extends GameActor implements Moveable {
  private int health; // The health of this frog
  private static final String IMG_PATH = "images" + File.separator + "frog.png"; // Image of the
                                                                                 // frog
  private boolean isDragging; // True if the mouse is pressed down and the mouse is within the
                              // frog's hitbox
  private float oldMouseX; // Used to calculate new coordinates in move method
  private float oldMouseY; // Used to calculate new coordinates in move method
  private Tongue tongue; // The frog's tongue

  /**
   * Constructor for a basic frog
   * 
   * @param x      the x position of this frog on the window
   * @param y      the y position of this frog on the window
   * @param health the amount of health this frog has
   * 
   * @throws IllegalArgumentException if the frog has a health less than 1
   */
  public Frog(float x, float y, int health) {
    super(x, y, IMG_PATH); // Calls the GameActor constructor
    this.health = health;
    isDragging = false; // This value is false by default and is only changed when the mouse is
                        // pressed
    try {
      tongue = new Tongue(x, y); // creates the tongue
    } catch (Exception e) {
      e.getMessage();
    }

    if (health < 1) {
      throw new IllegalArgumentException("The frog's health cannot be less than 1!");
    }
  }

  /**
   * Draws this Frog and its tongue to the screen. Extends the tongue if it has not hit the
   * boundary. Draws the tongue first and the frog over the tongue.
   */
  @Override
  public void draw() {
    if (tongue.isActive()) {
      tongue.draw();
      if (!tongueHitBoundary()) {
        tongue.extend(this.getX(), -2); // Extends the tongue upwards by 2 pixels every time draw is
                                        // called
      }
    }
    processing.image(image, this.getX(), this.getY());
  }

  /**
   * Gets the health of this Frog
   * 
   * @return health
   */
  public int getHealth() {
    return health;
  }

  /**
   * Gets the hitbox for this frog's tongue
   * 
   * @return the hitbox of the tongue
   */
  public Hitbox getTongueHitbox() {
    if(!tongue.isActive()) {
      throw new IllegalStateException("The tongue is currently inactive!");
    }
    return tongue.getHitbox();
  }

  /**
   * Checks if this frog is dead
   * 
   * @return true if health is less than or equal to 0, false otherwise.
   */
  public boolean isDead() {
    return health <= 0;
  }

  /**
   * Checks to see if this Frog collides with a bug
   * 
   * @param b the bug that might have collided with this Frog
   * @return true if this Frog collides with any bugs, false otherwise.
   */
  public boolean isHitBy(Bug b) {
    return this.getHitbox().doesCollide(b.getHitbox());
  }

  /**
   * Checks to see if the mouse is within the frog's image
   * 
   * @return true if the mouse is within the frog's image, false otherwise.
   */
  public boolean isMouseOver() {
    float mouseX = processing.mouseX;
    float mouseY = processing.mouseY;

    // The mouse must be within all dimensions of the frog
    boolean inImage = true;
    if (mouseX > this.getX() + (image.width / 2)) { // mouse is not further left than right edge
      inImage = false;
    }
    if (mouseX < this.getX() - (image.width / 2)) { // mouse is not further right than left edge
      inImage = false;
    }
    if (mouseY < this.getY() - (image.height / 2)) { // mouse is not down left than top edge
      inImage = false;
    }
    if (mouseY > this.getY() + (image.height / 2)) { // mouse is not further up than bottom edge
      inImage = false;
    }
    return inImage;
  }

  /**
   * Decreases the health of this Frog
   */
  public void loseHealth() {
    health--;
  }

  /**
   * Executes when the user presses left click on their mouse.
   */
  public void mousePressed() {
    isDragging = true;
  }

  /**
   * Executes when the user stops pressing left click on their mouse.
   */
  public void mouseReleased() {
    isDragging = false;
  }

  /**
   * Makes the frog follow the user's mouse.
   */
  public void move() {
    if (shouldMove()) {
      float mouseX = processing.mouseX;
      float mouseY = processing.mouseY;
      float moveX = oldMouseX - mouseX; // new x position of the frog
      float moveY = oldMouseY - mouseY; // new y position of the frog

      this.setX(this.getX() - moveX); // setting the new x position
      this.setY(this.getY() - moveY); // setting the new y position
      this.moveHitbox(this.getX(), this.getY()); // moving the hitbox along with the frog

      // Update where the frog's tongue is. It should still be anchored to the center of the frog
      tongue.updateStartPoint(this.getX(), this.getY());
      if (!tongue.isActive()) {
        tongue.updateEndPoint(this.getX(), this.getY());
      }
    }

    // oldMouseX and oldMouseY should still update, even if the frog should not move
    oldMouseX = processing.mouseX;
    oldMouseY = processing.mouseY;
  }

  /**
   * Keeps track of whether or not this Frog should move
   * 
   * @return true if the user is currently dragging this Frog
   */
  public boolean shouldMove() {
    return isDragging;
  }

  /**
   * First resets the tongue back to its original length, then launches the tongue forwards again.
   */
  public void startAttack() {
    tongue.reset();
    tongue.activate();
  }

  /**
   * Executes when the user hits a bug or when the tongue reaches the top of the screen.
   */
  public void stopAttack() {
    tongue.deactivate();
  }

  /**
   * Keeps track of whether or not the tongue's endpoint has hit the top of the screen.
   * 
   * @return true if the tongue has hit the top of the screen, false otherwise.
   */
  public boolean tongueHitBoundary() {
    return tongue.hitScreenBoundary();
  }
}
