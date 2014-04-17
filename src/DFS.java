import java.util.*;

import javalib.worldimages.*;

/**
 * 
 * @author Alex Narayanan
 * @author Rui Zheng
 * @since 4/7/14
 * To represent a depth first search on a minimal spanning tree, 
 * represented as an ArrayList of Edges
 *
 */
public class DFS extends ASearch {
    DFS(ArrayList<Edge> tree) {
        super(tree);
    }
    
    /**
     * Perform depth first search on the tree. Tells the abstract search
     * function to pop items from the end of the toDo list. 
     * @param visited, the current search path
     * @param size, the size of the list.
     * @return an updated search path
     */
    public ArrayList<Posn> search(ArrayList<Posn> visited, int size) {
        return this.abstractSearch(visited, size, false);
    }   
}
