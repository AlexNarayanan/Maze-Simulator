import javalib.appletworld.*;


public class MazeGameApplet extends WorldApplet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Produce a new World */
    public javalib.appletworld.World getNewWorld() {
        
        return new MazeGame(new Maze(20));
    }
    
    /** Set the size of this world */
    public void setWorldSize(){
      this.WIDTH = 600;
      this.HEIGHT = 600;
    }
    
    /** Optionally change the range of the slider
     * so that the highest tick rate is factor times as fast
     * as the default (here it is twice as fast)
     */
    public double setSpeedFactor(){
      return 10.0;
    }    
}