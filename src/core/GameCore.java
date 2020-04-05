package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import libs.Configs;
import libs.CoreFunc;
import libs.GameText;

import java.util.ArrayList;
import java.util.Random;

public class GameCore implements CoreFunc {
    // Main Game variables should be here
    int noOfWorlds = 6, partySize = 4; //init variables
    int start, overworld, selectedWorld;//map variables
    int turn, chosenTarget, currentLevel, currentWorld; //mid-battle variables
    String messageContent;

    Random aggro = new Random();
    GameText message = new GameText(Color.WHITE, Color.BLACK);
    MediaPlayer musicPlayer;

    Effects slash = new Effects("core/assets/images/slash.png", 200, 200);
    Effects hit = new Effects("core/assets/images/hit.png", 150, 150);
    Effects pointer = new Effects("core/assets/images/pointer.png", 50, 50);
    Background bg = new Background("core/assets/images/background/map.png");
    Textbox textbox = new Textbox("core/assets/images/textbox.png");

    World worlds []= new World[noOfWorlds];
    Hero heroes [] = new Hero[partySize];

    @Override
    public void init(GraphicsContext gc) {
        // initialize objects (initial position, initial size, etc)
        initObjects();
        playMusic("/core/assets/bgm/character_select.mp3");

        currentLevel = 0;
        currentWorld = 0;
        turn = 0;
        start = 0;
        overworld = 0;
        selectedWorld = 0;

        slash.resize(slash.xSize, slash.ySize);
        hit.resize(hit.xSize, hit.ySize);
        pointer.resize(pointer.xSize,pointer.ySize);
        textbox.resize(Configs.appWidth, Configs.appHeight);
        bg.resize(Configs.appWidth, Configs.appHeight);
        for (int i = 0; i < partySize; i ++)
        {
            heroes[i].sprite.resize(heroes[i].spriteXsize, heroes[i].spriteYsize);
        }
        for (int i = 0; i < noOfWorlds; i ++)
        {
            for (int j = 0; j < worlds[i].noOfLevels; j ++)
            {
                worlds[i].enemies[j].sprite.resize(worlds[i].enemies[j].spriteXsize ,worlds[i].enemies[j].spriteYsize);
            }
            worlds[i].bg.resize(worlds[i].spriteXMinisize, worlds[i].spriteYMinisize);
        }
    }

    @Override
    public void animate(GraphicsContext gc, int time, ArrayList input) {
        // any animations and keyboard controls should be here
        if (start == 0) {
            bg.render(gc, 0, 0);
            currentLevel = 0;
            messageContent = "Welcome!!" +
                    "\nPress enter to start!" +
                    "\nPress UP to attack" +
                    "\nPress DOWN to activate your skill" +
                    "\nHeal = heal 100 hp to teammates" +
                    "\nDraw Aggro = bodyguard teammates" +
                    "\nSuper Slash = deal 100 damage to enemy" +
                    "\nDebuff = reduce enemy attack by 50%";
            setTextbox(gc, messageContent, 30, 100, 150);
            if (input.contains("ENTER")) {
                delay(200);
                start++;
            }
        }
        else if (overworld == 0)
        {
            bg.render(gc, 0, 0);
            pointer.render(gc, worlds[selectedWorld].spriteXpos+worlds[selectedWorld].spriteXMinisize/3, worlds[selectedWorld].spriteYpos-pointer.ySize);
            message.setText(gc, "Select a world", 50, 50, 70);
            for (int i = 0; i < noOfWorlds; i ++)
            {
                worlds[i].bg.render(gc, worlds[i].spriteXpos, worlds[i].spriteYpos);
            }
            if (input.contains("LEFT") && selectedWorld!= 0)
            {
                selectedWorld--;
                delay(200);
            }
            if (input.contains("RIGHT") && selectedWorld+1 != noOfWorlds)
            {
                selectedWorld++;
                delay(200);
            }
            if (input.contains("ENTER"))
            {
                currentWorld = selectedWorld;
                delay(200);
                musicPlayer.dispose();
                playMusic(worlds[currentWorld].worldTheme);
                overworld++;
            }
        }
        else {
            worlds[currentWorld].bg.resize(worlds[currentWorld].spriteXFullsize, worlds[currentWorld].spriteYFullsize);
            worlds[currentWorld].bg.render (gc, 0, 0);

            worlds[currentWorld].enemies[currentLevel].sprite.render(gc, worlds[currentWorld].enemies[currentLevel].spriteXpos, worlds[currentWorld].enemies[currentLevel].spriteYpos);
            worlds[currentWorld].enemies[currentLevel].hpText.setText(gc, "HP: "+worlds[currentWorld].enemies[currentLevel].hp,50, worlds[currentWorld].enemies[currentLevel].spriteXpos,80);
            worlds[currentWorld].enemies[currentLevel].nameText.setText(gc, worlds[currentWorld].enemies[currentLevel].name,50, worlds[currentWorld].enemies[currentLevel].spriteXpos,130);

            message.setText(gc, "World: "+(currentWorld+1)+"\nLevel: "+(currentLevel+1), 40, 50, 60);
            pointer.render(gc, heroes[turn].spriteXpos+heroes[turn].spriteXsize/3, heroes[turn].spriteYpos+heroes[turn].spriteYsize);

            for (int i = 0; i < 4; i ++)
            {
                heroes[i].hpText.setText(gc, "HP "+heroes[i].hp,25,heroes[i].spriteXpos,200);
                heroes[i].nameText.setText(gc, heroes[i].name,25,heroes[i].spriteXpos,230);
                heroes[i].cooldownText.setText(gc, "Cooldown: "+heroes[i].cooldown, 25, heroes[i].spriteXpos, 260);
                heroes[i].skillNameText.setText(gc, heroes[i].skillName, 25, heroes[i].spriteXpos, 290);
                heroes[i].sprite.render(gc, heroes[i].spriteXpos, heroes[i].spriteYpos);
            }
            if (input.contains("UP"))
            {
                turn+=heroes[turn].attack(gc, worlds[currentWorld].enemies[currentLevel], slash);
                delay (200);
            }
            if (input.contains("DOWN"))
            {
                if (heroes[turn].cooldown!= 0)
                {
                    turn+=heroes[turn].attack(gc, worlds[currentWorld].enemies[currentLevel], slash);
                    delay (200);
                }
                else
                {
                    heroes[turn].cooldown = 2;
                    switch (turn)
                    {
                        case 0:
                            for (int i = 0; i < 4; i ++)
                            {
                                heroes[i].hp += 100;
                                if (heroes[i].hp > heroes[i].maxHP)
                                    heroes[i].hp = heroes[i].maxHP;
                            }
                            turn++;
                            break;
                        case 1:
                            turn++;
                            break;
                        case 2:
                            worlds[currentWorld].enemies[currentLevel].hp -= 100;
                            heroes[turn].cooldown++;
                            turn+=heroes[turn].attack(gc, worlds[currentWorld].enemies[currentLevel], slash);
                            delay (200);
                            break;
                        case 3:
                            worlds[currentWorld].enemies[currentLevel].strength*=0.5;
                            turn++;
                            break;
                    }
                }
                delay (200);
            }
            if (turn == 4)
            {
                if (heroes[1].cooldown!= 0)
                    chosenTarget = 1;
                else chosenTarget = aggro.nextInt(4);

                if (heroes[3].cooldown ==0)
                    worlds[currentWorld].enemies[currentLevel].strength = worlds[currentWorld].enemies[currentLevel].maxStrength;

                turn = worlds[currentWorld].enemies[currentLevel].attack(gc, heroes[chosenTarget], hit);
            }

            for (int i = 0; i < 4; i ++)
            {
                if (heroes[i].hp <= 0)
                {
                    heroes[i].hp = 0;
                    messageContent = "You lose!\n Press ENTER to restart.";
                    setTextbox(gc, messageContent, 50, 150, 150);
                    if (input.contains("ENTER"))
                        reinit();
                }
            }

            if (worlds[currentWorld].enemies[currentLevel].hp <= 0)
            {
                worlds[currentWorld].enemies[currentLevel].hp = 0;

                if (currentLevel+1 != worlds[currentWorld].noOfLevels)
                    messageContent = "You win!\n Advance to the next level!\n Press ENTER to continue.";
                else if (currentWorld+1 != noOfWorlds)
                    messageContent = "You win!\n Advance to the next world!\n Press ENTER to continue.";
                else messageContent = "CONGRATS! \nYOU BEAT THE GAME!\n Press enter to quit \n(might take a while to load)";

                if (input.contains("ENTER"))
                    currentLevel++;
                if (currentLevel == worlds[currentWorld].noOfLevels)
                {
                    currentLevel = 0;
                    reset();
                    if (input.contains("ENTER"))
                    {
                        currentWorld++;
                        musicPlayer.dispose();
                        if (currentWorld != noOfWorlds)
                            playMusic(worlds[currentWorld].worldTheme);
                    }
                }
                if (currentWorld == noOfWorlds) {
                    delay(200);
                    if (input.contains("ENTER"))
                    {
                        delay (200);
                        playMusic("/core/assets/bgm/character_select.mp3");
                        reinit();
                    }
                }
                turn = 0;
                setTextbox(gc, messageContent, 50, 200, 200);
            }
        }
        if (input.contains("Q"))
            reinit();
    }

    public void initObjects() //initializes or resets object properties
    {
        worlds [0] = new World(0, 3, "core/assets/images/worlds/world1.png", "/core/assets/bgm/world1.mp3", 50, 150);
        worlds [1] = new World(1, 3,  "core/assets/images/worlds/world2.png", "/core/assets/bgm/world2.mp3", 200, 300);
        worlds [2] = new World(2, 3, "core/assets/images/worlds/world3.png", "/core/assets/bgm/world3.mp3",350,150);
        worlds [3] = new World(3, 3, "core/assets/images/worlds/world4.png", "/core/assets/bgm/world4.mp3",500,300);
        worlds [4] = new World(4, 4, "core/assets/images/worlds/world5.png", "/core/assets/bgm/world5.mp3",650,150);
        worlds [5] = new World(5, 1, "core/assets/images/worlds/world6.png", "/core/assets/bgm/world6.mp3",800,300);

        worlds[0].worldSetEnemy("Rattata", 500, 70,0,"core/assets/images/enemies/world1A.png");
        worlds[0].worldSetEnemy("Pidgey", 550, 80,1,"core/assets/images/enemies/world1B.png");
        worlds[0].worldSetEnemy("Pikachu", 600, 80, 2,"core/assets/images/enemies/world1C.png");

        worlds[1].worldSetEnemy("Poliwag", 600, 85, 0,"core/assets/images/enemies/world2A.png");
        worlds[1].worldSetEnemy("Psyduck", 650, 85, 1,"core/assets/images/enemies/world2B.png");
        worlds[1].worldSetEnemy("Squirtle", 650, 90, 2,"core/assets/images/enemies/world2C.png");

        worlds[2].worldSetEnemy("Oddish", 650, 90, 0,"core/assets/images/enemies/world3A.png");
        worlds[2].worldSetEnemy("Exeggutor", 700, 90, 1,"core/assets/images/enemies/world3B.png");
        worlds[2].worldSetEnemy("Ivysaur", 700, 100, 2,"core/assets/images/enemies/world3C.png");

        worlds[3].worldSetEnemy("Magmar", 700, 125, 0,"core/assets/images/enemies/world4A.png");
        worlds[3].worldSetEnemy("Arcanine", 700, 125, 1,"core/assets/images/enemies/world4B.png");
        worlds[3].worldSetEnemy("Charizard", 750, 150, 2,"core/assets/images/enemies/world4C.png");

        worlds[4].worldSetEnemy("Moltres", 800, 150, 0,"core/assets/images/enemies/world5A.png");
        worlds[4].worldSetEnemy("Zapdos", 800, 150, 1,"core/assets/images/enemies/world5B.png");
        worlds[4].worldSetEnemy("Articuno", 800, 150, 2,"core/assets/images/enemies/world5C.png");
        worlds[4].worldSetEnemy("Mewtwo", 900, 200, 3,"core/assets/images/enemies/world5D.png");

        worlds[5].worldSetEnemy("Mew", 999, 300, 0,"core/assets/images/enemies/world6A.png");

        heroes [0] = new Hero("Tim", 350, 20, "core/assets/images/heroes/healer.png", "Heal",50, 300);
        heroes [1] = new Hero("Wyxi", 650, 40, "core/assets/images/heroes/tank.png","Draw Aggro", 200, 300);
        heroes [2] = new Hero("Jijun", 350, 50, "core/assets/images/heroes/warrior.png","Super Slash", 350, 300);
        heroes [3] = new Hero("Faliq", 350, 40, "core/assets/images/heroes/mage.png","Debuff",500, 300);
    }

    public void reset()
    {
        for (int i = 0; i < 4; i ++)
        {
            heroes[i].hp = heroes[i].maxHP;
            heroes[i].cooldown = 0;
        }
        for (int i = 0; i < noOfWorlds; i ++)
        {
            for (int j = 0; j < worlds[i].noOfLevels; j ++)
            {
                worlds[i].enemies[j].hp = worlds[i].enemies[j].maxHP;
                worlds[i].enemies[j].strength = worlds[i].enemies[j].maxStrength;
            }
        }
    }

    public void reinit()
    {
        playMusic("/core/assets/bgm/character_select.mp3");

        currentLevel = 0;
        currentWorld = 0;
        turn = 0;
        start = 0;
        overworld = 0;
        selectedWorld = 0;

        slash.resize(slash.xSize, slash.ySize);
        hit.resize(hit.xSize, hit.ySize);
        pointer.resize(pointer.xSize,pointer.ySize);
        textbox.resize(Configs.appWidth, Configs.appHeight);
        bg.resize(Configs.appWidth, Configs.appHeight);
        for (int i = 0; i < 4; i ++)
        {
            heroes[i].sprite.resize(heroes[i].spriteXsize, heroes[i].spriteYsize);
            heroes[i].hp = heroes[i].maxHP;
            heroes[i].cooldown = 0;
        }
        for (int i = 0; i < noOfWorlds; i ++)
        {
            for (int j = 0; j < worlds[i].noOfLevels; j ++)
            {
                worlds[i].enemies[j].sprite.resize(worlds[i].enemies[j].spriteXsize, worlds[i].enemies[j].spriteYsize);
                worlds[i].enemies[j].hp = worlds[i].enemies[j].maxHP;
                worlds[i].enemies[j].strength = worlds[i].enemies[j].maxStrength;
            }
            worlds[i].bg.resize(worlds[i].spriteXMinisize, worlds[i].spriteYMinisize);
        }
    }

    public void delay (int millis)
    {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void setTextbox(GraphicsContext gc, String messageContent, int fontSize, int x, int y)
    {
        textbox.render(gc, 0, 0);
        message.setText(gc, messageContent,fontSize, x, y);
    }

    public void playMusic(String musicFileDir)
    {
        Media mp3music = new Media(getClass().getResource(musicFileDir).toExternalForm());
        musicPlayer = new MediaPlayer(mp3music);
        musicPlayer.setAutoPlay(true);
        musicPlayer.setVolume(0.4);

        musicPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                musicPlayer.seek(Duration.ZERO);
            }
        });
    }

    @Override
    public void mouseClick(MouseEvent e) {
        // mouse click event here
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // mouse move event here
    }
}
