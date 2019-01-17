ASSIGNMENT 5
ARITIFICIAL INTELLIGENCE-1NAME: YAZHINI PRIYADHARSHINI RAJESHUIN ID: 664248302IIDFS uses the concept of both BFS and DFS
IIDFS takes the depth of the frames first before going forward in comparing the frames with the goal frame 
When the depth is increased, the frames are compared from the first frame to the nodes of the depth specified. 
Once each depth has been looked after, the algorithm is terminated
Time complexity of IIDFS is the same as of BFS (O^b)
function IIDFSImplement checks whether the goal state has been reached or not
If goal state has not been reached, each time, the depth is increased and each state from the initial state is looked at else (compared using goalTest) the state is given as the solution
Function findChildren helps in the swapping of the locations of the blank and the next position depending on left, right, up or down
Function RetLatestframe returns a new frame for the current location of the blank and gives out direction of the pathThis program was run on Eclipse using Java. The inputs are passed to the program through Run Configuration->Arguments-> 1 0 2 4 5 7 3 8 9 6 11 12 13 10 14 15It is a single class file. this program implements IIDFS in 4x4 puzzle
Output of the program is as below: Moves:RDLDDRR
Expanded Nodes:1489
  Memory: 13887
 Time: 31ms 