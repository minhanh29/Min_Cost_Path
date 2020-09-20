
public class Tester {
	public static void main(String[] args)
	{
		MinPathSolver solver = new MinPathSolver(3);
		System.out.println("The puzzle: ");
		solver.printPuzzle();
		System.out.println("\nThe path: ");
		solver.printPath();
	}
}
