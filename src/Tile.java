import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Represent a tile on the map.
 * @author Luca
 * @version 2.0
 */
public class Tile extends GridPane {

    /** Tile box size. */
    private final int TILE_SIZE = 25;
    
    /** The tile's neighbors by listing their y-axis and x-axis. */
    private final int[] TILE_NEIGHBOURS = {
            -1, -1,  -1,  0,  -1,  1,
             0, -1,   0,  0,   0,  1,
             1, -1,   1,  0,   1,  1};
             
    /** The number represent how many bombs next to this tile. */
    private int tileNumber;
    
    /** Represent this tile's y-axis and x-axis. */
    private int row, col;
    
    
    private Tile[][] map;
    private MapPane game;
    private boolean isBomb;
    private boolean isOpen = false;
    
    private Rectangle ansRec;
    private Rectangle queRec;
    private Text numText;
    private StackPane tileStack;
    
    
    public Tile(Tile[][] map, int row, int col, MapPane game) {
        this.row = row;
        this.col = col;
        this.map = map;
        this.game = game;
        numText = new Text("");
        queRec = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);
        queRec.setFill(Color.BLACK);
        queRec.setStroke(Color.WHITE);

        queRec.setOnMouseClicked(this::processClickedBox);

        ansRec = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);
        ansRec.setFill(Color.GRAY);
        ansRec.setStroke(Color.WHITE);

        tileStack = new StackPane(ansRec, numText, queRec);
    }
    
    public int countBombsInBlock() {
        int bombCounter = 0;
        for (int i = 0; i < TILE_NEIGHBOURS.length; i++) {
            int tempRow = row + i;
            int tempCol = col + ++i;
            if (this.validIndexNum(tempRow, tempCol)) 
                if (map[row + i][col + ++i].isBomb)
                    bombCounter++;
        }
        return bombCounter;
    }
    
    public void processClickedBox(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && game.getResult() >= 0) {
            openTile();
            game.setResult(game.getResult() + 1);
        }
        if (event.getButton() == MouseButton.SECONDARY && game.getResult() >= 0) {
            game.setBombsCounter(game.getBombsCounter() - 1);
            queRec.setFill(Color.RED);
        }
    }
    
    public boolean isBomb() {
        return isBomb;
    }
    
    private boolean validIndexNum(int r, int c) {
        if (r < map.length && r >= 0 && c < map[0].length && c >= 0)
            return true;
        else
            return false;
    }
    
    public boolean setBomb(Tile[][] map) {
        if (isBomb)
            return false;
        else {
            isBomb = true;
            for (int i = 0; i < TILE_NEIGHBOURS.length; i++) {
                int tempRow = row + TILE_NEIGHBOURS[i];
                int tempCol = col + TILE_NEIGHBOURS[++i];
                numText.setText("B");
                if (this.validIndexNum(tempRow, tempCol) && map[tempRow][tempCol].isBomb == false) {
                    map[tempRow][tempCol].countTileNumber();
                }
            }
            tileNumber = 9;
            return true;
        }
    }
    
    private void countTileNumber() {
        tileNumber += 1;
        setTileText(tileNumber);
    }
    
    public void setTileText(int counter) {
        if (counter == 0)
            numText.setText("");
        else
            numText.setText(tileNumber + "");
    }
    
    public int getTileNumber() {
        return tileNumber;
    }
    
    public void openTile() {
        if (!isOpen) {
            isOpen = true;
            queRec.setFill(null);
            
            
            if (this.tileNumber == 0) {
                openEachOnTheBlock();
            }
            else if (isBomb) {
                game.setResult(game.getResult() * -1);
            }
        }
    }
    
    public void openEachOnTheBlock() {
        for (int i = 0; i < TILE_NEIGHBOURS.length; i++) {
            int tempRow = row + TILE_NEIGHBOURS[i];
            int tempCol = col + TILE_NEIGHBOURS[++i];
            if (validIndexNum(tempRow, tempCol)) {
                map[tempRow][tempCol].openTile();
            }
        }
    }

    
    public StackPane getStackPane() {
        return tileStack;
    }
}
