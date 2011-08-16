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
    public Display display;
    public ZumaCanvas STNcanvas;
    
    public void startApp() {
        STNcanvas = new ZumaCanvas(this);
        STNcanvas.start();
        display = Display.getDisplay(this);
        display.setCurrent(STNcanvas);
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
