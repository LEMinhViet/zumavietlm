/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    int j;
    int beginBreak, endBreak;
    int Distance = 0;
    // Điểm đầu cuối của vector
    int Begin, End;
    //int[] ballSeq = { 0, 1, 2, 3, 4, 5, 6, 7 };
    int[] ballSeq = { 0, 1, 2, 3 };
    int[] Level = new int [100];
//    int[] Level = { 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1,
//                    //2, 2, 2, 2, 2, //2, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0, 0, 0, 0, //0, 0,
//                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                    1, 1, 1, 1, 1, 1, 1//, 2, 2,
////                    1, 1, 1, 0, 0, 0, 0, 0, 0, 0, //1, 1, 1, 1, 1, 1, 1,
////                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
////                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
////                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
////                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                } ;
    Sprite Sball;
    Vector BVector = new Vector ( NumOfBall, 1 );
    int iColliTemp;

    public BallVector( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
    }

    public void initBallVector () {
        createLv1();
        for ( j = 0; j < NumOfBall; j++) {

            try {
                /*if ( Level[j] == 0 )
                    BVector.addElement(new Sprite(Image.createImage("/picture/bong_do.png"), 16, 16));
                else if(Level[j] == 1)
                    BVector.addElement(new Sprite(Image.createImage("/picture/bong_xanh2.png"), 16, 16));*/
                BVector.addElement(new Sprite(Image.createImage("/picture/bi.png"), 16, 16));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ((Sprite)BVector.elementAt(j)).setFrameSequence(ballSeq);
            zumaCanvas.lm.append((Sprite)BVector.elementAt(j));
            ((Sprite)BVector.elementAt(j)).setPosition(1 - 8 - 16, 65 - 8);
            ((Sprite)BVector.elementAt(j)).setFrame(Level[j]);
        }
    }

    public void InsertBall( int iColli, int partColli ) {
        Sball = zumaCanvas.Sball.Ball;
        NumOfBall++;
        // Insert vào đầu vector
        iColliTemp = iColli;
        System.out.println("a");
        if ( iColli == 0 && zumaCanvas.headInsert ) {
            System.out.println("1");
            if ( zumaCanvas.InsertTime == 0 ) {
                ColliX = Sball.getX();
                ColliY = Sball.getY();

                try {
                    BVector.insertElementAt(new Sprite(Image.createImage("/picture/bi.png"), 16, 16), 0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                ((Sprite)BVector.elementAt(0)).setFrameSequence(ballSeq);
                zumaCanvas.lm.insert((Sprite)BVector.elementAt(0), 0);
                ((Sprite)BVector.elementAt(0)).setFrame(zumaCanvas.Sball.Ball.getFrame());
            }

            //System.out.println("InserT : Begin " + Begin + " SumOfInsert " + zumaCanvas.sumOfInsert + " " + Distance );
            System.out.println("SumOfInsert " + zumaCanvas.sumOfInsert);
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
            System.out.println("Colli " + iColli+ " Size "+ BVector.size());
            if( iColli != 0 && Sball.collidesWith(((Sprite)BVector.elementAt(iColli - 1)), true)) {
                iColliTemp = iColli - 1;
            }
            //System.out.println( iColli + " " + zumaCanvas.Number );
            if ( iColli == End - Begin ) {
               // System.out.println("Add cuối");
                if ( zumaCanvas.InsertTime == 0 ) {
                    ColliX = Sball.getX();
                    ColliY = Sball.getY();

                    try {
                        BVector.insertElementAt(new Sprite(Image.createImage("/picture/bi.png"), 16, 16), End - Begin + 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    ((Sprite)BVector.elementAt(End - Begin + 1)).setFrameSequence(ballSeq);
                    zumaCanvas.lm.insert((Sprite)BVector.elementAt(End - Begin + 1), 0);
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setFrame(zumaCanvas.Sball.Ball.getFrame());
                }

                x = zumaCanvas.lv[zumaCanvas.ite-16*(End + 1)-zumaCanvas.sumOfInsert][0];
                y = zumaCanvas.lv[zumaCanvas.ite-16*(End + 1)-zumaCanvas.sumOfInsert][1];

                ///////////////////////////////////////////////////////////////////////////////
                // Đoạn insert quả bóng vào trong đoàn bóng
                if ( zumaCanvas.InsertTime == 0 )
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setPosition
                        (ColliX + ( x - ColliX)/2, ColliY + ( y - ColliY)/2);
                else if( zumaCanvas.InsertTime == 1)
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setPosition
                        (ColliX + (x - ColliX)*2/3, ColliY + (y - ColliY)*2/3);
                 else if ( zumaCanvas.InsertTime == 2 )
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setPosition
                        (ColliX + (x - ColliX)*5/6, ColliY + (y - ColliY)*5/6);
                 else if ( zumaCanvas.InsertTime == 3 ) {
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setPosition
                        (ColliX + (x - ColliX)*9/10, ColliY + (y - ColliY)*9/10);
                    ((Sprite)BVector.elementAt(End - Begin + 1)).setPosition(x, y);
                    
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
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setFrameSequence(ballSeq);
                    zumaCanvas.lm.insert((Sprite)BVector.elementAt(iColliTemp +1), 0);
                    ((Sprite)BVector.elementAt(iColliTemp+1)).setFrame(zumaCanvas.Sball.Ball.getFrame());
                }
                
                // Lưu lại vị trí hiện tại và vị trí trước của viên bi trước
                x = zumaCanvas.lv[zumaCanvas.ite -16*Begin- 16*(iColliTemp+1)-zumaCanvas.sumOfInsert][0];
                y = zumaCanvas.lv[zumaCanvas.ite -16*Begin- 16*(iColliTemp+1)-zumaCanvas.sumOfInsert][1];

                ///////////////////////////////////////////////////////////////////////////////
                // Đoạn insert quả bóng vào trong đoàn bóng
                //System.out.println("iColliTemp " + iColliTemp+1);
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
                //System.out.println(zumaCanvas.InsertTime);
            }
        }
    }

    public void testBreak ( int iColli ) {
        zumaCanvas.Breaked = false;
        // Vòng lặp tìm các số cùng màu với bi bắn vào
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu điểm giao khác 0
        if ( iColli != 0 ) {
            for ( j = iColli + 2 ; j <= End - Begin; j++ ) {
                if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli+1)).getFrame()) {
                    endBreak = j-1;
                    //System.out.println( endBreak + " end " );
                    break;
                }
                if ( j == End - Begin )   endBreak = End - Begin;
            }
            System.out.println("iColli " + iColli );
            for ( j = iColli; j >= 0; j-- ) {
                try {
                    if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli+1)).getFrame()) {
                        beginBreak = j+1;
                        //System.out.println("begin " + beginBreak );
                        break;
                    }
                } catch ( ArrayIndexOutOfBoundsException x ) {
                    System.out.println(" BVect "+ BVector.size() + " j " + j + " iColli+1 " + iColli+1 );
                }
                if ( j == 0 )   beginBreak = 0;
            }
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu điểm giao = 0
        } else if ( iColli == 0 ) {
            beginBreak = 0;
            for ( j = iColli + 1 ; j <= End - Begin; j++ ) {
                if (((Sprite)BVector.elementAt(j)).getFrame() != ((Sprite)BVector.elementAt(iColli)).getFrame()) {
                    endBreak = j-1;
                    //System.out.println( endBreak + " end " );
                    break;
                }
                if ( j == End - Begin)   endBreak = End - Begin;
            }
        }
        if ( iColli + 1 == End - Begin) endBreak = End - Begin;

        //System.out.println("Number " + zumaCanvas.Number + " iColli " + iColli);
        System.out.println( " Test break " + endBreak + " " + beginBreak + " " + End + " " +Begin);
        if ( End < endBreak ) {
            endBreak = End;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Nếu số bóng cùng màu >= 2 => Xóa các viên bi cùng màu với bóng bắn vào
        if ( endBreak - beginBreak >= 2 ) {
            zumaCanvas.Breaked = true;
            for ( j = beginBreak; j <= endBreak; j++ ) {

                zumaCanvas.breakBall[j-beginBreak].setPosition(((Sprite)BVector.elementAt(j)).getX()-4, ((Sprite)BVector.elementAt(j)).getY()-4);
                zumaCanvas.breakBall[j-beginBreak].setVisible(true);
                ((Sprite)BVector.elementAt(j)).setVisible(false);
                zumaCanvas.Breaking = true;
            }

            ////////////////////////////////////////////////////////////////////////////////////////
            // Nếu cần tách vector => tách
            if ( beginBreak != 0 && (endBreak != End - Begin || (!zumaCanvas.OutOfBalls && endBreak == End - Begin && zumaCanvas.partColli == 0 ))) {
                //System.out.println("beginbreak " + beginBreak + " endbreak " + endBreak);
                zumaCanvas.Divide = true;
            /*} else if ( endBreak == End ) {
                zumaCanvas.Divide = true;*/
            } /*else {
                zumaCanvas.testBreak = true;
                System.out.println("Break at first bV");
            }*/

            System.out.println("endBreak " + endBreak + " beginBreadk " + beginBreak + " size " + BVector.size());
            System.out.println("ite " + zumaCanvas.ite);
            System.out.println("end+be " + (endBreak+Begin)*16);

            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu insert vào đoạn bóng đang ra 
            if ((endBreak+Begin)*16 >= zumaCanvas.ite - 16 && (endBreak+Begin)*16 <= zumaCanvas.ite ) {
                System.out.println("IN");
                zumaCanvas.State3 = true;
                zumaCanvas.State2 = false;
                zumaCanvas.State1 = false;
                for ( j = beginBreak; j <  endBreak; j++ ) {
                    BVector.removeElementAt(beginBreak);
                    zumaCanvas.NumB -= 16;
                    if ( beginBreak == 0 )  zumaCanvas.ite -= 16;
                    End--;
                }

            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu break cả đoạn bóng
            } else if (endBreak == BVector.size() - 1 && beginBreak == 0) {
                
                System.out.println("Break All");
                //System.out.println("1");
                //Xóa đoạn
                zumaCanvas.Part--;
                for ( j = beginBreak; j <= endBreak; j++ ) {
                    BVector.removeElementAt(beginBreak);
                    zumaCanvas.NumB -= 16;
                    if ( zumaCanvas.partColli >= 1 )
                        zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += 16;
                    for ( int e = 0; e < zumaCanvas.partColli; e++ ) {
                        zumaCanvas.vBall[e].Begin --;
                        zumaCanvas.vBall[e].End --;
                    }
                }
                if ( zumaCanvas.Part == 1 ) {
                    zumaCanvas.ite -= zumaCanvas.vBall[0].Distance;
                    zumaCanvas.vBall[0].Distance = 0;
                } else if (  zumaCanvas.Part > 1 )
                    zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += zumaCanvas.vBall[zumaCanvas.partColli].Distance;
                // Lùi các đoạn trước lại
                if ( zumaCanvas.partColli < zumaCanvas.Part ) {
                    for ( j = zumaCanvas.Part-1; j >= zumaCanvas.partColli+1; j-- ) {
                        zumaCanvas.vBall[j-1].copyBallVector(zumaCanvas.vBall[j]);
                    }
                }

                System.out.println("So Part = " + zumaCanvas.Part);
                for ( int i = 0; i < zumaCanvas.Part; i++ ) {
                    System.out.println("Vector so " + i + " Begin " + zumaCanvas.vBall[i].Begin + " End " + zumaCanvas.vBall[i].End );
                    System.out.println("Vector so " + i + " Size " + zumaCanvas.vBall[i].BVector.size() + " Distance " + zumaCanvas.vBall[i].Distance );
                }

                if ( zumaCanvas.Part > 1 && zumaCanvas.partColli >= 1) {
                    if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli-1].BVector.elementAt(0)).getFrame()
                            == ((Sprite)zumaCanvas.vBall[zumaCanvas.partColli].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli].BVector.size()-1)).getFrame()) {
                        zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColli-1].End;
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli;
                        System.out.println("ColliBack " + zumaCanvas.partColliBack);
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;

                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;

                    }
                } else if ( zumaCanvas.Part == 1 ) {
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    System.out.println("back to state1");

                } else if ( zumaCanvas.Part == 0 ) {
                    System.out.println("End Of level");
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                }
                    
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu đoạn bóng mất đi ở cuối đoàn bóng
            } else if (endBreak == BVector.size() - 1) {
                //System.out.println("break at last");
                //System.out.println("ColliBack1 " + zumaCanvas.partColli);

                if ( zumaCanvas.Part == 1 && !zumaCanvas.OutOfBalls 
                        && ((Sprite)BVector.elementAt(BVector.size()-1)).getX() != zumaCanvas.lv[1][0] 
                        && ((Sprite)BVector.elementAt(BVector.size()-1)).getY() != zumaCanvas.lv[1][1]) {
                    zumaCanvas.Part ++;
                    zumaCanvas.vBall[0].copyBallVector(zumaCanvas.vBall[1]);

                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    zumaCanvas.vBall[0].Begin = zumaCanvas.vBall[1].End + 1;
                    zumaCanvas.vBall[0].End = zumaCanvas.Number;
                    /////////////////////////
                    
                    zumaCanvas.State1 = false;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = true;
                } else  if (zumaCanvas.partColli >= 1) {
                    if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli-1].BVector.elementAt(0)).getFrame() == ((Sprite)BVector.elementAt(beginBreak-1)).getFrame()) {
                        zumaCanvas.iColli = End;
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli;
                        System.out.println("ColliBack " + zumaCanvas.partColliBack);
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;
                        if ( !zumaCanvas.Divide )   zumaCanvas.partColliBack--;
                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;
                    }
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        BVector.removeElementAt(beginBreak);
                        zumaCanvas.NumB-=16;
                        End--;
                        if ( zumaCanvas.partColli != 0 )   zumaCanvas.vBall[zumaCanvas.partColli-1].Begin--;
                    }

                    zumaCanvas.vBall[zumaCanvas.partColli-1].Distance += 16*(endBreak-beginBreak+1);
                } else if ( zumaCanvas.Part == 1 ) {
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    for ( int t = beginBreak; t <= endBreak; t++ ) {
                        zumaCanvas.vBall[zumaCanvas.partColli].BVector.removeElementAt(zumaCanvas.vBall[zumaCanvas.partColli].beginBreak);
                        //zumaCanvas.ite -= 16;
                        zumaCanvas.NumB -= 16;
                    }
                }
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu ở đầu đoàn bóng
            } else if ( beginBreak == 0 ) {
                System.out.println("0");
                //System.out.println("ColliBack2 " + zumaCanvas.partColli);
                if ( zumaCanvas.partColli < zumaCanvas.Part - 1 ) {
                    System.out.println("1");
                    //System.out.println("size "  +(zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.size() - 1));
                    if (((Sprite)zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.elementAt(zumaCanvas.vBall[zumaCanvas.partColli+1].BVector.size() - 1)).getFrame()
                        == ((Sprite)BVector.elementAt(endBreak+1)).getFrame()) {
                        
                        //zumaCanvas.backCount = 16*( endBreak - beginBreak + 1);
                        zumaCanvas.iColli = zumaCanvas.vBall[zumaCanvas.partColli+1].End;
                        if ( !zumaCanvas.State2 )
                            zumaCanvas.partColliBack = zumaCanvas.partColli;
                        System.out.println("ColliBack " + zumaCanvas.partColliBack);
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = true;
                        zumaCanvas.State3 = false;

                    } else {
                        zumaCanvas.State1 = false;
                        zumaCanvas.State2 = false;
                        zumaCanvas.State3 = true;
                    }
                    for ( j = beginBreak; j <= endBreak; j++ ) {
                        BVector.removeElementAt(beginBreak);
                        zumaCanvas.NumB-=16;
                        
                        End--;
                        if ( zumaCanvas.partColli > 0 )   zumaCanvas.vBall[zumaCanvas.partColli-1].Begin--;
                    }
                    
                    Distance += 16*(endBreak-beginBreak+1);
                } else if ( zumaCanvas.Part == 1) {
                    System.out.println("2");
                    zumaCanvas.State1 = true;
                    zumaCanvas.State2 = false;
                    zumaCanvas.State3 = false;
                    for ( int t = beginBreak; t <= endBreak; t++ ) {
                        try {
                            zumaCanvas.vBall[0].BVector.removeElementAt(0);
                        } catch ( ArrayIndexOutOfBoundsException n ) {
                            System.out.println("Bvector " + zumaCanvas.partColliBack + " " + zumaCanvas.vBall[zumaCanvas.partColliBack].BVector.size() +
                                 " " + zumaCanvas.vBall[zumaCanvas.partColliBack].beginBreak  );
                        }
                        zumaCanvas.ite -= 16;
                        zumaCanvas.NumB -= 16;
                        End--;
                    }
                }
            /////////////////////////////////////////////////////////////////////////////////////////////////
            // Nếu ở giữa
            } else {
                if (((Sprite)BVector.elementAt(endBreak+1)).getFrame() == ((Sprite)BVector.elementAt(beginBreak-1)).getFrame()) {
                    //zumaCanvas.backCount = 16*( endBreak - beginBreak + 1);
                    zumaCanvas.iColli = beginBreak - 1;
                    if ( !zumaCanvas.State2 )
                        zumaCanvas.partColliBack = zumaCanvas.partColli;
                    System.out.println("ColliBack " + zumaCanvas.partColliBack);
                    //System.out.println("zuma partColli " + zumaCanvas.partColli + " partColii Back " + zumaCanvas.partColliBack);
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
        zumaCanvas.Sball.resetBall();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
// Copy vector từ vector này sang vector ballVector
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void copyBallVector( BallVector ballVector ) {
        for ( j = 0; j < this.BVector.size(); j++ ) {
            ballVector.BVector.addElement(this.BVector.elementAt(j));
        }
        BVector.removeAllElements();
        ballVector.Begin = this.Begin;
        ballVector.End = this.End;
        ballVector.NumOfBall = this.NumOfBall;
        ballVector.Distance = this.Distance;
        
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
// Hàm tạo đoàn bóng cho lv1 và lv3
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void createLv1 () {
        int ranlv1, duplv1;
        for ( int i = 0; i < 100; ++i ) {
            ranlv1 = zumaCanvas.getRand( 0, 3);
            duplv1 = zumaCanvas.getRand(0, 5);
            for ( int j = 0; j < duplv1; ++j ) {
                if ( i + j < 100 )  Level[i+j] = ranlv1;
            }
            if ( duplv1 != 0 )  i += duplv1 - 1;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // Hàm tạo đoàn bóng cho lv1 và lv3
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void createLv2 () {
        
    }
}
