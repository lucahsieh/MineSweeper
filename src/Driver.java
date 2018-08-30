import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Driver extends Application{
    Text result;
    Text bombCounter;
    MapPane game1;
    
    
    
    public void start (Stage primaryStage) {
        
        final int appWidth = 700;
        final int appHeight = 500;
        
        game1 = new MapPane(10, 20, 10);
        game1.setOnMouseClicked(this::processClicked);
        result = new Text("start!\n");
        bombCounter = new Text(game1.getBombsCounter() + "Bombs left");
        FlowPane info = new FlowPane(result, bombCounter);
        info.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(info, game1);
        
        
        
        Scene scene = new Scene(root, appWidth, appHeight);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void processClicked(MouseEvent event) {
        bombCounter.setText(game1.getBombsCounter() + "Bombs left");
        if(game1.getResult() < 0)
            result.setText("GAME END\n Your Score is " + game1.getResult() * -1 );
        else if (game1.getResult() > 0)
            result.setText("SAFE\n Your Score is " + game1.getResult());
    }
}
