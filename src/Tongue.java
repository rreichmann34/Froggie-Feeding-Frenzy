import processing.core.PApplet;

/**
 * An instantiable class maintains data about the Tongue use by the Frog in the Froggie Feeding
 * Frenzie game. Graphically this is represented by a line. This tongue can move with the Frog and
 * extend when active.
 * 
 * @author Michelle
 */
public class Tongue {
  /** PApplet to use to draw to the screen */
  private static PApplet processing;
  /** keeps track of whether the tongue is being used */
  private boolean isActive;
  /** the starting x,y coordinates of the tongue, inside the Frog */
  private float[] startPoint;
  /** the ending x,y coordinates of the tip of the tongue */
  private float[] endPoint;
  /** the hitbox of the tongue used for game interactions */
  private Hitbox hitbox;

  /**
   * Constructor for a new tongue that starts and ends at the given point. The hitbox is centered at
   * the end point and has dimentions 30 x 30. By default the tongue is inactive.
   * 
   * @param x, the x-coordinate for the start of this tongue
   * @param y, the y-coordinate for the start of this tongue
   * @throws IllegalStateException if processing is null
   */
  public Tongue(float x, float y) {
    if (Tongue.processing == null)
      throw new IllegalStateException("Processing is null. setProcessing() "
          + "must be called before creating any Tongue objects.");
    this.startPoint = new float[] {x, y};
    this.endPoint = new float[] {x, y};
    this.hitbox = new Hitbox(x, y, 30, 30);
  }

  /**
   * Sets the processing for this tongue to use to draw.
   * 
   * @param processing, the processing to use for the Tongue class.
   */
  public static void setProcessing(PApplet processing) {
    Tongue.processing = processing;
  }

  /**
   * Moves the coordinates of the endpoint and the center of the hitbox of this tongue to the
   * x,y-values given.
   * 
   * @param x, the new x-coordinate of the endpoint
   * @param y, the new y-coordinate of the endpoint
   */
  public void updateEndPoint(float x, float y) {
    this.endPoint[0] = x;
    this.endPoint[1] = y;
    this.hitbox.setPosition(x, y); // move hitbox to be centered to the endpoint
  }

  /**
   * Moves the coordinates of the start point to the x,y-values given.
   * 
   * @param x, the new x-coordinate of the start point
   * @param y, the new y-coordinate of the start point
   */
  public void updateStartPoint(float x, float y) {
    this.startPoint[0] = x;
    this.startPoint[1] = y;
  }

  /**
   * Extends the line for the tongue by moving the endpoint and hitbox.
   * 
   * @param newX, the new x-coordinate for the endpoint
   * @param dy,   the change in the y direction to apply
   */
  public void extend(float newX, float dy) {
    float newY = endPoint[1] + dy; // calculate new y-coordinate
    this.endPoint[0] = newX; // update the x-coordinate, this primarily helps with getting
    // the tongue to move with the frog when it's being dragged around
    this.endPoint[1] = newY; // update the y-coordinate
    this.hitbox.setPosition(newX, newY); // move the hitbox so it matches the endpoint
  }

  /**
   * Reports whether or not this tongue is active.
   * 
   * @return true if this tongue is active, false otherwise
   */
  public boolean isActive() {
    return this.isActive;
  }

  /**
   * Makes this tongue active.
   */
  public void activate() {
    this.isActive = true;
  }

  /**
   * Makes this tongue inactive.
   */
  public void deactivate() {
    this.isActive = false;
  }

  /**
   * Draws this tongue to the screen, ONLY if it is active.
   */
  public void draw() {
    if (isActive) {
      processing.stroke(255, 179, 217); // change the color to pink
      processing.strokeWeight(10); // make the line thicker
      processing.line(startPoint[0], startPoint[1], endPoint[0], endPoint[1]); // draw the line
      // change color back to black, otherwise other things we draw after this will be pink
      processing.stroke(0, 0, 0);
      processing.strokeWeight(1); // reset line thickness
    }
  }

  /**
   * Determines whether or not the end of the tongue reaches the top of the screen.
   * 
   * @return true if the endpoint of the tongue is at the top of the screen, false otherwise
   */
  public boolean hitScreenBoundary() {
    return this.endPoint[1] <= 0;
  }

  /**
   * Accessor for the Hitbox of this tongue.
   * 
   * @return the Hitbox that this tongue uses.
   */
  public Hitbox getHitbox() {
    return this.hitbox;
  }

  /**
   * Resets the tongue to its default state by moving everything back to the starting point.
   */
  public void reset() {
    // endpoint is now the same as startpoint
    this.endPoint[0] = this.startPoint[0];
    this.endPoint[1] = this.startPoint[1];
    this.hitbox.setPosition(this.startPoint[0], this.startPoint[1]); // move the hitbox too
  }
}
