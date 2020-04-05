package core;

import libs.Sprite;
import javafx.scene.image.Image;

public class Effects extends Sprite
{
    public int xSize;
    public int ySize;
    public Effects(String img, int xSize, int ySize)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        super.imgPath = img;
        super.setImage(new Image(imgPath));
    }
}
