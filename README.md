This project is a Java program designed to solve the shortest path problem on directed graphs. It utilizes multithreading to efficiently process tasks, with four distinct threads performing different functions:

1. **Constraint Checker Thread**: This thread checks if a given task complies with the specified constraints;

2. **Solver Thread**: Once a task is validated, this thread finds all pairs of vertices with a distance between them up to a specified value;

3. **Solution Verifier Thread**: After the solver thread completes its task, this thread verifies the obtained solution against a provided answer;

4. **UI Display Thread**: Upon completion of the validation, solving, and verification stages for one task, this thread displays the result in the user interface.

The program accepts a directory containing input files with tasks and corresponding output files with expected answers. Each thread performs its tasks independently, maximizing parallel processing efficiency.

The user interface is built using JavaFX.

The core algorithm used for solving the shortest path problem is Dijkstra's algorithm.

### How to Use

1. Provide a directory containing input files with tasks and output files with expected answers;
	- each task must have two files close\[N].in and close\[N].out, where N is 1 - based index of a task;
	- .in file structure must be as follows: 
		*n k*
		*adjacency matrix*
		
		n - number of vertices, k - distance between a pair of vertices
		2 < n < 10000, 1 < k < 10000000
	- .out file structure must be as follows:
		*n* (number of pairs of vertices with distance)
		*source-node destination-node distance*
	
2. Run the program, which will process tasks using multithreading;
3. View the results in the user interface, displaying the result for each task.

### Input files example
*close1.in*:\
4 2\
0 1 2 3\
0 0 1 2\
0 0 0 1\
0 0 0 0

*close1.out*:\
5\
0 1 1\
0 2 2\
1 2 1\
1 3 2\
2 3 1
