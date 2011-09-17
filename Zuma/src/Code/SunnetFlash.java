package Code;


import Code.StartMidlet;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ThuongBinh
 */
public class SunnetFlash extends Canvas implements Runnable {

    private Timer timer1;
    private TimerTask timerTask;
    Font fontMedium;
    Font fontLarge;
    String s = "S", un = "UN", n = "N", et = "ET";
    int widthText, i = 1, delta = 1, delta1 = 1,t=0;
    int[] color1, color2, color3;
    int j = 1,rd = 0;
    private int style = 1;
    public static int DELAY_DEFAULT = 100;
    public StartMidlet StartMidlet;

    int[][] qp = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
            };
public boolean isDisplay = true;
    public SunnetFlash(StartMidlet StartMidlet) {
        timer1 = new Timer();
        fontMedium = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        fontLarge = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        widthText = fontLarge.stringWidth(s+un+n+et);
        this.setFullScreenMode(true);
        color1 = new int[]{0, 0, 0};
        color2 = new int[]{0, 0, 0};
        color3 = new int[]{0, 0, 0};
        this.StartMidlet = StartMidlet;
        timerTask = new TimerTask() {

            public void run() {
                if (isDisplay) {
                    if (true) {
                        if (i > 0) {
                            if ((i <= 11) && (delta > 0)) {
                                color2 = new int[]{i * 20 + 30, i * 15, 0};
                                color3 = new int[]{i * 20 + 30, i * 20 + 30, i * 20 + 30};
                               if(i<11){
                                i++;
                               }
                                if (i == 11) {
                                    if(t<10){
                                        t++;//i--;
                                    }else{
                                    delta = -1;
                                    }
                                }
                            } else if ((i > 0) && (delta < 0)) {
                                i--;
                                color2 = new int[]{i * 20 + 30,i * 15, 0};
                                color3 = new int[]{i * 20 + 30, i * 20 +30, i * 20 + 30};

                            }
                        } else {
                            if(j>0){
                            if ((j < 10) && (delta1 > 0)) {
                                color2 = new int[]{j * 25 + 30, i * 25 , 0};
                                color3 = new int[]{j * 25 + 30, j * 25+30, j * 25 + 30};
                                j++;
                                if (j == 10) {
                                    delta1 = -1;
                                }
                            } else if ((j > 0) && (delta1 < 0)) {
                                j--;
                                color2 = new int[]{j * 25 + 30,i * 25, 0};
                                color3 = new int[]{j * 25 + 30, j * 25 + 30, j * 25 + 30};

                            }
                        }else{
                          
                        }
                      
                        }
                        repaint(0, 0, getWidth(), getHeight());
                    }
                }
            }
        };
        timer1.schedule(timerTask, 0, 100);
        rd = new Random().nextInt(3);
        //rd = 2;
       //System.out.println(rd);
    }

    protected void paint(Graphics g) {
        if (i >0) {
            drawLogoQplay(g, getWidth() / 2 - 20, getHeight() / 2 - 30, color1, color2, color3);
        } else {
           drawGameIntro(g);
           if(delta1<0){
               drawGameName(g);
               if(j==0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
               }
           }
        }
         if(j==0){
            timer1.cancel();
            StartMidlet.sunnetCanvas = new SunnetCanvas(StartMidlet);
            this.StartMidlet.display.setCurrent(StartMidlet.sunnetCanvas);
        }
        Runtime.getRuntime().gc();
    }

    public void drawGameIntro(Graphics g){
            g.setColor(color1[0], color1[1], color1[2]);
            g.fillRect(0, 0, getWidth(), getHeight());
           // g.setFont(fontLarge);
           // g.setColor(color3[0], color3[1], color3[2]);
            byte[] text = new byte[10];
            Designer.toBytesIndex("giới thiệu", text);
            Designer.drawString(g,text, 0, 10, 2, getWidth()/2-30, getHeight()/3);
            // CustomFont.drawString(g ,"Giới thiệu", 0, 400, color, CustomFont.SIZE_MEDIUM, CustomFont.STYLE_PLAIN,CustomFont.MARGIN_CENTER,0,getHeight()/2-40,getWidth(),getHeight());
           // g.drawString("Giới thiệu", (getWidth() - fontLarge.stringWidth("Giới thiệu")) / 2, getHeight() / 3, 0);
    }

     public void drawGameName(Graphics g){
            if(j==0){
                //Clear Screen
                g.setColor(color1[0], color1[1], color1[2]);
                g.fillRect(0, 0, getWidth(), getHeight());
                //Draw some animation here
            }
            g.setFont(fontLarge);
            g.setColor(255-color2[0], color2[1], color2[2]);
            byte[] text = new byte[4];
            Designer.toBytesIndex("Zuma", text);
            Designer.drawString(g,text, 0, 4, 1, getWidth()/2-13, getHeight()/2);
            
            //  CustomFont.drawString(g ,"ĐẢO GIẤU VÀNG", 0, 400, color, CustomFont.SIZE_MEDIUM, CustomFont.STYLE_BOLD,CustomFont.MARGIN_CENTER,0,getHeight()/2,getWidth(),getHeight());
           // g.drawString("PACMAN", (getWidth() - fontLarge.stringWidth("PACMAN")) / 2, getHeight() / 2, 0);
    }

    public void drawLogoSunnet(Graphics g, int x, int y, int[] color1, int[] color2, int[] color3) {
        g.setColor(color1[0], color1[1], color1[2]);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color2[0], color2[1], color2[2]);
        g.fillArc(x - 28, y + 6, 30, 30, -8, 16);
        g.fillArc(x + 35, y + 6, 30, 30, -188, 16);
        g.fillArc(x - 26, y - 10, 30, 30, -38, 16);
        g.fillArc(x + 33, y - 10, 30, 30, -155, 16);
        g.fillArc(x - 16, y - 24, 30, 30, -68, 16);
        g.fillArc(x + 21, y - 24, 30, 30, -128, 16);
        g.fillArc(x + 3, y - 30, 30, 30, -98, 16);


        g.setColor(color1[0], color1[1], color1[2]);

        g.fillArc(x, y, 34, 34, 0, 360);

        g.setColor(color2[0], color2[1], color2[2]);

        g.fillArc(x + 3, y + 3, 29, 29, 0, 360);

        g.setColor(color1[0], color1[1], color1[2]);
        g.fillRect(0, y + 24, getWidth(), getHeight());

        g.setColor(color3[0], color3[1], color3[2]);
        g.setFont(fontLarge);
        g.drawString("S", x + (40 - widthText) / 2, y + 26, 0);
        g.setFont(fontMedium);
        g.drawString("UN", x + (40 - widthText + 2 * fontLarge.stringWidth(s)) / 2, y + 26 + (fontLarge.getHeight() - fontMedium.getHeight()) * 5 / 6, 0);
        g.setFont(fontLarge);
        g.drawString("N", x + (40 - widthText + 2 * fontLarge.stringWidth(s) + 2 * fontMedium.stringWidth(un)) / 2, y + 26, 0);
        g.setFont(fontMedium);
        g.drawString("ET", x + (40 - widthText + 2 * fontLarge.stringWidth(s +n) + 2 * fontMedium.stringWidth(un)) / 2, y + 26 + (fontLarge.getHeight() - fontMedium.getHeight()) * 5 / 6, 0);
        g.drawString("www.sunkhoai.com", x + (34 -  fontMedium.stringWidth("www.sunkhoai.com")) / 2, y + 26 + fontLarge.getHeight(), 0);
        g.setColor(color1[0], color1[1], color1[2]);
        g.fillRect(0, y + 26 + fontLarge.getHeight() / 3, getWidth(), 1);
        g.fillRect(0, y + 26 + fontLarge.getHeight() / 2, getWidth(), 1);
        g.fillRect(0, y + 26 + fontLarge.getHeight() * 2 / 3, getWidth(), 1);

    }

    public void drawLogoQplay(Graphics g, int x, int y, int[] color1, int[] color2, int[] color3) {
        g.setColor(color1[0], color1[1], color1[2]);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color2[0], color2[1], color2[2]);
        if(rd==0){

        for(int q=1;q<=35-(11-i)*3;q++){
            for(int p=1;p<=20;p++){
                if(qp[p-1][q-1]==1)
            g.fillRect(q*3-1+getWidth()/2-60, p*3-1+getHeight()/2-40, 2, 2);
        }
        }}
        else if(rd==1){

           for(int q=1;q<=35;q++){
            for(int p=1;p<=20-(11-i)*2;p++){
                if(qp[p-1][q-1]==1)
            g.fillRect(q*3-1+getWidth()/2-60, p*3-1+getHeight()/2-40, 2, 2);
        }}
        }else{
         for(int q=1;q<=35-(11-i)*3;q++){
            for(int p=1;p<=20-(11-i)*2;p++){
                if(qp[p-1][q-1]==1)
            g.fillRect(q*3-1+getWidth()/2-60, p*3-1+getHeight()/2-40, 2, 2);
        }
        }
        }

         byte[] text = new byte[10];
           // Designer.toBytesIndex1("www.qplay.vn", text);
           // Designer.drawString(g,text, 0, 12, Designer.FONT_TEXT_SMALL, 0, getWidth()/2-48, getHeight()*5/6);
         g.drawString("www.qplay.vn", x + (20 -  fontMedium.stringWidth("www.qlay.vn")) / 2, y + 60 + fontLarge.getHeight(), 0);

       
    }
    public void run() {
    }
}
