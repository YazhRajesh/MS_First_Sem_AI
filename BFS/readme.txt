ASSIGNMENT 4ARITIFICIAL INTELLIGENCE-1NAME: YAZHINI PRIYADHARSHINI RAJESHUIN ID: 664248302This program was run on Eclipse using Java. The inputs are passed to the program through Run Configuration->Arguments-> 1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15It is a single class file. this program implements BFS in 4x4 puzzle
In BFS, it starts with the root node then goes to the neighbor nodes, check if they are the final state, if none of them are, move to the next layer of children, function BFSImplements checks whether the goal state has been reached or not
If goal state has not been reached, the nodes are expanded and the next child is taken
If the goal state is reached (compared using goalTest) the state is given as the solution
Function findChildren helps in the swapping of the locations of the blank and the next position depending on left, right, up or down
Function RetLatestframe returns a new frame for the current location of the blank and gives out direction of the path
Output of the program is as below:Moves: RDLDDRRMemory: 11264kbTime: 102msExpanded Nodes: 2642