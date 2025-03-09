package Monster;

import Main.GamePanel;
import entity.Entity;

import java.util.Random;

public class Mon_GreenSlime extends Entity {


    public Mon_GreenSlime(GamePanel gp) {
        super(gp);

        name = "Slime";
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;
        dmg = 1;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/mobs/greenslime_down_1");
        up1 = setup("/mobs/greenslime_down_2");
        down1 = setup("/mobs/greenslime_down_1");
        down2 = setup("/mobs/greenslime_down_2");
        right1 = setup("/mobs/greenslime_down_1");
        right2 = setup("/mobs/greenslime_down_2");
        left1 = setup("/mobs/greenslime_down_1");
        left2 = setup("/mobs/greenslime_down_2");

    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

}
