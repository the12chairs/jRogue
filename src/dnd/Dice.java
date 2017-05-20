package dnd;

import java.util.Random;

/**
 * Class for dice implementation
 */
public class Dice {
	
	private int num; // How many dices you trow at same time
	private int edges; // Number of edges of each of dices

	private Random rand;

	public Dice(int num, int edges){
		
		this.num = num;
		this.edges = edges;
	
	}

	public Dice getDice(){
		return this;
	}
	
	public int throwDice(){
		rand = new Random();
		int sum = 0;
		for(int i = 0; i < num; i++){
			sum += (rand.nextInt(edges)+1);
		}
			return sum;
	}

	public String getPair(){
		return num + "d" + edges;
	}
}
