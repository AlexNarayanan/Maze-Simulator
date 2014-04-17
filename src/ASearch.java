import java.util.*;

import javalib.worldimages.*;

public abstract class ASearch {
    ArrayList<Edge> toDo;
    ArrayList<Edge> tree;
    public ASearch(ArrayList<Edge> tree) {
        this.toDo = new ArrayList<Edge>();
        this.tree = new ArrayList<Edge>(tree);
    }
    
    /**
     * Run a search algorithm through a tree. The algorithm is determined by
     * the order in which items are taken out of the toDo list. The toDo list 
     * is treated as a stack for depth first search, and as a queue for 
     * breadth first search. 
     * 
     * @param visited, The current search path
     * @param size, size of the grid
     * @param frontOrback, signal to take items off front or end of toDo list
     *          for DFS, input false to turn toDo list into a stack
     *          for BFS, input true to turn toDo list into a queue
     * @return an updated search path 
     */
    public ArrayList<Posn> abstractSearch(ArrayList<Posn> visited, 
            int size, boolean frontOrBack) {
        // Get the most recently searched node in the visited list
        Posn head = visited.get(visited.size() - 1);
        // If we haven't reached end of graph, continue searching
        if (!(this.posnEquals(head, new Posn(size, size)))) {
            // Add all edges that contain neighbors of the head to toDo
            this.addAllNeighbors(head);
            // Get the Edge at the either the front or back of toDo list
            Edge firstEdge = this.toDo.get(this.getIndex(frontOrBack));
            // Add the new Posn from firstEdge to the visited list
            visited.add(firstEdge.getPosn(visited));
            // Remove firstEdge from the tree and toDo
            this.tree.remove(firstEdge);
            this.toDo.remove(firstEdge);
            return visited;
        } 
        else {
            return visited;
        }
    }
    
    /**
     * Get the correct index from the toDo list that corresponds to the current 
     * search algorithm being used. 
     * @param boolean b
     * If given true, return the front, turning the toDo list into a queue
     * If given false, return the end, turning the toDolist into a stack
     * @return either the index of the front or end of the toDo list
     */
    public int getIndex(boolean b) {
        if (b) {
            return 0;
        }
        else {
            return this.toDo.size() - 1;
        }
    }
    
    /**
     * Add all visitable neighbors of the given current Posn 
     * from the tree to the toDo list
     * @param current, the current posn being searched
     */
    public void addAllNeighbors(Posn current) {
        for (Edge e : this.tree) {
            if (e.contains(current)) {
                this.toDo.add(e);
            }
        }
    }

    /**
     * Check the two given Posns for equality
     * Needed because the built in Posn equals method does not work
     * @param p1 Posn1
     * @param p2 Posn2
     * @return true if p1 is the same as p2
     */
    public boolean posnEquals(Posn p1, Posn p2) {
        return p1.x == p2.x &&
                p1.y == p2.y;
    }
}