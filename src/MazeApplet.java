import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** MazeApplet class that extends Applet. Contains all the information
 * necessary to run the applet. Utilizes the Runnable and KeyListener 
 * interfaces. 
 * @author Alex Narayanan
 * @since 4/26/14
 */
@SuppressWarnings("serial")
public class MazeApplet extends Applet implements Runnable, KeyListener {
    
    /** width of the applet */
    private int width = 600;
    /** height of the applet */
    private int height = 600;
    /** Thread that keeps the applet running */
    private Thread t = null;
    /** Should the applet keep running */
    private boolean keepGoing; 
    /** The maze object */
    private Maze maze = new Maze(20);
    /** The search object */
    private ASearch search;
    /** Whether the MST is visible */
    private boolean showTree = false;
    /** State of the search: 0 for nothing, 1 for BFS, 2 for DFS */
    private int searchState = 0;
    /** The width of a cell in the applet */
    private int cellWidth = this.width / this.maze.size;
    /** The back buffer */
    private Graphics bufferGraphics;
    /** The image object for the buffer */
    private Image buffer;     
    
    /** Initialize fields when applet is first created */
    public void init() {
        this.setBackground(Color.white);
        addKeyListener(this);
        buffer = createImage(this.width, this.height);
        bufferGraphics = buffer.getGraphics();
    }
    
    /** Stop running when applet is closed */
    public void destroy() { }
    
    /** Executed after the applet is created; and also whenever
        the browser returns to the page containing the applet.*/
    public void start() {
       t = new Thread(this);
       this.keepGoing = true;
       t.start();
    }
    
    /** Stop the execution of the applet. */
    public void stop() {
        if (t != null) {
            t = null;
        }
        this.keepGoing = false;
    }
    
    /** run the Thread */
    public void run() {
        while (keepGoing) {
            if (this.searchState == 2) {
                DFS dfs = (DFS) this.search;
                this.maze.searchPath = dfs.search(this.maze.searchPath,
                        this.maze.size);
            } 
            else if (this.searchState == 1) {
                BFS bfs = (BFS) this.search;
                this.maze.searchPath = bfs.search(this.maze.searchPath,
                        this.maze.size);
            }
            repaint();
            try {
                Thread.sleep(30);
            } 
            catch (InterruptedException ie) {                
            }
        }
    }
    
    /** Respond to key events */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // If n key is pressed generate new maze and start from scratch
        if (code == KeyEvent.VK_N) {
            this.searchState = 0;
            this.maze = new Maze(this.maze.size);
        // If up is pressed make a new larger maze
        } else if (code == KeyEvent.VK_UP) {
            this.searchState = 0;
            this.maze = new Maze(this.maze.size + 10);
            this.cellWidth = this.width / this.maze.size;
        // If down is pressed make a smaller maze
        } else if (code == KeyEvent.VK_DOWN) {
            this.searchState = 0;
            this.maze = new Maze(this.maze.size - 10);
            this.cellWidth = this.width / this.maze.size;
        // If b key is pressed run a new breadth-first search
        } else if (code == KeyEvent.VK_B) {
            this.searchState = 1;
            this.maze.searchPath.clear();
            this.maze.searchPath.add(new Point(1, 1));
            this.search = new BFS(this.maze.mst);
        // If d key is pressed run a new depth-first search
        } else if (code == KeyEvent.VK_D) {
            this.searchState = 2;
            this.maze.searchPath.clear();
            this.maze.searchPath.add(new Point(1, 1));
            this.search = new DFS(this.maze.mst);
        // If space is pressed show the spannig tree
        } else if (code == KeyEvent.VK_SPACE) {
            this.showTree = !this.showTree;
        }
        repaint();
        e.consume();
    }
    
    /** Included as part of keyListener api */
    public void keyTyped(KeyEvent e) {}
    
    /** Included as part of keyListener api */
    public void keyReleased(KeyEvent e) { }
    
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
    
    /** Paint the current image on the applet canvas */
    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, this.width, this.height);
        this.drawGrid(bufferGraphics);
        this.drawMaze(bufferGraphics);
        this.drawTree(bufferGraphics);
        this.drawPath(bufferGraphics);
        this.drawStartAndEnd(bufferGraphics);
        bufferGraphics.setColor(Color.black);
        bufferGraphics.drawRect(0, 0, this.width - 1, this.height - 1);
        g.drawImage(buffer, 0, 0, this);
    }
    
    /** Update the applet canvas on every clock tick */
    public void update(Graphics g) {
        paint(g);
    }
    
    /** Draw the square grid (before maze is generated */
    public void drawGrid(Graphics g) {
        g.setColor(Color.black);
        for (Point p: this.maze.cells) {
            p = this.imagify(p);
            g.drawRect(p.x, p.y, this.cellWidth, this.cellWidth);
        }
    }
    
    /** Draw the maze by erasing lines on the grid */
    public void drawMaze(Graphics g) {
        g.setColor(Color.white);
        for (Edge edge : this.maze.mst) {
            Point p = this.drawMazeHelper(edge.to, edge.from);
            g.fillRect(p.x, p.y, this.cellWidth - 1, this.cellWidth - 1);
        }
    }
    
    /** Create visual representation of current search path */    
    public void drawPath(Graphics g) {
        g.setColor(new Color(100, 160, 230, 130));
        for (Point p : this.maze.searchPath) {
            p = this.imagify(p);
            g.fillRect(p.x, p.y, this.cellWidth, this.cellWidth);
        }
    }
    
    /** Fill in the start and end rectangles */
    public void drawStartAndEnd(Graphics g) {
        g.setColor(new Color(0, 204, 102, 255));
        Point p = this.imagify(new Point(1, 1));
        g.fillRect(p.x, p.y, this.cellWidth, this.cellWidth);
        g.setColor(new Color(137, 62, 137, 255));
        p = this.imagify(this.maze.cells.get(this.maze.cells.size() - 1));
        g.fillRect(p.x, p.y, this.cellWidth, this.cellWidth);       
    }
    
    /** Draw the minimal spanning tree **/
    public void drawTree(Graphics g) {
        g.setColor(Color.red);
        int halfWidth = cellWidth / 2;
        if (showTree) {
            for (Edge e : this.maze.mst) {
                g.drawLine(e.from.x * cellWidth - halfWidth,
                        e.from.y * cellWidth - halfWidth,
                        e.to.x * cellWidth - halfWidth,
                        e.to.y * cellWidth - halfWidth);
            }
        }
    }
    
    /** 
     * Translate the points that represent cells so that they can be 
     * drawn using the drawRect method
     * @return traslated point
     */
    public Point imagify(Point p) {
        return new Point((p.x - 1) * this.cellWidth,
                (p.y - 1) * this.cellWidth);
    }
    
    /**
     * Takes two given points and returns a new point at the top left corner
     * of where the corresponding square needs to b on the applet canvas
     * @return a new translated points
     */
    public Point drawMazeHelper(Point a, Point b) {
        a = this.imagify(a);
        b = this.imagify(b);
        return new Point(((a.x + b.x) / 2) + 1,
                ((a.y + b.y) / 2) + 1);
    }
}
