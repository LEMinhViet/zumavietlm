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
public class Boss {
    ZumaCanvas zumaCanvas;
    Sprite Boss = null;
    int MoveSpeed, MoveDistance, Direction, Step = 0, BoHealth;
    int x, y, delay = 50;
    public Boss ( ZumaCanvas zumaCanvas) {
        this.zumaCanvas = zumaCanvas;
        if ( zumaCanvas.runningLevel == 3 ) {
            x = 200; y = 120;
            try {
                Boss = new Sprite(Image.createImage("/picture/boss-1.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        zumaCanvas.lm.insert(Boss, 0);
        Boss.setPosition(x, y);
    }

    public void AIBoss1 () {
        if ( delay == 0 ) {
            if ( Step == 0 ) {
                Direction = zumaCanvas.getRand( 0, 2);

                if ( Direction == 0 ) {
                    if ( Boss.getY() == 0 ) MoveDistance = 0;
                    else    MoveDistance = zumaCanvas.getRand(0, Boss.getY());
                } else {
                    if ( 320 - Boss.getHeight() - Boss.getY() < 0 ) MoveDistance = 0;
                    else
                        MoveDistance = zumaCanvas.getRand(0, 320 - Boss.getHeight() - Boss.getY());
                }
                System.out.println("Distance " + MoveDistance);
            }
            System.out.println("X, Y " + Boss.getX() + " " + Boss.getY() );
            Step+=2;
            if ( Direction == 0 ) {
                y -= 2;
                Boss.setPosition( x, y);
            } else {
                y += 2;
                Boss.setPosition( x, y);
            }
            if ( Step >= MoveDistance ) {
                Step = 0;
                delay = 50;
            }
        } else {
            delay--;
        }
        if ( zumaCanvas.Sball.Ball.collidesWith(Boss, true)) {
            zumaCanvas.Sball.Ball.setVisible(false);
            BoHealth--;
        }
        if ( BoHealth == 0 ) {
            Boss.setVisible(false);
            zumaCanvas.State5 = true;
            zumaCanvas.nextLevel();
        }
    }
}
