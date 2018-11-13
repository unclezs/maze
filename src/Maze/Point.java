package Maze;
//迷宫信息点阵
public class Point {
	private int x=0;
	private int y=0;
	private boolean visited=false;//点是否被访问过
	private boolean path=false;//是否为路经
	private int level=0;//遍历迷宫时候等高线等级
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	private boolean iscreat=false;
	public boolean isIscreat() {
		return iscreat;
	}
	public void setIscreat(boolean iscreat) {
		this.iscreat = iscreat;
	}
	public boolean isPath() {
		return path;
	}
	public void setPath(boolean path) {
		this.path = path;
	}
	protected Point() {
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
