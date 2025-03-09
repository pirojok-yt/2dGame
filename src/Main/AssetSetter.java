package Main;
import Monster.Mon_GreenSlime;
import entity.NPC_OldMan;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }

    public void setMonster() {
        gp.monsters[0] = new Mon_GreenSlime(gp);
        gp.monsters[0].worldX = gp.tileSize*23;
        gp.monsters[0].worldY = gp.tileSize*36;

        gp.monsters[1] = new Mon_GreenSlime(gp);
        gp.monsters[1].worldX = gp.tileSize*11;
        gp.monsters[1].worldY = gp.tileSize*11;

    }

}
