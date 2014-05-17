import java.util.*;
import java.awt.*;

/**
 * 
 * @author Alex Narayanan
 * @author Rui Zheng
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
    MazeGenerator(ArrayList<Point> cells, ArrayList<Edge> edges) {
        this.cells = cells;
        this.edges = new ArrayList<Edge>(edges);
        this.nReps = this.buildnReps();
    }
    
    /**
     * Run Kruskal's Algorithm and the Union Find Algorithm on an
     * ArrayList of cells, given all the possible edges between those cells. 
     * @return a minimal spanning tree of the maze grid
     */
    public ArrayList<Edge> kruskalAlg() {

        ArrayList<Edge> mst = new ArrayList<Edge>();
        
        while (mst.size() < this.cells.size() - 1) {
            
            //Select a random edge from the list of edges
            Random rand = new Random();
            Edge randEdge = this.edges.get(rand.nextInt(this.edges.size()));

            // Get the indexes of the random edge
            int indexFrom = this.cells.indexOf(randEdge.from);
            int indexTo = this.cells.indexOf(randEdge.to);

            // If the representative of indexFrom and indexTo are
            // not equal, union them and add the edge to the spanning tree
            if (this.findRep(indexFrom) != this.findRep(indexTo)) {
                this.union(indexFrom, indexTo);
                mst.add(randEdge);
            }

            // Remove the added edge so it doesn't get picked again
            this.edges.remove(randEdge);
        }
        return mst;
    }
    
    /** 
     * Build an ArrayList that says which subset any given cell
     * is a representative of. Every cell starts off referring to itself. 
     * @return an ArrayList list of nReps
     */
    public ArrayList<Integer> buildnReps() {
        ArrayList<Integer> nReps = new ArrayList<Integer>();
        while (nReps.size() <= this.cells.size() - 1) {
            nReps.add(nReps.size());
        }
        return nReps;
    }
    
    /**
     * Produces the representative of the given cell with index n
     * @param n, the index of the cell we want to find an nRep for
     * @return the index of the parent representative of the subset of n
     */
    public int findRep(int n) {
        if (this.nReps.get(n) == n) {
            return n;
        }
        else {
            return this.findRep(this.nReps.get(n));
        }
    }
    
    /** 
     * Finds the representatives of the cells with indexes n1 and n2.
     * If they are the same, connecting them will result in a cycle.
     * If they are not the same, set the value of the n1Rep to n2.\
     * @param n1, the index of the first cell
     * @param n2, the index of the second cell
     */   
    public void union(int n1, int n2) {
        int n1Rep = this.findRep(n1);
        this.nReps.set(n1Rep, n2);
    }
}