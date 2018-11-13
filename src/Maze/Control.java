package Maze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Control implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	String txtpath = "C://";
	FileChooser fileChooser;
	int flagmouse=0;//老鼠是否创建
	int flag = 0;// 判断是否多次点击遍历
	int flagonestep = 0;
	int flagFile = 0;
	int widths;
	int heights;
	int MouseX;
	int MouseY;
	int sx = 0, sy = 0, ex = 0, ey = 0;
	@FXML
	private Button creat;
	@FXML
	private Button shortpath;
	@FXML
	private Button stxt;
	@FXML
	private Button dtxt;
	@FXML
	private Button first;
	@FXML
	private Button travel;
	@FXML
	private Button onestep;
	@FXML
	private Button creatM;
	@FXML
	private Button up;
	@FXML
	private Button down;
	@FXML
	private Button right;
	@FXML
	private Button left;
	@FXML
	private TextField width;
	@FXML
	private TextField Sx;
	@FXML
	private TextField Sy;
	@FXML
	private TextField Ex;
	@FXML
	private TextField cx;
	@FXML
	private TextField cy;
	@FXML
	private TextField Ey;
	@FXML
	private TextField height;
	@FXML
	private Button findonepath;
	@FXML
	private Pane MazeBox;
	@FXML
	private Text result;
	@FXML
	private VBox XLine;
	@FXML
	private HBox YLine;
	Divid Mazes;
	//创建迷宫
	public void controlCreat(ActionEvent event) {
		creat.setOnMouseClicked(e -> {
			this.sx = Integer.parseInt(Sx.getText());
			this.sy = Integer.parseInt(Sy.getText());
//			this.ex = Integer.parseInt(Ex.getText());
//			this.ey = Integer.parseInt(Ey.getText());
			this.widths = Integer.parseInt(width.getText());
			this.heights = Integer.parseInt(height.getText());
			MyMazes.Maze = new Divid(widths, heights);
//			if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0) || MyMazes.Maze.grid[ex][ey] == 1) {
//				result.setFill(Color.RED);
//				result.setText("终点不可达！");
//				return;
//			}
			if ((sy < 0 || sx < 0) || (sy >= (widths - 1) || sx >= (heights - 1)) || MyMazes.Maze.data.grid[sx][sy] == 1) {
				result.setFill(Color.RED);
				result.setText("起点不合法！");
				return;
			}
			if ((widths != heights) || (widths % 2 == 0 || heights % 2 == 0)) {
				result.setFill(Color.RED);
				result.setText("请按照提示输入宽高！");
				return;
			}

			MazeBox.getChildren().clear();
			VBox vb1 = new VBox();
			MyMazes.Maze.printMaze(1, 1, widths - 2, heights - 2);
			MyMazes.Maze.openArandomDoor();
			for (int i = 0; i < widths; i++) {
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					ImageView img = null;
					if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb1.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb1);
			result.setFill(Color.GREEN);
			result.setText("迷宫已生成！");
		});

	}
	//寻找路径。非最短
	public void controlFind(ActionEvent event) {
		findonepath.setOnMouseClicked(e -> {
			if (!MyMazes.Maze.isfirstnow()) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			if (Ex.getText().equals("") || Ey.getText().equals("")) {
				result.setFill(Color.RED);
				result.setText("未输入终点坐标！");
				return;
			}
			this.sx = Integer.parseInt(Sx.getText());
			this.sy = Integer.parseInt(Sy.getText());
			this.ex = Integer.parseInt(Ex.getText());
			this.ey = Integer.parseInt(Ey.getText());
			if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0) || MyMazes.Maze.data.grid[ex][ey] == 1) {
				result.setFill(Color.RED);
				result.setText("终点不可达！");
				return;
			}
			if ((sy < 0 || sx < 0) || (sy >= (widths - 1) || sx >= (heights - 1)) || MyMazes.Maze.data.grid[sx][sy] == 1) {
				result.setFill(Color.RED);
				result.setText("起点不合法！");
				return;
			}
			MazeBox.getChildren().clear();
			VBox vb2 = new VBox();
			MyMazes.Maze.findPath(sx, sy, ex, ey);
			MyMazes.Maze.creatpath();
			for (int i = 0; i < widths; i++) {
				ImageView img = null;
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					if (i == sx && j == sy)
						img = autoImg("Images/start.jpg", 800, 800, i, j);
					else if (i == ex && j == ey)
						img = autoImg("Images/end.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 2)
						img = autoImg("Images/path.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb2.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb2);
			MyMazes.Maze.firstAll();
			result.setFill(Color.GREEN);
			result.setText("已成功找到路径！");
		});

	}
	//生成自动适应大小的ImageView对象
	public ImageView autoImg(String src, int width1, int height1, int x, int y) {
		ImageView img = new ImageView(src);
		img.setOnMouseClicked(e -> {
			cx.clear();
			cy.clear();
			cx.setText(x + "");
			cy.setText(y + "");
		});
		img.setFitWidth(width1 / (widths));
		img.setFitHeight(height1 / (heights));
		return img;
	}
	//生成自动大小的Pane，用于遍历
	public Pane autoPane(String src, int width1, int height1, int x, int y, int num) {
		VBox pane = new VBox();
		pane.setOnMouseClicked(e -> {
			cx.clear();
			cy.clear();
			cx.setText(x + "");
			cy.setText(y + "");
		});
		Text text = new Text(num + "");
		text.setFill(Color.RED);
		text.setFont(Font.font(width1 / (widths * 2)));
		pane.getChildren().add(text);
		pane.setStyle("-fx-border-color:red;");
		pane.setMaxWidth(width1 / (widths));
		pane.setMinWidth(width1 / (widths));
		pane.setMaxHeight(height1 / (heights));
		pane.setMaxHeight(height1 / (heights));
		return pane;
	}
	//方向键点击移动迷宫老鼠
	public void dirclick(ActionEvent Event) {
		up.setOnMouseClicked(e -> {
			if(flagmouse!=1) {
				result.setFill(Color.RED);
				result.setText("请先创建老鼠！");
				return;
			}
			MoveMouse(MouseX - 1, MouseY);
		});
		down.setOnMouseClicked(e -> {
			if(flagmouse!=1) {
				result.setFill(Color.RED);
				result.setText("请先创建老鼠！");
				return;
			}
			MoveMouse(MouseX + 1, MouseY);
		});
		left.setOnMouseClicked(e -> {
			if(flagmouse!=1) {
				result.setFill(Color.RED);
				result.setText("请先创建老鼠！");
				return;
			}
			MoveMouse(MouseX, MouseY - 1);
		});
		right.setOnMouseClicked(e -> {
			if(flagmouse!=1) {
				result.setFill(Color.RED);
				result.setText("请先创建老鼠！");
				return;
			}
			MoveMouse(MouseX, MouseY + 1);
		});
	}
	//创建老鼠，可以移动
	public void creatMouse() {
		creatM.setOnMouseClicked(e -> {
			if (!MyMazes.Maze.isfirstnow()) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			MazeBox.getChildren().clear();
			VBox vb1 = new VBox();
			this.MouseX = Integer.parseInt(Sx.getText());
			this.MouseY = Integer.parseInt(Sy.getText());
			this.ex = Integer.parseInt(Ex.getText());
			this.ey = Integer.parseInt(Ey.getText());
			for (int i = 0; i < widths; i++) {
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					ImageView img = null;
					if (i == MouseX && j == MouseY)
						img = autoImg("Images/start.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb1.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb1);
			flagmouse=1;
			result.setFill(Color.GREEN);
			result.setText("创建老鼠成功！！");
		});
	}
	//移动迷宫老鼠
	public void MoveMouse(int x, int y) {
		try {
			if (MyMazes.Maze.data.grid[x][y] == 1)
				return;
		} catch (Exception e) {
			return;
		}
		MazeBox.getChildren().clear();
		VBox vb1 = new VBox();
		this.MouseX = x;
		this.MouseY = y;
		for (int i = 0; i < widths; i++) {
			HBox hb = new HBox();
			for (int j = 0; j < heights; j++) {
				ImageView img = null;
				if (i == x && j == y)
					img = autoImg("Images/start.jpg", 800, 800, i, j);
				else if (MyMazes.Maze.data.grid[i][j] == 1)
					img = autoImg("Images/black.jpg", 800, 800, i, j);
				else if (MyMazes.Maze.data.grid[i][j] == 0)
					img = autoImg("Images/white.jpg", 800, 800, i, j);
				hb.getChildren().add(img);
			}
			vb1.getChildren().add(hb);
		}
		MazeBox.getChildren().add(vb1);
		try {
			if (MouseX == ex && MouseY == ey)
				result.setText("恭喜通过了！");
		} catch (Exception e) {
			return;
		}
	}
	//导入迷宫
	@SuppressWarnings("unused")
	public void openFile(ActionEvent event) {
		dtxt.setOnMouseClicked(e -> {
			File lastpath = new File(txtpath);
			fileChooser = new FileChooser();// 构建一个文件选择器实例
			fileChooser.setTitle("选择迷宫");
			fileChooser.setInitialDirectory(lastpath);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
			File selectedFile = fileChooser.showOpenDialog(MyMazes.stages);
			String path = selectedFile.getParentFile().getPath();
			this.txtpath = path;
			String[] txtmaze = null;
			int[][] txtmazes;
			try {
				BufferedReader bf = new BufferedReader(new FileReader(selectedFile));
				int num = 0;
				int x = 0;
				String str = "";
				while ((str = bf.readLine()) != null) {
					txtmaze = str.split(" ");
					for (String t : txtmaze)
						num++;
				}
				bf.close();
				BufferedReader bfs = new BufferedReader(new FileReader(selectedFile));
				widths = (int) Math.pow(num, 0.5);
				heights = (int) Math.pow(num, 0.5);
				MyMazes.Maze = new Divid(widths, heights);
				txtmazes = new int[widths][heights];
				while ((str = bfs.readLine()) != null) {
					txtmaze = str.split(" ");
					int j = 0;
					for (String t : txtmaze) {
						txtmazes[x][j] = Integer.parseInt(t);
						j++;
					}
					x++;
				}
				for (int i = 0; i < widths; i++)
					for (int j = 0; j < heights; j++) {
						MyMazes.Maze.data.grid[i][j] = txtmazes[i][j];
					}
				MazeBox.getChildren().clear();
				VBox vb1 = new VBox();
				for (int i = 0; i < widths; i++) {
					HBox hb = new HBox();
					for (int j = 0; j < heights; j++) {
						ImageView img = null;
						if (MyMazes.Maze.data.grid[i][j] == 1)
							img = autoImg("Images/black.jpg", 800, 800, i, j);
						else if (MyMazes.Maze.data.grid[i][j] == 0)
							img = autoImg("Images/white.jpg", 800, 800, i, j);
						hb.getChildren().add(img);
					}
					vb1.getChildren().add(hb);
				}
				MazeBox.getChildren().add(vb1);
				bfs.close();
				result.setFill(Color.GREEN);
				result.setText("导入成功！");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				flagFile = 1;
				this.sx = Integer.parseInt(Sx.getText());
				this.sy = Integer.parseInt(Sy.getText());
				this.ex = Integer.parseInt(Ex.getText());
				this.ey = Integer.parseInt(Ey.getText());
				this.widths = MyMazes.Maze.data.grid[0].length;
				System.out.println(widths);
				this.heights = this.widths;
				if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0)
						|| MyMazes.Maze.data.grid[ex][ey] == 1) {
					result.setFill(Color.RED);
					result.setText("终点不可达！");
					return;
				}
				if ((sy < 0 || sx < 0) || (sy >= (widths - 1) || sx >= (heights - 1))
						|| MyMazes.Maze.data.grid[sx][sy] == 1) {
					result.setFill(Color.RED);
					result.setText("起点不合法！");
					return;
				}
			}
		});
	}
	//保存迷宫
	public void SaveMaze() {
		stxt.setOnMouseClicked(e -> {
			if (!MyMazes.Maze.isfirstnow()) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			File lastpath = new File(txtpath);
			fileChooser = new FileChooser();// 构建一个文件选择器实例
			fileChooser.setTitle("保存文件");
			fileChooser.setInitialDirectory(lastpath);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("*", "*.txt"));
			File selectedFile = fileChooser.showSaveDialog(MyMazes.stages);
			String path = selectedFile.getParentFile().getPath();
			this.txtpath = path;
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile, true));
				String str = "";
				for (int i = 0; i < widths; i++) {
					for (int j = 0; j < heights; j++)
						str += MyMazes.Maze.data.grid[i][j] + " ";
					str += "\r\n";
					bw.write(str);
					str = "";
				}
				bw.close();
				result.setFill(Color.GREEN);
				result.setText("保存成功！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	//单步寻路
	public void oneStepFindPath() {
		onestep.setOnMouseClicked(e -> {
			if (flagFile == 1) {
				this.sx = Integer.parseInt(Sx.getText());
				this.sy = Integer.parseInt(Sy.getText());
				this.ex = Integer.parseInt(Ex.getText());
				this.ey = Integer.parseInt(Ey.getText());
				this.widths = MyMazes.Maze.data.grid[0].length;
				System.out.println(widths);
				this.heights = this.widths;
				if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0)
						|| MyMazes.Maze.data.grid[ex][ey] == 1) {
					result.setFill(Color.RED);
					result.setText("终点不可达！");
					return;
				}
				if ((sy < 0 || sx < 0) || (sy >= (widths - 1) || sx >= (heights - 1))
						|| MyMazes.Maze.data.grid[sx][sy] == 1) {
					result.setFill(Color.RED);
					result.setText("起点不合法！");
					return;
				}
			}
			else {
				this.ex = Integer.parseInt(Ex.getText());
				this.ey = Integer.parseInt(Ey.getText());
				if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0)
						|| MyMazes.Maze.data.grid[ex][ey] == 1) {
					result.setFill(Color.RED);
					result.setText("终点不可达！");
					return;
				}
			}
			if ((!MyMazes.Maze.isfirstnow()) && flagonestep == 0) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			if (MyMazes.Maze.data.grid[ex][ey] == 2) {
				return;
			}
			if (sx == 0 || sy == 0) {
				result.setFill(Color.RED);
				result.setText("请先生成迷宫！");
				return;
			}
			MazeBox.getChildren().clear();
			int dir = MyMazes.Maze.getARandomdir(sx, sy);
			if (dir != -1) {
				MyMazes.Maze.oneFind(sx, sy, 1);
				MyMazes.stack.push(dir);
				switch (dir) {
				case 1:
					sx--;
					break;
				case 2:
					sx++;
					break;
				case 3:
					sy++;
					break;
				case 4:
					sy--;
					break;
				}
			} else {
				MyMazes.Maze.oneFind(sx, sy, 1);
				MyMazes.Maze.data.j--;
			}
			VBox vb2 = new VBox();
			MyMazes.Maze.creatpath();
			for (int i = 0; i < widths; i++) {
				ImageView img = null;
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					if (i == ex && j == ey && MyMazes.Maze.data.grid[i][j] == 2) {
						img = autoImg("Images/end.jpg", 800, 800, i, j);
						result.setFill(Color.RED);
						result.setText("到达终点！");
					}
					if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 2)
						img = autoImg("Images/path.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb2.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb2);
			if (dir == -1) {
				MyMazes.Maze.oneFind(sx, sy, 2);
				switch (MyMazes.stack.pop()) {
				case 1:
					sx++;
					break;
				case 2:
					sx--;
					break;
				case 3:
					sy--;
					break;
				case 4:
					sy++;
					break;
				}
			}
			flagonestep = 1;
		});
	}
	//初始化
	public void first(ActionEvent event) {
		first.setOnMouseClicked(e -> {
			MyMazes.Maze.firstAll();
			flag = 0;
			flagonestep = 0;
			flagmouse=0;
			flagFile = 0;
			this.sx = Integer.parseInt(Sx.getText());
			this.sy = Integer.parseInt(Sy.getText());
			while (!MyMazes.stack.isEmpty())
				MyMazes.stack.pop();
			MazeBox.getChildren().clear();
			VBox vb1 = new VBox();
			for (int i = 0; i < widths; i++) {
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					ImageView img = null;
					if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb1.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb1);
			result.setFill(Color.RED);
			result.setText("初始化成功！");
		});
	}
	//最短路径
	public void shortPath(ActionEvent event) {
		shortpath.setOnMouseClicked(e -> {
			this.sx = Integer.parseInt(Sx.getText());
			this.sy = Integer.parseInt(Sy.getText());
			this.ex = Integer.parseInt(Ex.getText());
			this.ey = Integer.parseInt(Ey.getText());
			if ((ey >= (widths - 1) || ex >= (heights - 1)) || (ey < 0 || ex < 0) || MyMazes.Maze.data.grid[ex][ey] == 1) {
				result.setFill(Color.RED);
				result.setText("终点不可达！");
				return;
			}
			if ((sy < 0 || sx < 0) || (sy >= (widths - 1) || sx >= (heights - 1)) || MyMazes.Maze.data.grid[sx][sy] == 1) {
				result.setFill(Color.RED);
				result.setText("起点不合法！");
				return;
			}
			if (!MyMazes.Maze.isfirstnow()) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			MyMazes.Maze.traverlMaze(sx, sy, ex, ey);
			for (int i = 0; i < widths; i++)
				for (int j = 0; j < heights; j++)
					for (int k = 0; k < MyMazes.Maze.data.list.size(); k++)
						for (int m = 0; m < MyMazes.Maze.data.list.get(k).size(); m++) {
							if (i == MyMazes.Maze.data.list.get(k).get(m).getX()
									&& j == MyMazes.Maze.data.list.get(k).get(m).getY())
								MyMazes.Maze.data.grid[i][j] = MyMazes.Maze.data.list.get(k).get(m).getLevel() + 5;
						}
			MyMazes.Maze.shortPath(ex, ey, sx, sy);
			MazeBox.getChildren().clear();
			VBox vb2 = new VBox();
			for (int i = 0; i < widths; i++) {
				ImageView img = null;
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					if (i == sx && j == sy)
						img = autoImg("Images/start.jpg", 800, 800, i, j);
					else if (i == ex && j == ey)
						img = autoImg("Images/end.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == -2)
						img = autoImg("Images/path.jpg", 800, 800, i, j);
					hb.getChildren().add(img);
				}
				vb2.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb2);
			MyMazes.Maze.firstAll();
			result.setFill(Color.GREEN);
			result.setText("已成功找到路径！");
		});
	}

	// 遍历迷宫
	public void travelAll(ActionEvent event) {
		travel.setOnMouseClicked(e -> {
			if (!MyMazes.Maze.isfirstnow()) {
				result.setFill(Color.RED);
				result.setText("请先初始化！");
				return;
			}
			if (flag != 0)
				return;
			MyMazes.Maze.traverlMaze(sx, sy, ex, ey);
			for (int i = 0; i < widths; i++)
				for (int j = 0; j < heights; j++)
					for (int k = 0; k < MyMazes.Maze.data.list.size(); k++)
						for (int m = 0; m < MyMazes.Maze.data.list.get(k).size(); m++) {
							if (i == MyMazes.Maze.data.list.get(k).get(m).getX()
									&& j == MyMazes.Maze.data.list.get(k).get(m).getY())
								MyMazes.Maze.data.grid[i][j] = MyMazes.Maze.data.list.get(k).get(m).getLevel() + 5;
						}
			MazeBox.getChildren().clear();
			VBox vb1 = new VBox();
			for (int i = 0; i < widths; i++) {
				HBox hb = new HBox();
				for (int j = 0; j < heights; j++) {
					ImageView img = null;
					if (MyMazes.Maze.data.grid[i][j] == 1)
						img = autoImg("Images/black.jpg", 800, 800, i, j);
					else if (MyMazes.Maze.data.grid[i][j] == 0)
						img = autoImg("Images/white.jpg", 800, 800, i, j);
					else
						hb.getChildren().add(autoPane("Images/white.jpg", 800, 800, i, j, MyMazes.Maze.data.grid[i][j] - 5));
					if (img != null)
						hb.getChildren().add(img);
				}
				vb1.getChildren().add(hb);
			}
			MazeBox.getChildren().add(vb1);
			flag++;
		});
	}
}
