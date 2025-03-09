package entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;

    public boolean showInvincibility = true;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;


        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        diagSpeed = 3;
        direction = "down";

        // hp
        maxLife = 10;
        life = maxLife;
    }
    public void getPlayerImage() {

            up1 = setup("/player/boy_up_1");
            up2 = setup("/player/boy_up_2");
            down1 = setup("/player/boy_down_1");
            down2 = setup("/player/boy_down_2");
            left1 = setup("/player/boy_left_1");
            left2 = setup("/player/boy_left_2");
            right1 = setup("/player/boy_right_1");
            right2 = setup("/player/boy_right_2");
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/boy_attack_up_1");
        attackUp2 = setup("/player/boy_attack_up_2");
        attackDown1 = setup("/player/boy_attack_down_1");
        attackDown2 = setup("/player/boy_attack_down_2");
        attackLeft1 = setup("/player/boy_attack_left_1");
        attackLeft2 = setup("/player/boy_attack_left_2");
        attackRight1 = setup("/player/boy_attack_right_1");
        attackRight2 = setup("/player/boy_attack_right_2");
    }

    public void update() {
            if(keyH.upPressed || keyH.downPressed ||
                    keyH.leftPressed || keyH.rightPressed || keyH.fPressed) {

                if (keyH.upPressed && keyH.rightPressed) {
                    direction = "right up";
                } else if (keyH.leftPressed && keyH.downPressed) {
                    direction = "left down";
                } else if (keyH.downPressed && keyH.rightPressed) {
                    direction = "right down";
                } else if (keyH.leftPressed && keyH.upPressed) {
                    direction = "left up";
                } else if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }


                //клетки
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // объектс
                int objIndex = gp.cChecker.checkObject(this, true);
                picUpObject(objIndex);

                // нпс
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //монстрс
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
                contactMonster(monsterIndex);


                gp.eHandler.checkEvent();


                if (!collisionOn && !keyH.fPressed) {
                    switch (direction) {
                        case "right up":
                            worldY -= diagSpeed;
                            worldX += diagSpeed;
                            break;
                        case "left down":
                            worldX -= diagSpeed;
                            worldY += diagSpeed;
                            break;
                        case "right down":
                            worldY += diagSpeed;
                            worldX += diagSpeed;
                            break;
                        case "left up":
                            worldX -= diagSpeed;
                            worldY -= diagSpeed;
                            break;
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

                if (!keyH.fPressed) {
                    spriteCounter++;
                    if (spriteCounter > 12) {
                        if (spriteNum == 1) {
                            spriteNum = 2;
                        } else if (spriteNum == 2) {
                            spriteNum = 1;
                        }
                        spriteCounter = 0;
                    }
                } else {
                    standCounter++;
                    if (standCounter == 20) {
                        spriteNum = 1;
                        standCounter = 0;
                    }
                }
            }
            gp.keyH.fPressed = false;
            if(invincible) {
                invincibleTime++;
                if(invincibleTime > 60) {
                    invincible = false;
                    invincibleTime = 0;
                }
            }
    }

    public void picUpObject(int i){
        if(i != 999) {
        }
    }

    public void interactNPC(int i) {

        if(i != 999) {
            if(gp.keyH.fPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            if(!invincible) {
                life -= gp.monsters[i].dmg;
                invincible = true;
            }
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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

        if(invincible && showInvincibility){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
