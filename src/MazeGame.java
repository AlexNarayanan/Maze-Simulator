import java.awt.Color;
import java.util.*;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

/**
 * Alex Narayanan 4l3x Rui Zheng rui14 Final Project
 * 
 * @author Alex Narayanan
 * @author RuiZheng
 * @since 4/6/14
 * 
 *        To represent a maze game where mazes can be auto generated at the push
 *        of a button. At the push of another button, the maze can be auto
 *        solved using one of two searching algorithms.
 * 
 */
public class MazeGame extends World {
    int width = 720;
    int height = 720;
    Maze maze;
    DFS dfs;
    BFS bfs;
    boolean showTree;
    boolean runDFS;
    boolean runBFS;

    public MazeGame(Maze maze) {
        super();
        this.maze = maze;
        this.dfs = new DFS(this.maze.mst);
        this.bfs = new BFS(this.maze.mst);
        this.showTree = false;
        this.runDFS = false;
        this.runBFS = false;
    }

    /**
     * ========================================================================
      *     _______  _______ _________ _        
      *    (       )(  ___  )\__   __/( (    /|
      *    | () () || (   ) |   ) (   |  \  ( |  
      *    | || || || (___) |   | |   |   \ | | 
      *    | |(_)| ||  ___  |   | |   | (\ \) |   
      *    | |   | || (   ) |   | |   | | \   |  
      *    | )   ( || )   ( |___) (___| )  \  |    
      *    |/     \||/     \|\_______/|/    )_)
     * 
     * Main method that runs the program
     * ========================================================================
     */
    public static void main(String[] argv) {
        MazeGame mg = new MazeGame(new Maze(20));
        mg.bigBang(mg.width, mg.height, .05);
    }

    /**
     * ON each clock tick, increment the currently selected search algorithm,
     * either DFS or BFS.
     * 
     * @return the updated world
     */
    public World onTick() {
        if (this.runDFS) {
            this.maze.searchPath = this.dfs.search(this.maze.searchPath,
                    this.maze.size);
            return this;
        } 
        else if (this.runBFS) {
            this.maze.searchPath = this.bfs.search(this.maze.searchPath,
                    this.maze.size);
            return this;
        } 
        else {
            return this;
        }
    }

    /**
     * The OnKey handler method for this world. Respond to different key events
     * with the appropriate action
     * 
     * @param the
     *            key that is pressed represented as a String
     * @return the updated world
     */
    public World onKeyEvent(String ke) {
        // If the key "n" is pressed, create a new maze
        if (ke.equals("n")) {
            return new MazeGame(new Maze(this.maze.size));
        }
        // If the key "down" is pressed, create a new smaller maze
        else if (ke.equals("down") && this.maze.size > 5) {
            return new MazeGame(new Maze(this.maze.size - 5));
        }
        // If the key "up" is pressed, create a new larger maze
        else if (ke.equals("up") && this.maze.size < 35) {
            return new MazeGame(new Maze(this.maze.size + 5));
        }
        // If the key " " is pressed, show the MST of the current maze
        else if (ke.equals(" ")) {
            this.showTree = !this.showTree;
            return this;
        }
        // If the key "d" is pressed, run a new Depth First Search
        else if (ke.equals("d")) {
            this.runDFS = true;
            this.runBFS = false;
            this.maze.searchPath.clear();
            this.maze.searchPath.add(new Posn(1, 1));
            this.dfs = new DFS(this.maze.mst);
            this.bfs.toDo.clear();
            return this;
        }
        // If the key "b" is pressed, run a new Breadth First Search
        else if (ke.equals("b")) {
            this.runDFS = false;
            this.runBFS = true;
            this.maze.searchPath.clear();
            this.maze.searchPath.add(new Posn(1, 1));
            this.bfs = new BFS(this.maze.mst);
            this.dfs.toDo.clear();
            return this;
        }
        // else do nothing
        else {
            return this;
        }
    }

    /**
     * Draws the WorldImage. If showTree is true, show the minimal spanning tree
     * overlayed over the maze.
     * 
     * @return the image to be drawn on the canvas
     */
    public WorldImage makeImage() {
        if (this.showTree) {
            return this.drawPath(this.drawToDo(this.drawMST()));
        } 
        else {
            return this.drawPath(this.drawToDo(this.drawMaze()));
        }
    }

    /*
     * ========================================================================
      *  ___                                      
      * |_ _|_ __ ___   __ _  __ _  ___  ___      
      *  | || '_ ` _ \ / _` |/ _` |/ _ \/ __|      
      *  | || | | | | | (_| | (_| |  __/\__ \      
      * |___|_| |_| |_|\__,_|\__, |\___||___/      
      *                      |___/      
     * Auxillery drawing functions below
     * ========================================================================
     */

    // The background image for the maze program
    public WorldImage background = new RectangleImage(new Posn(400, 400),
            this.width, this.height, new Color(100, 100, 100, 0));

    /**
     * Iterate through the searchPath and draw it on top of the given Image
     * 
     * @return Visual Representation of search path
     */
    public WorldImage drawPath(WorldImage bg) {
        WorldImage result = bg;
        int cellWidth = this.width / this.maze.size;
        for (Posn p : this.maze.searchPath) {
            result = new OverlayImages(result, new RectangleImage(
                    this.imagify(p), cellWidth, cellWidth, new Color(100, 160,
                            230, 150)));
        }
        return result;
    }

    /**
     * Iterate through the toDo list and draw it on the canvas
     * 
     * @return Visual representation of toDo list
     */
    public WorldImage drawToDo(WorldImage bg) {
        WorldImage result = bg;
        int cellWidth = this.width / this.maze.size;
        for (Edge e : this.getToDo()) {
            result = new OverlayImages(result, new RectangleImage(
                    this.imagify(e.getPosn(this.maze.searchPath)), cellWidth,
                    cellWidth, new Color(100, 160, 230, 225)));
        }
        return result;
    }

    /**
     * Iterate through the cells and draw a square border around each
     * 
     * @return The maze grid
     */
    public WorldImage drawGrid() {
        WorldImage result = background;
        int cellWidth = this.width / this.maze.size;
        for (Posn p : this.maze.cells) {
            result = new OverlayImages(new FrameImage(this.imagify(p),
                    cellWidth, cellWidth, new Black()), result);
        }
        return result;
    }

    /**
     * Erase each of the lines that the minimal spanning tree passes thru
     * 
     * @return Graphical representation of the maze
     */
    public WorldImage drawMaze() {
        WorldImage result = this.drawGrid();
        for (Edge edge : this.maze.mst) {

            Posn to = edge.to;
            Posn from = edge.from;
            int cW = this.width / this.maze.size; // CellWidth for resizing

            result = new OverlayImages(result, new RectangleImage(new Posn(
                    ((to.x + from.x) * cW / 2) - (cW / 2), ((to.y + from.y)
                            * cW / 2)
                            - (cW / 2)), cW - 1, cW - 1, new White()));
        }
        return result;
    }

    /**
     * Draw the minimal spanning tree on top of the maze grid
     * 
     * @return Image of the minimal spanning tree
     */
    public WorldImage drawMST() {
        WorldImage result = this.drawMaze();
        for (Edge edge : this.maze.mst) {

            Posn to = edge.to;
            Posn from = edge.from;

            result = new OverlayImages(result, new LineImage(this.imagify(to),
                    this.imagify(from), new Red()));
        }
        return result;
    }

    /**
     * Given a Posn that represents a cell in a grid, resize it so that it can
     * be properly drawn on a large canvas on a monitor
     * 
     * @param p
     *            , grid Posn
     * @return Posn appriopriate for drawing
     */
    public Posn imagify(Posn p) {
        int cellWidth = this.width / this.maze.size;
        return new Posn(p.x * cellWidth - (cellWidth / 2), p.y * cellWidth
                - (cellWidth / 2));
    }

    /**
     * Return the correct toDo list corresponding to which search algorithm is
     * currently running
     * 
     * @return a toDo list
     */
    public ArrayList<Edge> getToDo() {
        if (this.runDFS) {
            return this.dfs.toDo;
        } 
        else {
            return this.bfs.toDo;
        }
    }
}