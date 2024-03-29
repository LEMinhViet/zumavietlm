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
    Sprite BGSpr, Level1, L1_way, wayPoint, wayBall;
    int [] breakSeq = { 0, 1, 2, 3 };
    int iDir = 0, ite = 0, iteS0 = 0, iteS1 = 0, iteS2 = 0, iteS3 = 0, ColliX, ColliY, NumB = 800;
    int keyState, testX = 20, testY = 220, Number = 0;
    int InsertTime = 0, add = 0, partColli = 0, partColliBack = 0;
    int sumOfDistance, sumOfInsert, backCount, countDownTiming = 1, countDownIte = 0;
    int checkTime = 0, NumOfColor = 0, BreakingTime = 0;
    int i, j, m, k, r, w, h, arr, times = 0, iColli, angleCount, runCount;
    int iColliS2, ranA;
    // Số đoạn
    int Part = 1;
    //int w1, h1, dx, dy, wh2, destX, destY;
    double dr, radian;
    long sleep, timeSinceStart;
    //int[] srcMap, destMap;
    Sprite[] breakBall = new Sprite[30];
    BallVector[] vBall = new BallVector[10];
    Ball Sball = new Ball(this);
    //Lưu điểm đầu
    int width = 1, height = 65;
    // Zigzac để đảm bảo đoàn bi không bị chèn lên nhau
    int zigzac = 0;
    int [] Color = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    int [][] lv = new int [1024][2];
    int [] moveX = { 0, 1, 0, -1,};
    int [] moveY = { -1, 0, 1, 0,};
    float iCount = 1;
    boolean run, State0 = true, State1 = false, State2 = false, State3 = false, Insert = false, Divide = false, Asm = false;
    boolean Stop = false, back = false, Shoot = false, Colli = false, testBreak = false, testConnect = false, pColli = false;
    boolean headInsert = false, backWard = true, afterBack = false, addColor = false, Breaking = false, Breaked = false;
    boolean OutOfBalls = false;
    public LayerManager lm;
    StartMidlet Midlet;
    Thread t;
    private int MAX_CPS = 30;
    private int MS_PER_SECOND = 1000/MAX_CPS;
    private long timeLastCycle = 0;

    public ZumaCanvas(StartMidlet Midlet) {
        super(false);
        this.Midlet = Midlet;
        this.setFullScreenMode(true);
        this.lm = new LayerManager();

        lv[0][0] = -7;
        lv[0][1] = 57;

        try {

            wayPoint = new Sprite(Image.createImage("/picture/pixel.png"), 1, 1);
            wayPoint.setPosition(1, 65);
            lm.append(wayPoint);

            wayBall = new Sprite(Image.createImage("/picture/bi.png"), 16, 16);
            wayBall.setPosition(0, 0);
            lm.append(wayBall);

            for ( i = 0; i < 30; i++ ) {
                breakBall[i] = new Sprite(Image.createImage("/picture/no_bongxanh.png"), 25, 26);
                breakBall[i].setFrameSequence(breakSeq);
                breakBall[i].setVisible(false);
                lm.append(breakBall[i]);
            }

            vBall[0] = new BallVector(this);
            vBall[0].NumOfBall = 50;
            vBall[0].initBallVector();
            vBall[0].Begin = 0;
            vBall[0].End = NumB/16-1;
            
            model = new Model();
            model.initModel(this);
            lm.append(model.Model);
            Sball.initBall();
            

            Level1 = new Sprite(Image.createImage("/picture/lv1.png"), 240, 320);
            Level1.setPosition(0, 0);
            lm.append(Level1);

            L1_way = new Sprite(Image.createImage("/picture/lv1-way.png"), 240, 320);
            L1_way.setPosition(0, 0);
            lm.append(L1_way);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void run() {
        Graphics g = getGraphics();
        g.setColor(0x00ff00);
        for ( k = 0; k < 997; k++ )
            move ( wayPoint, wayBall);
        wayBall.setVisible(false);
        wayPoint.setVisible(false);
        while ( run ) {
            timeLastCycle = System.currentTimeMillis();

            /////////////////////////////////
            // Model quay theo góc
            g.drawLine(124, 168, 124 + (int)(100*Math.cos((iCount/180)*Math.PI-Math.PI/2))
                    , 168 - (int)(100*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
            model.rotateModel();
            model.Model.paint(g);
            Sball.Ball.paint(g);
            
            // Các trạng thái tốc độ của đoàn bi
            if ( ite >= NumB )  Number = NumB/16-1;
            else    Number = ite/16;
            if ( Number == NumB/16-1 )   OutOfBalls = true;
            vBall[0].End = Number;
            //System.out.println("So ball : " + Number);

            for ( i = 0; i < Part; i++ ) {
                for ( j = 0; j < vBall[i].BVector.size(); j++ ) {
                    if ( ((Sprite)vBall[i].BVector.elementAt(j)).getX() == 1 - 8 &&
                            ((Sprite)vBall[i].BVector.elementAt(j)).getY() == 65 - 8 ) {
                        OutOfBalls = false;
                        //System.out.println("FALSE");
                    }
                }
            }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 0 : State chạy nhanh ban đầu lúc vừa vào level, sẽ không có bắn trong state này
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if ( State0 ) { // Vừa vào - tốc độ nhanh cho đến khi đủ 8 bi
                iteS0++;
                ite += 16;

                for ( k = 0; k < Number+1; k++ ){
                    if ( ite-16*k <= 997 ) {
                        ((Sprite) vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                        
                    } else if ((ite-16*k) - 997 < 16)
                        ((Sprite) vBall[0].BVector.elementAt(k)).setPosition(lv[997][0], lv[997][1]);
                }

                if ( iteS0 >= 12 )  {
                    State0 = false;
                    State1 = true;
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 1 : State chính lúc đoàn bóng di chuyển tốc độ bình thường
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State1 ) {
                //System.out.println("Số ball ; " + vBall[0].BVector.size());
                //vBall[0].End = Number;
                ite++;
                
                for ( k = 0; k < Number+1; k++ ) {
                    //try {
                    //if ((ite-16*k) <= 997 )
                        //System.out.println("Number loi~ " + k );
                        //System.out.println((ite-16*k) + " " + (ite-16*k));
                        try {
                            ((Sprite)vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*k][0], lv[ite-16*k][1]);
                        } catch ( ArrayIndexOutOfBoundsException b ) {
                            System.out.println("k = " +k);
                        }
                    //}catch ( ArrayIndexOutOfBoundsException a ) {
                        
                    //}
                }
                
                ///////////////////////////////////////////////////////////////////////////////
                // Xử lý bắn bóng 
                if ( Shoot ) {
                    partColli = 0;
                    Sball.ShootDistance += 15;
                    if ( Sball.ShootDistance < 200 ) {
                        Sball.shootBall( Sball.ShootAngle );
                        if ( !Colli ) {
                            headInsert = false;
                            //System.out.println("xet Va cham");
                            for ( iColli = 0; iColli < Number+1; iColli++ ) {
                                if ( Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli)), true)) {
                                    //System.out.println("Xet Va cham");
                                    if (( iColli != Number && Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli+1)), true))
                                        || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli-1)), true))
                                        || iColli == 0 || iColli == Number)  {
                                        if ( iColli == 0 && vBall[0].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[0].BVector.elementAt(iColli+1)), true))
                                            headInsert = true;
                                        Colli = true;
                                        break;
                                    } else {
                                        Colli = true;
                                        pColli = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if ( InsertTime == 0 ) {
                                vBall[0].End ++;
                                Sball.Ball.setVisible(false);
                                ite += 16;
                                NumB += 16;
                            }
                            for ( j = 0; j <= iColli; j++ ){
                                if ( ite-16*j <= 997 ) {
                                    ((Sprite) vBall[0].BVector.elementAt(j)).setPosition(lv[ite-16*j-12+InsertTime*4][0], lv[ite-16*j-12+InsertTime*4][1]);
                                } else if ( (ite-16*j) - 997 < 4 )
                                    ((Sprite) vBall[0].BVector.elementAt(j)).setPosition(lv[997][0], lv[997][1]);
                            }
                            vBall[0].InsertBall(iColli, 0);
                            if ( InsertTime == 4 ) {
                                InsertTime = 0;
                                //ColliPoint = iColli - 1;
                                
                                Colli = false;
                                Shoot = false;
                                vBall[0].testBreak(iColli);
                                testBreak = false;
                            }
                        }
                    } else {
                        Shoot = false;
                        Sball.resetBall();
                        Colli = false;
                    }
                } else
                    Sball.Ball.setPosition((int)(116 + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                        (int)(160 - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));

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

                    for ( j = Part-1; j >= partColliBack+1; j-- ) {
                        vBall[j].Distance = vBall[j-1].Distance;
                    }
                    //System.out.println("partColliback " + partColliBack);
                    //System.out.println("part " + Part);
                    vBall[partColliBack].Distance = 0;
                    
                    if ( Part > 1 && partColliBack + 1 != Part - 1 )
                        vBall[partColliBack+1].Distance -= 16;

                    /// Bỏ bóng
                    for ( m = vBall[partColliBack].beginBreak; m <= vBall[partColliBack].endBreak; m++ ) {
                        //System.out.println("divide " + vBall[partColliBack].beginBreak);
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
                        vBall[j-1].copyBallVector(vBall[j]);
                    }

                    vBall[Part - 1].Distance = 0;
                    ////////////////////////////////
                    //
                    //
                    //
                    ////////////////////////////////
                    vBall[partColliBack+1].End = vBall[partColliBack].beginBreak - 1 + vBall[partColliBack].Begin;
                    vBall[partColliBack+1].Begin = vBall[partColliBack].Begin;
                    vBall[partColliBack].Begin = vBall[partColliBack+1].End + 1;
                    vBall[partColliBack].End -= vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1;

                    for( j = partColliBack - 1; j >= 0; j-- ) {
                        vBall[j].Begin -= (vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1);
                        vBall[j].End -= (vBall[partColliBack].endBreak - vBall[partColliBack].beginBreak + 1);
                    }

                    for ( j = vBall[partColliBack+1].Begin; j <= vBall[partColliBack+1].End; j++ ) {
                        vBall[partColliBack+1].BVector.addElement(vBall[partColliBack].BVector.elementAt(0));
                        vBall[partColliBack+1].NumOfBall++;
                        vBall[partColliBack].BVector.removeElementAt(0);
                        vBall[partColliBack].NumOfBall--;
                    }

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Xử lý vector 0 add bóng => phân tách ra thành vector 0 và 1, vector 1 distance noi vao vector 2 => asm luôn
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    for ( int x = 0; x < Part - 1; x++ ) {
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
                                vBall[j].copyBallVector(vBall[j-1]);
                            }
                            Part--;
                            for ( j = 0; j < vBall[x].BVector.size(); j++ )
                                ((Sprite)vBall[x].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[x].Begin)][0], lv[ite-16*(j+vBall[x].Begin)][1]);
                        }
                    }

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
                        if ( countDownIte % 3 == 2 ) {
                           countDownTiming = (int)(countDownTiming*2);
                        }

                        if ( vBall[partColliBack].Distance - countDownTiming > 0 ) {
                            vBall[partColliBack].Distance -= countDownTiming;
                            if ( partColliBack + 1 == Part - 1)  ite -= countDownTiming;
                            else    vBall[partColliBack + 1].Distance += countDownTiming;
                            
                        } else {
                            if ( partColliBack + 1 == Part - 1 )    ite -= vBall[partColliBack].Distance;
                            else    vBall[partColliBack + 1].Distance += vBall[partColliBack].Distance;
                            vBall[partColliBack].Distance = 0;
                        }

                        //System.out.println("PartCOlliBack " + partColliBack );
                        for ( i = 0; i < vBall[partColliBack+1].BVector.size(); ++i ) {
                            if ( ite-16*(i+vBall[partColliBack+1].Begin)-vBall[partColliBack+1].Distance >= 0 )
                                ((Sprite)vBall[partColliBack+1].BVector.elementAt(i)).setPosition
                                    (lv[ite-16*(i+vBall[partColliBack+1].Begin)-vBall[partColliBack+1].Distance][0], lv[ite-16*(i+vBall[partColliBack+1].Begin)-vBall[partColliBack+1].Distance][1]);
                        }

                        if ( vBall[partColliBack].Distance == 0 && InsertTime == 0) {
                            //iColliS2 = vBall[partColliBack+1].Begin + vBall[partColliBack+1].BVector.size() - 1;
                            //System.out.println("iColliS2 " + iColliS2 + " beginbreak " + vBall[partColliBack].beginBreak + " endbreak " + vBall[partColliBack].endBreak);
                            
                            for ( k = 0; k <= vBall[partColliBack+1].End-vBall[partColliBack+1].Begin; k++ ) {
                                if ( vBall[partColliBack+1].BVector.size()-1 >= 0 ) {
                                    vBall[partColliBack].BVector.insertElementAt(vBall[partColliBack+1].BVector.elementAt(vBall[partColliBack+1].BVector.size()-1), 0);
                                    vBall[partColliBack+1].BVector.removeElementAt(vBall[partColliBack+1].BVector.size()-1);
                                }
                            }
                            for ( j = partColliBack; j < Part - 1; j++ ) {
                                vBall[j].Distance = vBall[j+1].Distance;
                            }
                            //vBall[partColliBack].Distance = vBall[partColliBack+1].Distance;
                            vBall[partColliBack].Begin = vBall[partColliBack+1].Begin;
                            vBall[partColliBack].End = vBall[partColliBack].Begin + vBall[partColliBack].BVector.size() - 1;
                            for ( j = partColliBack+2; j < Part; j++ ) {
                                vBall[j].copyBallVector(vBall[j-1]);
                            }

                            Part--;
                            for ( i = 0; i < Part; i++ ) {
                                System.out.println("Here2");
                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                            }

                            //System.out.println("Hoan tat noi doan");
                            //vBall[partColliBack].Distance += countDownTiming;
                            afterBack = true;
                            backWard = false;
                        }
                    } else if ( afterBack ) {
                        ////////////////////////////////////////////////////////////////////////////////////
                        // Lùi tiếp theo quán tính
                        ////////////////////////////////////////////////////////////////////////////////////
                        //System.out.println("AAAAAAASSSSSSSS" + vBall[partColliBack].Distance);
                        if ( countDownTiming/2 != 0 )
                            countDownTiming = (int)(countDownTiming/2);
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
                            }
                        } else if ( partColliBack == 0 ) {
                            if ( Part == 1 ) ite -= countDownTiming;
                            else    vBall[0].Distance += countDownTiming;
                        }

                        
                        try {
                            for ( i = 0; i < vBall[partColliBack].BVector.size(); ++i ) {
                                if ( ite-16*(i+vBall[partColliBack].Begin) < 4 )
                                    ((Sprite)vBall[partColliBack].BVector.elementAt(i)).setPosition(lv[0][0], lv[0][1]);
                                else
                                    ((Sprite)vBall[partColliBack].BVector.elementAt(i)).setPosition
                                            (lv[ite-16*(i+vBall[partColliBack].Begin)-vBall[partColliBack].Distance][0], lv[ite-16*(i+vBall[partColliBack].Begin)-vBall[partColliBack].Distance][1]);
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
                            System.out.println("PartColliBack " + partColliBack );
                            System.out.println("Distance " + vBall[0].Distance);
                            vBall[partColliBack].testBreak(iColli);
                            for ( i = 0; i < Part; i++ ) {
                                //System.out.println("Here2");
                                System.out.println("Vector so " + i + " Begin " + vBall[i].Begin + " End " + vBall[i].End );
                                System.out.println("Vector so " + i + " Size " + vBall[i].BVector.size() + " Distance " + vBall[i].Distance );
                            }

                            if ( !State2 ) {
                                for ( j = 0; j < Part; j++ ) {
                                    if ( vBall[j].Distance != 0 ) {
                                        State3 = true;
                                        State2 = false;
                                        State1 = false;
                                    }
                                }
                                if ( State3 != true ) {
                                    State2 = false;
                                    State1 = true;
                                }
                            } else {
                                if ( !Breaked && Part == 1) {
                                    State1 = true;
                                    State2 = false;
                                    State3 = false;
                                }
                            }
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
                if ( Shoot ) {
                    Sball.ShootDistance += 15;
                    if ( Sball.ShootDistance < 200 ) {
                        Sball.shootBall( Sball.ShootAngle );
                        if ( !Colli ) {
                            pColli = false;
                            //System.out.println("So part khi Shoot " + Part);
                            for (  partColli = 0; partColli < Part; partColli++ ) {
                                for ( iColli = 0; iColli < vBall[partColli].BVector.size(); iColli++ ) {
                                    if ( Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli)), true)) {
                                        if (( iColli != vBall[partColli].BVector.size()-1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                            || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true))
                                            || iColli == 0 || iColli == vBall[partColli].BVector.size()-1 )  {
                                            if ( iColli == 0 && vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                                headInsert = true;
                                            
                                            Colli = true;
                                            pColli = true;
                                            break;
                                        } else {
                                            Colli = true;
                                            pColli = true;
                                            break;
                                        }
                                    }
                                }
                                if ( pColli )    break;
                            }
                        } else {
                            if ( InsertTime == 0 ) {
                                
                                Sball.Ball.setVisible(false);
                                //if ( partColli == Part - 1 )
                                ite += 16;
                                NumB += 16;
                                if ( partColli == Part - 1 )    add += 16;
                                vBall[partColli].End++;
                                for ( m = partColli - 1; m >= 0; m-- ) {
                                    vBall[m].Begin++;
                                    vBall[m].End++;
                                }

                                if ( vBall[partColliBack].Distance > 16 )
                                        vBall[partColliBack].Distance -= 16;
                                else vBall[partColliBack].Distance = 0;
                            }
                            
                            sumOfInsert = 0;
                            for ( k = partColli; k < Part - 1; k++ ) {
                                sumOfInsert += vBall[k].Distance;
                            }

                            if ( iColli == 0 ) {
                                for ( j = 0; j < iColli; j++ ){
                                    try {
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                            lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                    } catch ( ArrayIndexOutOfBoundsException aio ) {
                                        //System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                                    }
                                }
                            } else {
                                for ( j = 0; j <= iColli; j++ ){
                                    //if ( ite-16*j <= 997 ) {
                                    try {
                                    ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                            lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                    } catch ( ArrayIndexOutOfBoundsException aio ) {
                                        //System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                                    }
                                    //}
                                }
                            }

                            //System.out.println("partColli insert " + partColli);
                            vBall[partColli].InsertBall(iColli, partColli);

                            if ( InsertTime == 4 ) {
                                if ( partColli != Part - 1 )
                                    ite-=16;

                                InsertTime = 0;
                                //ColliPoint = iColli - 1;
                                vBall[partColli].testBreak(iColli);

                                /*if ( Divide ) {

                                } else {
                                    if ( vBall[partColli].Distance > 16 )
                                        vBall[partColli].Distance -= 16;
                                    else vBall[partColli].Distance = 0;

                                    
                                }*/
                                
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
                    Sball.Ball.setPosition((int)(116 + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                        (int)(160 - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
                

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//State 3 : Bóng sau di chuyển để bắt kịp đoàn bóng trước nếu 2 đầu không cùng màu
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if ( State3 ) {
                //ite++;
                if ( Divide ) {
                    // Nếu bóng mất đi không ở 2 đầu
                    //System.out.println("part " + partColli);
                    vBall[Part] = new BallVector(this);
                    Part++;
                        
                    for ( j = Part-1; j >= partColli+1; j-- ) {
                        vBall[j].Distance = vBall[j-1].Distance;
                    }
                    vBall[partColli].Distance = 0;
                    if ( Part > 1 && partColli + 1 < Part - 1 )
                        vBall[partColli+1].Distance -= 16;
                    
                    /// Bỏ bóng
                    for ( m = vBall[partColli].beginBreak; m <= vBall[partColli].endBreak; m++ ) {
                        //System.out.println("divide " + vBall[partColli].beginBreak);
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
                    for ( j = Part-1; j >= partColli+2; j-- ) {
                        vBall[j-1].copyBallVector(vBall[j]);
                    }
                    vBall[Part - 1].Distance = 0;
                    vBall[0].End = NumB/16;
                    vBall[partColli+1].End = vBall[partColli].beginBreak - 1 + vBall[partColli].Begin;
                    vBall[partColli+1].Begin = vBall[partColli].Begin;
                    vBall[partColli].Begin  = vBall[partColliBack+1].End + 1;
                    vBall[partColli].End -= (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);

                    for( j = partColli - 1; j >= 0; j-- ) {
                        vBall[j].Begin -= (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);
                        vBall[j].End -= (vBall[partColli].endBreak - vBall[partColli].beginBreak + 1);
                    }

                    for ( j = vBall[partColli+1].Begin; j <= vBall[partColli+1].End; j++ ) {
                        vBall[partColli+1].BVector.addElement(vBall[partColli].BVector.elementAt(0));
                        vBall[partColli+1].NumOfBall++;
                        vBall[partColli].BVector.removeElementAt(0);
                        vBall[partColli].NumOfBall--;
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Xử lý vector 0 add bóng => phân tách ra thành vector 0 và 1, vector 1 distance < 0 => asm luôn
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    for ( int x = 0; x < Part - 1; x++ ) {
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
                                vBall[j].copyBallVector(vBall[j-1]);
                            }
                            Part--;
                            for ( j = 0; j < vBall[x].BVector.size(); j++ )
                                ((Sprite)vBall[x].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[x].Begin)][0], lv[ite-16*(j+vBall[x].Begin)][1]);
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
                                //if ((ite-16*k) <= 997 )
                                    //System.out.println((ite-16*(endBreak-beginBreak+1+k)+iteS3) + " " + (ite-16*(endBreak-beginBreak+1+k)+iteS3));
                                if ( ite-16*(k+vBall[0].Begin)-sumOfDistance >= 0 )
                                    ((Sprite)vBall[0].BVector.elementAt(k)).setPosition(lv[ite-16*(k+vBall[0].Begin)-sumOfDistance][0],
                                        lv[ite-16*(k+vBall[0].Begin)-sumOfDistance][1]);
                            }
                        } catch ( ArrayIndexOutOfBoundsException aiobe ) {
                            //System.out.println("Number lỗi " + (Number-vBall[0].Begin));
                        }
                        //System.out.println("Number " + Number +" vBall " + vBall[0].Begin + " Size "
                          //     + vBall[0].BVector.size() + " va " + vBall[1].BVector.size());
                        vBall[0].Distance--;
                    ///////////////////////////////////////////////////////////////////////////////
                    // Ghép 2 vector bóng
                    ///////////////////////////////////////////////////////////////////////////////
                    } else {
                        if ( Part == 1 && !Colli ) {
                            State3 = false;
                            State1 = true;
                            //add = 0;
                            partColli = 0;
                        } else {
                            if ( InsertTime == 0 )  Asm = true;
                            if ( Asm ) {
                                //add = 0;
                                for ( j = 0; j < Part - 1; j++ ) {
                                    vBall[j].Distance = vBall[j+1].Distance;
                                }

                                for ( k = 0; k <= vBall[1].End-vBall[1].Begin; k++ ) {
                                    if ( vBall[1].BVector.size()-1 >= 0 ) {
                                        vBall[0].BVector.insertElementAt(vBall[1].BVector.elementAt(vBall[1].BVector.size()-1), 0);
                                        vBall[1].BVector.removeElementAt(vBall[1].BVector.size()-1);
                                    }
                                }
                                vBall[0].End = vBall[0].BVector.size()-1;
                                vBall[0].Begin = vBall[1].Begin;
                                for ( j = 2; j < Part; j++ ) {
                                    vBall[j].copyBallVector(vBall[j-1]);
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
                    if ( Shoot ) {
                        Sball.ShootDistance += 15;
                        if ( Sball.ShootDistance < 200 ) {
                            Sball.shootBall( Sball.ShootAngle );
                            if ( !Colli ) {
                                pColli = false;
                                //System.out.println("So part khi Shoot " + Part);
                                for (  partColli = 0; partColli < Part; partColli++ ) {
                                    for ( iColli = 0; iColli < vBall[partColli].BVector.size(); iColli++ ) {
                                        if ( Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli)), true)) {
                                            if (( iColli != vBall[partColli].BVector.size()-1 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                                || ( iColli != 0 && Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli-1)), true))
                                                || iColli == 0 || iColli == vBall[partColli].BVector.size()-1 )  {
                                                if ( iColli == 0 && vBall[partColli].BVector.size() > 1 && !Sball.Ball.collidesWith(((Sprite)vBall[partColli].BVector.elementAt(iColli+1)), true))
                                                    headInsert = true;
                                                Colli = true;
                                                pColli = true;
                                                break;
                                            } else {
                                                Colli = true;
                                                pColli = true;
                                                break;
                                            }
                                        }
                                    }
                                    if ( pColli )    break;
                                }
                               
                            } else {
                                if ( InsertTime == 0 ) {
                                    Sball.Ball.setVisible(false);
                                    ite += 16;
                                    NumB += 16;
                                   // if ( partColli == Part - 1 )    add += 16;
                                    //System.out.println("add " + add);
                                    vBall[partColli].End++;
                                    for ( m = partColli - 1; m >= 0; m-- ) {
                                        vBall[m].Begin++;
                                        vBall[m].End++;
                                    }
                                }
                                
                                sumOfInsert = 0;
                                for ( k = partColli; k < Part - 1; k++ ) {
                                    sumOfInsert += vBall[k].Distance;
                                }

                                if ( iColli == 0 ) {
                                    for ( j = 0; j < iColli; j++ ){
                                        try {
                                        ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                                lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                        } catch ( ArrayIndexOutOfBoundsException aio ) {
                                            //System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                                        }
                                    }
                                } else {
                                    for ( j = 0; j <= iColli; j++ ){
                                        //if ( ite-16*j <= 997 ) {
                                        try {
                                        ((Sprite) vBall[partColli].BVector.elementAt(j)).setPosition(lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][0],
                                                lv[ite-16*(j+vBall[partColli].Begin)-sumOfInsert-12+InsertTime*4][1]);
                                        } catch ( ArrayIndexOutOfBoundsException aio ) {
                                            //System.out.println("dịch lên để insert vào  : " + j + " Colli " + iColli + " Begin " + vBall[partColli].Begin);
                                        }
                                        //}
                                    }
                                }
                 
                                vBall[partColli].InsertBall(iColli, partColli);

                                if ( InsertTime == 4 ) {
                                    if ( partColli != Part - 1 )
                                        ite-=16;
                                    
                                    InsertTime = 0;
                                    vBall[partColli].testBreak(iColli);
                                    
                                    if ( Divide ) {
                                        
                                    } else {
                                        if ( vBall[partColli].Distance > 16 )
                                            vBall[partColli].Distance -= 16;
                                        else vBall[partColli].Distance = 0;
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
                        Sball.Ball.setPosition((int)(116 + 15*Math.cos((iCount/180)*Math.PI-Math.PI/2)),
                            (int)(160 - 15*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
                }

            }

            /*for ( arr = 0; arr < Number+1; arr++ ) {
                if (((Sprite)vBall[0].BVector.elementAt(arr)).getX() == 0 && ((Sprite)vBall[0].BVector.elementAt(arr)).getY() == 0 )
                    ((Sprite)vBall[0].BVector.elementAt(arr)).setVisible(false);
            }*/

//            System.out.println("State = " + State1 + State2 + State3 );
            //System.out.println("So Part " + Part);
            if (((Sprite)vBall[Part-1].BVector.elementAt(0)).getX() == 0 && ((Sprite)vBall[Part-1].BVector.elementAt(0)).getY() == 0 ) {
                State0 = true;
                State1 = false;
                State2 = false;
                State3 = false;
            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Làm hiệu ứng nổ bóng
            if ( Breaking ) {
                if ( BreakingTime % 2 == 0 ) {
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
            
            if ( (keyState & LEFT_PRESSED) != 0 ) {
                if ( iCount > 2 ) iCount -= 3;
                else iCount = 359;
                //angleCount++;
            } else if ( (keyState & RIGHT_PRESSED) != 0 ) {
                if ( iCount < 357 ) iCount += 3;
                else iCount = 0;
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

    public void start(){
        this.run = true;
        t = new Thread(this);
        t.start();
    }

    public void stop(){
        t = null;
    }

/////////////////////////////////////////////////////////////////////////////////////////
// Hàm move để dịch chuyển 1 viên bi
// PointSpr : Sprite của 1 pixel di chuyển dọc theo đường vẽ
// ball     : Sprite viên bi có tâm trùng với pixel trên
// z        : số thứ tự của viên bi
/////////////////////////////////////////////////////////////////////////////////////////
    public void move ( Sprite PointSpr, Sprite ball) {
        for ( i = 0; i < 4; i++) {
            PointSpr.setPosition( width + moveX[i],
                    height + moveY[i]);
            if ( PointSpr.collidesWith( L1_way, true) ) { // Nếu va chạm
                // Xét các hướng đặc biệt để pixel không bị quay về
                // Nếu điểm xét là điểm đã đi qua
                if ((width + moveX[i] == w)
                        && (height + moveY[i] == h)) {
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
        ranA = getRand( 0, length );
        return ( Number[ranA]);
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
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getX() != 1
                            //&& ((Sprite)vBall[z].BVector.elementAt(t)).getY() != 65
                            && ((Sprite)vBall[z].BVector.elementAt(t)).isVisible() == true
                            ) {
                        addColor = false;
                        break;
                    }
                    if ( !((Sprite)vBall[z].BVector.elementAt(t)).isVisible()) {
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


//////////////////////////////////////////////////////////////////////////////////
// Hàm hình thành mảng tọa độ của level
    public void saveLocation ( Sprite ball, int times ) {
        lv[times][0] = ball.getX();
        lv[times][1] = ball.getY();
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
                if ( !back )    back = true;
                else back = false;
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
                if ( !Shoot )   Sball.ShootAngle = iCount;
                Shoot = true;
                /*if ( !Stop  ){
                    //System.out.println("Stop");
                    Stop = true;
                    State0 = false;
                    State1 = false;
                    State2 = false;
                } else if ( Stop ) {
                    //System.out.println("Continue");
                    Stop = false;
                    State0 = false;
                    State1 = true;
                    State2 = false;
                }*/
                break;
        }
    }

}