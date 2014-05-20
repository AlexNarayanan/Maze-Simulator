import java.util.*;
import java.awt.*;

/**
 * 
 * @author Alex Narayanan
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
     * Perform breadth first search on the tree. Tells the abstract search
     * function to pop items from the front of the toDo list. 
     * @param visited, the current search path
     * @param size, the size of the list.
     * @return an updated search path
     */
    public ArrayList<Point> search(ArrayList<Point> visited, int size) {
        return this.abstractSearch(visited, size, false);
    }
}
