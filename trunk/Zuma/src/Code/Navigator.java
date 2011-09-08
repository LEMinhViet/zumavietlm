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
        if ( zumaCanvas.Sball.Ball.getFrame() == 0 )
            g.setColor( 0x00ff00 );
        else if(zumaCanvas.Sball.Ball.getFrame() == 1)
            g.setColor( 0x0000ff );
        else if(zumaCanvas.Sball.Ball.getFrame() == 2)
            g.setColor( 0xff0000 );
        else if(zumaCanvas.Sball.Ball.getFrame() == 3)
            g.setColor( 0xffff00 );
        for ( iP = 0; iP <= 150; iP += 15 ) {
            g.fillArc( zumaCanvas.Sball.getPositionX() + 6 + (int)(iP*Math.cos((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)),
                    zumaCanvas.Sball.getPositionY() + 6 + (int)(iP*Math.sin((zumaCanvas.iCount/180)*Math.PI-Math.PI/2)), 4, 4, 0, 360);
        }
    }
}
