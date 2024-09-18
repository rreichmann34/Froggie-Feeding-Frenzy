/**
 * Custom interface for objects that can change their x,y coordinates on a game screen.
 * 
 * @author Michelle
 *
 */
public interface Moveable {

  /**
   * Moves the object by calculating and changing the x,y-coordinates.
   */
  public void move();

  /**
   * Determines if the object should or should not move (change x,y-coordinates)
   * 
   * @return true if the object should move, false otherwise
   */
  public boolean shouldMove();
}
