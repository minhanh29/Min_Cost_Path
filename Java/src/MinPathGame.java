import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import java.util.Stack;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MinPathGame extends JFrame {

	private static final long serialVersionUID = -8341508091088927317L;
	private MinPathSolver solver;
	private int[][] puzzle;
	private Stack<Integer[]> path;

	private JPanel contentPane;
	private JLabel outputLabel;
	private JTextField answerField;
	private JButton checkButton;
	private JLabel[][] puzzleLabel;
	private JPanel managerPanel;

	public MinPathGame(int size) {
		solver = new MinPathSolver(size);
		puzzle = solver.getPuzzle();
		path = solver.getPath();


		int width = 50 * size + 200;
		int height = 50 * size + 50;
		if (height < 250)
			height = 250;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 50 * size, 50 * size);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(size, size, 0, 0));

		puzzleLabel = new JLabel[size][size];
		for (int i = 0; i < puzzle.length; i++)
			for (int j = 0; j < puzzle.length; j++)
			{
				JLabel label = new JLabel("" + puzzle[i][j]);
				Border border = BorderFactory.createLineBorder(Color.BLACK);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setBorder(border);
				label.setOpaque(true);
				label.setBackground(Color.WHITE);
				panel.add(label);
				puzzleLabel[i][j] = label;
			}

		managerPanel = new JPanel();
		managerPanel.setBounds(50 * size + 10, 10, 180, 200);
		contentPane.add(managerPanel);
		managerPanel.setLayout(null);

		JLabel answerLabel = new JLabel("Your Answer");
		answerLabel.setBounds(55, 5, 79, 16);
		managerPanel.add(answerLabel);

		answerField = new JTextField();
		answerField.setBounds(30, 26, 130, 26);
		managerPanel.add(answerField);
		answerField.setColumns(10);

		checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
			}
		});
		checkButton.setBounds(30, 57, 130, 29);
		managerPanel.add(checkButton);

		JButton newPuzzleButton = new JButton("New Puzzle");
		newPuzzleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPuzzle();
			}
		});
		newPuzzleButton.setBounds(30, 91, 130, 29);
		managerPanel.add(newPuzzleButton);

		JButton showButton = new JButton("Show Result");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showResult();
			}
		});
		showButton.setBounds(30, 125, 130, 29);
		managerPanel.add(showButton);

		outputLabel = new JLabel("");
		outputLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		outputLabel.setBounds(30, 157, 130, 37);
		managerPanel.add(outputLabel);
	}


	private void checkAnswer()
	{
		try {
			int input = Integer.parseInt(answerField.getText());
			if (input == solver.getMinSum())
			{
				JOptionPane.showMessageDialog(null, "Congratulations! You are so smart.");
				showResult();
			}
			else
				JOptionPane.showMessageDialog(null, "Wrong answer! Try again!");
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Invalid input!");
		}
	}


	private void newPuzzle()
	{
		MinPathGUI menu = new MinPathGUI();
		menu.setVisible(true);
		setVisible(false);
	}

	private void showResult()
	{
		int result = solver.getMinSum();
		outputLabel.setText("" + result);

		Integer[] current;
		while (!path.isEmpty())
		{
			current = path.pop();
			puzzleLabel[current[0]][current[1]].setBackground(Color.YELLOW);
		}

		// disable the check button
		checkButton.setEnabled(false);
	}
}

