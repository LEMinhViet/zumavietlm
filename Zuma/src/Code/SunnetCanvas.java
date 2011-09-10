package Code;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Binh
 */
public class SunnetCanvas extends Canvas implements MessageListener {

    private Timer timer1;
    private TimerTask timerTask;
    public boolean isMenu = true;
    int curMenu = 0, langId = 1, minMenu = 0;
    int xL = 0, yL = 0;
//    public String[] menu,  menuViet = new String[]{"VÁN MỚI", "ÂM THANH", "ENGLISH", "TÙY CHỌN", "HƯỚNG DẪN", "ĐIỂM CAO", "QUAY LẠI", "THOÁT"};
//    public String[] menuEnglish = new String[]{"NEW GAME", "SOUND ON/OFF", "TIẾNG VIỆT", "SETTING", "HELP", "HIGH SCORE", "BACK", "EXIT"};
    Font fontMenu = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    Font fontTextMenu = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
    String msgReceived = "no Message";
    MessageConnection serverConn;
    byte[] text;
    public static int DELAY_DEFAULT = 100;
    private int color = 0;
    Image bkImage = null, menuPic = null, menu1 = null, menu2 = null, menu3 = null, menu4 = null, menu5 = null;
    StartMidlet StartMidlet;

    public SunnetCanvas(StartMidlet StartMidlet) {
        this.setFullScreenMode(true);
        this.StartMidlet = StartMidlet;
        this.StartMidlet.sunnetFlash=null;
        timer1 = new Timer();

        timerTask = new TimerTask() {

            public void run() {
                if (isShown()) {
                    if (true) {
                        if (xL < 10) {
                            xL++;
                        } else {
                            xL = 0;
                        }

                        if (yL < 10) {
                            yL++;
                        } else {
                            yL = 0;
                        }

                        repaint(0, 0, getWidth(), getHeight());
                    }
                }
            }
        };
        timer1.schedule(timerTask, 0, 100);
    }

    protected void paint(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, getWidth(), getHeight());
        //Draw background
        if(bkImage == null){
            try {
                bkImage = Image.createImage("/menu/bg.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        int width = getWidth();
        int height = getHeight();
        if(width > 240) {
            width = 240;
        }
        if(height > 320){
            height = 320;
        }
        g.drawRegion(bkImage, (240-width)/2, (320-height)/2, width, height,0, Math.abs(getWidth()-240)/2, Math.abs(getHeight()-320)/2,  0);

        if ( menuPic == null ) {
            try {
                menuPic = Image.createImage("/menu/bg-menu.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        g.drawRegion(menuPic, 0, 0, 209, 272, 0, 18, 25, 0);

        menu1 = createImage ("/menu/1.png");
        menu2 = createImage ("/menu/2.png");
        menu3 = createImage ("/menu/3.png");
        menu4 = createImage ("/menu/4.png");
        menu5 = createImage ("/menu/5.png");
        if ( curMenu == 0 ) {
            menu1 = createImage ("/menu/1_p.png");
        } else if ( curMenu == 1 ) {
            menu2 = createImage ("/menu/2_p.png");
        } else if ( curMenu == 2 ) {
            menu3 = createImage ("/menu/3_p.png");
        } else if ( curMenu == 3 ) {
            menu4 = createImage ("/menu/4_p.png");
        } else if ( curMenu == 4 ) {
            menu5 = createImage ("/menu/5_p.png");
        }
        g.drawImage(menu1, 240/2 - menu1.getWidth()/2, 80, Graphics.TOP | Graphics.LEFT);
        g.drawImage(menu2, 240/2 - menu2.getWidth()/2, 100, Graphics.TOP | Graphics.LEFT);
        g.drawImage(menu3, 240/2 - menu3.getWidth()/2, 120, Graphics.TOP | Graphics.LEFT);
        g.drawImage(menu4, 240/2 - menu4.getWidth()/2, 140, Graphics.TOP | Graphics.LEFT);
        g.drawImage(menu5, 240/2 - menu5.getWidth()/2, 160, Graphics.TOP | Graphics.LEFT);
        
//        if (isMenu) {
//            drawMenu(g);
//
//        } else {
//            g.setColor(255, 255, 255);
//            g.drawString(msgReceived, getWidth() / 29, getHeight() / 2, 0);
//        }
    }
    public static int VERTICAL = 1;
    public static int HORIZONTAL = 0;

    public void drawMenu(Graphics g) {
        
        
        Runtime.getRuntime().gc();
    }

    public Image createImage( String filepath ) {
        Image menu = null;
        try {
            menu = Image.createImage(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return menu;
    }

    protected void keyPressed(int keyCode) {

        System.out.println(keyCode);

        int instantkeyCode = KeyCodeAdapter.getInstance().adoptKeyCode(keyCode);

        System.out.println(instantkeyCode);

        System.out.println(KeyCodeAdapter.SOFT_KEY_LEFT);


        switch (instantkeyCode) {
            case KeyCodeAdapter.LEFT_KEY:
                //leftkey();

                repaint();
                return;
            case KeyCodeAdapter.RIGHT_KEY:
                // rightkey();
                repaint();

                return;
            case KeyCodeAdapter.DOWN_KEY:
                downKey();
                repaint();

                return;

            case KeyCodeAdapter.UP_KEY:
                upKey();
                repaint();

                return;
            case KeyCodeAdapter.CENTER_KEY:
                fireKey();
                repaint();
                return;

            case KeyCodeAdapter.SOFT_KEY_MIDDLE_INTERNET:
                fireKey();
                repaint();

                return;

            case KeyCodeAdapter.SOFT_KEY_LEFT:
                menuLeftKey();
                repaint();

                return;

            case KeyCodeAdapter.SOFT_KEY_RIGHT:
                menuRightKey();
                repaint();

                return;

        }
    }

    public void upKey() {

        if (curMenu > 0) {
            curMenu--;
        } else {
            curMenu = 4;
        }

    }

    public void downKey() {

        if (curMenu < 4) {
            curMenu++;
        } else {
            curMenu = 0;
        }
    }

    public void fireKey() {
        if (isMenu) {
            switch (curMenu) {
                case 0:
                    isMenu = false;
                    StartMidlet.STNcanvas = new ZumaCanvas(StartMidlet);
                    StartMidlet.STNcanvas.start();
                    StartMidlet.display.setCurrent(StartMidlet.STNcanvas);
                    StartMidlet.STNcanvas.langID = langId;
                    Runtime.getRuntime().gc();                  

                    return;
                case 1:
                    return;
//                case 2:
//                    if (langId == 1) {
//                        langId = 0;
////                        menu = menuEnglish;
//                    } else {
//                        langId = 1;
////                        menu = menuViet;
//                    }
//                    return;
//                case 3:
//                    //SettingsScreen settingsScreen;
//                    try {
//                        //settingsScreen = new SettingsScreen (midlet, midlet.settings);
//                        StartMidlet.settingsScreen.init();
//                        StartMidlet.display.setCurrent(StartMidlet.settingsScreen);
//                        Runtime.getRuntime().gc();
//                    } catch ( Exception ex ) {
//                        ex.printStackTrace();
//                    }
//                    return;
                case 2:
                    //HelpScreen helpScreen;
                    try {
                        StartMidlet.helpScreen = new HelpScreen (StartMidlet);
                        StartMidlet.display.setCurrent(StartMidlet.helpScreen);
                        Runtime.getRuntime().gc();
                    } catch ( Exception ex ) {
                        ex.printStackTrace();
                    }
                    return;
                case 5:
//                    //HighScoreScreen highscoreScreen;
//                    try {
//                        //highscoreScreen = new HighScoreScreen(midlet, midlet.score);
//                        StartMidlet.highscoreScreen.init();
//                        StartMidlet.display.setCurrent(StartMidlet.highscoreScreen);
//                        Runtime.getRuntime().gc();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    return;
                case 7:
                    StartMidlet.destroyApp(true);
                    return;
            }
        }
    }

    public void menuLeftKey() {
        //isMenu = !isMenu;
    }

    public void menuRightKey() {
        
       
    }

    
    public void notifyIncomingMessage(MessageConnection conn) {
        Message msg = null;
        //  Try reading (maybe block for) a message
        try {
            msg = conn.receive();
        } catch (Exception e) {
            // Handle reading errors
            msgReceived = e.toString();
            System.out.println("processMessage.receive " + e);
        }
      

        // Process the received message
        if (msg instanceof TextMessage) {
            TextMessage tmsg = (TextMessage) msg;
            msgReceived = tmsg.getPayloadText();
        } else {
           

            if (msg instanceof BinaryMessage) {
                BinaryMessage bmsg = (BinaryMessage) msg;
                byte[] data = bmsg.getPayloadData();
                //  Handle the binary message...
                msgReceived = data.toString();
            }
        }
    }
}
