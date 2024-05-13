package game.gui;

import game.engine.titans.Titan;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class TitanIcon {

    private Titan titan;
    private ImageView titanImage;
    private ProgressBar titanHealth;

    public Titan getTitan() {
        return titan;
    }

    public void setTitan(Titan titan) {
        this.titan = titan;
    }

    public ImageView getTitanImage() {
        return titanImage;
    }

    public void setTitanImage(ImageView titanImage) {
        this.titanImage = titanImage;
    }

    public ProgressBar getTitanHealth() {
        return titanHealth;
    }

    public void setTitanHealth(ProgressBar titanHealth) {
        this.titanHealth = titanHealth;
    }

    public TitanIcon(Titan titan, ImageView titanImage, ProgressBar titanHealth){
        this.titan = titan;
        this.titanHealth = titanHealth;
        this.titanImage = titanImage;
    }
    
}
