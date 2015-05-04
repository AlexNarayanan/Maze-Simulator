import java.util.*;
import java.awt.*;

/**
 * 
 * @author Alex Narayanan
 * @since 4/6/14
 *
 * To generate a minimal spanning of a graph of cells using Kruskal's
 * Algorithm and the Union/Find Algorithm to detect cycles. 
 * 
 */
public class MazeGenerator {
    
    ArrayList<Point> cells;
    ArrayList<Edge> edges;
    ArrayList<Integer> nReps;
    HashMap<Point, Point> unionMap;
    MazeGenerator(ArrayList<Point> cells, ArrayList<Edge> edges) {
        this.cells = cells;
        this.edges = new ArrayList<Edge>(edges);
        this.unionMap = new HashMap<Point, Point>();
        // Initialize the mapping
        for (Point p : this.cells) {
            unionMap.put(p, p);
        }
    }
    
    /**
     * Run Kruskal's Algorithm and the Union Find Algorithm on an
     * ArrayList of cells, given all the possible edges between those cells. 
     * @return a minimal spanning tree of the maze grid
     */
    public ArrayList<Edge> kruskalAlg() {
        // Declare minimum spanning tree
        ArrayList<Edge> mst = new ArrayList<Edge>();
        Random rand = new Random();
        // Keep going until MST is full
        while (mst.size() < this.cells.size() - 1) {
            //grab a random edge
            Edge randEdge = this.edges.remove(rand.nextInt(this.edges.size()));
            //If the parents are different, update the unionMap and add to mst
            Point p1 = randEdge.from;
            Point p2 = randEdge.to;
            Point grandparent1 = findGrandparent(p1);
            if (!grandparent1.equals(findGrandparent(p2))) {
                replaceAllParents(p2, grandparent1);
                mst.add(randEdge);
            }
        }        
        return mst;
    }
    
    /**
     * Finds the grandparent of the chain starting at the given point
     * @param p The point we want to find the grandparent for
     * @return the grandParent connection of the given point
     */
    public Point findGrandparent(Point p) {
        Point currentPoint = unionMap.get(p);
        Point currentParent = unionMap.get(currentPoint);
        while (!currentPoint.equals(currentParent)) {
            currentPoint = currentParent;
            currentParent = unionMap.get(currentPoint);
        }
        
        return currentParent;
    }
    
    /**
     * Replaces all parents in the chain of connected points to the new parent
     * @param p starting point in the chain
     * @param newParent replaces all parents in the chain with this Point
     */
    public void replaceAllParents(Point p, Point newParent) {
        Point currentPoint = p;
        Point grandParent = findGrandparent(p);
        Point currentParent = unionMap.get(currentPoint); 
        while(!currentPoint.equals(grandParent)) {
            this.unionMap.replace(currentPoint, newParent);
            currentPoint = currentParent;
            currentParent = unionMap.get(currentPoint);
        }
        this.unionMap.replace(currentPoint, newParent);
    }
}
