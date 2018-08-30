import java.util.Random;

import javafx.scene.layout.GridPane;

public class MapPane extends GridPane {

    private Tile[][] map;
    private int result;
    private int bombsCounter;

    public MapPane(int bombNum, int rowNum, int colNum) {
        bombsCounter = bombNum;
        map = new Tile[rowNum][colNum];
        result = 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Tile(map, i, j, this);
                add(map[i][j].getStackPane(), i, j);
            }
        }
        setBomb(bombNum);
    }

    public void setBomb(int n) {
        int tempBombNum = n;
        Random ran = new Random();
        do {
            int rowRan = ran.nextInt(map.length);
            int colRan = ran.nextInt(map[0].length);
            if (map[rowRan][colRan].setBomb(map))
                tempBombNum--;
        } while (tempBombNum > 0);
    }
    
    public int getResult() {
        return result;
    }
    
    public void setResult(int a) {
        result = a;
    }
    
    public void setBombsCounter(int n) {
        bombsCounter = n;
    }
    
    public int getBombsCounter() {
        return bombsCounter;
    }
    
    
    

}
