package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class ChSprite extends Sprite
{
    public ChSprite(String img)
    {
        super.imgPath = img;
        super.setImage(new Image(imgPath));
    }
}