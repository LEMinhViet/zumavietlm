/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Vitcum
 */
public class Navigator {
    int iP;
    ZumaCanvas zumaCanvas;
    public Navigator ( ZumaCanvas zumaCanvas ) {
        this.zumaCanvas = zumaCanvas;
    }
//g.drawLine(124, 168, 124 + (int)(100*Math.cos((iCount/180)*Math.PI-Math.PI/2))
 //                   , 168 - (int)(100*Math.sin((iCount/180)*Math.PI-Math.PI/2)));
    public void drawNavi( Graphics g ) {
        g.setColor( 0x00ff00 );
        for ( iP = 0; iP <= 140; iP += 10 ) {
            g.fillArc(123 + (int)(iP*Math.cos((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)),
                    166  - (int)(iP*Math.sin((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)), 3, 3, 0, 360);
        }
    }
}
