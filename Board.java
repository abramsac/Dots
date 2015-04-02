import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Board extends JComponent implements MouseListener{

	private static final long serialVersionUID = 1L;
	private static final int SIZE=500;
	private int boxNum = 5;
	private int boxSize = 100;
	private Color componentColor = Color.WHITE;
	private Box[][] boxes = new Box[boxNum][boxNum];
	private Player turn = Player.HUMAN;
	private Font bigFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
	private int ownedboxes;
	private int humanscore;
	private int compscore;
	private int walls;

	public Board(){

		setPreferredSize(new Dimension(SIZE, SIZE));
		addMouseListener(this);

		for (int row=0; row< boxNum; ++row) {
			for(int col=0; col< boxNum; ++col) {
				boxes[row][col] = new Box();
			}
		}
		for (int row=0; row< boxNum; ++row) {
			for(int col=0; col< boxNum; ++col) {
				Box box = boxes[row][col];	
				box.setNorth(new Wall());
				box.setWest(new Wall());

			}
		}

		for (int row=0; row< boxNum; ++row) {
			Box box = boxes[row][boxNum-1];
			box.setEast(new Wall());
		}
		for (int col=0; col< boxNum; ++col) {
			Box box = boxes[boxNum-1][col];
			box.setSouth(new Wall());
		}

		for (int row=1; row< boxNum; ++row) {
			for(int col=0; col< boxNum; ++col) {
				Box box = boxes[row][col];
				Box northbox = boxes[row-1][col];
				northbox.setSouth(box.nGetter());
			}
		}

		for (int row=0; row< boxNum; ++row) {
			for(int col=1; col< boxNum; ++col) {
				Box box = boxes[row][col];
				Box westbox = boxes[row][col-1];
				westbox.setEast(box.wGetter());
			}
		}
	}

	public void paintComponent(Graphics graphics) {
		int width  = getWidth();
		int height = getHeight();

		graphics.setColor(componentColor);
		graphics.fillRect(0,0, width, height);

		graphics.setColor(Color.BLACK);

		for (int row=0; row<=boxNum; ++row) {
			for(int col=0; col<=boxNum; ++col) {
				graphics.fillOval(row*boxSize-4, col*boxSize-4, 8, 8);
			}
		}

		for (int row=0; row<boxNum; ++row){
			for(int col=0; col<boxNum; ++col){

				Box current = boxes[row][col];

				if(current.nGetter().getPresent()){
					if (current.nGetter().playerGetter() == Player.HUMAN){
						graphics.setColor(Color.GREEN);
					}
					else{ graphics.setColor(Color.RED);}
					graphics.fillRect(col*boxSize, row*boxSize-3, boxSize, 6);

				}
				if(current.sGetter().getPresent()){
					if (current.sGetter().playerGetter() == Player.HUMAN){
						graphics.setColor(Color.GREEN);
					}
					else{ graphics.setColor(Color.RED);}
					graphics.fillRect(col*boxSize, row*boxSize + boxSize-3, boxSize, 6);

				}

				if(current.eGetter().getPresent()){
					if (current.eGetter().playerGetter() == Player.HUMAN){
						graphics.setColor(Color.GREEN);
					}
					else{ graphics.setColor(Color.RED);}
					graphics.fillRect(col*boxSize + boxSize-3, row*boxSize, 6, boxSize);

				}

				if(current.wGetter().getPresent()){
					if (current.wGetter().playerGetter() == Player.HUMAN){
						graphics.setColor(Color.GREEN);
					}
					else{ graphics.setColor(Color.RED);}
					graphics.fillRect(col*boxSize-3, row*boxSize, 6, boxSize);

				}
				graphics.setColor(Color.BLACK);
				if(current.ownerGetter() == Player.HUMAN){
					graphics.setFont(bigFont);
					graphics.drawString("H", (col+1)*boxSize-(2*boxSize/3), (row+1)*boxSize-(boxSize/4));
				}
				else if(current.ownerGetter() == Player.COMPUTER){
					graphics.setFont(bigFont);
					graphics.drawString("C", (col+1)*boxSize-(2*boxSize/3), (row+1)*boxSize-(boxSize/4));

				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		ownedboxes = 0;
		int x = e.getX();
		int y = e.getY();
		int row = y/boxSize;
		int col = x/boxSize;
		int ry = y%boxSize;
		int rx = x%boxSize;

		Box clicked = boxes[row][col];
		Wall region = clicked.regionGetter(rx,ry);
		if(humanscore + compscore == 25){
			return;
		}
		else{
			if(region.getPresent()){
				return;
			}
			else{
				region.setPresent();
				region.setWallOwner(turn);
				if (region == clicked.nGetter() && row != 0){
					boxes[row-1][col].sGetter().setPresent();
					boxes[row-1][col].sGetter().setWallOwner(turn);
					if (boxes[row-1][col].checkOwned()) {
						boxes[row-1][col].setBoxOwner(turn);
						ownedboxes++;
						humanscore++;
						repaint();
					}
				}
				if (region == clicked.sGetter() && row != boxNum-1){
					boxes[row+1][col].nGetter().setPresent();
					boxes[row+1][col].nGetter().setWallOwner(turn);
					if (boxes[row+1][col].checkOwned()) {
						boxes[row+1][col].setBoxOwner(turn);
						ownedboxes++;
						humanscore++;
						repaint();
					}
				}
				if (region == clicked.eGetter() && col != boxNum-1){
					boxes[row][col+1].wGetter().setPresent();
					boxes[row][col+1].wGetter().setWallOwner(turn);
					if (boxes[row][col+1].checkOwned()) {
						boxes[row][col+1].setBoxOwner(turn);
						ownedboxes++;
						humanscore++;
						repaint();
					}
				}
				if (region == clicked.wGetter() && col != 0){
					boxes[row][col-1].eGetter().setPresent();
					boxes[row][col-1].eGetter().setWallOwner(turn);
					if (boxes[row][col-1].checkOwned()) {
						boxes[row][col-1].setBoxOwner(turn);
						ownedboxes++;
						humanscore++;
						repaint();
					}
				}
				repaint();
				if (clicked.checkOwned()){
					clicked.setBoxOwner(turn);
					ownedboxes++;
					humanscore++;
					repaint();
				}
				if (turn == Player.HUMAN && ownedboxes > 0){
					turn = Player.HUMAN;
				}
				else if (turn == Player.HUMAN && ownedboxes == 0){
					turn = Player.COMPUTER;
					computersBigDay();
				}
				else if (turn == Player.COMPUTER && ownedboxes > 0){
					turn = Player.COMPUTER;
					computersBigDay();
				}
				else{
					turn = Player.HUMAN;
				}
			}
		}
	}

	public void computersBigDay(){
		ownedboxes = 0;
		Random generator = new Random();
		int randomX = generator.nextInt( 500 );
		int randomY = generator.nextInt( 500 );
		int comprow = randomX/boxSize;
		int compcol = randomY/boxSize;
		int compry = randomY%boxSize;
		int comprx = randomX%boxSize;
		Box compbox = boxes[comprow][compcol];
		Wall region = compbox.regionGetter(comprx,compry);
		if (humanscore + compscore == 25){
			return;
		}
		else{
			if(region.getPresent()){
				computersBigDay();
			}
			else{
				region.setPresent();
				region.setWallOwner(turn);
				if (region == compbox.nGetter() && comprow != 0){
					boxes[comprow-1][compcol].sGetter().setPresent();
					boxes[comprow-1][compcol].sGetter().setWallOwner(turn);
					if (boxes[comprow-1][compcol].checkOwned()) {
						boxes[comprow-1][compcol].setBoxOwner(turn);
						ownedboxes++;
						compscore++;
						repaint();
					}
				}
				if (region == compbox.sGetter() && comprow != boxNum-1){
					boxes[comprow+1][compcol].nGetter().setPresent();
					boxes[comprow+1][compcol].nGetter().setWallOwner(turn);
					if (boxes[comprow+1][compcol].checkOwned()) {
						boxes[comprow+1][compcol].setBoxOwner(turn);
						ownedboxes++;
						compscore++;
						repaint();
					}
				}
				if (region == compbox.eGetter() && compcol != boxNum-1){
					boxes[comprow][compcol+1].wGetter().setPresent();
					boxes[comprow][compcol+1].wGetter().setWallOwner(turn);
					if (boxes[comprow][compcol+1].checkOwned()) {
						boxes[comprow][compcol+1].setBoxOwner(turn);
						ownedboxes++;
						compscore++;
						repaint();
					}
				}
				if (region == compbox.wGetter() && compcol != 0){
					boxes[comprow][compcol-1].eGetter().setPresent();
					boxes[comprow][compcol-1].eGetter().setWallOwner(turn);
					if (boxes[comprow][compcol-1].checkOwned()) {
						boxes[comprow][compcol-1].setBoxOwner(turn);
						ownedboxes++;
						compscore++;
						repaint();
					}
				}
				repaint();
				if (compbox.checkOwned()){
					compbox.setBoxOwner(turn);
					ownedboxes++;
					compscore++;
					repaint();
				}
				if (ownedboxes > 0){
					turn = Player.COMPUTER;
					computersBigDay();
				}
				else {
					turn = Player.HUMAN;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public Player getTurn(){
		return turn;
	}
	public int getCompScore(){
		return compscore;
	}
	public int getHumanScore(){
		return humanscore;
	}

}
