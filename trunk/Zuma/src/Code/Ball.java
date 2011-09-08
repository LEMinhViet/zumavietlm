/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//text
package Code;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
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
    int nextColor, saveColor;
    
    public Ball( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
        ShootDistance = 15;
        ShootSpeed = 15;
        //ShootAngle = zumaCanvas.iCount;
    }

    public void initBall() {
        try {
            /*if ( ranColor == 0)
                Ball = new Sprite(Image.createImage("/picture/bong_do.png"), 16, 16);
            else if ( ranColor == 1 )
                Ball = new Sprite(Image.createImage("/picture/bong_xanh2.png"), 16, 16);*/
            if ( Ball == null ) Ball = new Sprite(Image.createImage("/picture/bi.png"), 16, 16);
            if ( Pixel == null ) Pixel = new Sprite(Image.createImage("/picture/pixel.png"), 1, 1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Ball.setFrameSequence(ballSeq);
        zumaCanvas.lm.insert(Ball, 0);
        zumaCanvas.lm.insert(Pixel, 0);
        Ball.setPosition( getPositionX(), getPositionY() );
        zumaCanvas.getColor();
        Ball.setFrame(zumaCanvas.getRandAmong(zumaCanvas.Color));
        nextColor = zumaCanvas.getRandAmong(zumaCanvas.Color);
        
    }

    public void shootBall( float ShootAngle ){
        Ball.setPosition((int)( getPositionX() + ShootDistance*Math.cos((ShootAngle/180)*Math.PI-Math.PI/2)),
                                (int)( getPositionY() + ShootDistance*Math.sin((ShootAngle/180)*Math.PI-Math.PI/2)));
    }

    public void resetBall(){
        //System.out.println("resetBall " + zumaCanvas.Part );
        if ( zumaCanvas.Part != 0 || ( zumaCanvas.Part == 1 && zumaCanvas.vBall[0].BVector.size() == 0 )) {
            // Hiện lại bóng để bắn, các biến liên quan đến việc bắn về lại trị số ban đầu
            ShootDistance = 15;
            Ball.setPosition((int)(getPositionX() + 15*Math.cos((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)),
                            (int)(getPositionY() + 15*Math.sin((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)));
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
            Ball.setFrame(nextColor);
            nextColor = zumaCanvas.getRandAmong(zumaCanvas.Color);
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

    public int getPositionX ( ) {
        return zumaCanvas.model.Model.getX() + zumaCanvas.model.Model.getWidth()/2 - 8;
    }

    public int getPositionY ( ) {
        return zumaCanvas.model.Model.getY() + zumaCanvas.model.Model.getHeight()/2 - 8;
    }

    public void drawNextBall ( Graphics g ) {
        saveColor = g.getColor();
        if ( nextColor == 0 ) g.setColor(0x00ff00);
        else if ( nextColor == 1 ) g.setColor(0x0000ff);
        else if ( nextColor == 2 ) g.setColor(0xff0000);
        else if ( nextColor == 3 ) g.setColor(0xffff00);
        g.fillArc( zumaCanvas.model.Model.getX() + 30 + -5,
                    zumaCanvas.model.Model.getY() + 30 + -5, 10, 10, 0, 360);
        //System.out.println("toa do " + Math.cos(-3*(Math.PI)/4) + " " + Math.sin(-3*(Math.PI)/4));
        g.setColor(saveColor);
    }
}
