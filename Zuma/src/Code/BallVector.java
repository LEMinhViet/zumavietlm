
package Code;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Admin
 */
public class BallVector {
    ZumaCanvas zumaCanvas;
    int ColliX, ColliY, x, y;
    int NumOfBall = 0;
    int i, j, ilevel, jlevel;
    int beginBreak, endBreak;
    int Distance = 0;
    // Điểm đầu cuối của vector
    int Begin, End;
    Sprite removeSprite;
    int[] Level = new int [500];
//    int[] Level = { 2, 2, 0, 0, 0, 0, 0, 0, 0, 1,
//                    1, 1, 0, 0, 0, 0, 0, 3, 3, 0,
//                    0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
//                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                    1, 1, 1, 1, 1, 1, 3, 3, 3, 3,
//                    1, 1, 1, 1, 1, 1, 3, 3, 3, 3,
//                    1, 1, 1, 1, 1, 1, 3, 3, 3, 3,
//                    1, 1, 1, 1, 1, 1, 3, 3, 3, 3,
//                    1, 1, 1, 1, 1, 1, 3, 3, 3, 3,
//                };
    Sprite Sball;
    Vector BVector = new Vector ( NumOfBall, 1 );
    int iColliTemp;

    public BallVector( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
    }

    public void removeSprite() {
        zumaCanvas.lm.remove(removeSprite);
        removeSprite = null;
    }

    public void initBallVector () {
//        for ( i = 0; i < zumaCanvas.Part; i++ ) {
//            for ( j = 0; j < zumaCanvas.vBall[i].BVector.size(); j++ ) {
//                 zumaCanvas.vBall[i].BVector.removeAllElements();
//            }
//        }
        if ( zumaCanvas.runningLevel == 1 || zumaCanvas.runningLevel == 3 ) createLv1();
        else if ( zumaCanvas.runningLevel == 2 ) createLv2();
        else if ( zumaCanvas.runningLevel == 4 || zumaCanvas.runningLevel == 5
                || zumaCanvas.runningLevel == 6 || zumaCanvas.runningLevel == 10 || zumaCanvas.runningLevel == 12 )   createLv4();
        else if ( zumaCanvas.runningLevel == 7 || zumaCanvas.runningLevel == 8 
                || zumaCanvas.runningLevel == 9 || zumaCanvas.runningLevel == 11 || zumaCanvas.runningLevel == 13 )   createLv7();
        
        if ( BVector != null ) {
            for ( j = 0; j < BVector.size(); j++ ) {
                removeSprite = ( Sprite )  zumaCanvas.vBall[i].BVector.elementAt(0);
                zumaCanvas.vBall[i].BVector.removeElementAt(0);
                removeSprite();
            }
            //BVector.removeAllElements();
            Runtime.getRuntime().gc();
            //BVector = null;
        }
        for ( j = 0; j < NumOfBall; j++) {
//            try {
            BVector.addElement( new Sprite (zumaCanvas.Sball.returnBall(), 16, 16));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            
//            ((Sprite)BVector.elementAt(j)).setFrameSequence(ballSeq);
            zumaCanvas.lm.insert((Sprite)BVector.elementAt(j), 0);
            ((Sprite)BVector.elementAt(j)).setPosition(-16, -16);
            ((Sprite)BVector.elementAt(j)).setFrame(Level[j]);
        }
       
    }

    public void addtoBallVector( int Num ) {
        for ( j = 0; j < Num; j++) {
//            try {
            BVector.addElement(new Sprite( zumaCanvas.Sball.returnBall(), 16, 16));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

//            ((Sprite)BVector.elementAt(j)).setFrameSequence(ballSeq);
            zumaCanvas.lm.insert((Sprite)BVector.elementAt(BVector.size() - 1), 0);
            ((Sprite)BVector.elementAt(BVector.size() - 1)).setPosition(-16, -16);
            ((Sprite)BVector.elementAt(BVector.size() - 1)).setFrame(Level[BVector.size() - 1]);
        }
    }

    public void InsertBall( int iColli, int partColli ) {
        Sball = zumaCanvas.Sball.Ball;
        // Insert vào đầu vector
        iColliTemp = iColli;
        if (iColli == 0 && zumaCanvas.headInsert && ( !zumaCanvas.Be_1ball 
                || ( BVector.size() == 1 && zumaCanvas.InsertTime == 0 && zumaCanvas.Be_1ball )
                || ( BVector.size() == 2 && zumaCanvas.InsertTime != 0 && zumaCanvas.Be_1ball )) && !zumaCanvas.Af_1ball) {
//            System.out.println("Head");
            if ( zumaCanvas.InsertTime == 0 ) {
                ColliX = Sball.getX();
                ColliY = Sball.getY();
//
//                try {
                    BVector.insertElementAt( new Sprite(zumaCanvas.Sball.returnBall(), 16, 16), 0);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }

//                ((Sprite)BVector.elementAt(0)).setFrameSequence(ballSeq);
                zumaCanvas.lm.insert((Sprite)BVector.elementAt(0), 0);
                ((Sprite)BVector.elementAt(0)).setFrame(zumaCanvas.Sball.Ball.getFrame());
            }

            x = zumaCanvas.lv[zumaCanvas.ite-16*Begin-zumaCanvas.sumOfInsert][0];
            y = zumaCanvas.lv[zumaCanvas.ite-16*Begin-zumaCanvas.sumOfInsert][1];
            ///////////////////////////////////////////////////////////////////////////////
            // Đoạn insert quả bóng vào trong đoàn bóng
            if ( zumaCanvas.InsertTime == 0 )
                ((Sprite)BVector.elementAt(0)).setPosition
                    (ColliX + ( x - ColliX)/2, ColliY + ( y - ColliY)/2);
            else if( zumaCanvas.InsertTime == 1)
                ((Sprite)BVector.elementAt(0)).setPosition
                    (ColliX + (x - ColliX)*2/3, ColliY + (y - ColliY)*2/3);
             else if ( zumaCanvas.InsertTime == 2 )
                ((Sprite)BVector.elementAt(0)).setPosition
                    (ColliX + (x - ColliX)*5/6, ColliY + (y - ColliY)*5/6);
             else if ( zumaCanvas.InsertTime == 3 ) {
                ((Sprite)BVector.elementAt(0)).setPosition
                    (ColliX + (x - ColliX)*9/10, ColliY + (y - ColliY)*9/10);
                ((Sprite)BVector.elementAt(0)).setPosition(x, y);                
            }
            zumaCanvas.InsertTime++;
        // Insert vào giữa
        } else {
//            System.out.println("End");
            if( iColli != 0 && Sball.collidesWith(((Sprite)BVector.elementAt(iColli - 1)), true)) {
                iColliTemp = iColli - 1;
            }
            // Insert vao duoi doan bong
//            System.out.println("Colli " + iColli + " " + End + " " + Begin );
            if (iColli == End - Begin - 1) {
//               System.out.println("EndOfOne");
               if ( zumaCanvas.InsertTime == 0 ) {
                    //zumaCanvas.vBall[partColli-1].Distance -= 16;
                    ColliX = Sball.getX();
                    ColliY = Sball.getY();

                    try {
                        BVector.insertElementAt(new Sprite(Image.createImage("/picture/bi.png"), 16, 16), End - Begin);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

//                    ((Sprite)BVector.elementAt(End - Begin)).setFrameSequence(ballSeq);
                    zumaCanvas.lm.insert((Sprite)BVector.elementAt(End - Begin), 0);
                    ((Sprite)BVector.elementAt(End - Begin)).setFrame(zumaCanvas.Sball.Ball.getFrame());
                }

                x = zumaCanvas.lv[zumaCanvas.ite-16*(End)-zumaCanvas.sumOfInsert][0];
                y = zumaCanvas.lv[zumaCanvas.ite-16*(End)-zumaCanvas.sumOfInsert][1];

                ///////////////////////////////////////////////////////////////////////////////
                // Đoạn insert quả bóng vào trong đoàn bóng
                if ( zumaCanvas.InsertTime == 0 )
                    ((Sprite)BVector.elementAt(End - Begin)).setPosition
                        (ColliX + ( x - ColliX)/2, ColliY + ( y - ColliY)/2);
                else if( zumaCanvas.InsertTime == 1)
                    ((Sprite)BVector.elementAt(End - Begin)).setPosition
                        (ColliX + (x - ColliX)*2/3, ColliY + (y - ColliY)*2/3);
                 else if ( zumaCanvas.InsertTime == 2 )
                    ((Sprite)BVector.elementAt(End - Begin)).setPosition
                        (ColliX + (x - ColliX)*5/6, ColliY + (y - ColliY)*5/6);
                 else if ( zumaCanvas.InsertTime == 3 ) {
                    ((Sprite)BVector.elementAt(End - Begin)).setPosition
                        (ColliX + (x - ColliX)*9/10, ColliY + (y - ColliY)*9/10);
                    ((Sprite)BVector.elementAt(End - Begin)).setPosition(x, y);
                    
                }
                zumaCanvas.InsertTime++;
            } else {
                if ( zumaCanvas.InsertTime == 0 ) {
                    // Toạ độ va chạm của bóng
                    ColliX = Sball.getX();
                    ColliY = Sball.getY();

                    try {
                        /*if ( zumaCanvas.Sball.ranColor == 0 )
                            BVector.insertElementAt(new Sprite(Image.createImage("/picture/bong_do.png"), 16, 16), iColliTemp + 1);
                        else if(zumaCanvas.Sball.ranColor == 1)
                            BVector.insertElementAt(new Sprite(Image.createImage("/picture/bong_xanh2.png"), 16, 16), iColliTemp + 1);
                    */
                        BVector.insertElementAt(new Sprite(Image.createImage("/picture/bi.png"), 16, 16), iColliTemp + 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//                    ((Sprite)BVector.elementAt(iColliTemp+1)).setFrameSequence(ballSeq);
                    zumaCanvas.lm.insert((Sprite)BVector.elementAt(iColliTemp +1), 0);
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setFrame(zumaCanvas.Sball.Ball.getFrame());
                }
                
                // Lưu lại vị trí hiện tại và vị trí trước của viên bi trước
                x = zumaCanvas.lv[zumaCanvas.ite -16*Begin- 16*(iColliTemp+1)-zumaCanvas.sumOfInsert][0];
                y = zumaCanvas.lv[zumaCanvas.ite -16*Begin- 16*(iColliTemp+1)-zumaCanvas.sumOfInsert][1];

                ///////////////////////////////////////////////////////////////////////////////
                // Đoạn insert quả bóng vào trong đoàn bóng
                if ( zumaCanvas.InsertTime == 0 )
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setPosition
                        (ColliX + ( x - ColliX)/2, ColliY + ( y - ColliY)/2);
                else if( zumaCanvas.InsertTime == 1)
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setPosition
                        (ColliX + (x - ColliX)*2/3, ColliY + (y - ColliY)*2/3);
                 else if ( zumaCanvas.InsertTime == 2 )
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setPosition
                        (ColliX + (x - ColliX)*5/6, ColliY + (y - ColliY)*5/6);
                 else if ( zumaCanvas.InsertTime == 3 ) {
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setPosition
                        (ColliX + (x - ColliX)*9/10, ColliY + (y - ColliY)*9/10);
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setPosition(x, y);
                    //Colli = false;
                    // Chuyển sang phần test bóng cùng màu
                    //testBreak = true;
                }
                zumaCanvas.InsertTime++;
                
            }
        }
    }

    public void testBreak ( int iColli ) {
        zumaCanvas.Breaked = false;
        zumaCanvas.notBreak = false;
        // Vòng lặp tìm các số cùng màu với bi bắn vào
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu điểm giao khác 0
        if ( iColli != 0 && iColli + 1 != End - Begin) {
            for ( j = iColli + 2 ; j <= End - Begin; j++ ) {
                if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli+1)).getFrame()) {
                    endBreak = j-1;
                    break;
                }
            }
            if ( j - 1 == End - Begin && ((Sprite)BVector.elementAt(j-1)).getFrame() == ((Sprite)BVector.elementAt(iColli+1)).getFrame())
                endBreak = End - Begin;
            for ( j = iColli; j >= 0; j-- ) {
                try {
                    if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli+1)).getFrame()) {
                        beginBreak = j+1;
                        break;
                    }
                } catch ( ArrayIndexOutOfBoundsException x ) {
//                    System.out.println(" BVect "+ BVector.size() + " j " + j + " iColli+1 " + (iColli+1) + " part " + zumaCanvas.partColli);
                }
            }
            if ( j + 1 == 0 && ((Sprite)BVector.elementAt(j+1)).getFrame() == ((Sprite)BVector.elementAt(iColli+1)).getFrame())
                beginBreak = 0;
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu điểm giao = 0
        } else if ( iColli == 0 && !zumaCanvas.colliAfter) {            
            beginBreak = 0;
            for ( j = iColli + 1 ; j <= End - Begin; j++ ) {
                if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(0)).getFrame()) {
                    endBreak = j-1;                    
                    break;
                }
            }            
            if ( j - 1 == End - Begin && ((Sprite)BVector.elementAt(j-1)).getFrame() == ((Sprite)BVector.elementAt(0)).getFrame())
                    endBreak = End - Begin;
        ///////////////////////////////////////////////////////////////////////////////////////////
        } else if ( iColli == 0 && zumaCanvas.colliAfter ) {
            if (((Sprite)BVector.elementAt(0)).getFrame() == ((Sprite)BVector.elementAt(1)).getFrame())
                beginBreak = 0;
            else beginBreak = 1;
            for ( j = 2 ; j <= End - Begin; j++ ) {
                if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(1)).getFrame()) {
                    endBreak = j-1;
                    break;
                }
            }
            if ( j - 1 == End - Begin && ((Sprite)BVector.elementAt(j-1)).getFrame() == ((Sprite)BVector.elementAt(0)).getFrame())
                    endBreak = End - Begin;
        } else if (iColli + 1 == End - Begin) {
            endBreak = End - Begin;
            for ( j = iColli; j >= 0; j-- ) {
                try {
                    if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli+1)).getFrame()) {
                        beginBreak = j+1;
                        break;
                    }
                } catch ( ArrayIndexOutOfBoundsException x ) {
//                    System.out.println(" BVect "+ BVector.size() + " j " + j + " iColli+1 " + iColli+1 );
                }
            }
            if ( j + 1 == 0 && ((Sprite)BVector.elementAt(j + 1)).getFrame() == ((Sprite)BVector.elementAt(iColli+1)).getFrame())
                    beginBreak = 0;
        }

//        System.out.println( " Test break " + endBreak + " " + beginBreak + " " + End + " " +Begin);
        if ( End < endBreak ) {
            endBreak = End;
        }
        if ( endBreak - beginBreak < 2 ) {
            zumaCanvas.notBreak = true;
            zumaCanvas.score.multiNum = 1;
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu số bóng cùng màu >= 2 => Xóa các viên bi cùng màu với bóng bắn vào
        } else if(endBreak - beginBreak >= 2) {
            zumaCanvas.score.addScore(beginBreak, endBreak);
//            System.out.println("Score " + zumaCanvas.score.Score + " " + zumaCanvas.score.multiNum);
            zumaCanvas.drawScore = true;
            zumaCanvas.score.drawScore();
            zumaCanvas.iColli = beginBreak - 1;
            zumaCanvas.isStateChange = true;
            zumaCanvas.Breaked = true;
            for ( j = beginBreak; j <= endBreak; j++ ) {

                zumaCanvas.breakBall[j-beginBreak].setPosition(((Sprite)BVector.elementAt(j)).getX()-4, ((Sprite)BVector.elementAt(j)).getY()-4);
                if ( ((Sprite)BVector.elementAt(j)).isVisible() == true )   
                    zumaCanvas.breakBall[j-beginBreak].setVisible(true);
                ((Sprite)BVector.elementAt(j)).setVisible(false);
                zumaCanvas.Breaking = true;
            }

            ////////////////////////////////////////////////////////////////////////////////////////
            // Nếu cần tách vector => tách
            //if ( beginBreak != 0 && (endBreak != End - Begin || (!zumaCanvas.OutOfBalls && endBreak == End - Begin && zumaCanvas.partColli == 0 ))) {
                //System.out.println("beginbreak " + beginBreak + " endbreak " + endBreak);
            //    zumaCanvas.Divide = true;
            /*} else if ( endBreak == End ) {
                zumaCanvas.Divide = true;*/
            //}
            /*else {
                zumaCanvas.testBreak = true;
                System.out.println("Break at first bV");
            }*/

            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu insert vào đoạn bóng đang ra 
            if ((endBreak+Begin)*16 >= zumaCanvas.ite - 16 && (endBreak+Begin)*16 <= zumaCanvas.ite ) {
//                System.out.println("1");
                if ( endBreak == End && beginBreak == Begin ) {
                    for ( j = beginBreak; j <=  endBreak; j++ ) {
                        removeSprite = (Sprite) BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.NumB -= 16;
                        if ( beginBreak == 0 )  zumaCanvas.ite -= 16;
                        End--;
                    }
                    zumaCanvas.State3 = false;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State1 = true;
                } else {
                    zumaCanvas.State3 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State1 = false;
                    zumaCanvas.Part ++;

                    for ( j = beginBreak; j <=  endBreak; j++ ) {
                        removeSprite = (Sprite) BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.NumB -= 16;
                        if ( beginBreak == 0 )  zumaCanvas.ite -= 16;
                        End--;
                    }
                    //////////////////////////////////////////
                    // Mới làm cho 2 đoàn bóng, phải làm nhiều đoàn bóng
                    //
                    if ( zumaCanvas.Part >= 3 ) {
                        for ( int i = 1; i < zumaCanvas.Part - 1; i++ ) {
                            zumaCanvas.vBall[i].copyBallVectorTo(zumaCanvas.vBall[i+1]);
                        }
                    }

                    zumaCanvas.vBall[0].End = beginBreak - 1;
                    if ( zumaCanvas.Part > 1 ) {
                        zumaCanvas.vBall[1] = new BallVector(zumaCanvas);
                        zumaCanvas.vBall[0].copyBallVectorTo(zumaCanvas.vBall[1]);
                        zumaCanvas.vBall[0].Begin = zumaCanvas.vBall[1].End + 1;
                    }
                    zumaCanvas.vBall[0].Distance = 16*(endBreak - beginBreak + 1);
                    zumaCanvas.vBall[0].End = zumaCanvas.Number;
                }
                for ( int i = 0; i < zumaCanvas.Part; i++ ) {
//                    System.out.println("Vector so " + i + " Begin " + zumaCanvas.vBall[i].Begin + " End " + zumaCanvas.vBall[i].End );
//                    System.out.println("Vector so " + i + " Size " + zumaCanvas.vBall[i].BVector.size() + " Distance " + zumaCanvas.vBall[i].Distance );
                }

            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu break cả đoạn bóng
            } else if (endBreak == BVector.size() - 1 && beginBreak == 0) {
//                System.out.println("2");
                //Xóa đoạn
                if ( zumaCanvas.Part == 2 ) {
                    if ( zumaCanvas.partColli == zumaCanvas.Part - 1 ) {
                        zumaCanvas.ite -= zumaCanvas.vBall[0].Distance;
                        zumaCanvas.vBall[0].Distance = 0;
                    }
                } else if (  zumaCanvas.Part > 2 ) {
                    zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += zumaCanvas.vBall[zumaCanvas.partColli].Distance;
                    if ( zumaCanvas.partColli == zumaCanvas.Part - 1 ) {
                        zumaCanvas.ite -= zumaCanvas.vBall[zumaCanvas.partColli-1].Distance;
                        zumaCanvas.vBall[zumaCanvas.partColli-1].Distance = 0;
                    }
                }


                for ( j = beginBreak; j <= endBreak; j++ ) {
                    removeSprite = (Sprite)BVector.elementAt(beginBreak);
                    BVector.removeElementAt(beginBreak);
                    removeSprite();
                    zumaCanvas.NumB -= 16;
                    if ( zumaCanvas.partColli == zumaCanvas.Part - 1 ) zumaCanvas.ite -= 16;
                    if ( zumaCanvas.partColli >= 1 )
                        zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += 16;
                    for ( int e = 0; e < zumaCanvas.partColli; e++ ) {
                        zumaCanvas.vBall[e].Begin --;
                        zumaCanvas.vBall[e].End --;
                    }
                }
                
//                if ( zumaCanvas.partColli >= 1 )
//                        zumaCanvas.vBall[zumaCanvas.partColli-1].Distance -= 16;
                // Lùi các đoạn trước lại
                if ( zumaCanvas.partColli < zumaCanvas.Part ) {
                    for ( j = zumaCanvas.partColli+1; j <= zumaCanvas.Part-1; j++ ) {
                        zumaCanvas.vBall[j].copyBallVectorTo(zumaCanvas.vBall[j-1]);
                    }
                }
                zumaCanvas.Part--;
                if ( zumaCanvas.Part-1 >= 0 )   zumaCanvas.vBall[zumaCanvas.Part-1].Distance = 0;
                
                for ( int i = 0; i < zumaCanvas.Part; i++ ) {
//                    System.out.println("Here");
//                    System.out.println("Vector so " + i + " Begin " + zumaCanvas.vBall[i].Begin + " End " + zumaCanvas.vBall[i].End );
//                    System.out.println("Vector so " + i + " Size " + zumaCanvas.vBall[i].BVector.size() + " Distance " + zumaCanvas.vBall[i].Distance );
                }
                if ( zumaCanvas.Part > 1 && zumaCanvas.partColli >= 1) {
                    if ( zumaCanvas.partColli >= 1 && zumaCanvas.partColli <= zumaCanvas.Part - 1
                            &&((Sprite)zumaCanvas.vBall[zumaCanvas.partColli-1].BVector.elementAt(0)).getFrame()
                            == ((Sprite)zumaCanvas.vBall[zumaCanvas.partColli].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli].BVector.size()-1)).getFrame()) {
                        zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColli+1].End - zumaCanvas.vBall[zumaCanvas.partColli+1].Begin;
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli - 1;
//                        System.out.println("ColliBack " + zumaCanvas.partColliBack);
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;

                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;

                    }
                } else if ( zumaCanvas.Part == 1 ) {
//                    if ( zumaCanvas.partColli == 0 )
//                        zumaCanvas.ite -= 16*(endBreak - beginBreak + 1);
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
//                    System.out.println("back to state1");
                    
                } else if ( zumaCanvas.Part == 0 ) {
//                    System.out.println("End Of level");
//                    zumaCanvas.nextLevel();
                    zumaCanvas.State0 = false;
                    zumaCanvas.State1 = false;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    zumaCanvas.State5 = true;
                    
                }
                    
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu đoạn bóng mất đi ở cuối đoàn bóng
            } else if (endBreak == BVector.size() - 1) {
//                System.out.println("3");
                if (zumaCanvas.partColli >= 1) {
                    if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli-1].BVector.elementAt(0)).getFrame() == ((Sprite)BVector.elementAt(beginBreak-1)).getFrame()) {
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli;
                        if ( !zumaCanvas.Divide && zumaCanvas.partColliBack > 0) {
                            zumaCanvas.partColliBack--;
                        }
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;
                        
                        
                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;
                    }
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        removeSprite = (Sprite) BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.NumB-=16;
                        End--;
                        if ( zumaCanvas.partColli > 0 )   {
                            for ( int t = 0; t < zumaCanvas.partColli; t++ ) {
                                zumaCanvas.vBall[t].Begin--;
                                zumaCanvas.vBall[t].End--;
                            }
                        }
                        //if ( zumaCanvas.partColli != 0 )   zumaCanvas.vBall[zumaCanvas.partColli-1].Begin--;
                    }
                    zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += 16*(endBreak-beginBreak+1);
//                    for ( j = 0; j < zumaCanvas.partColli; j++ ) {
//                        zumaCanvas.vBall[j].End -= endBreak-beginBreak+1;
//                        zumaCanvas.vBall[j].Begin -= endBreak-beginBreak+1;
//                    }
                    if ( zumaCanvas.State2 && zumaCanvas.partColliBack < zumaCanvas.Part - 1)
                        zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColliBack+1].End - zumaCanvas.vBall[zumaCanvas.partColliBack+1].Begin;
                } else if (zumaCanvas.Part == 1) {
//                    System.out.println("3.2");
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    for ( int t = beginBreak; t <= endBreak; t++ ) {
                        removeSprite = (Sprite) zumaCanvas.vBall[zumaCanvas.partColli].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli].beginBreak);
                        zumaCanvas.vBall[zumaCanvas.partColli].BVector.removeElementAt(zumaCanvas.vBall[zumaCanvas.partColli].beginBreak);
                        removeSprite();
                        //zumaCanvas.ite -= 16;
                        zumaCanvas.NumB -= 16;
                    }
                } else if ( zumaCanvas.partColli == 0 ) {
                    /*if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.size()-1))
                            .getFrame() == ((Sprite)BVector.elementAt(0)).getFrame()) {
                        System.out.println("3.3");
                        zumaCanvas.State1 = true;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = false;
                    } else {
                        System.out.println("3.4");
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;
                    }*/
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        removeSprite = (Sprite)BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.NumB-=16;
                        End--;
                    }
                }
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu ở đầu đoàn bóng
            } else if ( beginBreak == 0 ) {
//                System.out.println("4");
                if ( zumaCanvas.Part == 1) {
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    for ( int t = beginBreak; t <= endBreak; t++ ) {
                        try {
                            removeSprite = (Sprite) zumaCanvas.vBall[0].BVector.elementAt(0);
                            zumaCanvas.vBall[0].BVector.removeElementAt(0);
                            removeSprite();
                        } catch ( ArrayIndexOutOfBoundsException n ) {
//                            System.out.println("Bvector " + zumaCanvas.partColliBack + " " + zumaCanvas.vBall[zumaCanvas.partColliBack].BVector.size() +
//                                 " " + zumaCanvas.vBall[zumaCanvas.partColliBack].beginBreak  );
                        }
                        zumaCanvas.ite -= 16;
                        zumaCanvas.NumB -= 16;
                        End--;
                    }
                } else if ( zumaCanvas.partColli < zumaCanvas.Part - 1 ) {
                    if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.size() - 1)).getFrame()
                        == ((Sprite)BVector.elementAt(endBreak+1)).getFrame()) {
                        //zumaCanvas.backCount = 16*( endBreak - beginBreak + 1);
                        if ( zumaCanvas.partColli < zumaCanvas.Part ) zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColli+1].End - zumaCanvas.vBall[zumaCanvas.partColli+1].Begin;
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli;
//                        System.out.println("ColliBack " + zumaCanvas.partColliBack);
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;

                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;
                    }
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        removeSprite = (Sprite)BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.NumB -= 16;
                        End--;
                        if ( zumaCanvas.partColli > 0 )   {
                            for ( int t = 0; t < zumaCanvas.partColli; t++ ) {
                                zumaCanvas.vBall[t].Begin--;
                                zumaCanvas.vBall[t].End--;
                            }
                        }
                    }
                    Distance += 16*(endBreak-beginBreak+1);
                    if ( zumaCanvas.State2 && zumaCanvas.partColliBack < zumaCanvas.Part - 1 )
                        zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColliBack + 1].End - zumaCanvas.vBall[zumaCanvas.partColliBack + 1].Begin;
                }  else if ( zumaCanvas.partColli == zumaCanvas.Part - 1 ) {
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        removeSprite = (Sprite)BVector.elementAt(beginBreak);
                        BVector.removeElementAt(beginBreak);
                        removeSprite();
                        zumaCanvas.ite -= 16;
                        zumaCanvas.NumB -= 16;
                        End--;
                        if ( zumaCanvas.partColli > 0 )   {
                            for ( int t = 0; t < zumaCanvas.partColli; t++ ) {
                                zumaCanvas.vBall[t].Begin--;
                                zumaCanvas.vBall[t].End--;
                            }
                        }
                    }
                }
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu ở giữa
            } else {
                zumaCanvas.Divide = true;
                if (((Sprite)BVector.elementAt(endBreak+1)).getFrame() == ((Sprite)BVector.elementAt(beginBreak-1)).getFrame()) {
                    //zumaCanvas.backCount = 16*( endBreak - beginBreak + 1);
                    //zumaCanvas.iColli = endBreak - beginBreak + 1;
                    //if ( !zumaCanvas.State2 )
                    zumaCanvas.partColliBack = zumaCanvas.partColli;
                    zumaCanvas.State1 = false;
                    zumaCanvas.State2 = true;
                    zumaCanvas.State3 = false;

                } else {
                    zumaCanvas.State1 = false;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = true;

                }
            }
        }
        //zumaCanvas.Sball.resetBall();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
// Copy vector từ vector này sang vector ballVector
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void copyBallVectorTo( BallVector ballVector ) {
        for ( j = 0; j <= this.End - this.Begin; j++ ) {
            ballVector.BVector.addElement(this.BVector.elementAt(0));
            BVector.removeElementAt(0);
        }
        //BVector.removeAllElements();
        ballVector.Begin = this.Begin;
        ballVector.End = this.End;
        ballVector.Distance = this.Distance;
        
    }

int ranlv1, duplv1, iX;
/////////////////////////////////////////////////////////////////////////////////////////////////
// Hàm tạo đoàn bóng cho lv1 và lv3
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void createLv1 () {
        for ( iX = 0; iX < 500; ++iX )
            Level[iX] = 0;
        for ( iX = 0; iX < 500; ++iX ) {
            ranlv1 = zumaCanvas.getRand(0, 4);
            duplv1 = zumaCanvas.getRand(0, 4);
            for ( jlevel = 0; jlevel < duplv1; ++jlevel ) {
                if ( iX + jlevel < 500 )  Level[iX+jlevel] = ranlv1;
            }
            if ( duplv1 != 0 )  iX += duplv1 - 1;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // Hàm tạo đoàn bóng cho lv2
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void createLv2 () {
        for ( iX = 0; iX < 500; ++iX )
            Level[iX] = 0;
        for ( iX = 0; iX < 500; ++iX ) {
            ranlv1 = zumaCanvas.getRand(0, 4);
            duplv1 = zumaCanvas.getRand(0, 3);
            for ( jlevel = 0; jlevel < duplv1; ++jlevel ) {
                if ( iX + jlevel < 500 )  Level[iX+jlevel] = ranlv1;
            }
            if ( duplv1 != 0 )  iX += duplv1 - 1;
        }
    }

    public void createLv4 () {
        for ( iX = 0; iX < 500; ++iX )
            Level[iX] = 0;
        for ( iX = 0; iX < 500; ++iX ) {
            ranlv1 = zumaCanvas.getRand(0, 4);
            duplv1 = zumaCanvas.getRand(0, 2);
            for ( jlevel = 0; jlevel < duplv1; ++jlevel ) {
                if ( iX + jlevel < 500 )  Level[iX+jlevel] = ranlv1;
            }
            if ( duplv1 != 0 )  iX += duplv1 - 1;
        }
    }

    public void createLv7 () {
        ranlv1 = 1;
        for ( iX = 0; iX < 500; ++iX )
            Level[iX] = -1;
        for ( iX = 0; iX < 500; ++iX ) {
            if ( ranlv1 == 0 ) ranlv1 = zumaCanvas.getRand(1, 4);
            else  {
                ranlv1 = zumaCanvas.getRand(0, 4);
                if ( ranlv1 != 0 )  ranlv1 = zumaCanvas.getRand(0, 4);
            }
            

            if ( ranlv1 == 0 ) duplv1 = 1;
            else duplv1 = zumaCanvas.getRand(1, 3);
            for ( jlevel = 0; jlevel < duplv1; ++jlevel ) {
                if ( iX + jlevel < 500 )  Level[iX+jlevel] = ranlv1;
            }
            if ( duplv1 != 0 )  iX += duplv1 - 1;
            //System.out.println("+++" + Level[iX] + "++" + iX + "+" + duplv1 + " " + ranlv1);
        }
//        for ( iX = 0; iX < 500; ++iX ) {
//            System.out.println("+++" + Level[iX]);
//        }
    }
}
