package Maze;

import java.util.ArrayList;
import java.util.Stack;

import javafx.stage.Stage;

public	class MyMazes {
		public static Divid Maze;//创建的迷宫对象
		public static Stage stages;//用于打开迷宫文件的窗口绑定
		public static Stack<Integer> stack=new Stack<Integer>();//用于单步寻路贮存方向
		//用于存放遍历时候等高线的点
		public static ArrayList<ArrayList<Point>> listPath=new  ArrayList<ArrayList<Point>>();
	}