package cs411;

//IIDFS uses the concept of both BFS and DFS
//IIDFS takes the depth of the frames first before going forward in comparing the frames with the goal frame 
//When the depth is increased, the frames are compared from the first frame to the nodes of the depth specified. 
//Once each depth has been looked after, the algorithm is terminated
//Time complexity of IIDFS is the same as of BFS (O^b)

//function IIDFSImplement checks whether the goal state has been reached or not
//if goal state has not been reached, the nodes are expanded and the next child is taken
//if the goal state is reached (compared using goalTest) the state is given as the solution
//function findChildren helps in the swapping of the locations of the blank and the next position 
//depending on left, right, up or down
//function RetLatestframe returns a new frame for the current location of the blank and gives out direction of the path
import java.util.Arrays;
import java.util.LinkedList;


public class iidfsfifteenpuzzle 
{
	private static class node      //elements of the node
	{
		int level=0;
		int [][]frame= new int[4][4];
		int row;
		int col;
		String change= " ";
		node parent= null;
		node Up= null;
		node Down= null;
		node Left= null;
		node Right= null;
	}
	public static class initializer   //The output initialization
	{
	  int nodesexpanded=0;
	  node solutionNode= null;
	  long MemoryUsed;
	  long TimeOverall;
	  String path= "";
	  String startingframe="";
	  
	}

	public static boolean goaltest( node currentNode)   //to test if state is goal
	{
		int [][] goalframe = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		   return Arrays.deepEquals(currentNode.frame, goalframe); 
	}

	public static void main(String [] args)     //to get the inputs and call the IIDFS Solution
	{ 
		long starttime = System.currentTimeMillis();
		if(args.length<=0)
		{
			System.out.println("no puzzle was given");
			return;
		} 
		else if (TimeTaken(starttime)>30000)    //if time >30, stop the program
			
		{
			System.out.println("Can't find solution- ran out of time ");
			return;
		}
		else
		{
			String puzzle = "";
			node root= new node();
			root.change= "Start";
			
			for(int i=0;i<4;i++)
			{
				for(int q=0;q<4;q++)
				{
					root.frame[i][q]= Integer.parseInt(args[4*i +q]);  //read the input
					puzzle= puzzle + root.frame[i][q] +"";
					
					if(root.frame[i][q]==0)  //check where blank is
					{
						root.row=i;
						root.col=q;
					}
				}
			}
			initializer IIDFSolution = IIDFSImplement (root, puzzle);  //call the function to implement
			if(IIDFSolution !=null)     //if solution found print the output
				printpuzzle(IIDFSolution);
			else
				System.out.println("cant find solution");	
		}
	}
	
	public static initializer IIDFSImplement(node root, String puzzle )  //IIDFS Implementation 
	{
		    int depth = 0;  //depth is initialized to 0
		    int expanded = 0; //expanded nodes initialized
		    
	        initializer IIDFSolution = new initializer();   
	        
	        long starttime = System.currentTimeMillis();
	        
	        while(depth < Integer.MAX_VALUE)
	        {
	            
	            LinkedList<node> stack = new LinkedList<node>();  //creation of stack as IIDFS is a LIFO Implementation
	            stack.add(root); 
	            while(!stack.isEmpty())
	            {
	                node currentValue = stack.removeLast();   //take last value- LIFO

	                String curState = RetLatestframeID(currentValue);  //check it's ID

	                if(goaltest(currentValue))  //if it is the goal test, output
	                {
	                    IIDFSolution.nodesexpanded = expanded;
	                    IIDFSolution.solutionNode = currentValue;
	                    IIDFSolution.startingframe = puzzle;
	                    IIDFSolution.path = ShowPath(currentValue);
	                    IIDFSolution.TimeOverall= TimeTaken(starttime);
	                    IIDFSolution.MemoryUsed=MemoryTaken();
	                    return IIDFSolution;
	                }
	                else if(currentValue.level < depth)  //else find the children and expanded node is incremented
	                {
	                 
	                    findChildren(currentValue);
	                    expanded++;

	                    if(currentValue.Left != null)  //if there is a left to blank position, add that state to the queue
	                        stack.add(currentValue.Left);
	                    if(currentValue.Right != null) //if there is a right to blank position, add that state to the queue
	                        stack.add(currentValue.Right);
	                    if(currentValue.Up != null) //if there is a up to blank position, add that state to the queue
	                        stack.add(currentValue.Up); 
	                    if(currentValue.Down != null) //if there is a down to blank position, add that state to the queue
	                        stack.add(currentValue.Down);
	                }
	             }
	            depth++;  //depth is incremented to 1 and the process is repeated
	          }
	        return null;
	}
	
	
	public static void printpuzzle(initializer solution)  // Simply prints the solution and information regarding the solution
	{
	   System.out.println("  Moves:" + solution.path  );
	   System.out.println("Expanded Nodes:" + solution.nodesexpanded  );
	   System.out.println("  Memory: " + solution.MemoryUsed + "kb"  );
	   System.out.println( " Time: "+ solution.TimeOverall + "ms " );
	}
	 
	public static long TimeTaken(long start)  //calculates time taken
	{
		 
		 long timedifference;
		 timedifference= System.currentTimeMillis() - start;
		 return timedifference;
		 
	}
	public static long MemoryTaken()  //calculates overall memory space used
	{
		Runtime timerun = Runtime.getRuntime();
		long mem;
	    mem = (timerun.totalMemory() - timerun.freeMemory())/(1024);
	    return mem;
	}
	
	
	public static String RetLatestframeID(node currentNode) //function that creates an id for each frame to be later stored. This ID is unique.
	{
	   String idno = "";
	   for(int i = 0; i < 4; i++){
	       for (int q = 0; q < 4; q++)
	           idno = idno + ((char) (65 + currentNode.frame[i][q])); //giving an ID for each frame to be stored in 
	   }
	   return idno;
	}
	public static void findChildren(node currentNode){
		 if(currentNode.row > 0)   //moving up but row 1 cannot move up=> therefore row%4 not zero, then implement
	   {
	       currentNode.Up = new node();
	       currentNode.Up.change = "U";
	       currentNode.Up.parent = currentNode;
	       currentNode.Up.level = currentNode.level + 1;
	       currentNode.Up.col = currentNode.col;
	       currentNode.Up.row = currentNode.row - 1;    //up of (2,3) is (1,3)
	       currentNode.Up.frame = RetLatestframe(currentNode.frame, currentNode.col, currentNode.row, 'U');
	   
	   }
	    if(currentNode.row < 3)  //moving down but row 4 cannot move down=> therefore row%4 not three, then implement
	    {
	       currentNode.Down = new node();
	       currentNode.Down.change = "D";
	       currentNode.Down.parent = currentNode;
	       currentNode.Down.level = currentNode.level + 1;
	       currentNode.Down.col = currentNode.col;
	       currentNode.Down.row = currentNode.row + 1;   //down of (2,3) is (3,3)
	       currentNode.Down.frame = RetLatestframe(currentNode.frame, currentNode.col, currentNode.row,  'D');
	   }       
	    if(currentNode.col < 3)   //moving Right but column 4 cannot move right=> therefore column %4 not three, then implement
	    {
	        currentNode.Right = new node();
	        currentNode.Right.change = "R";
	        currentNode.Right.parent = currentNode; 
	        currentNode.Right.level = currentNode.level + 1;
	        currentNode.Right.col = currentNode.col + 1;     //Right of (2,3) is (2,4)
	        currentNode.Right.row = currentNode.row; 
	        currentNode.Right.frame = RetLatestframe(currentNode.frame, currentNode.col, currentNode.row,  'R');

	    }
	    
	    if(currentNode.col > 0)   //moving left but column 1 cannot move up=> therefore column %4 not zero, then implement
	   {
	       currentNode.Left = new node();
	       currentNode.Left.change = "L";
	       currentNode.Left.parent = currentNode;
	       currentNode.Left.level = currentNode.level + 1;  
	       currentNode.Left.col = currentNode.col - 1;  //left of (2,3) is (2,2)
	       currentNode.Left.row = currentNode.row;
	       currentNode.Left.frame = RetLatestframe(currentNode.frame, currentNode.col, currentNode.row, 'L');
	   }
	}

	// returns a new frame for the current location of the blank and gives out direction of the path

	public static int[][] RetLatestframe(int[][] currentframe, int x,int y, char move)
	{
	   int [][] Latestframe = new int [4][];
	   for(int i = 0; i < 4; i++)
	       Latestframe[i] = currentframe[i].clone();

	   if(move == 'U'){
	       Latestframe[y][x] = Latestframe[y - 1][x]; //swap x and y coordinates of zero with the coordinates of the position above the zero
	       Latestframe[y - 1][x] = 0;
	   }
	   else if(move == 'D'){
	       Latestframe[y][x] = Latestframe[y + 1][x];//swap x and y coordinates of zero with the coordinates of the position below the zero
	       Latestframe[y + 1][x] = 0;
	   }
	   else if(move == 'L')
	   {
	       Latestframe[y][x] = Latestframe[y][x - 1]; //swap x and y coordinates of zero with the coordinates of the position left of zero
	       Latestframe[y][x - 1] = 0;
	   }
	   else{
	       Latestframe[y][x] = Latestframe[y][x + 1]; //swap x and y coordinates of zero with the coordinates of the position right of zero
	       Latestframe[y][x + 1] = 0;
	   }
	   return Latestframe;
	}


	public static String ShowPath(node solution) // returns a string that shows the path taken to solve the puzzle
	{
	   LinkedList<node> solutionPath = new LinkedList<node>();
	   node finalsol = solution;       //add nodes to the stack (linked list)
	   String path = "";
	   while(finalsol != null){
	       solutionPath.addFirst(finalsol);
	       finalsol = finalsol.parent;
	   }
	   while(!solutionPath.isEmpty()){
	       node temp = solutionPath.removeFirst(); //temporary node to remove the elements of stack
	       if(temp.change != "Start")
	           path = path + temp.change;
	   }
	   return path;
	}


}
