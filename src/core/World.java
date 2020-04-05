package core;

import libs.Configs;

public class World {
    public int worldNumber;
    public int noOfLevels;
    String worldBG;
    String worldTheme;
    Background bg;
    int spriteXpos;
    int spriteYpos;
    public int spriteXFullsize = Configs.appWidth;
    public int spriteYFullsize = Configs.appHeight;
    public int spriteXMinisize = 150;
    public int spriteYMinisize = 100;

    public Enemy enemies [];

    public World (int worldNumber, int noOfLevels, String worldBG, String worldTheme, int spriteXpos, int spriteYpos)
    {
        this.worldNumber = worldNumber;
        this.noOfLevels = noOfLevels;
        enemies = new Enemy[noOfLevels];
        this.worldBG = worldBG;
        this.worldTheme = worldTheme;
        bg = new Background(worldBG);
        bg.resize(Configs.appWidth, Configs.appHeight);
        this.spriteXpos = spriteXpos;
        this.spriteYpos = spriteYpos;
    }

    public void worldSetEnemy (String name, int hp, int strength, int level, String spriteDir)
    {
        enemies[level] = new Enemy(name, hp, strength, level, spriteDir);
    }
}
