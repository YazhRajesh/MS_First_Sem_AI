package assignment4AI;
// this program implements BFS in 4x4 puzzle
// In BFS, it starts with the root node then goes to the neighbor nodes
// check if they are the final state
// if none of them are, move to the next layer of children
// function BFSImplements checks whether the goal state has been reached or not
// if goal state has not been reached, the nodes are expanded and the next child is taken
// if the goal state is reached (compared using goalTest) the state is given as the solution
// function findChildren helps in the swapping of the locations of the blank and the next position 
// depending on left, right, up or down
// function RetLatestframe returns a new frame for the current location of the blank and gives out direction of the path

import java.util.LinkedList;
import java.util.Arrays;


public  class bfsfifteenpuzzle 
{
	private static class node
	{
	   int level = 0;
	   int [][] frame = new int [4][4];
	   int ZeroRow;
	   int ZeroCol;
	   String move = "";
	   node parent = null; //initializing parent
	   node Up = null;   //initializing up as null
	   node Down = null; //initializing down as null
	   node Left = null;  //initializing left as null
	   node Right = null;  //initializing right as null
	}
	private static class puzzlesolveData 
	{
	   int NodesExpanded = 0;
	   node solutionNode = null;
	   long MemoryUsed;
	   long TimeOverall;
	   String path = "";
	   String startingpuzzle = "";
	}
	// checks to see if the current node is the required frame(goal)
	
	public static boolean goalTest(node currentNode){
	   int [][] goalframe = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
	   return Arrays.deepEquals(currentNode.frame, goalframe);    
	}
	
	public static void main(String [] args)
	{
	    if(args.length <= 0) 
	    {
	       System.out.println("No puzzle was provided");
	       return;
	   }
	   else if(args.length != 16)
	   {
	       System.out.println("Puzzle does not contain correct number of numbers");
	       return;
	   }
	  
	  
  try 
	   {
	       String puzzle = "";
	       node root = new node();
	       root.move = "Start";
	     
	       for (int i =0; i < 4; i++) 
	       {
	           for (int q = 0; q < 4; q++) 
	           {
	            	 
	          	 root.frame[i][q] = Integer.parseInt(args[4*i + q]);
	               puzzle = puzzle + root.frame[i][q] +" ";
	               
	               if(root.frame[i][q] == 0)
	               {
	                       root.ZeroRow = i;
	                       root.ZeroCol = q;
	               }
	           }
	       }

	       puzzlesolveData BFSolution = BFSImplement(root, puzzle);
	       
	       if(BFSolution != null)
	           printpuzzlesolveData(BFSolution);
	       else
	           System.out.println("Can't find solution- ran out of memory- no solution :("); //runs out of memory if no solution

	   }
	   catch(OutOfMemoryError e)
	   {
	       System.out.println("Can't find solution-  ran out of memory- no space :("); //runs out of memory if there isn't enough memory to store data
	   }
	 }
	
   
// A method that uses breadth first search to find a solution
public static puzzlesolveData BFSImplement(node root, String puzzle){
   LinkedList<node> queue = new LinkedList<node>();
   queue.add(root);
   
   puzzlesolveData BFSolution = new puzzlesolveData();
   int NodesExpanded = 0;
   long startTime = System.currentTimeMillis();

   while(!queue.isEmpty() && NodesExpanded <= Integer.MAX_VALUE)
   {
       node currentValue = queue.removeFirst();
       String currframe = RetLatestframeID(currentValue);

       if(goalTest(currentValue))
       {
           BFSolution.NodesExpanded = NodesExpanded;
           BFSolution.startingpuzzle = puzzle;
           BFSolution.solutionNode = currentValue;
           BFSolution.path = ShowPath(currentValue);
           BFSolution.TimeOverall= TimeTaken(startTime);
           BFSolution.MemoryUsed= Memorytaken();
           return BFSolution;
       }
       else 
       {
           // expands node
           findChildren(currentValue);
           NodesExpanded++;
            System.out.println(NodesExpanded);
           if(currentValue.Left != null)
               queue.add(currentValue.Left);
           if(currentValue.Right != null)
               queue.add(currentValue.Right);
           if(currentValue.Up != null)
               queue.add(currentValue.Up);
           if(currentValue.Down != null)
               queue.add(currentValue.Down);
       }
   }
   // should never happen unless puzzle is not valid or ran out of memory
   return null;
}

// Simply prints the solution and information regarding the solution
public static void printpuzzlesolveData(puzzlesolveData solution)
{
   System.out.println("  Moves:" + solution.path  );
   System.out.println("Expanded Nodes:" + solution.NodesExpanded  );
   System.out.println("  Memory: " + solution.MemoryUsed  );
   System.out.println( " Time: "+ solution.TimeOverall + "ms " );
}

public static long TimeTaken(long start)
{
	 
	 long timedifference;
	 timedifference= System.currentTimeMillis() - start;
	 return timedifference;
	 
}
public static long Memorytaken()
{
	Runtime timerun = Runtime.getRuntime();
	long mem;
    mem = (timerun.totalMemory() - timerun.freeMemory())/(1024);
    return mem;
}
//function that creates an id for each frame to be later stored. This ID is unique.
public static String RetLatestframeID(node currentNode)
{
   String idno = "";
   for(int i = 0; i < 4; i++){
       for (int q = 0; q < 4; q++)
           idno = idno + ((char) (65 + currentNode.frame[i][q]));
   }
   return idno;
}

public static void findChildren(node currentNode){
	 if(currentNode.ZeroRow > 0)   //moving up but row 1 cannot move up=> therefore row%4 not zero, then implement
   {
       currentNode.Up = new node();
       currentNode.Up.move = "U";
       currentNode.Up.parent = currentNode;
       currentNode.Up.level = currentNode.level + 1;
       currentNode.Up.ZeroCol = currentNode.ZeroCol;
       currentNode.Up.ZeroRow = currentNode.ZeroRow - 1;    //up of (2,3) is (1,3)
       currentNode.Up.frame = RetLatestframe(currentNode.frame, currentNode.ZeroCol, currentNode.ZeroRow,  'U');
   
   }
    if(currentNode.ZeroRow < 3)  //moving down but row 4 cannot move down=> therefore row%4 not three, then implement
    {
       currentNode.Down = new node();
       currentNode.Down.move = "D";
       currentNode.Down.parent = currentNode;
       currentNode.Down.level = currentNode.level + 1;
       currentNode.Down.ZeroCol = currentNode.ZeroCol;
       currentNode.Down.ZeroRow = currentNode.ZeroRow + 1;   //down of (2,3) is (3,3)
       currentNode.Down.frame = RetLatestframe(currentNode.frame, currentNode.ZeroCol, currentNode.ZeroRow,  'D');
   }       
    if(currentNode.ZeroCol < 3)   //moving Right but col 4 cannot move right=> therefore col%4 not three, then implement
    {
        currentNode.Right = new node();
        currentNode.Right.move = "R";
        currentNode.Right.parent = currentNode; 
        currentNode.Right.level = currentNode.level + 1;
        currentNode.Right.ZeroCol = currentNode.ZeroCol + 1;     //Right of (2,3) is (2,4)
        currentNode.Right.ZeroRow = currentNode.ZeroRow; 
        currentNode.Right.frame = RetLatestframe(currentNode.frame, currentNode.ZeroCol, currentNode.ZeroRow,  'R');

    }
    
    if(currentNode.ZeroCol > 0)   //moving left but col 1 cannot move up=> therefore col%4 not zero, then implement
   {
       currentNode.Left = new node();
       currentNode.Left.move = "L";
       currentNode.Left.parent = currentNode;
       currentNode.Left.level = currentNode.level + 1;  
       currentNode.Left.ZeroCol = currentNode.ZeroCol - 1;  //left of (2,3) is (2,2)
       currentNode.Left.ZeroRow = currentNode.ZeroRow;
       currentNode.Left.frame = RetLatestframe(currentNode.frame, currentNode.ZeroCol, currentNode.ZeroRow, 'L');
   }
}

// returns a new frame for the current location of the blank and gives out direction of the path

public static int[][] RetLatestframe(int[][] currentframe, int XofZero,int YofZero, char move)
{
   int [][] Latestframe = new int [4][];
   for(int i = 0; i < 4; i++)
       Latestframe[i] = currentframe[i].clone();

   if(move == 'U'){
       Latestframe[YofZero][XofZero] = Latestframe[YofZero - 1][XofZero]; //swap x and y coordinates of zero with the coordinates of the position above the zero
       Latestframe[YofZero - 1][XofZero] = 0;
   }
   else if(move == 'D'){
       Latestframe[YofZero][XofZero] = Latestframe[YofZero + 1][XofZero];//swap x and y coordinates of zero with the coordinates of the position below the zero
       Latestframe[YofZero + 1][XofZero] = 0;
   }
   else if(move == 'L'){
       Latestframe[YofZero][XofZero] = Latestframe[YofZero][XofZero - 1]; //swap x and y coordinates of zero with the coordinates of the position left of zero
       Latestframe[YofZero][XofZero - 1] = 0;
   }
   else{
       Latestframe[YofZero][XofZero] = Latestframe[YofZero][XofZero + 1]; //swap x and y coordinates of zero with the coordinates of the position right of zero
       Latestframe[YofZero][XofZero + 1] = 0;
   }
   return Latestframe;
}


public static String ShowPath(node solution) // returns a string that shows the path taken to solve the puzzle
{
   LinkedList<node> solutionPath = new LinkedList<node>();
   node finalsol = solution;       //add nodes to the linked list
   String path = "";
   while(finalsol != null){
       solutionPath.addFirst(finalsol);
       finalsol = finalsol.parent;
   }
   while(!solutionPath.isEmpty()){
       node temp = solutionPath.removeFirst(); //temporary node to remove the elements of queue
       if(temp.move != "Start")
           path = path + temp.move;
   }
   return path;
}


}

