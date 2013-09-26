package dnd;

import java.util.Random;



public class Dice {
	
	private int num;
	private int edges;
	
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
		return num * (rand.nextInt(edges) + 1);
	}
	
	public String getPair(){
		return num + "d" + edges;
	}
	public static void main(String args[]){
		
		Dice dice = new Dice(1, 6);
		
		for(int i = 0; i < 100; i++)
			System.out.println(dice.throwDice());
	}

}
