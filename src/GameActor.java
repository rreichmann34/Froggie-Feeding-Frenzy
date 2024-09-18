///////////////////////////////////////////////////////////////////////////////
//
// Title: GameActor is a generic object. This game contains bugs and frogs as GameActor(s).
// GameActor(s) have a position on the screen, a hitbox and some have the ability to move.
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

public class GameActor {
  private float[] coordinates; // Center of this GameActor
  private Hitbox hitbox; // The hitbox for this GameActor
  protected processing.core.PImage image; // This GameActor's image
  protected static processing.core.PApplet processing;

  /**
   * Constructor for a basic GameActor.
   * 
   * @param x       the x position for this GameActor
   * @param y       the y position for this GameActor
   * @param imgPath the image for this GameActor
   */
  public GameActor(float x, float y, String imgPath) {
    // Processing cannot be null when a new GameActor is initialized
    if (processing == null) {
      throw new IllegalStateException("Processing is null!");
    }

    image = processing.loadImage(imgPath);
    coordinates = new float[] {x, y}; // set the coordinates of the object
    try {
      hitbox = new Hitbox(x, y, image.width, image.height); // sets the hitbox for this GameActor
    } catch (Exception e) {
      e.getMessage();
    }
  }

  /**
   * Draws this gameActor to the screen
   */
  public void draw() {
    processing.image(image, coordinates[0], coordinates[1]);
  }

  /**
   * Gets the hitbox of this GameActor
   * 
   * @return the hitbox
   */
  public Hitbox getHitbox() {
    return hitbox;
  }

  /**
   * Gets the x coordinate of this GameActor
   * 
   * @return the x position
   */
  public float getX() {
    return coordinates[0];
  }

  /**
   * Gets the y coordinate of this GameActor
   * 
   * @return the y position
   */
  public float getY() {
    return coordinates[1];
  }

  /**
   * Moves the hitbox for this GameActor to the specified positon
   * 
   * @param x the x position to move the hitbox to
   * @param y the y position to move the hitbox to
   */
  public void moveHitbox(float x, float y) {
    setX(x);
    setY(y);
    hitbox.setPosition(x, y);
  }

  /**
   * Sets processing for all GameActor(s).
   * 
   * @param processing the processing to set
   */
  public static void setProcessing(processing.core.PApplet processing) {
    GameActor.processing = processing;
  }

  /**
   * Sets the x coordinate for this object
   * 
   * @param newX the new x position for the center of this GameActor
   */
  public void setX(float newX) {
    coordinates[0] = newX;
  }

  /**
   * Sets the y coordinate for this object
   * 
   * @param newX the new y position for the center of this GameActor
   */
  public void setY(float newY) {
    coordinates[1] = newY;
  }
}
