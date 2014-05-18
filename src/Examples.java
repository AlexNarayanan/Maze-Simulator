import java.awt.*;
import java.util.*;

import tester.Tester;

/** 
 * 
 * @author Alex Narayanan
 * @author Rui Zheng
 * @since 4/4/2014
 *
 * Examples and Tests for the various data structures
 * and algorithms associated with maze generation and solving
 */
class Examples {
    
    Maze maze1 = new Maze(1);
    Maze maze2 = new Maze(2);
    Maze maze3 = new Maze(3);
    Maze maze8 = new Maze(8);
    Maze maze10 = new Maze(10);
    MazeApplet mw = new MazeApplet();
    DFS dfs = new DFS(maze2.mst);
    BFS bfs = new BFS(maze2.mst);
    
    /**Test the Cell Generation for the maze class.
     * @param t Tester*/
    void testCellGeneratoin(Tester t) {
        t.checkExpect(maze10.cells.size(), 100);       
        t.checkExpect(maze10.cells.get(0), new Point(1, 1));
        t.checkExpect(maze10.cells.get(1), new Point(2, 1));
        t.checkExpect(maze10.cells.get(10), new Point(1, 2));
        t.checkExpect(maze10.cells.get(26), new Point(7, 3));
        t.checkExpect(maze8.cells.size(), 64);
        t.checkExpect(maze8.edges.size(), 112);       
        t.checkExpect(maze10.edges.size(), 180);  
    }
    
    /**Test the Edge Generation for the maze class.
     * @param t Tester
     */
    void testEdgeGeneration(Tester t) {
        t.checkExpect(maze10.edges.size(), 180);
        t.checkExpect(maze10.edges.get(0).from, new Point(1, 1));
        t.checkExpect(maze10.edges.get(0).to, new Point(1, 2));
        t.checkExpect(maze10.edges.get(9).from, new Point(10, 1));
        t.checkExpect(maze10.edges.get(9).to, new Point(10, 2));
        t.checkExpect(maze10.edges.get(89).from, new Point(10, 9));
        t.checkExpect(maze10.edges.get(89).to, new Point(10, 10));
        t.checkExpect(maze10.edges.get(90).from, new Point(1, 1));
        t.checkExpect(maze10.edges.get(90).to, new Point(2, 1));
        t.checkExpect(maze10.edges.get(179).from, new Point(9, 10));
        t.checkExpect(maze10.edges.get(179).to, new Point(10, 10));
        t.checkExpect(maze8.edges.get(2).from, new Point(3, 1));
        t.checkExpect(maze8.edges.get(2).to, new Point(3, 2));
        t.checkExpect(maze8.edges.get(10).from, new Point(3, 2));
        t.checkExpect(maze8.edges.get(10).to, new Point(3, 3));
        t.checkExpect(maze8.edges.get(56).from, new Point(1, 1));
        t.checkExpect(maze8.edges.get(56).to, new Point(2, 1));
    }
    
    /**
     * Test that kruskal's algorithm generates a correct maze for the class Maze
     * @param t Tester
     */
    void testMazeGeneration(Tester t) {
        t.checkExpect(maze2.mst.size(), 3);
        t.checkExpect(maze3.mst.size(), 8);
        t.checkExpect(maze10.mst.size(), 99);
    }
    
    /** 
     * Test the method contains in the Edge class
     * @param t Tester
     */
    void testContains(Tester t) {
        Edge e1 = new Edge(new Point(1, 1), new Point(1, 2));
        t.checkExpect(e1.contains(new Point(1, 1)), true);
        t.checkExpect(e1.contains(new Point(1, 2)), true);
        t.checkExpect(e1.contains(new Point(1, 3)), false);
    }
    
    /**
     * Test the method getPosn in the Edge class
     * @param t Tester
     */
    void testGetPosn(Tester t) {
        Edge e1 = new Edge(new Point(1, 1), new Point(1, 2));
        ArrayList<Point> lst = new ArrayList<Point>();
        lst.add(new Point(1, 1));
        t.checkExpect(e1.getPosn(lst), new Point(1, 2));
    }
    
    /**
     * Test the nRep generation in the class for the Kruskal Algorithm class
     * @param t Tester
     */
    void testnRepGeneration(Tester t) {
        MazeGenerator ka1 = new MazeGenerator(maze2.cells, maze2.edges);
        MazeGenerator ka2 = new MazeGenerator(maze10.cells, maze10.edges);
        t.checkExpect(ka1.nReps.size(), 4);
        t.checkExpect(ka2.nReps.size(), 100);
        t.checkExpect(ka1.findRep(0), 0);
        t.checkExpect(ka2.findRep(70), 70);
        t.checkExpect(ka2.findRep(99), 99);
    }
    
    /**
     * Test the method findRepresentative in the Kruskal Algorithm class
     * @param t Tester
     */
    void testFindRepresentative(Tester t) {
        MazeGenerator ka1 = new MazeGenerator(maze2.cells, maze2.edges);
        ka1.nReps.set(0, 2);
        ka1.nReps.set(1, 0);
        t.checkExpect(ka1.findRep(0), 2);
        t.checkExpect(ka1.findRep(1), 2);
        t.checkExpect(ka1.findRep(3), 3);
    }
    
    /**
     * Test the method union in the Kruskal Algorithm class
     * @param t Tester
     */
    void testUnion(Tester t) {
        MazeGenerator ka1 = new MazeGenerator(maze2.cells, maze2.edges);
        MazeGenerator ka2 = new MazeGenerator(maze3.cells, maze3.edges);
        ka1.union(0, 2);
        ka1.union(1,  0);
        ka1.union(1,  2);
        ka2.union(4, 5);
        ka2.union(0, 3);
        ka2.union(0, 1);
        ka2.union(6, 7);
        ka2.union(3, 6);
        t.checkExpect(ka1.findRep(0), 2);
        t.checkExpect(ka1.findRep(1), 2);
        t.checkExpect(ka1.findRep(3), 3);
        t.checkExpect(ka2.findRep(0), 7);
        t.checkExpect(ka2.findRep(3), 7);
        t.checkExpect(ka2.findRep(1), 7);
        t.checkExpect(ka2.findRep(4), 5);
    }
    
    /**
     * Test the posnEquals method in the ASearch class
     * @param t Tester
     */
    void testPosnEquals(Tester t) {
        t.checkExpect(dfs.posnEquals(new Point(1, 1), new Point(1, 1)), true);
        t.checkExpect(dfs.posnEquals(new Point(1, 1), new Point(1, 2)), false);
        t.checkExpect(bfs.posnEquals(new Point(1, 1), new Point(1, 1)), true);
        t.checkExpect(bfs.posnEquals(new Point(1, 1), new Point(1, 2)), false);
    }
    
    /**
     * Test the addAllNeighbors method in the ASearch class
     * @param t Tester
     */
    void testAddAllNeighbors(Tester t) {
        Edge e1 = new Edge(new Point(1, 1), new Point(1, 2));
        Edge e2 = new Edge(new Point(2, 2), new Point(2, 1));
        Edge e3 = new Edge(new Point(1, 1), new Point(2, 1));
        dfs.tree.clear();
        dfs.toDo.clear();
        dfs.tree.add(e1); 
        dfs.tree.add(e2); 
        dfs.tree.add(e3);
        dfs.addAllNeighbors(new Point(1, 1));
        t.checkExpect(dfs.toDo.size(), 2);
        t.checkExpect(dfs.toDo.get(0), e1);
        t.checkExpect(dfs.toDo.get(1), e3);
        dfs.toDo.clear();
        dfs.tree.remove(e1);
        dfs.addAllNeighbors(new Point(2, 1));
        t.checkExpect(dfs.toDo.size(), 2);
        t.checkExpect(dfs.toDo.get(0), e2);
        t.checkExpect(dfs.toDo.get(1), e3);
        dfs.toDo.clear();
    }
     
    
    /**
     * Test the getIndex method in the class ASearch
     */
    void testGetIndex(Tester t) {
        dfs.addAllNeighbors(new Point(1, 1));
        t.checkExpect(dfs.getIndex(true), 0);
        //t.checkExpect(dfs.getIndex(true), dfs.toDo.size() - 1);
        bfs.addAllNeighbors(new Point(1, 1));
        t.checkExpect(bfs.getIndex(true), 0);
        //t.checkExpect(bfs.getIndex(true), dfs.toDo.size() - 1);
    }
    
    /**
     * Test the DFS method in the DFS class
     * @param Tester t
     */
    void testDFS(Tester t) {      
        dfs.toDo.clear();
        Edge e1 = new Edge(new Point(1, 2), new Point(1, 1));
        Edge e2 = new Edge(new Point(2, 1), new Point(2, 2));
        Edge e3 = new Edge(new Point(1, 1), new Point(2, 1));
        Edge e4 = new Edge(new Point(2, 1), new Point(1, 2));
        Edge e5 = new Edge(new Point(1, 2), new Point(2, 2));
        ArrayList<Edge> tree = 
                new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4, e5));
        ArrayList<Point> visited = new ArrayList<Point>();
        visited.add(new Point(1, 1));
        dfs.tree = tree;
        t.checkExpect(visited.size(), 1);
        
        visited = dfs.search(visited, 2);
        t.checkExpect(visited.size(), 2);
        t.checkExpect(dfs.toDo.size(), 1);
        t.checkExpect(dfs.toDo.get(0), e1);
        t.checkExpect(dfs.tree.size(), 4);
        
        visited = dfs.search(visited, 2);    
        t.checkExpect(dfs.toDo.size(), 2);
        t.checkExpect(visited.size(), 3);
        t.checkExpect(dfs.tree.size(), 3);
        
        visited = dfs.search(visited, 2);
        t.checkExpect(visited.size(), 4);
        t.checkExpect(dfs.toDo.size(), 3);
    }
    
    /**
     * Test the BFS method in the BFS class
     * @param Tester t
     */
    void testBFS(Tester t) {      
        bfs.toDo.clear();
        Edge e1 = new Edge(new Point(1, 2), new Point(1, 1));
        Edge e2 = new Edge(new Point(2, 1), new Point(2, 2));
        Edge e3 = new Edge(new Point(1, 1), new Point(2, 1));
        Edge e4 = new Edge(new Point(2, 1), new Point(1, 2));
        Edge e5 = new Edge(new Point(1, 2), new Point(2, 2));
        ArrayList<Edge> tree = 
                new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4, e5));
        ArrayList<Point> visited = new ArrayList<Point>();
        visited.add(new Point(1, 1));
        bfs.tree = tree;
        t.checkExpect(visited.size(), 1);
        
        visited = bfs.search(visited, 2);
        t.checkExpect(visited.size(), 2);
        t.checkExpect(bfs.toDo.size(), 1);
        t.checkExpect(bfs.toDo.get(0), e3);
        t.checkExpect(bfs.tree.size(), 4);
        
        visited = bfs.search(visited, 2);    
        t.checkExpect(bfs.toDo.size(), 2);
        t.checkExpect(visited.size(), 3);
        t.checkExpect(bfs.tree.size(), 3);
        
        visited = bfs.search(visited, 2);
        t.checkExpect(visited.size(), 4);
        t.checkExpect(bfs.toDo.size(), 3);
    }
         
    /**
     * Test the method imagify in the MazeApplet class
     * @param t Tester
     */   
    void testImagify(Tester t) {       
        t.checkExpect(mw.imagify(new Point(1, 1)), new Point(0, 0));
        t.checkExpect(mw.imagify(new Point(2, 2)), new Point(30, 30));       
    }
    
    /**
     * Test the method getToDo in the MazeApplet class
     * @param t Tester   
     */
    /*void testGetToDo(Tester t) {
        mw.runDFS = true;
        mw.runBFS = false;
        t.checkExpect(mw.getToDo(), mw.dfs.toDo);
    }*/
    
    /**
     * Test the method drawMazeHelper in the MazeApplet class
     */
    void testDrawMazeHelper(Tester t) {
        t.checkExpect(mw.drawMazeHelper(new Point(1, 1),  new Point(2, 1)),
                new Point(16, 1));
        t.checkExpect(mw.drawMazeHelper(new Point(1, 1),  new Point(1, 2)),
                new Point(1, 16));
    }

    /**
     * Test the functionality of the game by running bigBang (no real other
     * tests are possible)
     */
    void testGame(Tester t) {
        // UNCOMMENT TO RUN THE GAME
        // MazeGame mg1 = new MazeGame(new Maze(20));
        // mg1.bigBang(720, 720, .05);
    }
}