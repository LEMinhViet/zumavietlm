package Code;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class ZumaCanvas extends GameCanvas implements Runnable {
    Random randomizer = new Random();
    Model model;
    Sprite BGSpr, Level1, L1_way, L2_way, wayPoint, wayBall, Lv_patch1, Lv_patch2, Lv_patch3, Lv_patch4, Lv_patch5;
    int [] breakSeq = { 0, 1, 2, 3 };
    int ite, iteS0, NumB, runningLevel;
    int keyState, testX = 20, testY = 220, Number;
    int InsertTime, add , partColli, partColliBack;
    int sumOfDistance, sumOfInsert, backCount, countDownTiming = 1, countDownIte, sumOfBack;
    int checkTime, NumOfColor, BreakingTime, runIte, iColliSave;
    int i, j, m, k, r, w, h, arr, times = 1, iColli, angleCount, runCount;
    int iColliS2, ranA, keyPressed, angleAdd = 3, subDistance, check_1ball;
    int Part = 1;
    Score score = new Score(this);
    Navigator N = new Navigator(this);
    Effect E1 = new Effect ( this, 1 );
    double dr, radian;
    long sleep, timeSinceStart;
    Sprite[] breakBall = new Sprite[30];
    BallVector[] vBall = new BallVector[10];
    Ball Sball = new Ball(this);
    //Lưu điểm đầu
    int width, height;
    // Zigzac để đảm bảo đoàn bi không bị chèn lên nhau
    int zigzac = 0;
    int [] Color = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    int [] ColorOld = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    int [][] lv = new int [2048][3];
    int [] moveX = { 0, 1, 0, -1,};
    int [] moveY = { -1, 0, 1, 0,};
    float iCount = 1;
    boolean run, State0 = true, State1 = false, State2 = false, State3 = false, State4 = false, Divide = false, Asm = false;
    boolean Stop = false, Back = false, Shoot = false, Colli = false, pColli = false, isStateChange = false, isColliAtEnd = false;
    boolean headInsert = false, backWard = true, afterBack = false, addColor = false, Breaking = false, Breaked = false;
    boolean OutOfBalls = false, unchangeColor = true, colliAfter = false, notBreak = false, Be_1ball = false, Af_1ball;
    boolean isColli = false, isRotated = false, drawScore = false;
    public LayerManager lm;
    StartMidlet Midlet;
    Thread t;
    private int MAX_CPS = 40;
    private int MS_PER_SECOND = 1000/MAX_CPS;
    private long timeLastCycle = 0;
    Graphics g = getGraphics();

    public ZumaCanvas(StartMidlet Midlet) {
        super(false);
        this.Midlet = Midlet;
        this.setFullScreenMode(true);
        this.lm = new LayerManager();

        try {

            wayPoint = new Sprite(Image.createImage("/picture/pixel.png"), 1, 1);
            lm.append(wayPoint);

            wayBall = new Sprite(Image.createImage("/picture/bi.png"), 16, 16);
            lm.append(wayBall);

            for ( i = 0; i < 30; i++ ) {
                breakBall[i] = new Sprite(Image.createImage("/picture/no_bongxanh.png"), 25, 26);
                breakBall[i].setFrameSequence(breakSeq);
                breakBall[i].setVisible(false);
                lm.append(breakBall[i]);
            }

            vBall[0] = new BallVector(this);
            vBall[0].NumOfBall = 50;
            NumB = 16*vBall[0].NumOfBall;
            vBall[0].initBallVector();
            vBall[0].Begin = 0;
            vBall[0].End = NumB/16-1;

            runningLevel = 1;
            
            model = new Model();
            model.initModel(this);
            lm.append(model.Model);
            Sball.initBall();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        
        g.setColor(0x00ff00);
        
        chooseLevel(runningLevel);
        //for ( k = 0; k < 100; k++ )
           // System.out.println("lv " + lv[k][1] + " " + lv[k][0]);
        wayBall.setVisible(false);
        wayPoint.setVisible(false);
        while ( run ) {
            timeLastCycle = System.currentTimeMillis();
            
            // Các trạng thái tốc độ của đoàn bi
            if ( ite >= NumB )  Number = NumB/16-1;
            else    Number = ite/16;
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
                

                if ( iteS0 >= 10 )  {
                    iteS0 = 0;
                    State0 = false;
                    State1 = true;
                }                
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 1 : State chính lúc đoàn bóng di chuyển tốc độ bình thường
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State1 ) {
                //for ( i = 0; i < Part; i++ ) {
                    for ( j = 0; j < Number+1; j++ ) {
                        ((Sprite)vBall[0].BVector.elementAt(j)).setVisible(true);
                    }
                //}
                //System.out.println("Số ball ; " + vBall[0].BVector.size());
                //vBall[0].End = Number;
                if ( !Stop && !Back )    ite++;
                else if ( Back ) ite--;
                for ( k = 0; k < Number + 1; k++ ) {
                    try {
                        ((Sprite)vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                    } catch ( ArrayIndexOutOfBoundsException b ) {
                        System.out.println("End " + vBall[0].End + " size " + vBall[0].BVector.size() + " k " + k );
                    }
                    if ( ite-16*k >= 0 && lv[ite-16*k][2] == 1 ) {
                        ((Sprite) vBall[0].BVector.elementAt(k)).setVisible(false);
                        System.out.println("Visible");
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
                if ( Divide ) {                    
                    ////////////////////////////////////////////////////////////////////////////////////
                    // Tách làm 2 đoạn sau khi có khoảng trống
                    ////////////////////////////////////////////////////////////////////////////////////
                    vBall[Part] = new BallVector(this);
                    Part++;
                    System.out.println("Part " + Part);
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
                            vBall[partColliBack].BVector.removeElementAt(vBall[partColliBack].beginBreak);
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
                        System.out.println("Here1");
                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
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
                                System.out.println("Here2");
                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
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
                                        System.out.println("Here6");
                                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
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
                            if ( Sball.Ball.getX() == (int)(Sball.getPositionX() + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2))
                                        && Sball.Ball.getY() == (int)(Sball.getPositionY() - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)))  {
                                for ( int i = 0; i < 10; i++ ) {
                                    ColorOld[i] = Color[i];
                                }
                                getColor();
                                if ( compareColor( Color, ColorOld ) == 0 ) {
                                    System.out.println("Compare ");
                                    Sball.resetBall();
                                }                               
                            }
                                    
                            for ( i = 0; i < Part; i++ ) {
                                System.out.println("Here5");
                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                            }

                            if ( !Divide ) {
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
                            System.out.println("  "+State1+State2+State3 + " divide "+ Divide + " p " + partColli);

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
                            vBall[partColli].BVector.removeElementAt(vBall[partColli].beginBreak);
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
                        System.out.println("Here3");
                        System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                        System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
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
                            System.out.println("Number lỗi " + (Number-vBall[0].Begin));
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
                                for ( i = 0; i < Part; i++ ) {
                                    System.out.println("Here4");
                                    System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                    System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                                }
                                Asm = false;
                            }
                        }
                    }
                    ///////////////////////////////////////////////////////////////////////////////
                    // Xử lý bắn bóng State3
                    ////////////////////////////////////////////////////////////////////////////
                    S3Shoot();
                }

            } else if ( State4 ) {
                for ( i = 0; i < Part; i++ ) {
                    for ( k = 0; k < vBall[i].BVector.size(); k++ ){
                        if ( ite-16*k >= 0 && ite-16*k < 2048 )
                            ((Sprite) vBall[i].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                        if ( ite-16*k >= 0 && ite-16*k < 2048 && lv[ite-16*k][2] == 1 )
                            ((Sprite) vBall[i].BVector.elementAt(k)).setVisible(false);
                        else ((Sprite) vBall[i].BVector.elementAt(k)).setVisible(true);
                        if (((Sprite)vBall[i].BVector.elementAt(k)).getX() == 0 && ((Sprite)vBall[i].BVector.elementAt(k)).getY() == 0 )
                            ((Sprite)vBall[i].BVector.elementAt(k)).setVisible(false);
                    }
                }

                if (((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getX() == 0
                        && ((Sprite)vBall[0].BVector.elementAt(vBall[0].BVector.size()-1)).getY() == 0 ) {
                    restartLevel();
                    State4 = false;
                }
                ite += 24;
            }
        }

 //           System.out.println("State = " + State1 + State2 + State3 );
//            System.out.println("So Part " + Part);
            if ( Part >= 1 ) {
                if (((Sprite)vBall[Part-1].BVector.elementAt(0)).getX() == 0 && ((Sprite)vBall[Part-1].BVector.elementAt(0)).getY() == 0 ) {
                    ((Sprite)vBall[Part-1].BVector.elementAt(0)).setVisible(false);
                    State0 = false;
                    State1 = false;
                    State2 = false;
                    State3 = false;
                    State4 = true;
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
            }
            if ( (keyState & LEFT_PRESSED) != 0 ) {
                keyPressed++;
                if ( keyPressed % 5 == 0 )
                    if ( angleAdd <= 9)     angleAdd += 2;
                if ( iCount < 360 - angleAdd ) iCount += angleAdd;
                else iCount = 0;
                //angleCount++;
            } else if ( (keyState & RIGHT_PRESSED) != 0 ) {
                keyPressed++;
                if ( keyPressed % 5 == 0 )
                    if ( angleAdd <= 9 )    angleAdd += 2;
                if ( iCount > angleAdd ) iCount -= angleAdd;
                else iCount = 359;
                //angleCount++;
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
            /////////////////////////////////
            // Model quay theo góc
            N.drawNavi(g);
            model.rotateModel();
            if ( Shoot )    model.whenShoot(iCount);
            //model.Model.paint(g);
            //Sball.Ball.paint(g);
            
            if ( drawScore )    score.drawScore();
            
            flushGraphics();
            lm.paint(g, 0, 0);
            timeSinceStart = System.currentTimeMillis()-timeLastCycle;
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
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)(Sball.getPositionY() - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
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
                                System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
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
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)(Sball.getPositionY() - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
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
                                System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
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
            Sball.Ball.setPosition((int)(Sball.getPositionX() + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                (int)( Sball.getPositionY() - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
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
    public void move ( Sprite PointSpr, Sprite ball, Sprite L_way) {
        for ( i = 0; i < 4; i++) {
            PointSpr.setPosition( width + moveX[i], height + moveY[i]);
            if ( PointSpr.collidesWith( L_way, true) ) { // Nếu va chạm
                // Xét các hướng đặc biệt để pixel không bị quay về
                // Nếu điểm xét là điểm đã đi qua
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
                        move ( PointSpr, ball, L_way );
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

            } else {
                // Không va chạm thì quay lại điểm đầu
                PointSpr.setPosition((width) , (height));
            }
        }
        
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
        for ( int u = 0; u < 10; u++ ) {
            Color[u] = -1;
        }
        for ( int z = 0; z < Part; z++ ) {
            for ( int t = 0; t < vBall[z].BVector.size(); t++ ) {
                addColor = true;
                for ( int u = 0; u < 10; u++ ) {
                    if ( Color[u] == ((Sprite)(vBall[z].BVector.elementAt(t))).getFrame()
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getX() != 1 - 8
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getY() != 65 - 8
                            && ((Sprite)vBall[z].BVector.elementAt(t)).isVisible() == true
                            ) {
                        addColor = false;
                        break;
                    }
                    if ( !((Sprite)vBall[z].BVector.elementAt(t)).isVisible() || 
                            (((Sprite)vBall[z].BVector.elementAt(t)).getX() <= 1 - 8
                            && ((Sprite)vBall[z].BVector.elementAt(t)).getY() == 65 - 8)) {
                        addColor = false;
                        break;
                    }
                }
                if ( addColor ) {
                    Color[NumOfColor] = ((Sprite)(vBall[z].BVector.elementAt(t))).getFrame();
                    NumOfColor++;
                }
            }
        }
    }

    public int compareColor ( int[] Color, int[] OldColor ) {
        int Num = 0, OldNum = 0;
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
        System.out.println("Change " + haveChangeColor);
        if ( haveChangeColor )  {
            for ( i = 0; i < Color.length; i++ ) {
                if ( Sball.Ball.getFrame() == Color[i] )
                    return 1;
            }
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
            System.out.println("times " + times);
        }
    }

    

//////////////////////////////////////////////////////////////////////////////////
// Hàm chọn level 
    public void chooseLevel ( int runningLevel ) {
        for ( k = 0; k < 2048; k++ ) {
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
                L1_way = new Sprite(Image.createImage("/picture/lv1-way.png"), 256, 320);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Level1.setPosition(0, 0);
            lm.append(Level1);
            
            L1_way.setPosition(-16, 0);
            lm.append(L1_way);

            for ( k = 0; k < 2048; k++ )
                move ( wayPoint, wayBall, L1_way);
        } else if ( runningLevel == 2 ) {

        } else if ( runningLevel == 3 ) {

        } else if ( runningLevel == 4 ) {
            lm.remove(Level1);
            lm.remove(L1_way);

            Level1 = null;
            L1_way = null;

            wayBall.setVisible(true);
            wayPoint.setVisible(true);
            try {
                Lv_patch1 = new Sprite(Image.createImage("/picture/lv4-patch.png"), 22, 18);
                Lv_patch2 = new Sprite(Image.createImage("/picture/lv4-patch1.png"), 24, 35);
                Lv_patch3 = new Sprite(Image.createImage("/picture/lv4-patch2.png"), 20, 25);
                Lv_patch4 = new Sprite(Image.createImage("/picture/lv4-patch3.png"), 15, 17);
                Lv_patch5 = new Sprite(Image.createImage("/picture/lv4-patch4.png"), 17, 39);
                Level1 = new Sprite(Image.createImage("/picture/lv4.png"), 240, 320);
                L1_way = new Sprite(Image.createImage("/picture/lv4-way-1.png"), 240, 320);
                L2_way = new Sprite(Image.createImage("/picture/lv4-way-2.png"), 240, 320);
                
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
            lm.append(Level1);

            L1_way.setPosition(0, 0);
            lm.append(L1_way);

            L2_way.setPosition( 0, 0);
            lm.append(L2_way);
            
            width = 182;
            height = 52;
            lv[0][0] = 173;
            lv[0][1] = 44;
            for ( k = 0; k < 1500; k++ )
                move ( wayPoint, wayBall, L1_way);

            width = 48;
            height = 272;
            lv[1321][0] = 40;
            lv[1321][1] = 264;
            for ( k = 1321; k < 1751; k++ )
                move ( wayPoint, wayBall, L2_way);

//            for ( k = 0; k < 1751; k++ )
//                System.out.println(k + " " + lv[k][0] + " " + lv[k][1]);
            for ( k = 840; k < 1027; k++ )
                lv[k][2] = 1;
            for ( k = 1315; k < 1381 ; k++ )
                lv[k][2] = 1;
            
        } else if ( runningLevel == 5 ) {
            lm.remove(Level1);
            lm.remove(L1_way);
            lm.remove(L2_way);
            lm.remove(Lv_patch1);
            lm.remove(Lv_patch2);
            lm.remove(Lv_patch3);
            lm.remove(Lv_patch4);
            lm.remove(Lv_patch5);

            Level1 = null;
            L1_way = null;
            L2_way = null;
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
                L1_way = new Sprite(Image.createImage("/picture/lv5-way.png"), 240, 320);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Lv_patch1.setPosition(194, 79);
            lm.insert(Lv_patch1, 0);

            Level1.setPosition(0, 0);
            lm.append(Level1);

            L1_way.setPosition(0, 0);
            lm.append(L1_way);

            for ( k = 0; k < 2048; k++ )
                move ( wayPoint, wayBall, L1_way);
            
//            for ( k = 0; k < 1751; k++ )
//                System.out.println(k + " " + lv[k][0] + " " + lv[k][1]);

            for ( k = 604; k < 659; k++ )
                lv[k][2] = 1;

        } else if ( runningLevel == 6 ) {

        } else if ( runningLevel == 7 ) {

        } else if ( runningLevel == 8 ) {

        } else if ( runningLevel == 9 ) {

        }else if ( runningLevel == 10 ) {

        } else if ( runningLevel == 11 ) {

        } else if ( runningLevel == 12 ) {

        } else if ( runningLevel == 13 ) {

        } else if ( runningLevel == 14 ) {

        } else if ( runningLevel == 15 ) {

        }
        
    }

//////////////////////////////////////////////////////////////////////////////////
// chuyển sang level khác
    public void nextLevel() {
        if ( runningLevel == 1 )    runningLevel = 4;
        else if(runningLevel == 4)    runningLevel = 5;

        vBall[0] = new BallVector(this);
        vBall[0].NumOfBall = 50;
        vBall[0].initBallVector();
        vBall[0].Begin = 0;
        vBall[0].End = NumB/16-1;

        model.initModel(this);
        Sball.resetBall();

        times = 1;
        chooseLevel(runningLevel);
        wayBall.setVisible(false);
        wayPoint.setVisible(false);

        ite = 0;
        NumB = 16*vBall[0].NumOfBall;
        Part = 1;

        State4 = false;
        State3 = false;
        State2 = false;
        State1 = false;
        State0 = true;
    }

//////////////////////////////////////////////////////////////////////////////////
// restart level
    public void restartLevel() {
        lm.remove(Level1);
        for ( i = 0; i < Part; i++ ) {
            for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                 vBall[i].BVector.removeAllElements();
            }
        }
        lm.insert(Level1, 0);
        //vBall[0] = new BallVector(this);
        vBall[0].NumOfBall = 50;
        vBall[0].initBallVector();
        vBall[0].Begin = 0;
        vBall[0].End = NumB/16-1;
        
        model.initModel(this);
        Sball.resetBall();

        ite = 0;
        NumB = 16*vBall[0].NumOfBall;
        Part = 1;

        for ( i = 0; i < Part; i++ ) {
            for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                ((Sprite)vBall[i].BVector.elementAt(j)).setVisible(true);
            }
        }
        
        State4 = false;
        State3 = false;
        State2 = false;
        State1 = false;
        State0 = true;
    }

//////////////////////////////////////////////////////////////////////////////////
// Xử lý các việc ấn nút
    public void keyPressed( int KeyCode ){
        KeyCode = KeyCodeAdapter.getInstance().adoptKeyCode(KeyCode);
        switch(KeyCode) {
            case KeyCodeAdapter.UP_KEY:
                iteS0 = 0;
                State0 = true;
                State1 = false;

                break;
            case KeyCodeAdapter.DOWN_KEY:
                if ( !Back )    Back = true;
                else Back = false;
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
                break;
            case KeyCodeAdapter.RIGHT_KEY:
                break;
            case KeyCodeAdapter.CENTER_KEY:

            case KeyCodeAdapter.KEY_5:
                if ( !Shoot )   Sball.ShootAngle = iCount;
                Shoot = true;
                model.Back = false;
                break;
            case KeyCodeAdapter.KEY_1:
                Sball.Ball.setFrame(0);
                break;
            case KeyCodeAdapter.KEY_2:
                Sball.Ball.setFrame(1);
                break;
            case KeyCodeAdapter.KEY_3:
                Sball.Ball.setFrame(2);
                break;
            case KeyCodeAdapter.KEY_4:
                Sball.Ball.setFrame(3);

                //E1.SlowEf();
                //if ( !Stop  ){

                    //t.interrupt();
                    //Stop = true;
                //}
                break;
            case KeyCodeAdapter.KEY_6:
                this.run = false;
                break;
            case KeyCodeAdapter.KEY_7:
                restartLevel();
                break;
            case KeyCodeAdapter.KEY_8:
                nextLevel();
                break;
            
        }
    }
    
    /*public void keyRepeated() {
        KeyCode = KeyCodeAdapter.getInstance().adoptKeyCode(KeyCode);
        switch(KeyCode) {
            
        }
    }*/

}