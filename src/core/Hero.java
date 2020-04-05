package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import libs.GameText;

public class Hero extends Characters{
    public String skillName;
    public GameText cooldownText;
    public GameText skillNameText;
    int cooldown = 0;
    public Hero (String name, int hp, int strength, String spriteDir, String skillName, int spriteXpos, int spriteYpos)
    {
        this.spriteXsize = 150;
        this.spriteYsize = 150;
        this.spriteXpos = spriteXpos;
        this.spriteYpos = spriteYpos;
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.strength = strength;
        this.maxStrength = strength;
        this.skillName = skillName;
        sprite = new ChSprite(spriteDir);
        nameText = new GameText(Color.WHITE, Color.BLACK);
        hpText = new GameText(Color.WHITE, Color.BLACK);
        cooldownText = new GameText(Color.WHITE, Color.BLACK);
        skillNameText = new GameText(Color.WHITE, Color.BLACK);
    }

    @Override
    public int attack(GraphicsContext gc, Characters enemy, Effects slash)
    {
        enemy.hp -= this.strength;
        slash.render(gc, enemy.spriteXpos+100, enemy.spriteYpos+100);
        if (this.cooldown> 0)
            this.cooldown--;
        return 1;
    }
}
