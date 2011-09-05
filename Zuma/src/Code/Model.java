/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Admin
 */
public class Model {

    ZumaCanvas zumaCanvas;
    Sprite Model;
    int[] ModelSeq = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
    int Quarter, Angle, x, y;
    int back;
    boolean Back;

    public void initModel( ZumaCanvas zumaCanvas ) {
        
        this.zumaCanvas = zumaCanvas;
        positionInLevel(zumaCanvas.runningLevel);
        try {
            if ( Model == null )
                Model = new Sprite ( Image.createImage("/picture/mod.png"), 60, 60);
            Model.setFrameSequence(ModelSeq);
            Model.setPosition(x, y);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }

    public void rotateModel() {
        Quarter = ((int)zumaCanvas.iCount)/90;
       
        if ( Quarter == 0 ) {
             Model.setTransform(Model.TRANS_NONE);
             //x = 80; y = 124;
        }
        else if ( Quarter == 1 ) {
            Model.setTransform(Model.TRANS_ROT270);
            //x = 80; y = 124 + 68;
        }
        else if ( Quarter == 2 ) {
            Model.setTransform(Model.TRANS_ROT180);
            //x = 12; y = 56;
        }
        else if ( Quarter == 3 ) {
            Model.setTransform(Model.TRANS_ROT90);
            //x = 12; y = 124;
        }
        Model.setPosition(x, y);
        
        Angle = ((int)(zumaCanvas.iCount%90));
        if (Angle < 5 && Angle >= 0)
            Model.setFrame(0);
        if ( Angle < 15 && Angle >= 5 )
            Model.setFrame(1);
        if ( Angle < 25 && Angle >= 15 )
            Model.setFrame(2);
        if ( Angle < 35 && Angle >= 25 )
            Model.setFrame(3);
        if ( Angle < 45 && Angle >= 35 )
            Model.setFrame(4);
        if ( Angle < 55 && Angle >= 45 )
            Model.setFrame(5);
        if ( Angle < 65 && Angle >= 55 )
            Model.setFrame(6);
        if ( Angle < 75 && Angle >= 65 )
            Model.setFrame(7);
        if ( Angle < 90 && Angle >= 75 )
            Model.setFrame(8);
        

    }

    public void positionInLevel ( int Level ) {
        if ( Level == 1 ) {   x = 91; y = 135; }
        else if ( Level == 4 ) {   x = 95; y = 152; }
        else if ( Level == 5 ) {   x = 109; y = 148; }
    }

    public void whenShoot ( float iCount ) {
        if ( back < 8 ) {
            if ( !Back ) {
                Model.setPosition(Model.getX() - (int)(back*Math.cos((iCount/180)*Math.PI-Math.PI/2)), Model.getY() + (int)(back*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
                back+=2;
            }
        } else {
            Back = true;
            back = 0;
            Model.setPosition(x, y);
        }
    }
}
