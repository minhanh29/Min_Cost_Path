import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MinPathGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4641580052452274644L;
	private JPanel contentPane;
	private JTextField inputField;

	public MinPathGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(260, 149);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel sizeLabel = new JLabel("Size");
		sizeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sizeLabel.setBounds(36, 27, 38, 16);
		contentPane.add(sizeLabel);

		inputField = new JTextField();
		inputField.setBounds(86, 23, 117, 26);
		contentPane.add(inputField);
		inputField.setColumns(10);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int size = Integer.parseInt(inputField.getText());
					if (size > 0)
					{
						try {
							MinPathGame nFrame = new MinPathGame(size);
							nFrame.setVisible(true);
							setVisible(false);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else
						JOptionPane.showMessageDialog(null,  "Invalid input! Size must be positive.");
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null,  "Invalid input! Please type an integer.");
				}
			}
		});
		startButton.setBounds(86, 67, 117, 29);
		contentPane.add(startButton);
	}
}

