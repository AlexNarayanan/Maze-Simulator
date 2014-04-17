MazeGame

Created by: Alex Narayanan and Rui Zheng

========

Generate and auto-solve mazes!

Final Project for CS 2510 Fundamentals of Computer Science 2 at Northeastern University.

Uses tester and javalib libraries for testing and execution of a world program. 

USER GUIDE

The program can be run from either the main method in the MazeGame class
or the examples class. UNCOMMENT THE LAST METHOD IN EXAMPLES TO RUN FROM
EXAMPLES CLASS. In run configurations, select tester.main for the main
class and select the examples class as the program argument to run from 
the examples class, or select MazeGame for the main class to run from the 
main method in the MazeGame class. 

WHAT TO EXPECT:
-Mazes generated with Kruskal's Algorithm and Union Find Algorithm
-Ability to search through a randomly generated maze using one of two algorithms: breath-first or depth-first serach
-When starting a new search, cells colored light blue will be cells that have
already been searched
-Cells colored darker blue will be cells that are on the to-do list of the
current search

CONTROLS:
space           show the minimal spanning tree of the current maze
d               start a new depth-first search
b               start a new breadth-first search
n               create a new maze of the same size
up              create a new maze of bigger size (up to a cap of size 35)
down            create a new maze of smaller size (up to a cap of size 5)

