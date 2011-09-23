package Code;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class ZumaCanvas extends GameCanvas implements Runnable {
    Random randomizer = new Random();
    Model model;
    Sprite BGSpr, Level1, L1_way, L2_way, wayPoint, wayBall, Lv_patch1, Lv_patch2, Lv_patch3, Lv_patch4, Lv_patch5, FinishPoint, removeSprite;
    Sprite Sound;
    Image Pause, Gauge, Gauge_full;
    byte[] Level = new byte[18];
    byte[] backmenu1 = new byte[18];
    byte[] backmenu2 = new byte[19];
    byte[] backmenu3 = new byte [6];
//    int [] breakSeq = { 0, 1, 2, 3 };
//    int [] finPointSeq = { 0, 1, 2, 3};
    int ite, iteS0, iteS0Max, NumB, runningLevel;
    int keyState, testX = 20, testY = 220, Number, distanceAdd;
    int InsertTime, add , partColli, partColliBack, widthLv, heightLv;
    int sumOfDistance, sumOfInsert, backCount, countDownTiming = 1, countDownIte, sumOfBack;
    int checkTime, NumOfColor, BreakingTime, runIte, iColliSave, iS5, Num, OldNum, widthMap, heightMap;
    int i, j, m, k, r, w, h, u, z, x, arr, times = 1, iColli, angleCount, runCount;
    int iColliS2, ranA, keyPressed, angleAdd = 3, subDistance, check_1ball, resetBall;
    int Part = 1, langID, ibackmenu1, ibackmenu2, ibackmenu3, submenu, SubmenuState, SubColor;
    int ScoretoOver, gaugeCount;
    Player player;
    Score score = new Score(this);
    Navigator N = new Navigator(this);
    Effect E1 = new Effect ( this, 1 );
    double dr, radian;
    long sleep, timeSinceStart;
    Sprite[] breakBall = new Sprite[30];
    BallVector[] vBall = new BallVector[10];
    BallVector[] vBallX = new BallVector[10];
    Ball Sball = new Ball(this);
    Boss Boss, BossFinal;
    //Lưu điểm đầu
    int width, height;
    // Zigzac để đảm bảo đoàn bi không bị chèn lên nhau
    int zigzac = 0;
    int [] Color = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    int [] ColorOld = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    int [][] lv = new int [2560][3];
    int [] moveX = { 0, 1, 0, -1,};
    int [] moveY = { -1, 0, 1, 0,};
    float iCount = 1;
    boolean run, State0 = false, State1 = false, State2 = false, State3 = false, State4 = false, State5 = true, State6 = false, State7 = false, Divide = false, Asm = false;
    boolean Stop = false, Back = false, Shoot = false, Colli = false, pColli = false, isStateChange = false, isColliAtEnd = false;
    boolean headInsert = false, backWard = true, afterBack = false, addColor = false, Breaking = false, Breaked = false, restartLv = false;
    boolean OutOfBalls = false, unchangeColor = true, colliAfter = false, notBreak = false, Be_1ball = false, Af_1ball, nextLv = false;
    boolean isColli = false, isRotated = false, drawScore = false, ballReset, nextballreset, firstTime = true, BossInsert = false;
    boolean slow = false;
    public LayerManager lm;
    StartMidlet Midlet;
    Thread t;
    private int MAX_CPS = 30;
    private int MS_PER_SECOND = 1000/MAX_CPS;
    private long timeLastCycle = 0;
    Graphics g = getGraphics();
    Graphics gG = getGraphics();
    
    public ZumaCanvas(StartMidlet Midlet) {
        super(false);
        this.Midlet = Midlet;
        this.setFullScreenMode(true);
        this.lm = new LayerManager();

        try {
            Gauge = Image.createImage("/picture/trang-thai.png");
            Gauge_full = Image.createImage("/picture/trang-thai-_2.png");
            wayPoint = new Sprite(Image.createImage("/picture/pixel.png"), 1, 1);
            lm.append(wayPoint);

            wayBall = new Sprite(Image.createImage("/picture/bi.png"), 16, 16);
            lm.append(wayBall);

            for ( i = 0; i < 30; i++ ) {
                breakBall[i] = new Sprite(Image.createImage("/picture/no_bongxanh.png"), 25, 26);
//                breakBall[i].setFrameSequence(breakSeq);
                breakBall[i].setVisible(false);
                lm.append(breakBall[i]);
            }

            vBall[0] = new BallVector(this);
            vBall[0].NumOfBall = 100;
            NumB = 16*vBall[0].NumOfBall;
            
            runningLevel = 1;

            vBall[0].initBallVector();
            vBall[0].Begin = 0;
            vBall[0].End = NumB/16-1;

            
            model = new Model();
            model.initModel(this);
            lm.append(model.Model);
            Sball.initBall();

            Pause = Image.createImage("/menu/pause.png");
            Sound = new Sprite (Image.createImage("/menu/loa.png"), 13, 11);
            lm.append(Sound);
            Sound.setPosition( 5, getHeight() - Sound.getHeight() -5);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {

        Sball.resetBall();
        chooseLevel(runningLevel);
        wayBall.setVisible(false);
        wayPoint.setVisible(false);
        while ( run ) {
            System.out.println(((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getX()
                        + " " + ((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getY());
            timeLastCycle = System.currentTimeMillis();
            /////////////////////////////////
            // Model quay theo góc
            N.drawNavi(g);
            Sball.Ball.paint(g);
            Sball.drawNextBall(g);
            model.Model.paint(g);
            g.drawImage(Pause, getWidth()-Pause.getWidth()-5, getHeight()-Pause.getHeight()-5, Graphics.TOP | Graphics.LEFT);
            model.rotateModel();
            g.drawImage(Gauge, 0, 0, Graphics.TOP | Graphics.LEFT);

            drawGauge(gG);
            Designer.drawNumber(g, score.Score, 0, 70, 2);
//            Designer.drawNumber(g, score.Score, 1, 70, 2);

            if ( Shoot )    model.whenShoot(iCount);                      
            if ( runningLevel == 3 || runningLevel == 6 || runningLevel == 9 || runningLevel == 12 )    {
                if ( !BossInsert )  {
                    lm.insert(Boss.Boss, 0);
                    BossInsert = true;
                }
                if ( !State5 && !State6 ) {
                    Boss.AIBoss1( );
                    Boss.BossColli();
                }
            } else if ( runningLevel == 13 ) {
                if ( !BossInsert ) {
                    lm.insert(Boss.Boss, 0);
                    lm.insert(BossFinal.Boss, 0);
                    BossInsert = true;
                }
                if ( !State5 && !State6 )   {
                    Boss.AIBoss2( );
                    BossFinal.AIBoss2( );
                    Boss.BossColli13(1);
                    BossFinal.BossColli13(2);
               }
            }

            // Các trạng thái tốc độ của đoàn bi
            if ( ite >= NumB )  {
                if ( gaugeCount >= Gauge_full.getWidth() || State4 )   Number = NumB/16-1;
                else {
                    
                    vBall[0].addtoBallVector(5);
                    vBall[0].NumOfBall += 5;
                    NumB += 16*5;
                }
            } else    Number = ite/16;
            if ( Number == NumB/16-1 )   OutOfBalls = true;
            if ( !State2 )  vBall[0].End = Number;
            //System.out.println("So ball : " + Number);

            for ( i = 0; i < Part; i++ ) {
                for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                    if ( ((Sprite)vBall[i].BVector.elementAt(j)).getX() <= lv[0][0] &&
                            ((Sprite)vBall[i].BVector.elementAt(j)).getY() == lv[0][1] ) {
                        OutOfBalls = false;
                        //System.out.println("FALSE");
                    }
                }
            }

            runIte++;
        //if ( Shoot || (!Shoot && runIte%2 != 3)  || State0 || State2 ) {
        if ( runIte%2 != 2  || State0 || State2 ) {
            if ( ite < 0 )  ite = 0;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 0 : State chạy nhanh ban đầu lúc vừa vào level, sẽ không có bắn trong state này
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if ( State0 ) { // Vừa vào - tốc độ nhanh cho đến khi đủ 8 bi
                System.out.println("State0");
                iteS0++;
                ite += 24;
                for ( k = 0; k < Number + 1; k++ ){
                    /*if ( lv[ite-16*k][2] == 1 )
                        ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(false);
                    else ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(true);*/
                    ((Sprite) vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                    if ( ite-16*k >= 0 && lv[ite-16*k][2] == 1 )
                        ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(false);
                    else ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(true);
                }
//                System.out.println("iteS0 " + iteS0);

                if ( runningLevel == 1 )    iteS0Max = 25;
                else if(runningLevel == 2 )    iteS0Max = 55;
                else if ( runningLevel == 3 )   iteS0Max = 10;
                else if ( runningLevel == 4 )   iteS0Max = 40;
                else if ( runningLevel == 5 )   iteS0Max = 40;
                else if ( runningLevel == 6 )   iteS0Max = 10;
                else if ( runningLevel == 7 )   iteS0Max = 20;
                else if ( runningLevel == 8 )   iteS0Max = 30;
                else if ( runningLevel == 9 )   iteS0Max = 20;
                else if ( runningLevel == 10 )   iteS0Max = 30;
                else if ( runningLevel == 11 )   iteS0Max = 30;
                else if ( runningLevel == 12 )   iteS0Max = 15;
                else if ( runningLevel == 13 )   iteS0Max = 10;


                if ( iteS0 >= iteS0Max ) {
                    iteS0 = 0;
                    State0 = false;
                    State1 = true;
                }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 1 : State chính lúc đoàn bóng di chuyển tốc độ bình thường
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State1 ) {
                System.out.println("State1");
                //for ( i = 0; i < Part; i++ ) {
                    for ( j = 0; j < Number+1; j++ ) {
                        ((Sprite)vBall[0].BVector.elementAt(j)).setVisible(true);
                    }
                //}
                //System.out.println("Số ball ; " + vBall[0].BVector.size());
                //vBall[0].End = Number;
                if ( !Stop && !Back ) {
                    if ( slow ) {
                        ite++;
//                        slow = false;
                    } else slow = true;
                }
                else if ( Back ) ite--;
                for ( k = 0; k < Number + 1; k++ ) {
                    try {
                        ((Sprite)vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                    } catch ( ArrayIndexOutOfBoundsException b ) {
//                        System.out.println("End " + vBall[0].End + " size " + vBall[0].BVector.size() + " k " + k );
                    }
                    if ( ite-16*k >= 0 && lv[ite-16*k][2] == 1 ) {
                        ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(false);
//                        System.out.println("Visible");
                    }
                    else ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(true);
                }
                
                ///////////////////////////////////////////////////////////////////////////////
                // Xử lý bắn bóng 
                S1Shoot();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 2 : bóng trước lùi về bóng sau nếu 2 đầu bóng cùng màu
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State2 ) {
                System.out.println("State2");
                if ( Divide ) {                    
                    ////////////////////////////////////////////////////////////////////////////////////
                    // Tách làm 2 đoạn sau khi có khoảng trốngS
                    ////////////////////////////////////////////////////////////////////////////////////
                    vBall[Part] = new BallVector(this);
                    Part++;
//                    System.out.println("Part " + Part);
                    /*for ( j = Part-1; j >= partColliBack+1; j-- ) {
                        vBall[j].Distance = vBall[j-1].Distance;
                    }*/
                    
                    //if ( Part > 1 && partColliBack + 1 != Part - 1 )
                     //   vBall[partColliBack].Distance -= 16;
                    
                    subDistance = vBall[partColliBack].Distance;
                    vBall[partColliBack].Distance = 0;
                    
                    /// Bỏ bóng
                    for ( m = vBall[partColliBack].beginBreak; m <= vBall[partColliBack].endBreak; m++ ) {
                        try {
                            removeSprite = (Sprite)vBall[partColliBack].BVector.elementAt(vBall[partColliBack].beginBreak);
                            vBall[partColliBack].BVector.removeElementAt(vBall[partColliBack].beginBreak);
                            removeSprite();
                        } catch ( ArrayIndexOutOfBoundsException ae ) {
                            //System.out.println("partColliBack " + partColliBack );
                        }
                        NumB -= 16;
                        vBall[partColliBack].Distance += 16;
                        //Number--;
                    }


                    for ( j = Part-1; j >= partColliBack+2; j-- ) {
                        vBall[j-1].copyBallVectorTo(vBall[j]);
                    }

                    vBall[partColliBack+1].Distance = subDistance;
                    vBall[Part - 1].Distance = 0;
                    ////////////////////////////////
                    //
                    //
                    //
                    ////////////////////////////////
                    vBall[partColliBack+1].End = vBall[partColliBack].beginBreak - 1 + vBall[partColliBack].Begin;
                    vBall[partColliBack+1].Begin = vBall[partColliBack].Begin;
                    vBall[partColliBack].Begin = vBall[partColliBack+1].End + 1;
                    vBall[partColliBack].End -= (vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1);

                    for( j = partColliBack - 1; j >= 0; j-- ) {
                        vBall[j].Begin -= (vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1);
                        vBall[j].End -= (vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1);
                    }

                    for ( j = vBall[partColliBack+1].Begin; j <= vBall[partColliBack+1].End; j++ ) {
                        vBall[partColliBack+1].BVector.addElement(vBall[partColliBack].BVector.elementAt(0));
                        vBall[partColliBack].BVector.removeElementAt(0);
                    }
                    
                    
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Xử lý vector 0 add bóng => phân tách ra thành vector 0 và 1, vector 1 distance noi vao vector 2 => asm luôn
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    for ( int x = partColli+1; x < Part - 1; x++ ) {
                        if ( Part == 1 && x == 0 )  continue;
                        if ( vBall[x].Distance <= 0 ) {
                            for ( j = x; j < Part - 1; j++ ) {
                                vBall[j].Distance = vBall[j+1].Distance;
                            }

                            for ( k = 0; k <= vBall[x+1].End-vBall[x+1].Begin; k++ ) {
                                if ( vBall[x+1].BVector.size()-1 >= 0 ) {
                                    vBall[x].BVector.insertElementAt(vBall[x+1].BVector.elementAt(vBall[x+1].BVector.size()-1), 0);
                                    vBall[x+1].BVector.removeElementAt(vBall[x+1].BVector.size()-1);
                                }
                            }

                            vBall[x].Begin = vBall[x+1].Begin;
                            for ( j = x+2; j < Part; j++ ) {
                                vBall[j].copyBallVectorTo(vBall[j-1]);
                            }
                            Part--;
                            for ( j = 0; j < vBall[x].BVector.size(); j++ ) {
                                ((Sprite)vBall[x].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[x].Begin)][0], lv[ite-16*(j+vBall[x].Begin)][1]);
                                if ( ite-16*(j+vBall[x].Begin) >= 0 && lv[ite-16*(j+vBall[x].Begin)][2] == 1 )
                                    ((Sprite) vBall[x].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[x].BVector.elementAt(j)).setVisible(true);
                            }
                        }
                    }
                    iColli = vBall[partColliBack+1].End - vBall[partColliBack+1].Begin;
                    for ( i = 0; i < Part; i++ ) {
//                        System.out.println("Here1");
//                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                    }
                    Divide = false;
                } else {
                    ////////////////////////////////////////////////////////////////////////////////////
                    // Lùi về đến khi hết khoảng cách
                    ////////////////////////////////////////////////////////////////////////////////////
                    if ( backWard ) {
                        countDownIte++;
                        if ( countDownIte > 8 && countDownIte % 3 == 2 ) {
                           countDownTiming = (int)(countDownTiming*3);
                        }
                        //System.out.println("ColliBack " + partColliBack);
                        if ( vBall[partColliBack].Distance - countDownTiming > 0 ) {
                            vBall[partColliBack].Distance -= countDownTiming;
                            if ( partColliBack + 1 == Part - 1)  ite -= countDownTiming;
                            else    vBall[partColliBack + 1].Distance += countDownTiming;
                            
                        } else {
                            if ( partColliBack + 1 == Part - 1 )    ite -= vBall[partColliBack].Distance;
                            else    vBall[partColliBack + 1].Distance += vBall[partColliBack].Distance;
                            vBall[partColliBack].Distance = 0;
                        }

                        sumOfBack = 0;
                        for ( i = partColliBack+1; i < Part - 1; i++ ) {
                            sumOfBack += vBall[i].Distance;
                        }

                        for ( i = 0; i < vBall[partColliBack+1].BVector.size(); ++i ) {
                            if ( ite-16*(i+vBall[partColliBack+1].Begin)-sumOfBack >= 0 ) {
                                ((Sprite)vBall[partColliBack+1].BVector.elementAt(i)).setPosition
                                    (lv[ite-16*(i+vBall[partColliBack+1].Begin)-sumOfBack][0], lv[ite-16*(i+vBall[partColliBack+1].Begin)-sumOfBack][1]);
                                if ( ite-16*(i+vBall[partColliBack+1].Begin)-sumOfBack >= 0 && lv[ite-16*(i+vBall[partColliBack+1].Begin)-sumOfBack][2] == 1 )
                                    ((Sprite) vBall[partColliBack+1].BVector.elementAt(i)).setVisible(false);
                                else ((Sprite) vBall[partColliBack+1].BVector.elementAt(i)).setVisible(true);
                            }

                        }

                        if ( vBall[partColliBack].Distance == 0 && InsertTime == 0 && !Shoot) {
//                            iColli =  vBall[partColliBack+1].BVector.size() - 1;
                            //System.out.println("iColliS2 " + iColliS2 + " beginbreak " + vBall[partColliBack].beginBreak + " endbreak " + vBall[partColliBack].endBreak);
                            
                            for ( k = 0; k <= vBall[partColliBack+1].End-vBall[partColliBack+1].Begin; k++ ) {
                                if ( vBall[partColliBack+1].BVector.size()-1 >= 0 ) {
                                    vBall[partColliBack].BVector.insertElementAt(vBall[partColliBack+1].BVector.elementAt(vBall[partColliBack+1].BVector.size()-1), 0);
                                    vBall[partColliBack+1].BVector.removeElementAt(vBall[partColliBack+1].BVector.size()-1);
                                }
                            }
//                            for ( j = partColliBack; j < Part - 1; j++ ) {
//                                vBall[j].Distance = vBall[j+1].Distance;
//                            }
                            vBall[partColliBack].Distance = vBall[partColliBack+1].Distance;
                            vBall[partColliBack].Begin = vBall[partColliBack+1].Begin;
                            vBall[partColliBack].End = vBall[partColliBack].Begin + vBall[partColliBack].BVector.size() - 1;
                            for ( j = partColliBack+2; j < Part; j++ ) {
                                vBall[j].copyBallVectorTo(vBall[j-1]);
                            }

                            Part--;
                            //if ( partColli == partColliBack + 1 )    partColli--;
                            partColli = partColliBack;
                            for ( i = 0; i < Part; i++ ) {
//                                System.out.println("Here2");
//                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                            }
                            afterBack = true;
                            backWard = false;
                        }
                    } else if ( afterBack ) {
                        ////////////////////////////////////////////////////////////////////////////////////
                        // Lùi tiếp theo quán tính
                        ////////////////////////////////////////////////////////////////////////////////////
                        if ( countDownTiming/2 != 0 )
                            countDownTiming = (int)(countDownTiming/1.8);
                        else countDownTiming = 0;
                        
                        if ( partColliBack > 0 ) {
                            if (vBall[partColliBack - 1].Distance - countDownTiming > 0) {
                                if ( partColliBack == Part - 1 )    ite -= countDownTiming;
                                else    vBall[partColliBack].Distance += countDownTiming;
                                vBall[partColliBack - 1].Distance -= countDownTiming;
                                
                            } else {
                                if ( partColliBack == Part - 1 )     ite-= vBall[partColliBack - 1].Distance;
                                else    vBall[partColliBack].Distance += vBall[partColliBack - 1].Distance;
                                vBall[partColliBack - 1].Distance = 0;

                                if ( !Shoot ) {
                                    for ( i = 0; i < vBall[partColliBack].End - vBall[partColliBack].Begin + 1; i++ ) {
                                        vBall[partColliBack-1].BVector.insertElementAt(vBall[partColliBack].BVector.elementAt(vBall[partColliBack].BVector.size()-1), 0);
                                        vBall[partColliBack].BVector.removeElementAt(vBall[partColliBack].BVector.size() - 1);
                                    }
                                    vBall[partColliBack-1].Begin = vBall[partColliBack].Begin;
                                    vBall[partColliBack-1].End = vBall[partColliBack-1].Begin + vBall[partColliBack-1].BVector.size() - 1;
                                    vBall[partColliBack-1].Distance = vBall[partColliBack].Distance;
                                    for ( i = partColliBack; i < Part - 1; i++ ) {
                                        vBall[i+1].copyBallVectorTo(vBall[i]);
                                    }
                                    Part--;
                                    if ( partColliBack > 0 ) partColliBack--;
                                    for ( i = 0; i < Part; i++ ) {
//                                        System.out.println("Here6");
//                                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                                    }
                                    //partColli--;
                                    //iColli = vBall[partColliBack].End - vBall[partColliBack].Begin;
                                    //vBall[partColliBack].testBreak(iColli);
                                }
                            }
                        } else if ( partColliBack == 0 ) {
                            if ( Part == 1 ) ite -= countDownTiming;
                            else    vBall[0].Distance += countDownTiming;
                        }

                        sumOfBack = 0;
                        for ( i = partColliBack; i < Part - 1; i++ ) {
                            sumOfBack += vBall[i].Distance;
                        }
                        
                        try {
                            for ( i = 0; i < vBall[partColliBack].BVector.size(); ++i ) {
                                if ( ite-16*(i+vBall[partColliBack].Begin) < 4 )
                                    ((Sprite)vBall[partColliBack].BVector.elementAt(i)).setPosition(lv[0][0], lv[0][1]);
                                else {
                                    ((Sprite)vBall[partColliBack].BVector.elementAt(i)).setPosition
                                            (lv[ite-16*(i+vBall[partColliBack].Begin)-sumOfBack][0], lv[ite-16*(i+vBall[partColliBack].Begin)-sumOfBack][1]);
                                    if ( ite-16*(i+vBall[partColliBack].Begin)-sumOfBack >= 0 && lv[ite-16*(i+vBall[partColliBack].Begin)-sumOfBack][2] == 1 )
                                        ((Sprite) vBall[partColliBack].BVector.elementAt(i)).setVisible(false);
                                    else ((Sprite) vBall[partColliBack].BVector.elementAt(i)).setVisible(true);
                                }
                            }
                        } catch ( ArrayIndexOutOfBoundsException e ) {
                            //System.out.println("AAAAAAA" + i);
                        }

                        //////////////////////////////////////////////////////////////////////
                        // Nếu lùi quá
                        if ( (partColliBack > 0 && vBall[partColliBack - 1].Distance == 0) || countDownTiming == 0) {
                            /*for ( j = partColliBack; j < Part - 1; j++ ) {
                                vBall[j].Distance = vBall[j+1].Distance;
                            }*/
                            vBall[Part-1].Distance = 0;
                            afterBack = false;
                            backWard = true;
                            countDownTiming = 1;
                            countDownIte = 0;
//                            System.out.println(vBall[0].End);
//                            System.out.println("partcolliback " + partColliBack + " " + vBall[0].BVector.size());
                            vBall[partColliBack].testBreak(iColli);
                            if ( Sball.Ball.getX() == (int)(Sball.getPositionX() + 20*Math.cos((iCount/180)*Math.PI-Math.PI/2))
                                        && Sball.Ball.getY() == (int)(Sball.getPositionY() + 20*Math.sin((iCount/180)*Math.PI-Math.PI/2)))  {
                                for ( int i = 0; i < 10; i++ ) {
                                    ColorOld[i] = Color[i];
                                }
                                getColor();
//                                System.out.println("AAAAAAAAAAAAAAA");
//                                System.out.println(" " + compareColor( Color, ColorOld ));
                                if ( compareColor( Color, ColorOld ) == 0 ) {
//                                    System.out.println("Compare ");
                                    if ( nextballreset )
                                        Sball.nextColor = getRandAmong(Color);
                                    if ( ballReset ) 
                                        Sball.resetBall();
                                }
                                
                            }
                                    
                            for ( i = 0; i < Part; i++ ) {
//                                System.out.println("Here5");
//                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                            }

                            if ( !Divide && !State5) {
                                if ( !Breaked && Part == 1) {
                                        State1 = true;
                                        State2 = false;
                                        State3 = false;
                                } else if ( !State2 || ( State2 && notBreak )) {
                                    for ( j = 0; j < Part; j++ ) {
                                        if ( vBall[j].Distance != 0 ) {
                                            State3 = true;
                                            State2 = false;
                                            State1 = false;
                                            break;
                                        } else {
                                            State3 = false;
                                            State2 = false;
                                            State1 = true;
                                        }
                                    }
                                    if ( State3 )  {
                                        partColli = j;
                                        
                                    }
                                }  
                            }
                            if ( Part == 1 )    partColli = 0;
//                            System.out.println("  "+State1+State2+State3 + " divide "+ Divide + " p " + partColli);

                            /*System.out.println("OutBall " + OutOfBalls);
                            if ( !OutOfBalls ) {
                                System.out.println("OutOfBall");
                                if ( ((Sprite)vBall[partColli].BVector.elementAt(0)).getX() == 1 - 8 &&
                                      ((Sprite)vBall[partColli].BVector.elementAt(0)).getY() == 65 - 8 ) {
                                    System.out.println("State3");
                                    State3 = true;
                                    State1 = false;
                                    State2 = false;
                                }
                            }*/
                        }
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////
                // Điều khiển bắn bóng State2
                ////////////////////////////////////////////////////////////////////////////////////
                S2Shoot ();
                
                

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 3 : Bóng sau di chuyển để bắt kịp đoàn bóng trước nếu 2 đầu không cùng màu
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State3 ) {
                System.out.println("State3");
                score.multiNum = 1;
                //ite++;
                if ( Divide ) {
                    vBall[Part] = new BallVector(this);
                    Part++;

                    for ( j = Part-1; j >= partColli+2; j-- ) {
                        vBall[j-1].copyBallVectorTo(vBall[j]);
                    }
                    
                    /*for ( j = Part-1; j >= partColli+1; j-- ) {
                        vBall[j].Distance = vBall[j-1].Distance;
                    }*/
                    if ( Part > 1 && partColli + 1 < Part - 1 ) {
                        vBall[partColli+1].Distance = vBall[partColli].Distance;
                        //vBall[partColli+1].Distance -= 16;
                    }
                    vBall[partColli].Distance = 0;                    
                    
                    /// Bỏ bóng
                    for ( m = vBall[partColli].beginBreak; m <= vBall[partColli].endBreak; m++ ) {
                        try {
                            removeSprite = (Sprite) vBall[partColli].BVector.elementAt(vBall[partColli].beginBreak);
                            vBall[partColli].BVector.removeElementAt(vBall[partColli].beginBreak);
                            removeSprite();
                        } catch ( ArrayIndexOutOfBoundsException ae ) {
                            //System.out.println("partColli " + partColli );
                        }
                        NumB -= 16;
                        vBall[partColli].Distance += 16;
                    }
                    // Dồn các vector bóng khác về sau và tạo một vector bóng mới

                    //vBall[i].End = vBall[i].BVector.size()-1;
                    
                    vBall[Part - 1].Distance = 0;
                    //vBall[0].End = NumB/16;

                    if ( partColli < Part - 1 ) {
                        vBall[partColli+1].End = vBall[partColli].beginBreak - 1 + vBall[partColli].Begin;
                        vBall[partColli+1].Begin = vBall[partColli].Begin;
                        for ( j = vBall[partColli+1].Begin; j <= vBall[partColli+1].End; j++ ) {
                            vBall[partColli+1].BVector.addElement(vBall[partColli].BVector.elementAt(0));
                            vBall[partColli].BVector.removeElementAt(0);
                        }
                    }              
                    
                    if ( partColli > 0 ) {
                        for( j = partColli - 1; j >= 0; j-- ) {
                            vBall[j].Begin -= (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);
                            vBall[j].End -= (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);
                        }
                    }

                    vBall[partColli].Begin = vBall[partColli+1].End + 1;
                    vBall[partColli].End = vBall[partColli].Begin + vBall[partColli].BVector.size() - 1;
                    //vBall[partColli].End = (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);
                    
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Xử lý vector 0 add bóng => phân tách ra thành vector 0 và 1, vector 1 distance < 0 => asm luôn
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    for ( int x = partColli; x < Part - 1; x++ ) {
                        if ( Part == 1 && x == 0 )  continue;
                        if ( vBall[x].Distance <= 0 ) {
                            /*for ( j = x; j < Part - 1; j++ ) {
                                vBall[j].Distance = vBall[j+1].Distance;
                            }*/
                            vBall[x].Distance = vBall[x+1].Distance;

                            for ( k = 0; k <= vBall[x+1].End-vBall[x+1].Begin; k++ ) {
                                if ( vBall[x+1].BVector.size()-1 >= 0 ) {
                                    vBall[x].BVector.insertElementAt(vBall[x+1].BVector.elementAt(vBall[x+1].BVector.size()-1), 0);
                                    vBall[x+1].BVector.removeElementAt(vBall[x+1].BVector.size()-1);
                                }
                            }

                            vBall[x].Begin = vBall[x+1].Begin;
                            for ( j = x+2; j < Part; j++ ) {
                                vBall[j].copyBallVectorTo(vBall[j-1]);
                            }
                            Part--;
                            for ( j = 0; j < vBall[x].BVector.size(); j++ ) {
                                ((Sprite)vBall[x].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[x].Begin)][0], lv[ite-16*(j+vBall[x].Begin)][1]);
                                if ( ite-16*(j+vBall[x].Begin) >= 0 && lv[ite-16*(j+vBall[x].Begin)][2] == 1 )
                                    ((Sprite) vBall[x].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[x].BVector.elementAt(j)).setVisible(true);
                            }
                        }
                    }
                    
                    for ( i = 0; i < Part; i++ ) {
//                        System.out.println("Here3");
//                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                    }
                    Divide = false;
                } else {
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Di chuyển đoàn bóng sau
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if ( vBall[0].Distance != 0 ) {
                        //for ( k = 0; k <= vBall[0].End-vBall[0].Begin; k++ ) {
                        sumOfDistance = 0;
                        for ( k = 0; k < Part - 1; k++ ) {
                            sumOfDistance += vBall[k].Distance;
                        }
                        try {
                            for ( k = 0; k < vBall[0].BVector.size(); k++ ) {
                                if ( ite-16*(k+vBall[0].Begin)-sumOfDistance >= 0 ) {
                                    ((Sprite)vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*(k+vBall[0].Begin)-sumOfDistance][0],
                                        lv[ite-16*(k+vBall[0].Begin)-sumOfDistance][1]);
                                    if ( ite-16*(k+vBall[0].Begin)-sumOfDistance >= 0 && lv[ite-16*(k+vBall[0].Begin)-sumOfDistance][2] == 1 )
                                        ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(false);
                                    else ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(true);
                                }
                            }
                        } catch ( ArrayIndexOutOfBoundsException aiobe ) {
//                            System.out.println("Number lỗi " + (Number-vBall[0].Begin));
                        }
                        if ( !Stop && !Back )    vBall[0].Distance--;
                        else if (Back) {
                            vBall[0].Distance++;
                        }
                    ///////////////////////////////////////////////////////////////////////////////
                    // Ghép 2 vector bóng
                    ///////////////////////////////////////////////////////////////////////////////
                    } else  {
                        if ( Part == 1 && !Colli ) {
                            State3 = false;
                            State1 = true;
                            //add = 0;
                            partColli = 0;
                        } else {
                            Asm = false;
                            if ( !Shoot && InsertTime == 0 && Sball.Ball.isVisible() )  Asm = true;
                            if ( Asm ) {
                                //System.out.println("SCORE  " + score.drawTimes);
                                //add = 0;
                                //if ( partColli > 0 )    partColli --;
                                /*for ( j = 0; j < Part - 1; j++ ) {
                                    vBall[j].Distance = vBall[j+1].Distance;
                                }*/
                                vBall[0].Distance = vBall[1].Distance;

                                for ( k = 0; k <= vBall[1].End-vBall[1].Begin; k++ ) {
                                    if ( vBall[1].BVector.size()-1 >= 0 ) {
                                        vBall[0].BVector.insertElementAt(vBall[1].BVector.elementAt(vBall[1].BVector.size()-1), 0);
                                        vBall[1].BVector.removeElementAt(vBall[1].BVector.size()-1);
                                    }
                                }                                
                                vBall[0].Begin = vBall[1].Begin;
                                vBall[0].End = vBall[0].Begin + vBall[0].BVector.size()-1;

                                for ( j = 2; j < Part; j++ ) {
                                    vBall[j].copyBallVectorTo(vBall[j-1]);
                                }
                               
                                Part--;
//                                for ( i = 0; i < Part; i++ ) {
//                                    System.out.println("Here4");
//                                    System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
//                                    System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
//                                }
                                Asm = false;
                            }
                        }
                    }
                    ///////////////////////////////////////////////////////////////////////////////
                    // Xử lý bắn bóng State3
                    ////////////////////////////////////////////////////////////////////////////
                    S3Shoot();
                }
///////////////////////////////////////////////////////////////////////////////
// State4 : kết thúc level
////////////////////////////////////////////////////////////////////////////
            } else if ( State4 ) {
                System.out.println("State4");
                for ( i = 0; i < Part; i++ ) {
                    for ( k = 0; k < vBall[i].BVector.size(); k++ ){
                        if ( ite-16*k >= 0 && ite-16*k < 2560 )
                            ((Sprite) vBall[i].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                        System.out.println(((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getX()
                        + " " + ((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getY());
                        if ( ite-16*k >= 0 && ite-16*k < 2560 && lv[ite-16*k][2] == 1 )
                            ((Sprite) vBall[i].BVector.elementAt(k)).setVisible(false);
                        
                        else ((Sprite) vBall[i].BVector.elementAt(k)).setVisible(true);
                        if (((Sprite)vBall[i].BVector.elementAt(k)).getX() == 0 && ((Sprite)vBall[i].BVector.elementAt(k)).getY() == 0 )
                            ((Sprite)vBall[i].BVector.elementAt(k)).setVisible(false);
                    }
                }

                if (((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getX() == 0
                        && ((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getY() == 0 ) {
                    //restartLv = true;
                    State0 = false;
                    State1 = false;
                    State4 = false;
                    State5 = true;
                }
                ite += 24;

///////////////////////////////////////////////////////////////////////////////
// State5 : load level
////////////////////////////////////////////////////////////////////////////
            } else if ( State5 ) {
                System.out.println("State5");
                if ( iS5 == 0 && !firstTime ) {   //restartLevel();
                    if ( nextLv ) {
                        nextLevel();
                        nextLv = false;
                    }  else if ( restartLv ) {
                        restartLevel();
                        restartLv = false;
                    }
                }

//                State0 = false;
//                State1 = false;
//                State4 = true;

                if ( iS5 == 0 && firstTime ) firstTime = false;
                if ( iS5 == 0 ) {
//                    try {
//                        player.stop();
//                    } catch (MediaException ex) {
//                        ex.printStackTrace();
//                    }
//                    System.out.println("Running " + runningLevel );
                    Designer.toBytesIndex("Level " + runningLevel + " . . . . . ", Level);
                }
                g.setColor(0x000000);
                g.fillRect(0, 0, 240, 320);
                Designer.drawCenterString(g, Level, 0, 6 + runningLevel/10 + 1 + 1 + iS5/10, 2, 120, 150);
                iS5++;
                if ( iS5 == 100 ) {
                    System.out.println("State5");
                    iS5 = 0;
                    State0 = true;
                    System.out.println("State0 " + State0 );
                    State5 = false;
                    restartLv = false;
                    BreakingTime = 0;
                    for ( i = 0; i < 30; i++ ) {
                        lm.remove(breakBall[i]);
                        lm.insert(breakBall[i], 0);
                    }
                }

///////////////////////////////////////////////////////////////////////////////
// State6 : Submenu
////////////////////////////////////////////////////////////////////////////
            } else if ( State6 ) {
                ibackmenu1 = 2;     ibackmenu2 = 2;     ibackmenu3 = 2;
                if ( submenu == 0 ) ibackmenu1 = 1;
                else if ( submenu == 1 )    ibackmenu2 = 1;
                else if ( submenu == 2 )    ibackmenu3 = 1;
                if ( langID == 1) {
                    Designer.toBytesIndex("Thoát và save game", backmenu1);
                    Designer.drawCenterString(g, backmenu1, 0, 18, ibackmenu1, getWidth()/2, getHeight()/2 - 20);
                    Designer.toBytesIndex("Thoát và không save", backmenu2);
                    Designer.drawCenterString(g, backmenu2, 0, 19, ibackmenu2, getWidth()/2, getHeight()/2);
                    Designer.toBytesIndex("Trở về", backmenu3);
                    Designer.drawCenterString(g, backmenu3, 0, 6, ibackmenu3, getWidth()/2, getHeight()/2 + 20);
                } else {
                    Designer.toBytesIndex("Save and Quit", backmenu1);
                    Designer.drawCenterString(g, backmenu1, 0, 13, ibackmenu1, getWidth()/2, getHeight()/2 - 20);
                    Designer.toBytesIndex("Quit without save", backmenu2);
                    Designer.drawCenterString(g, backmenu2, 0, 17, ibackmenu2, getWidth()/2, getHeight()/2);
                    Designer.toBytesIndex("Return", backmenu3);
                    Designer.drawCenterString(g, backmenu3, 0, 6, ibackmenu3, getWidth()/2, getHeight()/2 + 20);
                }
            }
        }

 //           System.out.println("State = " + State1 + State2 + State3 );
//            System.out.println("So Part " + Part);
            if ( !restartLv && Part >= 1 ) {
                if (((Sprite)vBall[Part-1].BVector.elementAt(0)).getX() == 0 && ((Sprite)vBall[Part-1].BVector.elementAt(0)).getY() == 0 ) {
                    ((Sprite)vBall[Part-1].BVector.elementAt(0)).setVisible(false);
                    State0 = false;
                    State1 = false;
                    State2 = false;
                    State3 = false;
                    State4 = true;
                    restartLv = true;
                }
            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Làm hiệu ứng nổ bóng
            if ( Breaking ) {
                if ( BreakingTime % 2 == 0 && BreakingTime != 0) {
                    for ( i = 0; i < 30; ++i ) {
                        breakBall[i].nextFrame();
                    }
                }

                BreakingTime++;
                if ( BreakingTime == 8 ) {
                    BreakingTime = 0;
                    Breaking = false;
                    for ( i = 0; i < 30; ++i ) {
                        breakBall[i].setVisible(false);
                    }
                }
            }

            keyState = getKeyStates();
            if ( keyState == 0 )    {
                angleAdd = 3;
                keyPressed = 0;
                distanceAdd = 2;
            }

            if ( !State6 && !State5 && !State4 ) {
                if ( (keyState & LEFT_PRESSED) != 0 ) {
                    if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9
                            && runningLevel != 12 && runningLevel != 13 && runningLevel != 8 && runningLevel != 7 ) {
                        keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( angleAdd <= 9 )    angleAdd += 2;
                        if ( iCount > angleAdd ) iCount -= angleAdd;
                        else iCount = 359;
                    } else if ( runningLevel == 13 || runningLevel == 8) {
                        keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( distanceAdd <= 9 ) distanceAdd += 2;
                        if ( model.x > model.yMin + distanceAdd ) model.x -= distanceAdd;
                        else model.x = model.yMin;
                    }

                } else if ( (keyState & RIGHT_PRESSED) != 0 ) {
                    if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9
                            && runningLevel != 12 && runningLevel != 13 && runningLevel != 8 && runningLevel != 7 ) {
                        keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( angleAdd <= 9)     angleAdd += 2;
                        if ( iCount < 360 - angleAdd ) iCount += angleAdd;
                        else iCount = 0;
                    } else if ( runningLevel == 13 || runningLevel == 8 ) {
                        keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( distanceAdd <= 9 ) distanceAdd += 2;
                        if ( model.x < model.yMax - distanceAdd ) model.x += distanceAdd;
                        else model.x = model.yMax;
                    }

                } else if ( ( keyState & UP_PRESSED) != 0 ) {
                    if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9 && runningLevel != 12 && runningLevel != 7) {

                    } else {
                        keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( distanceAdd <= 9 ) distanceAdd += 2;
                        if ( model.y > model.yMin + distanceAdd )     model.y -= distanceAdd;
                        else model.y = model.yMin;
                    }

                } else if (( keyState & DOWN_PRESSED) != 0 ) {
                    if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9 && runningLevel != 12 && runningLevel != 7 ) {

                    } else {
                       keyPressed++;
                        if ( keyPressed % 5 == 0 )
                            if ( distanceAdd <= 9 ) distanceAdd += 2;
                        if ( model.y < model.yMax - distanceAdd ) model.y += distanceAdd;
                        else model.y = model.yMax;
                    }
                } 

                /*if( angleCount % 10 == 1 )     Sball.Ball.nextFrame();

                runCount++;
                if ( runCount%3 == 1 ) {
                    for ( i = 0; i < Part; i++ ) {
                        for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                            ((Sprite)vBall[i].BVector.elementAt(j)).nextFrame();
                        }
                    }
                }*/

            }
            

            if ( drawScore )    score.drawScore();
            
            flushGraphics();
            lm.paint(g, 0, 0);
            timeSinceStart = System.currentTimeMillis()-timeLastCycle;
            //System.out.println(timeSinceStart);
            sleep = MS_PER_SECOND-timeSinceStart;
            if(sleep < 0)
                sleep = 0;
            //System.out.println(sleep);

            try {
                t.sleep(sleep);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Runtime.getRuntime().gc();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    // Xử lý bắn bóng State1
    ////////////////////////////////////////////////////////////////////////////
    public void S1Shoot() {
        if ( Shoot ) {
            partColli = 0;
            Sball.ShootDistance += 15;
            if ( Sball.ShootDistance < 200 ) {
                Sball.shootBall( Sball.ShootAngle );
                if ( !Colli ) {
                    headInsert = false;
                    isColli = false;
                    iColliSave = iColli;
                    for ( iColli = 0; iColli < Number+1; iColli++ ) {
                        if ( Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli)), true)) {
                            if (( iColli != Number && Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli+1)), true))
                                || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli-1)), true))
                                || iColli == 0 || iColli == Number)  {
                                /*if ( iColli == 0 && vBall[partColli].BVector.size() >= 1) {
                                    if (vBall[partColli].BVector.size() == 1
                                            || ( vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))) {
                                        headInsert = true;
                                    }
                                }*/
                                if ( iColli == 0 && vBall[partColli].BVector.size() >= 1) {
                                    if (vBall[partColli].BVector.size() == 1
                                            || ( vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))) {
                                        headInsert = true;
                                    } else if ( vBall[partColli].BVector.size() > 1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true)) {
                                        colliAfter = true;
                                    }
                                }
                                isColli = true;
                                Colli = true;
                                break;
                            } else {
                                isColli = true;
                                Colli = true;
                                pColli = true;
                                break;
                            }

                        }
                    }
                    if ( !isColli )  iColli = iColliSave;
                } else {
                    if ( InsertTime == 0 ) {
                        vBall[0].End ++;
                        Sball.Ball.setVisible(false);
                        ite += 16;
                        NumB += 16;
                    }
                    sumOfInsert = 0;
                    sumOfDistance = 0;
                    for ( j = 0; j <= iColli; j++ ){
                        ((Sprite) vBall[0].BVector.elementAt(j)).setPosition(lv[ite-16*j-12+InsertTime*4][0], lv[ite-16*j-12+InsertTime*4][1]);
                        if ( ite-16*j-12+InsertTime*4 >= 0 && lv[ite-16*j-12+InsertTime*4][2] == 1 )
                            ((Sprite) vBall[0].BVector.elementAt(j)).setVisible(false);
                        else ((Sprite) vBall[0].BVector.elementAt(j)).setVisible(true);
                    }
                    vBall[0].InsertBall(iColli, 0);
                    if ( InsertTime == 4 ) {
                        InsertTime = 0;
                        //ColliPoint = iColli - 1;

                        Colli = false;
                        Shoot = false;
                        vBall[0].testBreak(iColli);
    //                                if ( Sball.Ball.getX() == (int)(116 + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2))
    //                                        && Sball.Ball.getY() == (int)(160 - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)))
                            Sball.resetBall();
                    }
                }
            } else {
                Shoot = false;
                Sball.resetBall();
                Colli = false;
            }
        } else
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 20*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)(Sball.getPositionY() + 20*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Xử lý bắn bóng State2
    ////////////////////////////////////////////////////////////////////////////
    public void S2Shoot() {
        if ( Shoot ) {
            Sball.ShootDistance += 15;
            if ( Sball.ShootDistance < 200 ) {
                Sball.shootBall( Sball.ShootAngle );
                if ( !Colli ) {
                    headInsert = false;
                    pColli = false;
                    isColliAtEnd = false;
                    Be_1ball = false;
                    Af_1ball = false;
                    isColli = false;
                    iColliSave = iColli;
                    for (  partColli = 0; partColli < Part; partColli++ ) {
                        for ( iColli = 0; iColli < vBall[partColli].BVector.size(); iColli++ ) {
                            if ( Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli)), true)) {
                                if (( iColli != vBall[partColli].BVector.size()-1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                    || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true))
                                    || iColli == 0 || iColli == vBall[partColli].BVector.size()-1 )  {
                                    if ( iColli == 0 && vBall[partColli].BVector.size() >= 1) {
                                        if (vBall[partColli].BVector.size() == 1
                                                || ( vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))) {
                                            headInsert = true;
                                        } else if ( vBall[partColli].BVector.size() > 1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true)) {
                                            colliAfter = true;
                                        }
                                        if ( vBall[partColli].BVector.size() > 1  && iColli == vBall[partColli].BVector.size()-1
                                                && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true)) {
                                            isColliAtEnd = true;
                                        }
                                        if ( vBall[partColli].BVector.size() == 1 ) {
                                            if( partColli > 0 ) {
                                                check_1ball = ChekBe_Af((Sprite)vBall[partColli-1].BVector.elementAt(0), Sball.Ball, (Sprite)vBall[partColli].BVector.elementAt(0));
                                                if ( check_1ball == 1 ) {
                                                    Be_1ball = true;
                                                    //headInsert = true;
                                                } else {
                                                    Af_1ball = true;
                                                    headInsert = false;
                                                    isColliAtEnd = true;
                                                }
                                            } else if ( partColli < Part-1 ) {
                                                check_1ball = ChekBe_Af( (Sprite)vBall[partColli+1].BVector.elementAt(0), Sball.Ball, (Sprite)vBall[partColli].BVector.elementAt(0));
                                                if ( check_1ball == 0 ) {
                                                    Af_1ball = true;
                                                    isColliAtEnd = true;
                                                    headInsert = false;
                                                } else {
                                                    Be_1ball = true;
                                                    //isColliAtEnd = true;
                                                }
                                            }
                                        }
                                    }
                                    isColli = true;
                                    Colli = true;
                                    pColli = true;
                                    break;
                                } else {
                                    isColli = true;
                                    Colli = true;
                                    pColli = true;
                                    break;
                                }
                            }
                        }
                        if ( !isColli )     iColli = iColliSave;
                        if ( pColli )    break;
                    }
                } else {
                    
                    if ( InsertTime == 0 ) {
                        Sball.Ball.setVisible(false);
                        NumB += 16;
                        
                        vBall[partColli].End++;
                        for ( m = partColli - 1; m >= 0; m-- ) {
                            vBall[m].Begin++;
                            vBall[m].End++;
                        }
                        if ( partColli == Part - 1 )    ite += 16;
                        else {
                            if(vBall[partColli].Distance > 16)
                                vBall[partColli].Distance -= 16;
                            else vBall[partColli].Distance = 0;
                        }
                    }

                    sumOfInsert = 0;
                    if ( Part > 1 ) {
                        for ( k = partColli; k < Part - 1; k++ ) {
                            sumOfInsert += vBall[k].Distance;
                        }
                    } else {
                        sumOfInsert = 0;
                    }

                    if ( iColli != 0 || ( iColli == 0 && Af_1ball) ) {
                        for ( j = 0; j <= iColli; j++ ){
                            try {
                                ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                        lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                if ( ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4 >= 0 && lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][2] == 1 )
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(true);
                            } catch ( ArrayIndexOutOfBoundsException aio ) {
                                
                            }
                            //}
                        }
                    } else if ( iColli == 0 && colliAfter ) {
                        for ( j = 0; j <= iColli; j++ ){
                            try {
                                ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                        lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                if ( ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4 >= 0 && lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][2] == 1 )
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(true);
                            } catch ( ArrayIndexOutOfBoundsException aio ) {
                            }
                        }
                    }

                    vBall[partColli].InsertBall(iColli, partColli);

                    if ( InsertTime == 4 ) {
//                                if ( partColli != Part - 1 )
//                                    ite-=16;
                        InsertTime = 0;
                        //ColliPoint = iColli - 1;
                        isStateChange = false;
                        vBall[partColli].testBreak(iColli);
                        if ( !isStateChange ) {
                            if ( headInsert  ) {
                                if ( partColli != Part-1 && ((Sprite)vBall[partColli].BVector.elementAt(0)).getFrame()
                                    == ((Sprite)vBall[partColli+1].BVector.elementAt(vBall[partColli+1].BVector.size()-1)).getFrame()) {
                                    State1 = false;
                                    State2 = true;
                                    State3 = false;
                                    partColliBack = partColli;
                                }
                            } else if ( isColliAtEnd ) {
                                if ( partColli != 0 && ((Sprite)vBall[partColli].BVector.elementAt(vBall[partColli].BVector.size()-1)).getFrame()
                                    == ((Sprite)vBall[partColli-1].BVector.elementAt(0)).getFrame()) {
                                    State1 = false;
                                    State2 = true;
                                    State3 = false;
                                    partColliBack = partColli - 1;
                                }
                            }
                        }

                        Colli = false;
                        Shoot = false;
                        Sball.resetBall();
                        Asm = true;

                    }
                }
            } else {
                Shoot = false;
                Sball.resetBall();
                Colli = false;
                Asm = true;
            }
        } else
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 20*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)(Sball.getPositionY() + 20*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
    }
    ///////////////////////////////////////////////////////////////////////////////
    // Xử lý bắn bóng State3
    ////////////////////////////////////////////////////////////////////////////
    public void S3Shoot () {
        if ( Shoot ) {
            Sball.ShootDistance += 15;
            if ( Sball.ShootDistance < 200 ) {
                Sball.shootBall( Sball.ShootAngle );
                if ( !Colli ) {
                    headInsert = false;
                    pColli = false;
                    colliAfter = false;
                    isColliAtEnd = false;
                    Be_1ball = false;
                    Af_1ball = false;
                    isColli = false;
                    iColliSave = iColli;
                    for (  partColli = 0; partColli < Part; partColli++ ) {
                        for ( iColli = 0; iColli < vBall[partColli].BVector.size(); iColli++ ) {
                            if ( Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli)), true)) {
                                if (( iColli != vBall[partColli].BVector.size()-1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                    || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true))
                                    || iColli == 0 || iColli == vBall[partColli].BVector.size()-1 )  {
                                    if ( iColli == 0 && vBall[partColli].BVector.size() >= 1) {
                                        if (vBall[partColli].BVector.size() == 1
                                                || ( vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))) {
                                            headInsert = true;
                                        } else if ( vBall[partColli].BVector.size() > 1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true)) {
                                            colliAfter = true;
                                        }
                                    }
                                    if ( vBall[partColli].BVector.size() > 1  && iColli == vBall[partColli].BVector.size()-1
                                            && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true)) {
                                        isColliAtEnd = true;
                                    }
                                    if ( vBall[partColli].BVector.size() == 1 ) {
                                        if( partColli > 0 ) {
                                            check_1ball = ChekBe_Af((Sprite)vBall[partColli-1].BVector.elementAt(0), Sball.Ball, (Sprite)vBall[partColli].BVector.elementAt(0));
                                            if ( check_1ball == 1 ) {
                                                Be_1ball = true;
                                                //headInsert = true;
                                            } else {
                                                Af_1ball = true;
                                                headInsert = false;
                                                isColliAtEnd = true;
                                            }
                                        } else if ( partColli < Part-1 ) {
                                            check_1ball = ChekBe_Af( (Sprite)vBall[partColli+1].BVector.elementAt(0), Sball.Ball, (Sprite)vBall[partColli].BVector.elementAt(0));
                                            if ( check_1ball == 0 ) {
                                                Af_1ball = true;
                                                isColliAtEnd = true;
                                                headInsert = false;
                                            } else {
                                                Be_1ball = true;
                                                //isColliAtEnd = true;
                                            }
                                        }
                                    }
                                    isColli = true;
                                    Colli = true;
                                    pColli = true;
                                    break;
                                } else {
                                    isColli = true;
                                    Colli = true;
                                    pColli = true;
                                    break;
                                }
                            }
                        }
                        if ( !isColli )     iColli = iColliSave;
                        if ( pColli )    break;
                    }
                } else {
                    if ( InsertTime == 0 ) {
                        Sball.Ball.setVisible(false);

                        NumB += 16;
                        //if ( partColli == Part - 1 )    add += 16;
                        //System.out.println("add " + add);
                        vBall[partColli].End++;
                        for ( m = partColli - 1; m >= 0; m-- ) {
                            vBall[m].Begin++;
                            vBall[m].End++;
                        }
                        if ( partColli == Part - 1 )    ite += 16;
                        else {
                            if ( vBall[partColli].Distance > 16 )
                                vBall[partColli].Distance -= 16;
                            else vBall[partColli].Distance = 0;
                        }
                    }

                    sumOfInsert = 0;
                    for ( k = partColli; k < Part - 1; k++ ) {
                        sumOfInsert += vBall[k].Distance;
                    }

                     if ( iColli != 0 || ( iColli == 0 && Af_1ball) ) {
                        for ( j = 0; j <= iColli; j++ ){
                            //if ( ite-16*j <= 997 ) {
                            //System.out.println("add here " + )
                            try {
                                ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                        lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                if ( ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4 >= 0 && lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][2] == 1 )
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(true);
                            } catch ( ArrayIndexOutOfBoundsException aio ) {
//                                System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                            }
                            //}
                        }
                    } else if ( iColli == 0 && colliAfter ) {
                        for ( j = 0; j <= iColli; j++ ){
                            try {
                                ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                        lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                if ( ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4 >= 0 && lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][2] == 1 )
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(false);
                                else ((Sprite) vBall[partColli].BVector.elementAt(j)).setVisible(true);
                            } catch ( ArrayIndexOutOfBoundsException aio ) {
                                //System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                            }
                        }
                    }
                    
                    vBall[partColli].InsertBall(iColli, partColli);

                    if ( InsertTime == 4 ) {
                        //if ( partColli != Part - 1 )
                        //    ite-=16;
                        InsertTime = 0;
                        isStateChange = false;
                        vBall[partColli].testBreak(iColli);
                        if ( !isStateChange ) {
                            if ( headInsert  ) {
                                //System.out.println("12321321");
                                if ( partColli != Part-1 && ((Sprite)vBall[partColli].BVector.elementAt(0)).getFrame()
                                    == ((Sprite)vBall[partColli+1].BVector.elementAt(vBall[partColli+1].BVector.size()-1)).getFrame()) {
                                    State1 = false;
                                    State2 = true;
                                    State3 = false;
                                    partColliBack = partColli;
                                }
                            } else if ( isColliAtEnd ) {
                                if ( partColli != 0 && ((Sprite)vBall[partColli].BVector.elementAt(vBall[partColli].BVector.size()-1)).getFrame()
                                    == ((Sprite)vBall[partColli-1].BVector.elementAt(0)).getFrame()) {
                                    State1 = false;
                                    State2 = true;
                                    State3 = false;
                                    partColliBack = partColli - 1;
                                }
                            }
//                            System.out.println ( "               " + iColli);
                        }

                        Colli = false;
                        Shoot = false;
                        Sball.resetBall();
                        Asm = true;
                    }
                }
            } else {
                Shoot = false;
                Sball.resetBall();
                Colli = false;
                Asm = true;
            }
        } else
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 20*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)( Sball.getPositionY() + 20*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
    }


    public void start(){
        this.run = true;
        t = new Thread(this);
        t.start();
    }

    public void stop(){
        t = null;
        this.run = false;
    }

    public void drawGauge( Graphics gG) {
        gG.setClip( 240-Gauge_full.getWidth(), 0, gaugeCount, Gauge_full.getHeight());
        gG.drawImage(Gauge_full, 240-Gauge_full.getWidth(), 0, Graphics.TOP | Graphics.LEFT);
        if ( gaugeCount < score.Score_level*Gauge_full.getWidth()/ScoretoOver )
            gaugeCount++;
    }

    public void initOverScore() {
        switch ( runningLevel ) {
            case 1: ScoretoOver = 5000; break;
            case 2: ScoretoOver = 7000; break;
            case 3: ScoretoOver = 50000; break;
            case 4: ScoretoOver = 10000; break;
            case 5: ScoretoOver = 14000; break;
            case 6: ScoretoOver = 50000; break;
            case 7: ScoretoOver = 19000; break;
            case 8: ScoretoOver = 24000; break;
            case 9: ScoretoOver = 50000; break;
            case 10: ScoretoOver = 29000; break;
            case 11: ScoretoOver = 35000; break;
            case 12: ScoretoOver = 50000; break;
            case 13: ScoretoOver = 50000; break;

        }
    }

    public void removeSprite (  ) {
        lm.remove(removeSprite);
        removeSprite = null;
    }
    
    public int ChekBe_Af ( Sprite ball1, Sprite ball2, Sprite ball3 ) {
        int x1, x2, y1, y2, x3, y3;
        double i1, i2;
        double  d1, d2, d3, d4;
        x1 = ball1.getX();
        y1 = ball1.getY();
        x2 = ball2.getX();
        y2 = ball2.getY();
        x3 = ball3.getX();
        y3 = ball3.getY();
        d1 = Math.abs(x1-x2);
        d2 = Math.abs(y1-y2);
        d3 = Math.abs(x1-x3);
        d4 = Math.abs(y1-y3);
        i1 = Math.sqrt(d1*d1 + d2*d2);
        i2 = Math.sqrt(d3*d3 + d4*d4);
        if ( i1 >= i2 )  return 1;
        else    return 0;
    }

/////////////////////////////////////////////////////////////////////////////////////////
// Hàm move để dịch chuyển 1 viên bi
// PointSpr : Sprite của 1 pixel di chuyển dọc theo đường vẽ
// ball     : Sprite viên bi có tâm trùng với pixel trên
// z        : số thứ tự của viên bi
/////////////////////////////////////////////////////////////////////////////////////////
    public void move ( Sprite PointSpr, Sprite ball ) {
        for ( i = 0; i < 4; i++) {
//            System.out.println(widthMap + "X" + heightMap );
//            System.out.println(width + " "  + height + " " + ((height + moveY[i] + heightLv )*widthMap + width + moveX[i] + widthLv) +  " end ");
            if ( (height + moveY[i] + heightLv )*widthMap + width + moveX[i] + widthLv >= 0 &&
                    imageArray[(height + moveY[i] + heightLv )*widthMap + width + moveX[i] + widthLv]  == -16777216 ) { // Nếu va chạm
                // Xét các hướng đặc biệt để pixel không bị quay về
                // Nếu điểm xét là điểm đã đi qua
                PointSpr.setPosition( width + moveX[i], height + moveY[i]);
                if ((width + moveX[i] == w) && (height + moveY[i] == h)) {
                    continue;
                }
                
                //Xử lý đoạn zigzac để đoàn bóng di chuyển hợp lý
                if (( w == (width) && moveY[i] == 0 )
                        || ( h == (height) && moveX[i] == 0 )) {
                    zigzac++;
                    w = width;
                    h = height;
                    ball.setPosition((width) - 8, (height) - 8);
                    width = width + moveX[i];
                    height = height + moveY[i];
                    times++;
                    saveLocation ( ball, times - 1 );
                    if ( zigzac%3 == 1 ) {
                        //System.out.println("More");
                        times--;
                        move ( PointSpr, ball );
                        //System.out.println(PointSpr.getX() + "x" + PointSpr.getY() );
                    }
                    break;

                }
                w = width;
                h = height;
                ball.setPosition((width) - 8, (height) - 8);
                width = width + moveX[i];
                height = height + moveY[i];
                //System.out.println(PointSpr.getX() + "x" + PointSpr.getY() );
                times++;
                
                saveLocation ( ball, times - 1 );
                break;

            }
//            else {
//                // Không va chạm thì quay lại điểm đầu
//                PointSpr.setPosition((width) , (height));
//            }
        }
        
    }
//////////////////////////////////////////////////////////////////////////////////
// Ham đọc ảnh vào một mảng
    int[] imageArray;
    Image wayMap;
    public void readImage ( String filepath ) {
        //imageArray = null;
        wayMap = null;
        Runtime.getRuntime().gc();
        try {
            wayMap = Image.createImage(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        heightMap = wayMap.getHeight();
        widthMap = wayMap.getWidth();
        imageArray = new int [heightMap* widthMap];
        
        wayMap.getRGB(imageArray, 0, widthMap, 0, 0, widthMap, heightMap);
       
//        for ( int d = 0; d < wayMap.getHeight(); d++ ) {
//            for ( int e = 0; e < wayMap.getWidth(); e++ ) {
//                System.out.print( " " + imageArray[d*wayMap.getWidth() + e]);
//            }
//            System.out.println();
//        }
    }

//////////////////////////////////////////////////////////////////////////////////
// Hàm sinh số random trong khoàng min max
    public int getRand(int min, int max) {
        r = Math.abs(randomizer.nextInt());
        return ((r % (max - min)) + min);
    }

//////////////////////////////////////////////////////////////////////////////////
// Hàm sinh số random trong các số trong mảng Number
    public int getRandAmong ( int[] Number ) {
        int length = 0;
        for ( int z = 0; z < 10; z++ ) {
            if ( Number[z] != -1 )  length++;
            else break;
        }
        if ( length > 0 ) {
            ranA = getRand( 0, length );
            return ( Number[ranA]);
        } else {
            return 0;
        }
    }

//////////////////////////////////////////////////////////////////////////////////
// Hàm nhặt các màu trong đoàn bóng
    public void getColor () {
        NumOfColor = 0;
        for (u = 0; u < 10; u++ ) {
            Color[u] = -1;
        }
        for (  z = 0; z < Part; z++ ) {
            for (  x = 0; x < vBall[z].BVector.size(); x++ ) {
                addColor = true;
                for (  u = 0; u < 10; u++ ) {
                    if ( Color[u] == ((Sprite)(vBall[z].BVector.elementAt(x))).getFrame()
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getX() != 1 - 8
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getY() != 65 - 8
                            && ((Sprite)vBall[z].BVector.elementAt(x)).isVisible() == true
                            ) {
                        addColor = false;
                        break;
                    }
                    if ( !((Sprite)vBall[z].BVector.elementAt(x)).isVisible() ||
                            (((Sprite)vBall[z].BVector.elementAt(x)).getX() <= 1 - 8
                            && ((Sprite)vBall[z].BVector.elementAt(x)).getY() == 65 - 8)) {
                        addColor = false;
                        break;
                    }
                }
                if ( addColor ) {
                    Color[NumOfColor] = ((Sprite)(vBall[z].BVector.elementAt(x))).getFrame();
                    NumOfColor++;
                }
            }
        }
    }

    public int compareColor ( int[] Color, int[] OldColor ) {
        Num = 0;
        OldNum = 0;
        boolean checkCo;
        boolean haveChangeColor = false;
        for ( i = 0; i < Color.length; i++ )
            if ( Color[i] != -1 )   Num++;
        for ( i = 0; i < Color.length; i++ )
            if ( Color[i] != -1 )   OldNum++;
        if ( OldNum != Num )     haveChangeColor = true;
        else {
            for ( i = 0; i < Color.length; i++ ) {
                checkCo = false;
                for ( j = 0; j < OldColor.length; j++ ) {
                    if ( Color[j] == OldColor[i] ) {
                        checkCo = true;
                        break;
                    }
                }
                if ( !checkCo ) haveChangeColor = true;
                break;
            }
            //haveChangeColor = false;
        }
//        System.out.println("Change " + haveChangeColor);
        if ( haveChangeColor )  {
            nextballreset = false;
            ballReset = false;
            resetBall = 0;
            for ( i = 0; i < Color.length; i++ ) {
                if ( Sball.Ball.getFrame() == Color[i] ) {
                    //resetBall = 1;
                    ballReset = false;
                    break;
                } else {
                    ballReset = true;
                }
            }
            
            for ( i = 0; i < Color.length; i++ ) {
                if ( Sball.nextColor == Color[i] ) {
                    //resetBall = 2;
                    nextballreset = false;
                    break;
                } else {
                    nextballreset = true;
                }
            }
            if ( nextballreset || ballReset )   
                return 0;
        }
        return 1;
    }

//////////////////////////////////////////////////////////////////////////////////
// Hàm hình thành mảng tọa độ của level
    public void saveLocation ( Sprite ball, int times ) {
        try {
            lv[times][0] = ball.getX();
            lv[times][1] = ball.getY();
        } catch (ArrayIndexOutOfBoundsException abc ) {
//            System.out.println("times " + times);
        }
    }

 //////////////////////////////////////////////////////////////////////////////////
// Hàm phát nhạc
//    private void getMusic() throws MediaException {
//        if( player != null ) {
//            try {
//                player.stop();
//            } catch (MediaException ex) {
//                ex.printStackTrace();
//            }
//            player=null;
//        }
//        InputStream in;
//        in = getClass().getResourceAsStream("/sound/track3.mid");
//        try {
//            player = Manager.createPlayer(in, "audio/midi");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        player.setLoopCount(-1);
//        player.start();
//    }


    public void clearPreLevel() {
        if ( Level1 != null ) {
            lm.remove(Level1);
            Level1 = null;
        }
    }

//////////////////////////////////////////////////////////////////////////////////
// Hàm chọn level 
    public void chooseLevel ( int runningLevel ) {
        initOverScore();
//        try {
//            getMusic();
//        } catch (MediaException ex) {
//            ex.printStackTrace();
//        }
        for ( k = 0; k < 2560; k++ ) {
            lv[k][0] = 0;
            lv[k][1] = 0;
            lv[k][2] = 0;
        }
        
        if( runningLevel == 1 ) {
            width = -16;
            height = 65;
            lv[0][0] = -24;
            lv[0][1] = 57;
            try {
                Level1 = new Sprite(Image.createImage("/picture/lv1.png"), 240, 320);
                readImage ("/picture/lv1-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Level1.setPosition(0, 0);
            lm.append(Level1);

            widthLv = 16;  heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );
            
//////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 2 ) {
            clearPreLevel();

            width = 25;
            height = 335;
            lv[0][0] = 17;
            lv[0][1] = 327;
            try {
                Level1 = new Sprite(Image.createImage("/picture/lv2.png"), 240, 320);
                readImage("/picture/lv2-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                                               
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

            widthLv = 0;  heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 3 ) {
            clearPreLevel();
            
            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 162;
            height = -16;
            lv[0][0] = 154;
            lv[0][1] = -24;
            try {
               Level1 = new Sprite(Image.createImage("/picture/lv3.png"), 240, 320);
               readImage("/picture/lv3-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Level1.setPosition(0, 0);
            //lm.insert(Level1, 0);
            lm.append(Level1);
            
            Boss = new Boss (this);
            widthLv = 0; heightLv = 16;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 4 ) {
            Boss.clearBoss();
            clearPreLevel();
           
            wayBall.setVisible(true);
            wayPoint.setVisible(true);
            try {
                Lv_patch1 = new Sprite(Image.createImage("/picture/lv4-patch.png"), 22, 18);
                Lv_patch2 = new Sprite(Image.createImage("/picture/lv4-patch1.png"), 24, 35);
                Lv_patch3 = new Sprite(Image.createImage("/picture/lv4-patch2.png"), 20, 25);
                Lv_patch4 = new Sprite(Image.createImage("/picture/lv4-patch3.png"), 15, 17);
                Lv_patch5 = new Sprite(Image.createImage("/picture/lv4-patch4.png"), 17, 39);
                Level1 = new Sprite(Image.createImage("/picture/lv4.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv4-way-1.png"), 240, 320);
//                L2_way = new Sprite(Image.createImage("/picture/lv4-way-2.png"), 240, 320);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Lv_patch1.setPosition(175, 42);
            lm.insert(Lv_patch1, 0);

            Lv_patch2.setPosition(38, 110);
            lm.insert(Lv_patch2, 0);

            Lv_patch3.setPosition(185, 87);
            lm.insert(Lv_patch3, 0);

            Lv_patch4.setPosition(58, 264);
            lm.insert(Lv_patch4, 0);

            Lv_patch5.setPosition(15, 232);
            lm.insert(Lv_patch5, 0);

            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

            width = 182;
            height = 52;
            lv[0][0] = 173;
            lv[0][1] = 44;

            readImage("/picture/lv4-way-1.png");
            widthLv = 0; heightLv = 0;
            for ( k = 0; k < 1500; k++ )
                move ( wayPoint, wayBall );

            width = 48;
            height = 272;
            lv[1321][0] = 40;
            lv[1321][1] = 264;
            readImage("/picture/lv4-way-2.png");
            widthLv = 0; heightLv = 0;
            for ( k = 1321; k < 1751; k++ )
                move ( wayPoint, wayBall );

            for ( k = 840; k < 1027; k++ )
                lv[k][2] = 1;
            for ( k = 1315; k < 1381 ; k++ )
                lv[k][2] = 1;
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 5 ) {
            clearPreLevel();
            lm.remove(Lv_patch1);
            lm.remove(Lv_patch2);
            lm.remove(Lv_patch3);
            lm.remove(Lv_patch4);
            lm.remove(Lv_patch5);

            Level1 = null;
            Lv_patch1 = null;
            Lv_patch2 = null;
            Lv_patch3 = null;
            Lv_patch4 = null;
            Lv_patch5 = null;

            wayBall.setVisible(true);
            wayPoint.setVisible(true);
            
            width = 206;
            height = 295;
            lv[0][0] = 198;
            lv[0][1] = 287;
            try {
                Lv_patch1 = new Sprite(Image.createImage("/picture/lv5-patch.png"), 46, 45);
                Level1 = new Sprite(Image.createImage("/picture/lv5.png"), 240, 320);
                //L1_way = new Sprite(Image.createImage("/picture/lv5-way.png"), 240, 320);
                readImage("/picture/lv5-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

            Lv_patch1.setPosition(194, 79);
            lm.insert(Lv_patch1, 0);

            
            widthLv = 0;    heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );
            
            for ( k = 604; k < 659; k++ )
                lv[k][2] = 1;
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 6 ) {
            clearPreLevel();
//            lm.remove(Level1);
            lm.remove(Lv_patch1);
//            Level1 = null;
            Lv_patch1 = null;

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 171;
            height = -16;
            lv[0][0] = 163;
            lv[0][1] = -24;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv6.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv6-way.png"), 240, 400);
                readImage("/picture/lv6-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(0, -16);
//            lm.append(L1_way);
            Boss = new Boss (this);
            widthLv = 0;    heightLv = 16;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 7 ) {
            Boss.clearBoss();
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 179;
            height = 319;
            lv[0][0] = 171;
            lv[0][1] = 311;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv7.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv7-way.png"), 240, 320);
                readImage("/picture/lv7-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);
            widthLv = 0;    heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 8 ) {
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 41;
            height = -16;
            lv[0][0] = 33;
            lv[0][1] = -24;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv8.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv8-way.png"), 240, 336);
                readImage("/picture/lv8-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(0, -16);
//            lm.append(L1_way);
            widthLv = 0; heightLv = 16;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 9 ) {
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 124;
            height = 319;
            lv[0][0] = 116;
            lv[0][1] = 311;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv9.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv9-way.png"), 240, 336);
                readImage("/picture/lv9-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(0, -8);
//            lm.append(L1_way);
            Boss = new Boss (this);
            widthLv = 0;    heightLv = 8;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
//        } else if ( runningLevel == 10 ) {
////
///////////////////////////////////////////////////////////////////////////////////////////////////
//        } else if ( runningLevel == 11 ) {
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 12 ) {
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 112;
            height = 333;
            lv[0][0] = 104;
            lv[0][1] = 325;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv12.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv12-way.png"), 240, 352);
                readImage("/picture/lv12-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(0, -16);
//            lm.append(L1_way);
            Boss = new Boss (this);
            widthLv = 0;    heightLv = 16;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 10 ) {
            Boss.clearBoss();
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = 138;
            height = 244;
            lv[0][0] = 130;
            lv[0][1] = 236;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv10.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv10-way.png"), 240, 320);
                readImage("/picture/lv10-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(0, 0);
//            lm.append(L1_way);
            widthLv = 0;    heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////
        } else if ( runningLevel == 11 ) {
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = -16;
            height = 139;
            lv[0][0] = -24;
            lv[0][1] = 131;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv11.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv11-way.png"), 256, 320);
                readImage("/picture/lv11-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(-16, 0);
//            lm.append(L1_way);
            widthLv = 16;   heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
/////////////////////////////////////////////////////////////////////////////////////////////////      
        } else if ( runningLevel == 13 ) {
//            Boss.clearBoss();
            clearPreLevel();

            wayBall.setVisible(true);
            wayPoint.setVisible(true);

            width = -16;
            height = 72;
            lv[0][0] = -24;
            lv[0][1] = 66;

            try {
                Level1 = new Sprite(Image.createImage("/picture/lv13.png"), 240, 320);
//                L1_way = new Sprite(Image.createImage("/picture/lv13-way.png"), 272, 320);
                readImage("/picture/lv13-way.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.insert(Level1, 0);

//            L1_way.setPosition(-16, 0);
//            lm.append(L1_way);
            Boss = new Boss (this);
            BossFinal = new Boss ( this, 13);
            widthLv = 16;   heightLv = 0;
            for ( k = 0; k < 2560; k++ )
                move ( wayPoint, wayBall );

            wayBall.setVisible(false);
            wayPoint.setVisible(false);
        }
        imageArray = null;

        if ( runningLevel == 1 || runningLevel == 2 || runningLevel == 3 || runningLevel == 4 || runningLevel == 5 || runningLevel == 6) {
            if ( FinishPoint == null ) {
                try {
                    FinishPoint = new Sprite(Image.createImage("/picture/nhan-su.png"), 35, 40);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } 
            lm.insert(FinishPoint, 0);
            if ( runningLevel == 1 )    FinishPoint.setPosition(95, 82);
            else if ( runningLevel == 2 )   FinishPoint.setPosition(146, 216);
            else if ( runningLevel == 3 )   FinishPoint.setPosition(75, 0);
            else if ( runningLevel == 4 )   FinishPoint.setPosition(187, 5);
            else if ( runningLevel == 5 )   FinishPoint.setPosition(170, 54);
            else if ( runningLevel == 6 )   FinishPoint.setPosition(98, 34);
            
        } else {
            if ( FinishPoint != null )  lm.remove(FinishPoint);
            if ( runningLevel == 7 ) {
                FinishPoint = null;
            }
            try {
                FinishPoint = new Sprite(Image.createImage("/picture/key.png"), 40, 40);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            FinishPoint.setFrameSequence(finPointSeq);
            lm.insert(FinishPoint, 0);
            if ( runningLevel == 7 )  {  FinishPoint.setTransform(FinishPoint.TRANS_ROT270);     FinishPoint.setPosition(36, 38);   FinishPoint.setFrame(2);}
            else if ( runningLevel == 8 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT270);    FinishPoint.setPosition(-1, 261); FinishPoint.setFrame(0);}
            else if ( runningLevel == 9 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT180);    FinishPoint.setPosition(73, 271); }
//            else if ( runningLevel == 10 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT90);   FinishPoint.setPosition(128, 74); }
//            else if ( runningLevel == 11 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT180);   FinishPoint.setPosition(126, 124);  FinishPoint.setFrame(2);}
            else if ( runningLevel == 10 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT90);   FinishPoint.setPosition(114, 25);  FinishPoint.setFrame(3);}
            else if ( runningLevel == 11 ) {  FinishPoint.setVisible(false);    }
            else if ( runningLevel == 12 ) {  FinishPoint.setTransform(FinishPoint.TRANS_ROT180);   FinishPoint.setPosition(155, 244);  FinishPoint.setFrame(0);}
            else if ( runningLevel == 13 ) {  FinishPoint.setVisible(false);}
        }

    }

//////////////////////////////////////////////////////////////////////////////////
// chuyển sang level khác
    public void nextLevel() {
        //Reset thanh gauge
        score.Score_level = 0;
        gaugeCount = 0;
        //System.out.println("nextLevel " + iS5);
        if ( runningLevel == 1 )    runningLevel = 2;
        else if(runningLevel == 2)    runningLevel = 3;
        else if(runningLevel == 3)    runningLevel = 4;
        else if(runningLevel == 4)    runningLevel = 5;
        else if(runningLevel == 5)    runningLevel = 6;
        else if(runningLevel == 6)    runningLevel = 7;
        else if(runningLevel == 7)    runningLevel = 8;
        else if(runningLevel == 8)    runningLevel = 9;
        else if(runningLevel == 9)    runningLevel = 10;
        else if(runningLevel == 10)    runningLevel = 11;
        else if(runningLevel == 11)    runningLevel = 12;
        else if(runningLevel == 12)    runningLevel = 13;
//        else if(runningLevel == 13)    runningLevel = 14;
//        else if(runningLevel == 14)    runningLevel = 15;

        
        //vBall[0] = new BallVector(this);
        model.initModel(this);
        Sball.resetBall();

        times = 1;
        chooseLevel(runningLevel);
        wayBall.setVisible(false);
        wayPoint.setVisible(false);

        for ( i = 0; i < Part; i++ ) {
            for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                removeSprite = (Sprite) vBall[i].BVector.elementAt(0);
                vBall[i].BVector.removeElementAt(0);
                removeSprite();
            }
        }
        
        vBall[0].NumOfBall = 100;
        vBall[0].initBallVector();
        vBall[0].BVector.setSize(100);
        vBall[0].Begin = 0;
        vBall[0].End = NumB/16-1;

        ite = 0;
        NumB = 16*vBall[0].NumOfBall;
        Part = 1;

        lm.insert(FinishPoint, 0);

        State5 = true;
        State4 = false;
        State3 = false;
        State2 = false;
        State1 = false;
        State0 = false;
    }

//////////////////////////////////////////////////////////////////////////////////
// restart level
    public void restartLevel() {        
        lm.remove(Level1);
        for ( i = 0; i < Part; i++ ) {
            for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                removeSprite = (Sprite) vBall[i].BVector.elementAt(0);
                vBall[i].BVector.removeElementAt(0);
                removeSprite();
                
            }
        }
        lm.insert(Level1, 0);
        
        //vBall[0] = new BallVector(this);
        vBall[0].NumOfBall = 100;

        vBall[0].initBallVector();
        vBall[0].BVector.setSize(100);
        vBall[0].Begin = 0;
        vBall[0].End = NumB/16-1;

        model.initModel(this);
        Sball.resetBall();

        ite = 0;
        NumB = 16*vBall[0].NumOfBall;
        Part = 1;

//        for ( i = 0; i < Part; i++ ) {
//            for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
//                ((Sprite)vBall[i].BVector.elementAt(j)).setVisible(true);
//            }
//        }

        lm.insert(FinishPoint, 0);
        
    }

//////////////////////////////////////////////////////////////////////////////////
// Xử lý các việc ấn nút
    public void keyPressed( int KeyCode ){
        KeyCode = KeyCodeAdapter.getInstance().adoptKeyCode(KeyCode);
        switch(KeyCode) {
            case KeyCodeAdapter.UP_KEY:
                if ( State6 ) {
                    if ( submenu > 0 )  submenu--;
                    else submenu = 2;
                } else {
                     if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9 && runningLevel != 12 && runningLevel != 7 ) {
                        SubColor = Sball.Ball.getFrame();
                        Sball.Ball.setFrame(Sball.nextColor);
                        Sball.nextColor = SubColor;
                     }
                }
                break;
            case KeyCodeAdapter.DOWN_KEY:
                if ( State6 ) {
                    if ( submenu < 2 )  submenu++;
                    else submenu = 0;
                } else {
                    if ( runningLevel != 3 && runningLevel != 6 && runningLevel != 9 && runningLevel != 10 && runningLevel != 11 && runningLevel != 12 && runningLevel != 7 ) {
                        iCount += 180;
                        if ( iCount >= 360 ) iCount -= 360;
                    } else if ( runningLevel == 10 || runningLevel == 11  ) {
                       model.Swap();
                    }
                }
                //if ( !Back )    Back = true;
                //else Back = false;
                /*for ( int j = 0; j < 50; j++ ) {
                    temp[j] = w[j];
                    w[j] = width[j];
                    width[j] = temp[j];
                }
                for ( int j = 0; j < 50; j++ ) {
                    temp[j] = h[j];
                    h[j] = height[j];
                    height[j] = temp[j];
                }*/
                break;
            case KeyCodeAdapter.LEFT_KEY:
                if ( runningLevel == 3 || runningLevel == 6 || runningLevel == 9 || runningLevel == 12 || runningLevel == 7 ) {
                    SubColor = Sball.Ball.getFrame();
                    Sball.Ball.setFrame(Sball.nextColor);
                    Sball.nextColor = SubColor;
                 }
                break;
            case KeyCodeAdapter.RIGHT_KEY:
                if ( runningLevel == 3 || runningLevel == 6 || runningLevel == 9 || runningLevel == 12 || runningLevel == 7 ) {
                    iCount += 180;
                    if ( iCount >= 360 ) iCount -= 360;

                 }
                break;
            case KeyCodeAdapter.CENTER_KEY:
            case KeyCodeAdapter.KEY_5:
                if ( !State6 ) {
                    if ( !Shoot )   Sball.ShootAngle = iCount;
                    Shoot = true;
                    model.Back = false;
                }
                
                if ( State6 ) {
                    State6 = false;
                    if ( submenu == 2 ) {
                        if ( SubmenuState == 0 )    State0 = true;
                        else if ( SubmenuState == 1 )    State1 = true;
                        else if ( SubmenuState == 2 )    State2 = true;
                        else if ( SubmenuState == 3 )    State3 = true;
                        else if ( SubmenuState == 4 )    State4 = true;
                        else if ( SubmenuState == 5 )    State5 = true;
                    } else if ( submenu == 1 ) {
                        Midlet.sunnetCanvas.isMenu = true;
                        stop();
                        this.Midlet.SunnetCanvasShow();
                    }
                }
                break;
//            case KeyCodeAdapter.KEY_1:
//                Sball.Ball.setFrame(0);
//                break;
//            case KeyCodeAdapter.KEY_2:
//                Sball.Ball.setFrame(1);
//                break;
//            case KeyCodeAdapter.KEY_3:
//                Sball.Ball.setFrame(2);
//                break;
//            case KeyCodeAdapter.KEY_4:
//                Sball.Ball.setFrame(3);
//
//                //E1.SlowEf();
//                //if ( !Stop  ){
//
//                    //t.interrupt();
//                    //Stop = true;
//                //}
//                break;
//            case KeyCodeAdapter.KEY_6:
//                this.run = false;
//                break;
//            case KeyCodeAdapter.KEY_7:
//                State5 = true;
//                State4 = false;
//                State3 = false;
//                State2 = false;
//                State1 = false;
//                State0 = false;
//                break;
            case KeyCodeAdapter.KEY_8:
                State5 = true;
                State4 = false;
                State3 = false;
                State2 = false;
                State1 = false;
                State0 = false;
                nextLv = true;
                break;
            case KeyCodeAdapter.SOFT_KEY_RIGHT:
                if ( State0 )    { SubmenuState = 0;  State0 = false; }
                else if ( State1 )    { SubmenuState = 1;  State1 = false; }
                else if ( State2 )    { SubmenuState = 2;  State2 = false; }
                else if ( State3 )    { SubmenuState = 3;  State3 = false; }
                else if ( State4 )    { SubmenuState = 4;  State4 = false; }
                else if ( State5 )    { SubmenuState = 5;  State5 = false; }
                State6 = true;
                break;

            case KeyCodeAdapter.SOFT_KEY_LEFT:
                Sound.nextFrame();
                if ( Sound.getFrame() == 0 ){
//                    try {
//                        player.start();
//                    } catch (MediaException ex) {
//                        ex.printStackTrace();
//                    }
                } else {
//                    try {
//                        player.stop();
//                    } catch (MediaException ex) {
//                        ex.printStackTrace();
//                    }
                }
        }
    }
    
    /*public void keyRepeated() {
        KeyCode = KeyCodeAdapter.getInstance().adoptKeyCode(KeyCode);
        switch(KeyCode) {
            
        }
    }*/

}