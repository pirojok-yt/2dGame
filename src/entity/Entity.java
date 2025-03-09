package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;

    //состояния
    public int worldX, worldY;
    public boolean invincible = false;
    public int spriteNum = 1;
    public boolean collisionOn = false;
    int dialogueIndex = 1;
    public String direction = "down";
    public int actionLockCounter = 0;

    //счетчики
    public int invincibleTime = 0;
    public int spriteCounter = 0;

    //хитбоксы/картинки
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1,
            attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;

    //статы
    public int type; // 0 игрок 1 нпс 2 монстр
    public int maxLife;
    public int life;
    public int dmg;
    public int speed;
    public int diagSpeed;
    public String name;

    //изгои
    String[] dialogues = new String[20];
    public boolean collision = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}

    public void speak() {
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        if(dialogueIndex == 0) {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            gp.ui.startTypingEffect();
        }
        else {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            gp.ui.startTypingEffect();
            dialogueIndex++;
        }
        switch(gp.player.direction) {
            case "right up", "up", "left up":
                direction = "down";
                break;
            case "left down", "down", "right down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monsters);
        gp.cChecker.checkPlayer(this);
        boolean touchPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && touchPlayer) {
            if(!gp.player.invincible) {
                gp.player.life -= dmg;
                gp.player.invincible = true;
            }
        }

        if(!collisionOn) {
            switch(direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "right up", "up", "left up":
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = up2;
                    }
                    break;
                case "left down", "down", "right down":
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = right2;
                    }
                    break;
                }
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }


    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
