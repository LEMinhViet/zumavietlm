/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Vitcum
 */
public class ImageSet {
    ZumaCanvas zumacanvas;
    Image Ball;
    Graphics gr;
    public ImageSet ( ZumaCanvas zumacanvas) {
        this.zumacanvas = zumacanvas;
        try {
            Ball = Image.createImage("/picture/bi.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void paintBall0( int x, int y ) {
        gr.setClip(x, y, 16, 16);
        gr.drawImage(Ball, x,y, Graphics.TOP | Graphics.LEFT);
        //gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    public void paintBall1() {
        gr.setClip(16, 0, 16, 16);
        gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
        //gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    public void paintBall2() {
        gr.setClip(32, 0, 16, 16);
        gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
        //gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    public void paintBall3() {
        gr.setClip(48, 0, 16, 16);
        gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
        //gr.drawImage(Ball, 0, 0, Graphics.TOP | Graphics.LEFT);
    }
}
