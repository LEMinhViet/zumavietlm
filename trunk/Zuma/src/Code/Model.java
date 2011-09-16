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
//    int[] ModelSeq = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
    int Quarter, Angle, x, y, x2, y2;
    int back, yMin, yMax, temp;
    boolean Back;


    public void initModel( ZumaCanvas zumaCanvas ) {
        
        this.zumaCanvas = zumaCanvas;
        positionInLevel(zumaCanvas.runningLevel);
        try {
            if ( Model == null )
                Model = new Sprite ( Image.createImage("/picture/mod_.png"), 60, 60);
//            Model.setFrameSequence(ModelSeq);
            Model.setPosition(x, y);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if ( zumaCanvas.runningLevel == 3 || zumaCanvas.runningLevel == 6
                || zumaCanvas.runningLevel == 9 || zumaCanvas.runningLevel == 12 || zumaCanvas.runningLevel == 7 )
            zumaCanvas.iCount = 90;
        else zumaCanvas.iCount = 0;
        if ( zumaCanvas.runningLevel == 3 ) { yMax = 250; yMin = 20; }
        else if (zumaCanvas.runningLevel == 6) { yMax = 226; yMin = 22; }
        else if (zumaCanvas.runningLevel == 7) { yMax = 210; yMin = 10; }
        else if (zumaCanvas.runningLevel == 8) { yMax = 172; yMin = -10; }
        else if (zumaCanvas.runningLevel == 9) { yMax = 245; yMin = 10; }
        else if (zumaCanvas.runningLevel == 12) { yMax = 256; yMin = 11; }
        else if (zumaCanvas.runningLevel == 13) { yMax = 190; yMin = 0; }
    }

    public void rotateModel() {
        Quarter = ((int)zumaCanvas.iCount)/90;
       
        if ( Quarter == 0 ) {
             Model.setTransform(Model.TRANS_NONE);
             //x = 80; y = 124;
        }
        else if ( Quarter == 1 ) {
            Model.setTransform(Model.TRANS_ROT90);
            //x = 80; y = 124 + 68;
        }
        else if ( Quarter == 2 ) {
            Model.setTransform(Model.TRANS_ROT180);
            //x = 12; y = 56;
        }
        else if ( Quarter == 3 ) {
            Model.setTransform(Model.TRANS_ROT270);
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
        if ( Level == 1 ) {   x = 93; y = 135; }
        else if ( Level == 2 ) {   x = 89; y = 183; }
        else if ( Level == 3 ) {   x = 5; y = 133; }
        else if ( Level == 4 ) {   x = 95; y = 152; }
        else if ( Level == 5 ) {   x = 109; y = 148; }
        else if ( Level == 6 ) {   x = 13; y = 178; }
        else if ( Level == 7 ) {   x = -8; y = 128; }
        else if ( Level == 8 ) {   x = 73; y = 109; }
        else if ( Level == 9 ) {   x = -8; y = 113; }
        else if ( Level == 12 ) {   x = 9; y = 129; }
        else if ( Level == 10 ) {   x = 51; y = 206; x2 = 147; y2 = 57; } // 147; 57
        else if ( Level == 11 ) {   x = 85; y = 51; x2 = 115; y2 = 231; } //115; 231
        else if ( Level == 13 ) {   x = 87; y = 137; }

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

    public void Swap () {
        temp = x;
        x = x2;
        x2 = temp;
        temp = y;
        y = y2;
        y2 = temp;
    }
}
