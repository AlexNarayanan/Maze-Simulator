import tester.*;

import java.awt.Color;
import java.util.*;

import javalib.*;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

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
    MazeGame mw = new MazeGame(maze10);
    DFS dfs = new DFS(maze2.mst);
    BFS bfs = new BFS(maze2.mst);
    
    /**Test the Cell Generation for the maze class.
     * @param t Tester*/
    void testCellGeneratoin(Tester t) {
        t.checkExpect(maze10.cells.size(), 100);       
        t.checkExpect(maze10.cells.get(0), new Posn(1, 1));
        t.checkExpect(maze10.cells.get(1), new Posn(2, 1));
        t.checkExpect(maze10.cells.get(10), new Posn(1, 2));
        t.checkExpect(maze10.cells.get(26), new Posn(7, 3));
        t.checkExpect(maze8.cells.size(), 64);
        t.checkExpect(maze8.edges.size(), 112);       
        t.checkExpect(maze10.edges.size(), 180);  
    }
    
    /**Test the Edge Generation for the maze class.
     * @param t Tester
     */
    void testEdgeGeneration(Tester t) {
        t.checkExpect(maze10.edges.size(), 180);
        t.checkExpect(maze10.edges.get(0).from, new Posn(1, 1));
        t.checkExpect(maze10.edges.get(0).to, new Posn(1, 2));
        t.checkExpect(maze10.edges.get(9).from, new Posn(10, 1));
        t.checkExpect(maze10.edges.get(9).to, new Posn(10, 2));
        t.checkExpect(maze10.edges.get(89).from, new Posn(10, 9));
        t.checkExpect(maze10.edges.get(89).to, new Posn(10, 10));
        t.checkExpect(maze10.edges.get(90).from, new Posn(1, 1));
        t.checkExpect(maze10.edges.get(90).to, new Posn(2, 1));
        t.checkExpect(maze10.edges.get(179).from, new Posn(9, 10));
        t.checkExpect(maze10.edges.get(179).to, new Posn(10, 10));
        t.checkExpect(maze8.edges.get(2).from, new Posn(3, 1));
        t.checkExpect(maze8.edges.get(2).to, new Posn(3, 2));
        t.checkExpect(maze8.edges.get(10).from, new Posn(3, 2));
        t.checkExpect(maze8.edges.get(10).to, new Posn(3, 3));
        t.checkExpect(maze8.edges.get(56).from, new Posn(1, 1));
        t.checkExpect(maze8.edges.get(56).to, new Posn(2, 1));
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
        Edge e1 = new Edge(new Posn(1, 1), new Posn(1, 2));
        t.checkExpect(e1.contains(new Posn(1, 1)), true);
        t.checkExpect(e1.contains(new Posn(1, 2)), true);
        t.checkExpect(e1.contains(new Posn(1, 3)), false);
    }
    
    /**
     * Test the method getPosn in the Edge class
     * @param t Tester
     */
    void testGetPosn(Tester t) {
        Edge e1 = new Edge(new Posn(1, 1), new Posn(1, 2));
        ArrayList<Posn> lst = new ArrayList<Posn>();
        lst.add(new Posn(1, 1));
        t.checkExpect(e1.getPosn(lst), new Posn(1, 2));
        //t.checkExpect(e1.getPosn(lst), new Posn(1, 1));
    }
    
    /**
     * Test the nRep generation in the class for the Kruskal Algorithm class
     * @param t Tester
     */
    void testnRepGeneration(Tester t) {
        KruskalAlgorithm ka1 = new KruskalAlgorithm(maze2.cells, maze2.edges);
        KruskalAlgorithm ka2 = new KruskalAlgorithm(maze10.cells, maze10.edges);
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
        KruskalAlgorithm ka1 = new KruskalAlgorithm(maze2.cells, maze2.edges);
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
        KruskalAlgorithm ka1 = new KruskalAlgorithm(maze2.cells, maze2.edges);
        KruskalAlgorithm ka2 = new KruskalAlgorithm(maze3.cells, maze3.edges);
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
        t.checkExpect(dfs.posnEquals(new Posn(1, 1), new Posn(1, 1)), true);
        t.checkExpect(dfs.posnEquals(new Posn(1, 1), new Posn(1, 2)), false);
        t.checkExpect(bfs.posnEquals(new Posn(1, 1), new Posn(1, 1)), true);
        t.checkExpect(bfs.posnEquals(new Posn(1, 1), new Posn(1, 2)), false);
    }
    
    /**
     * Test the addAllNeighbors method in the ASearch class
     * @param t Tester
     */
    void testAddAllNeighbors(Tester t) {
        Edge e1 = new Edge(new Posn(1, 1), new Posn(1, 2));
        Edge e2 = new Edge(new Posn(2, 2), new Posn(2, 1));
        Edge e3 = new Edge(new Posn(1, 1), new Posn(2, 1));
        dfs.tree.clear();
        dfs.toDo.clear();
        dfs.tree.add(e1); 
        dfs.tree.add(e2); 
        dfs.tree.add(e3);
        dfs.addAllNeighbors(new Posn(1, 1));
        t.checkExpect(dfs.toDo.size(), 2);
        t.checkExpect(dfs.toDo.get(0), e1);
        t.checkExpect(dfs.toDo.get(1), e3);
        dfs.toDo.clear();
        dfs.tree.remove(e1);
        dfs.addAllNeighbors(new Posn(2, 1));
        t.checkExpect(dfs.toDo.size(), 2);
        t.checkExpect(dfs.toDo.get(0), e2);
        t.checkExpect(dfs.toDo.get(1), e3);
        dfs.toDo.clear();
    }
     
    
    /**
     * Test the getIndex method in the class ASearch
     */
    void testGetIndex(Tester t) {
        dfs.addAllNeighbors(new Posn(1, 1));
        t.checkExpect(dfs.getIndex(true), 0);
        t.checkExpect(dfs.getIndex(true), dfs.toDo.size() - 1);
        bfs.addAllNeighbors(new Posn(1, 1));
        t.checkExpect(bfs.getIndex(true), 0);
        t.checkExpect(bfs.getIndex(true), dfs.toDo.size() - 1);
    }
    
    /**
     * Test the DFS method in the DFS class
     * @param Tester t
     */
    void testDFS(Tester t) {      
        dfs.toDo.clear();
        Edge e1 = new Edge(new Posn(1, 2), new Posn(1, 1));
        Edge e2 = new Edge(new Posn(2, 1), new Posn(2, 2));
        Edge e3 = new Edge(new Posn(1, 1), new Posn(2, 1));
        Edge e4 = new Edge(new Posn(2, 1), new Posn(1, 2));
        Edge e5 = new Edge(new Posn(1, 2), new Posn(2, 2));
        ArrayList<Edge> tree = 
                new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4, e5));
        ArrayList<Posn> visited = new ArrayList<Posn>();
        visited.add(new Posn(1, 1));
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
        Edge e1 = new Edge(new Posn(1, 2), new Posn(1, 1));
        Edge e2 = new Edge(new Posn(2, 1), new Posn(2, 2));
        Edge e3 = new Edge(new Posn(1, 1), new Posn(2, 1));
        Edge e4 = new Edge(new Posn(2, 1), new Posn(1, 2));
        Edge e5 = new Edge(new Posn(1, 2), new Posn(2, 2));
        ArrayList<Edge> tree = 
                new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4, e5));
        ArrayList<Posn> visited = new ArrayList<Posn>();
        visited.add(new Posn(1, 1));
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
     * Test the onTick method in the MazeGame class
     * @param t Tester
     */
    void testOnTick(Tester t) {
        t.checkExpect(mw.onTick(), mw);
        mw.runDFS = true;
        mw.onTick();
        t.checkExpect(mw.maze.searchPath.size(), 2);
        mw.runDFS = false;
        mw.runBFS = true;
        mw.onTick();
        t.checkExpect(mw.maze.searchPath.size(), 3);
    }
    
    /**
     * Test the keyEvent handler in the MazeGame class
     * @param t Tester
     */
    void testKeyEvent(Tester t) {
        mw = new MazeGame(maze10);
        t.checkExpect(mw.runBFS, false);
        t.checkExpect(mw.onKeyEvent("a"), mw);
        t.checkExpect(mw.onKeyEvent(" "), mw);
        t.checkFail(mw.onKeyEvent("n"), mw);
        t.checkFail(mw.onKeyEvent("down"), mw);
        mw.maze.size = 3;
        t.checkExpect(mw.onKeyEvent("down"), mw);
        mw.maze.size = 40;
        t.checkExpect(mw.onKeyEvent("up"), mw);
        mw.maze.size = 10;
        t.checkFail(mw.onKeyEvent("up"), mw);
        mw.onKeyEvent("d");
        t.checkExpect(mw.runDFS, true);
        t.checkExpect(mw.runBFS, false);
        mw.onKeyEvent("b");
        t.checkExpect(mw.runDFS, false);
        t.checkExpect(mw.runBFS, true);
        mw.onKeyEvent(" ");
        t.checkExpect(mw.showTree, false);    
        mw.onKeyEvent(" ");      
        t.checkExpect(mw.showTree, true);
    }
    
    /**
     * Test various draw methods in the MazeGame class
     * @param t Tester
     */
    void testDraw(Tester t) {
        MazeGame mw1 = new MazeGame(maze1);
        WorldImage bg = 
                new RectangleImage(new Posn(400, 400), 
                        720, 720, new Color(100, 100, 100, 0));
        
        t.checkExpect(mw1.drawMaze(), new OverlayImages(
                new FrameImage(new Posn(360, 360), 
                        720, 720, new Black()), bg));
        
        t.checkExpect(mw1.drawGrid(), new OverlayImages(
                new FrameImage(new Posn(360, 360), 
                        720, 720, new Black()), bg));
        
        t.checkExpect(mw1.drawMaze(), mw1.drawMaze());        
        t.checkExpect(mw1.drawMST(), mw1.drawMST());        
        t.checkExpect(mw1.drawPath(bg), mw1.drawPath(bg));        
        t.checkExpect(mw1.drawToDo(bg), bg);        
        t.checkExpect(mw1.makeImage(), mw1.makeImage());    
        mw1.showTree = true;
        t.checkExpect(mw1.makeImage(), mw1.makeImage());
    }
         
    /**
     * Test the method imagify in the MazeGame class
     * @param t Tester
     */   
    void testImagify(Tester t) {       
        t.checkExpect(mw.imagify(new Posn(1, 1)), new Posn(36, 36));
        
    }
    
    /**
     * Test the method getToDo in the MazeGame class
     * @param t Teser    
     */
    void testGetToDo(Tester t) {
        mw.runDFS = true;
        mw.runBFS = false;       
        t.checkExpect(mw.getToDo(), mw.dfs.toDo);      
    }
    
 /**
  * Test the functionality of the game by running bigBang
  * (no real other tests are possible)
  */
    void testGame(Tester t) {
        // UNCOMMENT TO RUN THE GAME
        // MazeGame mg1 = new MazeGame(new Maze(20));
        // mg1.bigBang(720, 720, .05);
    }
}