package Main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gp) {

        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;

            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if(canTouchEvent) {
            if (hit(27, 16, "right")) {dmgPit(27, 16, gp.gameState);}
            if (hit(23, 12, "up")) {healingLake(23, 12, gp.dialogueState);}
            if (hit(23, 19, "any")) {dmgPit(23, 19, gp.gameState);}
            //if(hit(27, 16, "right")) { teleport(27, 16, gp.playState); }
        }
    }

    public boolean hit(int col,int row, String reqDirection) {

        boolean hit = false;

        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;
        eventRect[col][row].x += col*gp.tileSize;
        eventRect[col][row].y += row*gp.tileSize;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.equals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;


        return hit;
    }

    public void dmgPit(int col, int row, int gameState) {

        gp.gameState = gameState;
      //  gp.ui.currentDialogue = "ПОЧЕМУ НЕ РАБОТАЕТ";
        gp.player.life -= 1;
     //   eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingLake(int col, int row, int gameState) {

        if(gp.keyH.fPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water.\nYour life has been recovered.";
            gp.player.life = gp.player.maxLife;
        }

    }

    public void teleport(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }
}
