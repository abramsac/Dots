import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Dots{

	private JLabel turn;
	private static JTextField turnres;
	private JLabel compscore;
	private static JTextField compscoreres;
	private JLabel humscore;
	private static JTextField humscoreres;
	private static Board boardmaker;


	public Dots(){

		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();

		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1.0;
		turn = new JLabel("Turn");
		gridBag.setConstraints(turn, cons);
		cons.gridwidth = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		turnres = new JTextField();
		gridBag.setConstraints(turnres, cons);
		cons.gridwidth = GridBagConstraints.RELATIVE;
		compscore = new JLabel("Computer Score");
		gridBag.setConstraints(compscore, cons);
		cons.gridwidth = GridBagConstraints.REMAINDER;
		compscoreres = new JTextField();
		gridBag.setConstraints(compscoreres, cons);
		cons.gridwidth = GridBagConstraints.RELATIVE;
		humscore = new JLabel("Human Score");
		gridBag.setConstraints(humscore, cons);
		cons.gridwidth = GridBagConstraints.REMAINDER;
		humscoreres = new JTextField();
		gridBag.setConstraints(humscoreres, cons);
		cons.weightx = 0.0;
		boardmaker = new Board();
		gridBag.setConstraints(boardmaker, cons);

		JFrame frame = new JFrame("Dots Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(gridBag);
		frame.add(turn);
		frame.add(turnres);
		frame.add(compscore);
		frame.add(compscoreres);
		frame.add(humscore);
		frame.add(humscoreres);
		frame.add(boardmaker);

		frame.pack();
		frame.setLocation(20,50); // pixels, relative to upper left of screen
		frame.setVisible(true);
		
		humscoreres.setText("0");
		compscoreres.setText("0");
		turnres.setText("HUMAN");
	}
	public static void main(String[] args){
		Dots board = new Dots();
	}
	public static String stringmaker(Player player){
		if (player == Player.HUMAN){
			return "HUMAN";
		}
		else{
			return "COMPUTER";
		}
	}
	
	public static Board getBoardmaker(){
		return boardmaker;
	}

	
	public static void humanScoreSetter(){
		humscoreres.setText("" + boardmaker.getHumanScore());
	}
	public static void compScoreSetter(){
		compscoreres.setText("" + boardmaker.getCompScore());
	}
	public static void turnSetter(Player player){
		turnres.setText(stringmaker(player));
	}
	
}
