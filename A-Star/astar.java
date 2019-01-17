package cs411;
//Misplaced Tiles: 
//We see that all the tiles are not in their final position except ‘7’ thus the heuristic value(h) will be 7 for this instance.
//Remember we don’t consider the blank space as a tile while calculating this heuristic value.
//This heuristics returns the number of tiles that are not in their final position.

//Manhattan Distance:
//This method of computing  is called the Manhattan method because it is computed by calculating the total number of squares 
//moved horizontally and vertically to reach the target square from the current square. 
//We ignore diagonal movement and any obstacles that might be in the way.


import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class astar 
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
			{   System.out.println("\nA* Heuristic 1:");
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
				initializer astarh1sol = astarh1 (root, puzzle);  //call the function to implement
				if(astarh1sol !=null)     //if solution found print the output
				{ System.out.println("AStar H1: ");
					printpuzzle(astarh1sol);
					}
				else
					System.out.println("cant find solution");	
				
				System.gc();
				System.out.println("\nA* Heuristic 2:");
				String puzzle1 = "";
				node root1 = new node();
				root1.change= "Start";
				
				for(int i=0;i<4;i++)
				{
					for(int q=0;q<4;q++)
					{
						root1.frame[i][q]= Integer.parseInt(args[4*i +q]);  //read the input
						puzzle1= puzzle1 + root1.frame[i][q] +"";
						
						if(root1.frame[i][q]==0)  //check where blank is
						{
							root1.row=i;
							root1.col=q;
						}
					}
				}
				initializer astarh2sol = astarh2 (root1, puzzle);  //call the function to implement
				if(astarh2sol !=null)     //if solution found print the output
					{
					System.out.println("AStar H2: ");
					printpuzzle(astarh2sol);
					}
				else
					System.out.println("cant find solution");	
				
				
				
			}
		}
		
		public static initializer astarh1(node root, String puzzle )  //Astar Heuristic 1 Implementation 
		{
			ArrayList<node> unexpanded = new ArrayList<node>();
	        unexpanded.add(root);

	        initializer astarh1sol = new initializer();
	        int expanded = 0;
	        long starttime = System.currentTimeMillis();

	        node currentValue = null;
	        while(expanded < Integer.MAX_VALUE){
	            // get node with lowest f(n) = g(n) + h(n) result
	            int minimum = Integer.MAX_VALUE;
	            for (int i = 0; i < unexpanded.size() ; i++) 
	            {
	                node tmp = unexpanded.get(i);
	                if(findTilesMissplaced(tmp) + tmp.level < minimum){
	                    minimum = findTilesMissplaced(tmp) + tmp.level;
	                    currentValue = tmp;
	                }
	            }
	            unexpanded.remove(currentValue);

	            if (findTilesMissplaced(currentValue) == 0)
	            {
	            	astarh1sol.nodesexpanded = expanded;
	            	astarh1sol.solutionNode = currentValue;
	            	astarh1sol.startingframe = puzzle;
	            	astarh1sol.path = ShowPath(currentValue);
	            	astarh1sol.TimeOverall= TimeTaken(starttime);
	            	astarh1sol.MemoryUsed=MemoryTaken();
                    return astarh1sol;
	            }
	            else
	            {
	                findChildren(currentValue);
	                expanded++;

	                if(currentValue.Left != null)
	                    unexpanded.add(currentValue.Left);
	                if(currentValue.Right != null)
	                    unexpanded.add(currentValue.Right);
	                if(currentValue.Up != null)
	                    unexpanded.add(currentValue.Up);
	                if(currentValue.Down!= null)
	                    unexpanded.add(currentValue.Down);
	            }
	        }
	        return null;		
	       }
		
		public static initializer astarh2(node root, String puzzle )  //Astar Heuristic 2 Implementation 
		{
			ArrayList<node> unexpanded = new ArrayList<node>();
	        unexpanded.add(root);

	        initializer astarh2sol = new initializer();
	        int expanded = 0;
	        long starttime = System.currentTimeMillis();

	        node currentValue = null;
	        while(expanded < Integer.MAX_VALUE)
	        {
	        	 int minimum = Integer.MAX_VALUE;
	             for (int i = 0; i < unexpanded.size() ; i++) {
	                 node temporary = unexpanded.get(i);
	                 if(getManhattanSum(temporary) + temporary.level < minimum){
	                     minimum = getManhattanSum(temporary) + temporary.level;
	                     currentValue = temporary;
	                 }
	            }
	            unexpanded.remove(currentValue);

	            if (findTilesMissplaced(currentValue) == 0)
	            {
	            	astarh2sol.nodesexpanded = expanded;
	            	astarh2sol.solutionNode = currentValue;
	            	astarh2sol.startingframe = puzzle;
	            	astarh2sol.path = ShowPath(currentValue);
	            	astarh2sol.TimeOverall= TimeTaken(starttime);
	            	astarh2sol.MemoryUsed=MemoryTaken();
                    return astarh2sol;
	            }
	            else{
	                findChildren(currentValue);
	                expanded++;

	                if(currentValue.Left != null)
	                    unexpanded.add(currentValue.Left);
	                if(currentValue.Right != null)
	                    unexpanded.add(currentValue.Right);
	                if(currentValue.Up != null)
	                    unexpanded.add(currentValue.Up);
	                if(currentValue.Down!= null)
	                    unexpanded.add(currentValue.Down);
	            }
	        }
	        return null;		
	       }
		
		public static int findTilesMissplaced(node currentNode)
		{
	        int [][] goalTest = {{1,2,3,4},{5,6,7,8},{9, 10,11,12},{13,14,15,0}};
	        int count = 0;
	        for (int row = 0; row<4; row++)
	        {
	            for (int col = 0; col < 4; col++)
	            	
	            {
	                if(goalTest[row][col] != currentNode.frame[row][col])
	                	count++;
	            }
	        }
	        return count;
	    }
		
		public static int getManhattanSum(node curNode)
		{
	        int manhattan = 0;
	        for (int comprow = 0; comprow < 4 ; comprow++) 
	        {
	            for (int compcol = 0; compcol < 4 ; compcol++ ) 
	            {
	                int x;
	                int y;
	                int value = curNode.frame[comprow][compcol];
	                
	                if(value >= 1 && value <= 4)   //if element is between 1 and 4, subtract x axis with 1.
	                {
	                    y = 0;
	                    x = value - 1;
	                }
	                else if(value >= 5 && value <= 8)  //if element is between 5 and 8, subtract x axis with 5.
	                {
	                    y = 1;
	                    x = value - 5;
	                }
	                else if(value >= 9 && value <= 12)  //if element is between 9 and 12, subtract x axis with 9.
	                {
	                    y = 2;
	                    x = value - 9;
	                }
	                else if(value >= 13 && value <= 15)  //if element is between 13 and 15, subtract x axis with 13.
	                {
	                    y = 3;
	                    x = value - 13;
	                }
	                else
	                {
	                    y = 3;
	                    x = 3;
	                }

	                manhattan = manhattan + Math.abs(comprow - y) + Math.abs(compcol - x);
	            }
	        }
	        return manhattan;
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


