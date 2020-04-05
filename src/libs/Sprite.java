package libs;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.scene.media.AudioClip;
import java.nio.file.Paths;

public class Sprite extends ImageView {

    int xpos;
    int ypos;

    public String imgPath;
    Configs config = new Configs();

    public void resize(double factor) {
        double newdim = this.getImage().getHeight() * factor;
        this.setImage(new Image(this.imgPath, newdim, newdim, true, false));
    }

    public void resize(int width, int height) {
        // check first which one is larger between height and width
        Image tmp = new Image(this.imgPath);
        this.setImage(new Image(this.imgPath, width, height, false, true));
    }

    public void render(GraphicsContext gc, int x, int y) {
        gc.drawImage(this.getImage(), x, y);
        this.ypos = y;
        this.xpos = x;
    }
    
    public void changeImage(String imgPath) {
        double width = this.getImage().getWidth();
        double height = this.getImage().getHeight();
        this.setImage(new Image(imgPath, width, height, true, true));
    }
                    
    public void soundEffect(String effectPath) {
        String path = "src" + effectPath;
        AudioClip audioClip = new AudioClip(Paths.get(path).toUri().toString());
        audioClip.play();
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.xpos, this.ypos, this.getImage().getWidth(), this.getImage().getHeight());
    }

    public boolean collide(Sprite sprite) {
        return (this.getBoundary().intersects(sprite.getBoundary()));
    }


}
