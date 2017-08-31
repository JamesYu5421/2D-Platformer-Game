package Main;

import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {
		
		JFrame window = new JFrame("The Secrets of The Maze"); //Always title your shiz
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // lets you close the ploop when you press the red button
		window.setResizable(false); //doesnt let you make the thing bigger or small... puts you in power
		window.pack();
		window.setVisible(true);
	}

}