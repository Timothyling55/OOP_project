# What this repository is about?

This repository is a simple framework for game development in JavaFX, used in Object Oriented Programming Lab at Universiti Teknologi PETRONAS, Malaysia

# Can I use it for free?

Yes

# Who develop this framework?

This framework is developed and maintained by Arwan Ahmad Khoiruddin

# Can I participate to develop the framework?

Yes

# How I use this framework?

## Start your new Game

Your code for your game will be anything inside `core` directory. This repository contains an example of simple "game" in JavaFX. If you want to start the new game, just remove all the files inside `core` package/folder and follow the instructions below.

Your code must contains at least two java files (once again, all inside `core` package):
* `GameCore.java`
* `YourSprite.java`

More information on creating those two files can be found in the next subsections.

Unless you know what you do, please do not modify anything inside `Main.java` and files inside `libs` package (except `Configs.java`).

## Game Configuration

Some configuration of the game can be found in `Configs.java` inside `libs` folder. You can modify the configurations such as:

* Application title (`appTitle`)
* Application width (`appWidth`)
* Application height (`appHeight`)

## Creating Sprite

To create sprite, you can extends your class with `Sprite` class in `libs` folder. See example below:

```
package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class Balloon extends Sprite {

    public Balloon()  {
        super.imgPath = "/core/balloon.jpeg";
        super.setImage(new Image(imgPath));
    }

}

```

To create other Sprite object, you must extend your new class to Sprite, copy the lines inside the Balloon constructor and modify the `imgPath` to point to your Sprite image (to make the things easier, put the images inside `core` package).

Say you want to create `Monkey.java`. You put an image of monkey (`monkey.png`) in `core` package and your class will be like this

```
package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class Monkey extends Sprite {

    public Monkey()  {
        super.imgPath = "/core/monkey.png";
        super.setImage(new Image(imgPath));
    }

}
```

## Main place to code your game

The main place to code your game is in `GameCore` class. You may notice that the `GameCore` class implements `CoreFunc`, which will ensure that `GameCore` contains all functions needed to run the game.

To start with your project, create `GameCore` class inside `core` package. Your code should be like following

```
package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import libs.Configs;
import libs.CoreFunc;

import java.util.ArrayList;

public class GameCore implements CoreFunc {

   @Override
    public void init(GraphicsContext gc) {
    }

    @Override
    public void animate(GraphicsContext gc, int time, ArrayList input) {
 
    }

    @Override
    public void mouseClick(MouseEvent e) {
    }
}

```

See examples in `core` package in this repository to create the game.

Good luck with your game project in JavaFX

# Some Nice Free Resources

* Sound effects: freesound.org
* Characters: vecteezy.com
* Background music (No Copyright Sounds): https://www.youtube.com/user/NoCopyrightSounds
