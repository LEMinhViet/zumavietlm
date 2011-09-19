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
public class Boss {
    ZumaCanvas zumaCanvas;
    Sprite Boss = null;
    int MoveSpeed, MoveDistance, Direction, Step = 0, BoHealth;
    int x, y, x1, y1, delay = 50, xModel, yModel, shootSpeed = 15, runTimes = 0, xD, yD;
    Image Shoot;
    boolean bShoot;

    public Boss ( ZumaCanvas zumaCanvas) {
        this.zumaCanvas = zumaCanvas;
        if ( zumaCanvas.runningLevel == 3 ) {
            x = 200; y = 120;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-1.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 6 ) {
            x = 200; y = 120;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-lv2.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 9 ) {
            x = 200; y = 120;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-lv3.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 12 ) {
            x = 200; y = 120;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-lv4.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 13 ) {
            //x = 200; y = 120;
            x1 = 90; y1 = 18;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-lv5.png"), 60, 39);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        zumaCanvas.lm.insert(Boss, 0);
        Boss.setPosition(x1, y1);
    }

     public Boss ( ZumaCanvas zumaCanvas, int runningLevel ) {
        this.zumaCanvas = zumaCanvas;
        if ( zumaCanvas.runningLevel == 13 ) {
            x1 = 86; y1 = 276;
            try {
                Shoot = zumaCanvas.Sball.returnBall();
                Boss = new Sprite(Image.createImage("/picture/boss-lv4.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Boss.setTransform(Boss.TRANS_ROT90);
            BoHealth = 10;
        }
     }

    public void clearBoss () {
        zumaCanvas.lm.remove(Boss);
        Boss = null;
        Shoot = null;
    }

    public void AIBoss1 () {
        /////////////////////// Boss chuyen dong \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( delay == 0 ) {
            if ( Step == 0 ) {
                Direction = zumaCanvas.getRand( 0, 2);

                if ( Direction == 0 ) {
                    if ( Boss.getY() == 0 ) MoveDistance = 0;
                    else    MoveDistance = zumaCanvas.getRand(0, Boss.getY());
                } else {
                    if ( 320 - Boss.getHeight() - Boss.getY() <= 0 ) MoveDistance = 0;
                    else
                        MoveDistance = zumaCanvas.getRand(0, 320 - Boss.getHeight() - Boss.getY());
                }
            }
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
        /////////////////////// Boss ban' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( delay == 0 && !bShoot) {
            runTimes = 0;
            xModel = zumaCanvas.model.Model.getX();
            yModel = zumaCanvas.model.Model.getY();
            xD = xModel - Boss.getX() - 6;
            yD = yModel - Boss.getY() - 28;
            xD = xD/40;
            yD = yD/40;
            bShoot = true;
        }
        if ( bShoot ) {
            zumaCanvas.g.drawImage(Shoot, Boss.getX() + 6 + xD*runTimes, Boss.getY() + 28 + yD*runTimes, Graphics.TOP | Graphics.LEFT);
            runTimes++;
            if ( runTimes >= 55 ) bShoot = false;
        }

        /////////////////////// Boss mau' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( zumaCanvas.Shoot && zumaCanvas.Sball.Ball.collidesWith(Boss, true)) {
            zumaCanvas.Sball.Ball.setVisible(false);
            BoHealth--;
        }
        if ( BoHealth == 0 ) {
            Boss.setVisible(false);
            zumaCanvas.State5 = true;
            zumaCanvas.nextLevel();
        }
    }

    public void AIBoss2 () {
        /////////////////////// Boss chuyen dong \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        System.out.println("Move " + MoveDistance);
        if ( delay == 0 ) {
            if ( Step == 0 ) {
                Direction = zumaCanvas.getRand( 0, 2);

                if ( Direction == 0 ) {
                    if ( Boss.getX() == 0 ) MoveDistance = 0;
                    else    MoveDistance = zumaCanvas.getRand(0, Boss.getX());
                } else {
                    if ( 240 - Boss.getWidth() - Boss.getX() <= 0 ) MoveDistance = 0;
                    else
                        MoveDistance = zumaCanvas.getRand(0, 240 - Boss.getWidth() - Boss.getX());
                }
            }
            Step+=2;
            if ( Direction == 0 ) {
                x1 -= 2;
                Boss.setPosition( x1, y1);
            } else {
                x1 += 2;
                Boss.setPosition( x1, y1);
            }
            if ( Step >= MoveDistance ) {
                Step = 0;
                delay = 50;
            }
        } else {
            delay--;
        }
        /////////////////////// Boss ban' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( delay == 0 && !bShoot) {
            runTimes = 0;
            xModel = zumaCanvas.model.Model.getX();
            yModel = zumaCanvas.model.Model.getY();
            xD = xModel - Boss.getX() - 6;
            yD = yModel - Boss.getY() - 28;
            xD = xD/40;
            yD = yD/40;
            bShoot = true;
        }
        if ( bShoot ) {
            zumaCanvas.g.drawImage(Shoot, Boss.getX() + 6 + xD*runTimes, Boss.getY() + 28 + yD*runTimes, Graphics.TOP | Graphics.LEFT);
            runTimes++;
            if ( runTimes >= 55 ) bShoot = false;
        }

        /////////////////////// Boss mau' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( zumaCanvas.Shoot && zumaCanvas.Sball.Ball.collidesWith(Boss, true)) {
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
