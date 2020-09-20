import java.util.Random;
import java.util.Stack;

public class MinPathSolver {
	private int[][] puzzle = {
		{1, 5, 10},
		{3, 2, 6},
		{11, 18, 4}
	};
	private int[][] sum;
	private Stack<Integer[]> path;

	public MinPathSolver(int size)
	{
		puzzle = new int[size][size];
		sum = new int[size][size];
		generatePuzzle();
		path = new Stack<Integer[]>();

		// find the path
		calculateMinSum();
		markPath(puzzle.length-1, puzzle.length-1);
	}


	private void generatePuzzle()
	{
		Random rand = new Random();

		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = rand.nextInt(20) + 1;
	}


	// calculate minimum sum for all cells
	private void calculateMinSum()
	{
		int leftSum, upSum;

		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
			{
				if (row == 0 && col == 0)
				{
					sum[row][col] = puzzle[row][col];
					continue;
				}

				leftSum = Integer.MAX_VALUE;
				upSum = Integer.MAX_VALUE;
				if (col > 0)
					leftSum = sum[row][col-1];
				if (row > 0)
					upSum = sum[row-1][col];

				sum[row][col] = Math.min(leftSum, upSum) + puzzle[row][col];
			}
	}


	// mark the cell at (row, col) as path
	private void markPath(int row, int col)
	{
		Integer[] cell = {row, col};
		path.push(cell);

		if (row == 0 && col == 0)
			return;

		if (getSum(row-1, col) < getSum(row, col-1))
			markPath(row-1, col);
		else
			markPath(row, col-1);
	}


	// return the sum at (row, col)
	private int getSum(int row, int col)
	{
		if (row < 0 || col < 0)
			return Integer.MAX_VALUE;

		return sum[row][col];
	}


	public int[][] getPuzzle()
	{
		return puzzle;
	}


	public Stack<Integer[]> getPath()
	{
		return path;
	}


	public int getMinSum()
	{
		return sum[sum.length-1][sum.length-1];
	}


	// print the path to the console
	@SuppressWarnings("unchecked")
	public void printPath()
	{
		Stack<Integer[]> temp = (Stack<Integer[]>) path.clone();
		Integer[] current = temp.pop();
		for (int row = 0; row < puzzle.length; row++)
		{
			for (int col = 0; col < puzzle[0].length; col++)
			{
				if (row == current[0] && col == current[1])
				{
					System.out.printf("%5d", puzzle[row][col]);
					if (!temp.isEmpty())
						current = temp.pop();
				}
				else
					System.out.printf("%5s", "");
			}
			System.out.println();
		}
	}


	// print puzzle to the console
	public void printPuzzle()
	{
		for (int row = 0; row < puzzle.length; row++)
		{
			for (int col = 0; col < puzzle[0].length; col++)
				System.out.printf("%5d", puzzle[row][col]);
			System.out.println();
		}
	}
}

