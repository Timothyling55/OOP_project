package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import libs.GameText;

public class Enemy extends Characters {
    public int level;
    public Enemy (String name, int hp, int strength, int level, String spriteDir) {
        this.spriteXpos = 600;
        this.spriteYpos = 50;
        this.spriteXsize = 400;
        this.spriteYsize = 400;
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.strength = strength;
        this.maxStrength = strength;
        this.level = level;
        sprite = new ChSprite(spriteDir);
        nameText = new GameText(Color.WHITE, Color.BLACK);
        hpText = new GameText(Color.WHITE, Color.BLACK);
    }

    @Override
    public int attack(GraphicsContext gc, Characters chosenHero, Effects hit)
    {
        chosenHero.hp -= this.strength;
        hit.render(gc, chosenHero.spriteXpos, chosenHero.spriteYpos);
        return 0;
    }

}
