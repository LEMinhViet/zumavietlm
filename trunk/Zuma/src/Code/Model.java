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

    public void initModel( ZumaCanvas zumaCanvas ) {
        
        this.zumaCanvas = zumaCanvas;
        x = 88; y = 132;
        try {
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

    /*public void drawModel( Graphics g, int X, int Y ) {
        g.drawImage( Model, X, Y, Graphics.TOP | Graphics.LEFT);
    }*/

    /////////////////////////////////////////////////////////////////////////////////////////
    // Hàm rotate để quay model
    // imgSource : Ảnh gốc để quay
    // cx       : tọa độ X tâm ảnh imgSource
    // cy       : tọa độ Y tâm ảnh imgSource
    // theta    : góc quay của ảnh
    /////////////////////////////////////////////////////////////////////////////////////////
    /*public Image rotate(Image imgSource, int cx, int cy, double theta) {
        if (Math.abs(theta % 360) < 0.1) {
            return imgSource;
        }
        w1 = imgSource.getWidth();
        h1 = imgSource.getHeight();
        srcMap = new int[w1 * h1];
        imgSource.getRGB(srcMap, 0, w1, 0, 0, w1, h1);
        dx= cx;
        dy = cy;
        dr = Math.sqrt(dx * dx + dy * dy);
        wh2 = (int) (2 * dr + 1);
        destMap = new int[wh2 * wh2];
        radian = theta * Math.PI / 180;
        for ( i = 0; i < w1; i++) {
            for (j = 0; j < h1; j++) {
                destX = (int) (dr + (i - cx) * Math.cos(radian) + (j - cy) * Math.sin(radian));
                destY = (int) (dr + (j - cy) * Math.cos(radian) - (i - cx) * Math.sin(radian));
                destMap[(int) wh2 * destY + (int) destX] = srcMap[j * w1 + i];
                destMap[(int) wh2 * (int) destY + (int) destX + 1] = srcMap[j * w1 + i];
            }
        }
        return Image.createRGBImage(destMap, wh2, wh2, true);
    }*/
}
