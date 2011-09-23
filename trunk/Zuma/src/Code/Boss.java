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
    int MoveSpeed, MoveDistance, Direction, Step = 0, BoHealth, effectTime, effectTime2;
    int x, y, x1, y1, delay = 0, xModel, yModel, shootSpeed = 15, runTimes = 0, xD, yD;
    Sprite [] Heart = new Sprite [5];
    Sprite Shoot;
    boolean bShoot;
    byte [] cantShoot = new byte [12];

    public Boss ( ZumaCanvas zumaCanvas) {
        this.zumaCanvas = zumaCanvas;
        try {
            Shoot = new Sprite(Image.createImage("/picture/dau-lau.png"), 20, 40);
            for ( int y = 0; y < 5; y++ ) {
                Heart[y] = new Sprite ( Image.createImage("/picture/trai-tim.png"), 10, 8 );
                zumaCanvas.lm.insert(Heart[y], 0);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if ( zumaCanvas.runningLevel == 3 ) {
            x = 200; y = 120;
            try {
                Boss = new Sprite(Image.createImage("/picture/boss-1.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 6 ) {
            x = 200; y = 120;
            try {
                Boss = new Sprite(Image.createImage("/picture/boss-lv2.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 9 ) {
            x = 200; y = 120;
            try {
                Boss = new Sprite(Image.createImage("/picture/boss-lv3.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        if ( zumaCanvas.runningLevel == 12 ) {
            x = 200; y = 120;
            try {
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
                Boss = new Sprite(Image.createImage("/picture/boss-lv5.png"), 60, 39);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BoHealth = 10;
        }
        zumaCanvas.lm.insert(Boss, 0);
        zumaCanvas.lm.insert(Shoot, 0);
        if ( zumaCanvas.runningLevel != 13 )   Shoot.setTransform(Shoot.TRANS_MIRROR_ROT90);
        Boss.setPosition(x, y);
        zumaCanvas.BossInsert = false;
    }

     public Boss ( ZumaCanvas zumaCanvas, int runningLevel ) {
        this.zumaCanvas = zumaCanvas;
        if ( zumaCanvas.runningLevel == 13 ) {
            x1 = 86; y1 = 276;
            try {
                for ( int y = 0; y < 5; y++ ) {
                    Heart[y] = new Sprite ( Image.createImage("/picture/trai-tim.png"), 10, 8 );
                    zumaCanvas.lm.insert(Heart[y], 0);
                }
                Shoot = new Sprite(Image.createImage("/picture/dau-lau.png"), 20, 40);
                Boss = new Sprite(Image.createImage("/picture/boss-lv4.png"), 39, 60);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            zumaCanvas.lm.insert(Boss, 0);
            zumaCanvas.lm.insert(Shoot, 0);
            Shoot.setTransform(Shoot.TRANS_ROT180);
            Boss.setTransform(Boss.TRANS_ROT90);
            
            BoHealth = 10;
        }
        zumaCanvas.BossInsert = false;
     }

    public void clearBoss () {
        zumaCanvas.lm.remove(Boss);
        Boss = null;
        Shoot = null;
    }

    public void BossColli () {
        if ( Shoot.collidesWith(zumaCanvas.model.Model, true) ) {
            bShoot = false;
            effectTime = 200;
            if ( zumaCanvas.runningLevel == 6 ) Designer.toBytesIndex("can't shoot", cantShoot);
        }
        BossEffect();
    }

    public void BossColli13( int iB ) {
        if ( Shoot.collidesWith(zumaCanvas.model.Model, true) ) {
            bShoot = false;
            effectTime = 200;
            effectTime2 = 200;
            Designer.toBytesIndex("can't shoot", cantShoot);
        }
        if ( effectTime >= 0 )  {
            BossEffect13(iB);
        }
    }

    public void BossEffect() {
        switch ( zumaCanvas.runningLevel ) {
                // di chuyển chậm
            case 3:     effectTime--;
                        if ( effectTime > 0 )   {
                            if ( zumaCanvas.keyPressed % 5 == 0 ) {
                                if ( zumaCanvas.distanceAdd >= 5 ) zumaCanvas.distanceAdd -= 2;
                                else zumaCanvas.distanceAdd -= 1;
                            }
                        }
                        break;
                 //Không thể bắn
            case 6:     effectTime -= 2;
                        if ( effectTime > 0 ) {
                              if ( effectTime %16 >= 8 ) {
                                Designer.drawString(zumaCanvas.g, cantShoot, 0, 11, 2, zumaCanvas.model.Model.getX(), zumaCanvas.model.Model.getY() - 15);
                            } else {
                                Designer.drawString(zumaCanvas.g, cantShoot, 0, 11, 2, zumaCanvas.model.Model.getX(), zumaCanvas.model.Model.getY() - 18);
                            }
                            zumaCanvas.Shoot = false;
                          }
                        break;
                 //Không thể di chuyển
            case 9:     effectTime -= 2;
                        zumaCanvas.distanceAdd = 0;
                        break;
                // Mất item
            case 12:    break;
        }
    }

    public void BossEffect13( int iB ) {
        if ( iB == 1 ) {
            effectTime--;
            if ( effectTime > 0 )   {
                if ( zumaCanvas.keyPressed % 5 == 0 ) {
                    if ( zumaCanvas.distanceAdd >= 5 ) zumaCanvas.distanceAdd -= 2;
                    else zumaCanvas.distanceAdd -= 1;
                }
            }
        } else {
            effectTime2 -= 2;
            if ( effectTime2 > 0 ) {
                  if ( effectTime2 %16 >= 8 ) {
                    Designer.drawString(zumaCanvas.g, cantShoot, 0, 11, 2, zumaCanvas.model.Model.getX(), zumaCanvas.model.Model.getY() - 15);
                } else {
                    Designer.drawString(zumaCanvas.g, cantShoot, 0, 11, 2, zumaCanvas.model.Model.getX(), zumaCanvas.model.Model.getY() - 18);
                }
                zumaCanvas.Shoot = false;
              }
        }
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
        if ( !bShoot) {
            runTimes = 0;
            xModel = zumaCanvas.model.Model.getX();
            yModel = zumaCanvas.model.Model.getY();
            xD = xModel - Boss.getX() - 6;
            yD = yModel - Boss.getY() - 28;
            xD = xD/40;
            yD = yD/40;
            xModel = Boss.getX();
            yModel = Boss.getY();
            bShoot = true;
        }
        if ( bShoot ) {
            Shoot.setPosition(xModel + 6 + xD*runTimes, yModel + 28 + yD*runTimes);
            runTimes++;
            if ( runTimes %5 == 0 ) Shoot.nextFrame();
            if ( runTimes >= 100 ) bShoot = false;
        }

        /////////////////////// Boss mau' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( zumaCanvas.Shoot && zumaCanvas.Sball.Ball.collidesWith(Boss, true)) {
            zumaCanvas.Sball.Ball.setVisible(false);
            BoHealth--;
        }
        if ( zumaCanvas.runningLevel == 13 ) {
            for ( int w = 0; w < 5; w++ ) {
                Heart[w].setPosition( Boss.getX() + Boss.getWidth() + 5, Boss.getY() + 10*w);
            }
        } else {
            for ( int w = 0; w < 5; w++ ) {
                Heart[w].setPosition( Boss.getX() + 12*w, Boss.getY() + Boss.getHeight() + 5);
            }
        }
        
        if ( BoHealth == 0 ) {
            Boss.setVisible(false);
            zumaCanvas.State5 = true;
            zumaCanvas.nextLevel();
        } else {
            if ( BoHealth%2 == 1 )  Heart[BoHealth/2].setFrame(1);
            else {
                Heart[BoHealth/2-1].setFrame(0);
                for ( int w = BoHealth/2; w < 5; w++ ) {
                    Heart[w].setVisible(false);
                }
            }
        }
    }

    public void AIBoss2 () {
/////////////////////// Boss chuyen dong \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//        System.out.println("Move " + MoveDistance);
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
        if ( !bShoot) {
            runTimes = 0;
            xModel = zumaCanvas.model.Model.getX();
            yModel = zumaCanvas.model.Model.getY();
            xD = xModel - Boss.getX() - 6;
            yD = yModel - Boss.getY() - 28;
            xD = xD/40;
            yD = yD/40;
            xModel = Boss.getX();
            yModel = Boss.getY();
            bShoot = true;
        }
        if ( bShoot ) {
            Shoot.setPosition( xModel + 6 + xD*runTimes, yModel + 28 + yD*runTimes);
            runTimes++;
            if ( runTimes %5 == 0 ) Shoot.nextFrame();
            if ( runTimes >= 200 ) bShoot = false;
        }

        /////////////////////// Boss mau' \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if ( zumaCanvas.Shoot && zumaCanvas.Sball.Ball.collidesWith(Boss, true)) {
            zumaCanvas.Sball.Ball.setVisible(false);
            BoHealth--;
        }
        for ( int w = 0; w < 5; w++ ) {
            Heart[w].setPosition( Boss.getX() + Boss.getWidth() + 5, Boss.getY() + 10*w);
        }
        if ( BoHealth == 0 ) {
            Boss.setVisible(false);
            zumaCanvas.State5 = true;
            zumaCanvas.nextLevel();
        } else {
            if ( BoHealth%2 == 1 )  Heart[BoHealth/2].setFrame(1);
            else {
                Heart[BoHealth/2-1].setFrame(0);
                for ( int w = BoHealth/2; w < 5; w++ ) {
                    Heart[w].setVisible(false);
                }
            }
        }
    }
}
