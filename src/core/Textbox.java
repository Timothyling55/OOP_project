package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class Textbox extends Sprite
{
    public Textbox(String img)
    {
        super.imgPath = img;
        super.setImage(new Image(imgPath));
    }
}