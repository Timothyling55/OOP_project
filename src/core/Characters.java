package core;

import javafx.scene.canvas.GraphicsContext;
import libs.GameText;

public abstract class Characters {
    public ChSprite sprite;
    public int spriteXpos;
    public int spriteYpos;
    public int spriteXsize;
    public int spriteYsize;
    public String name;
    public int hp;
    public int strength;
    int maxHP;
    public int maxStrength;
    public GameText nameText;
    public GameText hpText;

    public abstract int attack (GraphicsContext gc, Characters c, Effects e);
}
