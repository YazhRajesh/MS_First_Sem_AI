ASSIGNMENT 7ARITIFICIAL INTELLIGENCE-1NAME: YAZHINI PRIYADHARSHINI RAJESHUIN ID: 664248302This program was run on Eclipse using Java. The inputs are passed to the program through Run Configuration->Arguments-> 1 2 0 4 6 7 3 8 5 9 10 12 13 14 11 15 
It is a single class file. //Misplaced Tiles
//We see that all the tiles are not in their final position except ‘7’ thus the heuristic value(h) will be 7 for this instance.
//Remember we don’t consider the blank space as a tile while calculating this heuristic value.
//This heuristics returns the number of tiles that are not in their final position.

//Manhattan Distance:
//This method of computing  is called the Manhattan method because it is computed by calculating the total number of squares 
//moved horizontally and vertically to reach the target square from the current square. 
//We ignore diagonal movement and any obstacles that might be in the way.

//IDA Star Algorithm:
1. Initialise minimum (initial node's depth) to be the cutoff (of initial node)
// 2. Repeat: f(n)- current node"s level
//a. Perform depth-first search by expanding all nodes N such that f(N) ≤ cutoff
//b. Reset cutoff to smallest value f of non-expanded (leaf) nodes
//The algorithm keeps revisiting the nodes from the staring. 
// IDA* does not utilise dynamic programming and therefore often ends up exploring the same nodes many times.
Output of the program is as below:IDAStar H2: 
  Moves:DLLDRRDR
Expanded Nodes:14
  Memory: 2336kb
 Time: 1ms
