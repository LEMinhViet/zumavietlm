/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Admin
 */
public class Effect {
    int EffectType;
    ZumaCanvas zumaCanvas;
    int Slowite = 0;
    Sprite Item;
    boolean issetItem = false;
    int partItem = 0, iItem = 10, sttItem = 0, makeItem;

    public Effect( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
        try {
            Item = new Sprite(Image.createImage("/picture/item.png"), 16, 16);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        makeItem = 300;
    }


    public void setItem ( ) {
        if ( !issetItem ) {
            zumaCanvas.lm.insert(Item, 0);
            issetItem = true;
        }
        Item.setVisible(true);
        Item.setFrame(this.sttItem);
        Item.setPosition(((Sprite)zumaCanvas.vBall[this.partItem].BVector.elementAt(this.iItem)).getX(),
                ((Sprite)zumaCanvas.vBall[this.partItem].BVector.elementAt(this.iItem)).getY());
    }

    public void newlevelItem () {
        zumaCanvas.lm.remove(Item);
        issetItem = false;
    }

    public void callEffect ( ) {
        Item.setVisible(false);
        makeItem = 300;
    }
}
