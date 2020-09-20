#include <iostream>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <vector>
using namespace std;


// puzzle
int** grid;
int** sumGrid;
int** pathGrid;
int repeatCount(0), callCount(0);

// generate a n x n random number grid
// between start and end
void generateGrid(const int& n, const int& start, const int& end)
{
	grid = new int*[n];
	sumGrid = new int*[n];
	pathGrid = new int*[n];

	srand(time(NULL));
	for (int i = 0; i < n; i++)
	{
		grid[i] = new int[n];
		sumGrid[i] = new int[n];
		pathGrid[i] = new int[n];
		for (int j = 0; j < n; j++)
		{
			grid[i][j] = rand() % (start - end) + start;
			sumGrid[i][j] = INT_MAX;
			pathGrid[i][j] = INT_MAX;
		}
	}
}


// print the array to the screen
void printGrid(int** gridData, int size)
{
	for (int i = 0; i < size; ++i)
	{
		for (int j = 0; j < size; ++j)
		{
			if (gridData[i][j] != INT_MAX)
				cout << setw(5) << gridData[i][j];
			else
				cout << setw(5) << "";
		}
		cout << endl;
	}
}


// find the minimum path sum for (x, y)
int posSum(int x, int y)
{
	++callCount;

	if (x < 0 || y < 0)
		return INT_MAX;

	if (x == 0 && y == 0)
	{
		sumGrid[0][0] = grid[0][0];
		return sumGrid[0][0];
	}

	if (sumGrid[x][y] != INT_MAX)
	{
		++repeatCount;
		return sumGrid[x][y];
	}

	sumGrid[x][y] = min(posSum(x-1, y), posSum(x, y-1)) + grid[x][y];
	return sumGrid[x][y];
}


// iterative version for posSum
void iterativePosSum(int size)
{
	int leftSum, upSum;

	for (int x = 0; x < size; ++x)
	{
		for (int y = 0; y < size; ++y)
		{
			leftSum = upSum = INT_MAX;
			if (x == 0 && y == 0)
			{
				sumGrid[x][y] = grid[x][y];
				continue;
			}

			if (x > 0)
				upSum = sumGrid[x-1][y];
			if (y > 0)
				leftSum = sumGrid[x][y-1];

			sumGrid[x][y] = grid[x][y] + min(leftSum, upSum);
		}
	}
}

// find the minimum cost path
void markPath(int x, int y)
{
	pathGrid[x][y] = grid[x][y];
	if (x == 0 && y == 0)
		return;

	if (x > 0 && grid[x][y] == (sumGrid[x][y] - sumGrid[x-1][y]))
		markPath(x - 1, y);
	else if (y > 0 && grid[x][y] == (sumGrid[x][y] - sumGrid[x][y-1]))
		markPath(x, y - 1);
}


int main()
{
	int size;
	cout << "Enter the grid size: ";
	cin >> size;

	generateGrid(size, 1, 10);
	printGrid(grid, size);

	iterativePosSum(size);
	/* int minSum = posSum(size - 1, size - 1); */
	int minSum = sumGrid[size - 1][size - 1];
	cout << "Minimum Sum: " << minSum << endl;
	cout << "Number of calls: " << callCount << endl;
	cout << "Skipped repeated calls: " << repeatCount << endl;

	cout << "\nSum:" << endl;
	printGrid(sumGrid, size);
	markPath(size - 1, size - 1);
	cout << "\nThe minimum cost path:" << endl;
	printGrid(pathGrid, size);

	return 0;
}
