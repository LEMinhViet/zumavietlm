/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.m3g.Image2D;

/**
 *
 * @author Admin
 */
public class Ball {
    ZumaCanvas zumaCanvas;
    Sprite Ball, Pixel;
    int ShootSpeed, ShootDistance;
    float ShootAngle;
    //int[] ballSeq = { 0, 1, 2, 3, 4, 5, 6, 7 };
    int[] ballSeq = { 0, 1, 2, 3 };
    int ranColor;
    
    public Ball( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
        ShootDistance = 15;
        ShootSpeed = 15;
        //ShootAngle = zumaCanvas.iCount;
    }

    public void initBall() {
        ranColor = zumaCanvas.getRand(0, 2);
        try {
            /*if ( ranColor == 0)
                Ball = new Sprite(Image.createImage("/picture/bong_do.png"), 16, 16);
            else if ( ranColor == 1 )
                Ball = new Sprite(Image.createImage("/picture/bong_xanh2.png"), 16, 16);*/
            Ball = new Sprite(Image.createImage("/picture/bi.png"), 16, 16);
            Pixel = new Sprite(Image.createImage("/picture/pixel.png"), 1, 1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Ball.setFrameSequence(ballSeq);
        zumaCanvas.lm.insert(Ball, 0);
        zumaCanvas.lm.insert(Pixel, 0);
        Ball.setPosition( 116, 160);
        Ball.setFrame(zumaCanvas.getRand(0, 3));
    }

    public void shootBall( float ShootAngle ){
        Ball.setPosition((int)(116 + ShootDistance*Math.cos((ShootAngle/180)*Math.PI-Math.PI/2)),
                                (int)(160 - ShootDistance*Math.sin((ShootAngle/180)*Math.PI-Math.PI/2)));
    }

    public void resetBall(){
        // Hiện lại bóng để bắn, các biến liên quan đến việc bắn về lại trị số ban đầu
        ShootDistance = 15;
        Ball.setPosition((int)(116 + 15*Math.cos((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)),
                        (int)(160 - 15*Math.sin((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)));
        /*System.out.print( "Color " );
        for ( int u = 0; u < 10; u++ ) {
            System.out.print( zumaCanvas.Color[u] + "\t");
        }
        System.out.print( "\n " );*/
        zumaCanvas.getColor();
       /* System.out.print( "Color " );
        for ( int u = 0; u < 10; u++ ) {
            System.out.print( zumaCanvas.Color[u] + "\t");
        }
        System.out.print( "\n " );*/
        Ball.setFrame(zumaCanvas.getRandAmong(zumaCanvas.Color));
        //Ball = null;
        //ranColor = zumaCanvas.getRand(0, 2);
        /*try {
            if ( ranColor == 0)
                Ball = new Sprite(Image.createImage("/picture/bong_do.png"), 16, 16);
            else if ( ranColor == 1 )
                Ball = new Sprite(Image.createImage("/picture/bong_xanh2.png"), 16, 16);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/

        Ball.setVisible(true);
    }
}
