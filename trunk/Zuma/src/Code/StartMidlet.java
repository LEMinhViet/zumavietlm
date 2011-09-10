package Code;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author Admin
 */
public class StartMidlet extends MIDlet {
    public static SunnetFlash sunnetFlash;
    public static SunnetCanvas sunnetCanvas;
    public Display display;
    public ZumaCanvas STNcanvas;
    
    public void startApp() {
        sunnetFlash = new SunnetFlash(this);
        //STNcanvas = new ZumaCanvas(this);
        //STNcanvas.start();
        display = Display.getDisplay(this);
        display.setCurrent(sunnetFlash);
        //display.setCurrent(STNcanvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        Runtime.getRuntime().gc();
        notifyDestroyed();
    }

    public void gameScreenShow() {
        STNcanvas.start();
        display.setCurrent(STNcanvas);
    }
}
