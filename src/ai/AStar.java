package ai;

import java.util.List;
import java.util.ArrayList;
import lowlevel.Tile;

public class AStar {
	private List<Tile> open;
	private List<Tile> closed;
	private int g; // Стоимость перемещения
	private int h; // Расстояние от точки до конечной
	private int f; // g+h
	
	public AStar(){
		open = new ArrayList<Tile>();
		closed = new ArrayList<Tile>();
	}
}
