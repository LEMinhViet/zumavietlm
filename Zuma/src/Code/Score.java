/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Admin
 */
public class Score {
    //Graphics g;
    ZumaCanvas zumaCanvas;
    int Score;
    int Score_level;
    int addScore;
    int bonusScore;
    int multiNum;
    int drawTimes = 0;
    int x = 2, y = 2, x1, y1;
    int partColli, ibreak;
    byte[] add = new byte[1];

    public Score ( ZumaCanvas zumaCanvas) {
        this.zumaCanvas = zumaCanvas;
        Score = 0;
        addScore = 0;
        bonusScore = 0;
        multiNum = 1;
        Score_level = 0;
    }

    public void addScore ( int beginBreak, int endBreak ) {
        Score += addScore;
        Score_level += addScore;
        addScore = ( endBreak - beginBreak +1 )*30*multiNum;
        multiNum++;
    }

    public void drawScore ( ) {
        if ( drawTimes == 0 ) {
            this.partColli = zumaCanvas.partColli;
            ibreak = zumaCanvas.vBall[partColli].endBreak + zumaCanvas.vBall[partColli].beginBreak + 2*zumaCanvas.vBall[partColli].Begin;
            
            try {
                x1 = ((Sprite)zumaCanvas.vBall[partColli].BVector.elementAt(ibreak/2)).getX();
                y1 = ((Sprite)zumaCanvas.vBall[partColli].BVector.elementAt(ibreak/2)).getY();
            } catch ( ArrayIndexOutOfBoundsException abc ) {
//                System.out.println("error " + partColli + " " + ibreak + " " + zumaCanvas.vBall[partColli].BVector.size());
            }
        }
        if ( drawTimes < 15) {            
            Designer.toBytesIndex("+", add);
            Designer.drawString( zumaCanvas.g, add, 0, 1, 1, x1 - 20, y1 - y*(drawTimes/3));
            Designer.drawNumber( zumaCanvas.g, addScore, 1, x1, y1 - y*(drawTimes/3));
            drawTimes++;            
        } else {
            zumaCanvas.drawScore = false;
            drawTimes = 0;
        }
    }
}
