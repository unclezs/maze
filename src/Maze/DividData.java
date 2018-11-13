package Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class DividData {
	public LinkedList<ArrayList<Point>> list;
	public Point[] p;
	public int j;
	public int col;
	public int row;
	public int[][] grid;
	public Random r;

	public DividData(LinkedList<ArrayList<Point>> list, int j, int col, int row, Random r) {
		this.list = list;
		this.j = j;
		this.col = col;
		this.row = row;
		this.r = r;
	}
}