package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class Background extends Sprite
{
    public Background(String img)
    {
        super.imgPath = img;
        super.setImage(new Image(imgPath));
    }
}
