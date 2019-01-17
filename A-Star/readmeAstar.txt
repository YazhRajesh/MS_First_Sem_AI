ASSIGNMENT 6ARITIFICIAL INTELLIGENCE-1NAME: YAZHINI PRIYADHARSHINI RAJESHUIN ID: 664248302This program was run on Eclipse using Java. The inputs are passed to the program through Run Configuration->Arguments-> 1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15It is a single class file. Misplaced Tiles (Heuristic 1): 
As the name suggests, the function takes in the tiles that are misplaced and calculates the final position for them. Remember we don’t consider the blank space as a tile while calculating this heuristic value. This heuristics returns the number of tiles that are not in their final position.
Manhattan Distance (Heuristic 2):
This method of computing  is called the Manhattan method because it is computed by calculating the total number of squares moved horizontally and vertically to reach the target square from the current square. 
We ignore diagonal movement and any obstacles that might be in the way.


Output of the program is as below:A* Heuristic 1:
AStar H1: 
  Moves:RDLDDRR
Expanded Nodes:7
  Memory: 2336kb
 Time: 1ms 

A* Heuristic 2:
AStar H2: 
  Moves:RDLDDRR
Expanded Nodes:7
  Memory: 918kb
 Time: 1ms 
