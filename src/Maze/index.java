package Maze;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class index extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage Stage) throws Exception {
		Parent index=FXMLLoader.load(getClass().getResource("index.fxml"));
		Stage.setTitle("Maze-By-Uncle");
		Scene scene = new Scene(index);
		Stage.getIcons().add(new Image("Images/title.jpg"));
		Stage.setScene(scene);
		Stage.show();
		MyMazes.stages=Stage;
	}
}
