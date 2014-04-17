import java.util.*;
import javalib.worldimages.*;

/**
 * 
 * @author Alex Narayanan
 * @author Rui Zheng
 * @since 4/2/2014
 * 
 * To represent a Maze. A maze contains all the cells within it, all 
 * possible edges between cells, a searchPath of all cells traversed while 
 * solving the maze, and a Minimal Spanning Tree of all the cells in the maze. 
 * 
 */
public class Maze {
    int size;
    ArrayList<Posn> cells = new ArrayList<Posn>();
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Posn> searchPath = new ArrayList<Posn>();
    ArrayList<Edge> mst;
    Maze(int size) {
        this.size = size;
        this.generateCells(); 
        this.generateEdges();
        this.generateMaze();
        this.searchPath.add(new Posn(1, 1));
    }
    
    /**
     * Generate the 100 cells for the maze and place them in the HashMap cells
     * Cells are represented by their coordinates (Posns)
     */
    void generateCells() {
        for (int y = 1; y <= this.size; y = y + 1) {
            for (int x = 1; x <= this.size; x = x + 1) {
                this.cells.add(new Posn(x, y));
            }
        }
    }
    
    /**
     * Generate all possible edges in this maze. All cells get connected to 
     * their surrounding cells on the maze grid. 
     */
    void generateEdges() {
        // Generate all vertical edges in the maze
        int index = 0;
        while (index < this.cells.size() - this.size) {
            edges.add(new Edge(this.cells.get(index),
                    this.cells.get(index + this.size)));
            index = index + 1;
        }
        // Generate all horizontal edges in the maze
        for (int ind = 0; ind < this.cells.size(); ind = ind + 1) {
            if (this.cells.get(ind).x % this.size != 0) {
                edges.add(new Edge(this.cells.get(ind),
                        this.cells.get(ind + 1)));
            }
        }
    }
    
    /**
     * Generate a minimal spanning tree of all the cells in the grid
     * using Kruskal's Algorithm. 
     */
    void generateMaze() {
        this.mst = new KruskalAlgorithm(cells, edges).kruskalAlg();
    }
}

/**
 * To represent an edge of a graph
 */
class Edge {
    Posn from;
    Posn to;
    Edge(Posn from, Posn to) {
        this.from = from;
        this.to = to;
    }
    
    /**
     * Does this edge contain the given Posn
     * @param p Posn
     * @return boolean 
     */
    public boolean contains(Posn p) {
        return ((this.from.x == p.x) && (this.from.y == p.y)) ||
                ((this.to.x == p.x) && (this.to.y == p.y));
    }
    
    /**
     * Returns any Posn that is in the edge but not already in the given tree
     * @param lst, tree
     * @return Posn in this edge that is not in the tree
     */
    public Posn getPosn(ArrayList<Posn> lst) {
        Posn result = this.from;
        for (Posn p : lst) {
            if (p.x == this.from.x && p.y == this.from.y) {
                result = this.to;
            }
        }
        return result;
    }
}