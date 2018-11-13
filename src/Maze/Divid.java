package Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Divid {
	public DividData data = new DividData(new LinkedList<ArrayList<Point>>(), 0, 0, 0, new Random());
	//根据宽高构造
	Divid(int col,int rol){
		this.data.col=col;
		this.data.row=rol;
		this.data.p=new Point[col*data.row];
		for(int i=0;i<col*data.row;i++)
			data.p[i]=new Point();
		this.data.grid=new int[col][data.row];
		for(int i=0;i<col;i++) {
			data.grid[i][0]=1;
			data.grid[i][col-1]=1;
		}
		for(int j=0;j<data.row;j++) { 
			data.grid[0][j]=1;
			data.grid[data.row-1][j]=1;
		}
	}
	//根据Point数组里的所有点是否为路径，更新迷宫二维数组
	public void creatpath() {
		for(int i=0;i<data.col;i++) {
			for(int j=0;j<data.row;j++)
			{
					if(data.grid[i][j]==2)
						data.grid[i][j]=0;
			}
		}
		for(int i=0;i<data.col;i++) {
			for(int j=0;j<data.row;j++)
			{
				for(int k=0;k<data.col*data.row;k++)
				if(i==data.p[k].getX()&&j==data.p[k].getY()&&data.p[k].isPath()) 
					data.grid[i][j]=2;
			}
		}
	}
	//控制台打印迷宫点阵
	public void print() {
		for(int i=0;i<data.col;i++) {
			for(int j=0;j<data.row;j++)
			{
				System.out.print(data.grid[i][j]+" ");
			}
			System.out.println();
		}
	}
	/*
	 * 随机开墙
	 */
	public void openRandomDoor(int x1,int y1,int x2,int y2) {
		int pos;
		 if(x1==x2) {
			pos=y1+data.r.nextInt((y2-y1)/2+1)*2;
			data.grid[x1][pos]=0;
		}
		 else if(y1==y2) {
			 pos=x1+data.r.nextInt((x2-x1)/2+1)*2;
			 data.grid[pos][y1]=0;
		 }else
			 System.out.println("error");
	}
	//递归画墙生成迷宫，宽高为奇数
	public void printMaze(int x,int y,int width,int height) {
		int xpos,ypos;
		if(width<=2||height<=2)
			return;
		xpos=x+data.r.nextInt(height/2)*2+1;
		for(int i=y;i<y+width;i++) {
			data.grid[xpos][i]=1;
		}
		ypos=y+data.r.nextInt(width/2)*2+1;
		for(int i=x;i<x+height;i++) {
			data.grid[i][ypos]=1;
		}
        int isClosed = data.r.nextInt(4) + 1;
        switch (isClosed) 
        {
        case 1:
            openRandomDoor(xpos + 1, ypos, x + height - 1, ypos);// 2
            openRandomDoor(xpos, ypos + 1, xpos, y + width - 1);// 3
            openRandomDoor(x, ypos, xpos - 1, ypos);// 4
            break;
        case 2:
            openRandomDoor(xpos, ypos + 1, xpos, y + width - 1);// 3
            openRandomDoor(x, ypos, xpos - 1, ypos);// 4
            openRandomDoor(xpos, y, xpos, ypos - 1);// 1
            break;
        case 3:
            openRandomDoor(x, ypos, xpos - 1, ypos);// 4
            openRandomDoor(xpos, y, xpos, ypos - 1);// 1
            openRandomDoor(xpos + 1, ypos, x + height - 1, ypos);// 2
            break;
        case 4:
            openRandomDoor(xpos, y, xpos, ypos - 1);// 1
            openRandomDoor(xpos + 1, ypos, x + height - 1, ypos);// 2
            openRandomDoor(xpos, ypos + 1, xpos, y + width - 1);// 3
            break;
        default:
            break;
        }
        printMaze(x, y, ypos-y, xpos-x);
        printMaze(xpos+1, y, ypos-y, height-xpos+x-1);
        printMaze(x, ypos+1, width-ypos+y-1, xpos-x);
        printMaze(xpos+1, ypos+1, width-ypos+y-1, height-xpos+x-1);
        
	}
	//判断方向是否可走
	public boolean ishasdir(int x, int y, int dir) {
		switch (dir) {
		case 1:
			if (x > 1&&data.grid[x - 1][y] == 0 )
				return true;
			else
				return false;
		case 2:
			if (x < data.col - 2&&data.grid[x + 1][y] == 0)
				return true;
			else
				return false;
		case 3:
			if ( y < data.row - 2&&data.grid[x][y + 1] == 0)
				return true;
			else
				return false;
		case 4:
			if ( y > 1 &&data.grid[x][y - 1] == 0)
				return true;
			else
				return false;
		default:
			return false;
		}
	}
	//随机获取该点一个可以访问的方向
	public int getARandomdir(int x,int y) {
		int dir;
		int flag=0;
		int times1=0;
		int times2=0;
		int times3=0;
		int times4=0;
		do {
			dir=data.r.nextInt(4)+1;
			if(dir==1&&times1==1||dir==2&&times2==1||dir==3&&times3==1||dir==4&&times4==1)
				continue;
			if(ishasdir(x,y,dir)&&isVisited(x, y, dir)==false)
				return dir;
			if(dir==1) {
				if(times1==0){
					flag++;
				}
				times1++;
			}
			
			if(dir==2) {
				if(times2==0){
					flag++;
				}
				times2++;
			}
			if(dir==3) {
				if(times3==0){
					flag++;
				}
				times3++;
			}
			if(dir==4) {
				if(times4==0){
					flag++;
				}
				times4++;
			}
		}while(flag!=4);
		return -1;
	}
	//返回点的所有可以访问方向
	public int[] getALLRandomdir(int x,int y) {
		int[] dirs=new int[]{0,0,0,0};
		int i=0;
		int dir;
		int flag=0;
		int times1=0;
		int times2=0;
		int times3=0;
		int times4=0;
		do {
				dir=data.r.nextInt(4)+1;
			if((dir==1&&times1==1)||(dir==2&&times2==1)||(dir==3&&times3==1)||(dir==4&&times4==1))
				continue;
			if(ishasdir(x,y,dir)&&isVisited(x, y, dir)==false)
				dirs[i++]=dir;
			if(dir==1) {
				if(times1==0){
					flag++;
				}
				times1++;
			}
			
			if(dir==2) {
				if(times2==0){
					flag++;
				}
				times2++;
			}
			if(dir==3) {
				if(times3==0){
					flag++;
				}
				times3++;
			}
			if(dir==4) {
				if(times4==0){
					flag++;
				}
				times4++;
			}
		}while(flag!=4);
		return dirs;
	}
	//指定方向的点是否被访问过
	public boolean isVisited(int x,int y,int dir) {
		switch (dir) {
		case 1:
//			if(x!=1)
			 x--;
			break;
		case 2:
			x++;
			break;
		case 3:
			y++;
			break;
		case 4:
//			if(y!=1)
			 y--;
			break;
		}
		for(int i=0;i<data.p.length;i++) {
			if(x==data.p[i].getX()&&y==data.p[i].getY())
				if(data.p[i].isVisited())
					return true;
		}
		return false;	
	}
	//寻找随机路径，不一定最短
	public void findPath(int sx,int sy,int ex,int ey) {
		int x = sx;
		int y = sy;
		int i=0;
		Point pt=new Point();
		data.p[i].setVisited(true);
		Stack<Point> pos= new Stack<>();
		do{
			if (getARandomdir(x, y)!=-1) {
					data.p[i].setX(x);
					data.p[i].setY(y);
					data.p[i].setVisited(true);
					data.p[i].setPath(true);
					pos.push(data.p[i]);
					i++;
					switch (getARandomdir(x, y)) {
					case 1:
						x--;
						break;
					case 2:
						x++;
						break;
					case 3:
						y++;
						break;
					case 4:
						y--;
						break;
					}
					
			} else {
				int flag=1;
				for(int k=0;k<data.col*data.row;k++)
					if(x==data.p[k].getX()&&y==data.p[k].getY()&&data.p[k].isPath()) {
						data.p[k].setX(x);
						data.p[k].setY(y);
						data.p[k].setPath(false);
						data.p[k].setVisited(true);
						flag=-1;
					}
				if(flag==1) {		
					data.p[i].setX(x);
					data.p[i].setY(y);
					data.p[i].setPath(false);
					data.p[i].setVisited(true);
					i++;
				}
				pt=pos.pop();
				x=pt.getX();
				y=pt.getY();
			}
		}while(!(x==ex&&y==ey));
		data.p[i].setX(x);
		data.p[i].setY(y);
		data.p[i].setVisited(true);
		data.p[i].setPath(true);
	}
	//单步寻路判断点是否可走
	public boolean oneFind(int x,int y,int flags) {
			if (flags==1) {
					data.p[data.j].setX(x);
					data.p[data.j].setY(y);
					data.p[data.j].setVisited(true);
					data.p[data.j].setPath(true);
					data.j++;
					return true;
			} 
			if(flags==2) {
				int flag=1;
				for(int k=0;k<data.p.length;k++)
					if(x==data.p[k].getX()&&y==data.p[k].getY()) {
						data.p[k].setPath(false);
						data.p[k].setVisited(true);
						flag=-1;
					}
				if(flag==1) {		
					data.p[data.j].setX(x);
					data.p[data.j].setY(y);
					data.p[data.j].setPath(false);
					data.p[data.j].setVisited(true);
					data.j++;
				}
				return false;
			}
			return true;
	}
	//初始化迷宫所有属性
	public void firstAll() {
		data.j=0;
		for(int i=0;i<data.col;i++) {
			for(int j=0;j<data.row;j++)
			{
				if(data.grid[i][j]==2)
					data.grid[i][j]=0;
				else if(data.grid[i][j]==-2||data.grid[i][j]>=5)
					data.grid[i][j]=0;
			}
		}
		for(int i=0;i<data.p.length;i++) {
			data.p[i].setPath(false);
			data.p[i].setVisited(false);
			data.p[i].setX(0);
			data.p[i].setY(0);
		}
			
	}
	//判断迷宫是否为初始化状态
	public boolean isfirstnow() {
		for(int i=0;i<data.col;i++) {
			for(int j=0;j<data.row;j++)
			{
					if(data.grid[i][j]==2)
						return false;
					else if(data.grid[i][j]==-2||data.grid[i][j]>=5)
						return false;
			}
		}
		return true;
	}
	/*
	 * 用于多条路开墙时判断该点是否适合开墙
	 * 点为墙且上方下方或者左右方向为路径的适合开点
	 * 或者只有一个方向为路经，其他情况返回False
	 */
	public boolean isthreepath(int x,int y) {
		boolean flags=true;
		int flag=0;
		int flag1=0;//左右情况
		int flag2=0;//上下情况
		int flag3=0;//上左情况
		int flag4=0;//上右情况
		int flag5=0;//下左情况
		int flag6=0;//下右情况
		if(x==0||y==0||x==data.col-1||y==data.row-1)
			return false;
		if(data.grid[x-1][y]==0) {
			flag++;
			flag2++;
			flag3++;
			flag4++;
		}
		if(data.grid[x+1][y]==0) {
			flag++;
			flag2++;
			flag5++;
			flag6++;
		}
		if(data.grid[x][y-1]==0) {
			flag++;
			flag1++;
			flag3++;
			flag5++;
		}
		if(data.grid[x][y+1]==0) {
			flag++;
			flag1++;
			flag4++;
			flag6++;
		}
		if(flag>=3)
			return false;
		if(flag==1||flag==0)
			flags=false;
		if(flag1==2||flag2==2)
			flags=true;
		if(flag3==2||flag4==2||flag5==2||flag6==2)
			flags=false;
		return flags;
	}
	/*
	 * 获取最短路径
	 * 根据终点的等高线等级
	 * 判断终点四周的为路径点的点的等高线等级，如果比自己低一级就将这点设置成最短路径点
	 * 跳入该点，继续上步操作
	 * 当跳入点的等高线等级为最高级跳出循环
	*Get the shortest path
	*Contour level according to endpoints
	*Determine the contour level around the end point as the path point. If it is lower than oneself, set this point as the shortest path point.
	*Jump into the point and continue to step up.
	*When the contour level of the jump point is the highest level, jump out of the loop.
	*/
	public void shortPath(int ex, int ey, int sx, int sy) {
		int level = data.grid[ex][ey];
		while (level != 5) {
			if (data.grid[ex - 1][ey] == level - 1) {
				data.grid[ex - 1][ey] = -2;
				level--;
				ex--;
			} else if (data.grid[ex + 1][ey] == level - 1) {
				data.grid[ex + 1][ey] = -2;
				level--;
				ex++;
			} else if (data.grid[ex][ey + 1] == level - 1) {
				data.grid[ex][ey + 1] = -2;
				level--;
				ey++;
			} else if (data.grid[ex][ey - 1] == level - 1) {
				data.grid[ex][ey - 1] = -2;
				level--;
				ey--;
			}
		}
		for (int i = 0; i < data.col; i++) {
			for (int j = 0; j < data.row; j++) {
				if (data.grid[i][j] == 0 || data.grid[i][j] == 1 || data.grid[i][j] == -2)
					continue;
				else
					data.grid[i][j] = 0;
			}
		}
	}
	/*
	 *迷宫生成后只有一条路经
	 *用于随机开辟合适数量的墙，生成多条路径 
	 */
	public void openArandomDoor() {
		int flag=0;
		int i,j;
		while(true) {
			i=data.r.nextInt(data.col);
			j=data.r.nextInt(data.row);
					if(data.grid[i][j]==1)
						if(isthreepath(i, j)) {
							data.grid[i][j]=0;
							flag++;
						}
					if(data.col<=7&&flag==1) {
						return;
					}
					if(flag>data.col/2)
						return;
		}
	}	
	/*
	 * 遍历迷宫
	 * 每次将同一等高线上的每个点的每个可行方向获取出来
	 * 将每个可行方向的点放入下一个等高线设置为访问过
	 * 所有点被访问过的时候跳出循环
	 */
	public void traverlMaze(int sx,int sy,int ex,int ey) {
		int x = sx;
		int y = sy;
		int i=1;
		int k=0;
		ArrayList<Point> lists=new ArrayList<Point>();//存放同一等高线的点
		int level=1;//等高线等级
		data.p[0].setLevel(0);
		data.p[0].setVisited(true);
		data.p[0].setX(x);
		data.p[0].setY(y);
		lists.add(data.p[0]);
		data.list.add(lists);
		lists=new ArrayList<Point>();
		while (i != getpassnum()) {
			for (int m = 0; m < data.list.get(k).size(); m++) {
				int[] dir = new int[4];
				x = data.list.get(k).get(m).getX();
				y = data.list.get(k).get(m).getY();
				dir = getALLRandomdir(x, y);
				for (int j = 0; j < 4; j++) {
					if (dir[j] == 0)
						continue;
					if (dir[j] == 1) {
						data.p[i].setLevel(level);
						data.p[i].setVisited(true);
						data.p[i].setY(y);
						data.p[i].setX(x-1);
						lists.add(data.p[i]);
						i++;
					}
					if (dir[j] == 2) {
						data.p[i].setLevel(level);
						data.p[i].setVisited(true);
						data.p[i].setY(y);
						data.p[i].setX(x+1);
						lists.add(data.p[i]);
						i++;
					}
					if (dir[j] == 3) {
						data.p[i].setLevel(level);
						data.p[i].setVisited(true);
						data.p[i].setY(y+1);
						data.p[i].setX(x);
						lists.add(data.p[i]);
						i++;
					}
					if (dir[j] == 4) {
						data.p[i].setLevel(level);
						data.p[i].setVisited(true);
						data.p[i].setY(y-1);
						data.p[i].setX(x);
						lists.add(data.p[i]);
						i++;
					}
				}
			}
			level++;
			data.list.add(lists);
			lists = new ArrayList<Point>();
			k++;
		}
	}
	//获取有多少个点为路经点
	public int getpassnum() {
		int num=0;
		for(int i=0;i<data.col;i++)
			for(int j=0;j<data.row;j++) {
				if(data.grid[i][j]==0)
					num++;
			}
		return num;
	}
}