/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.IOException;

/**
 * @author Admin
 */
public class GameDesign {

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Image lv1;
    private TiledLayer lv1GD;
    private Image mod;
    private Sprite Scop;
    public int Scopseq001Delay = 200;
    public int[] Scopseq001 = {8, 0, 0, 0, 0};
    //</editor-fold>//GEN-END:|fields|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    public void updateLayerManagerForLm(LayerManager lm) throws java.io.IOException {//GEN-LINE:|1-updateLayerManager|0|1-preUpdate
        // write pre-update user code here
        getScop().setPosition(91, 135);//GEN-BEGIN:|1-updateLayerManager|1|1-postUpdate
        getScop().setVisible(true);
        lm.append(getScop());
        getLv1GD().setPosition(0, 0);
        getLv1GD().setVisible(true);
        lm.append(getLv1GD());//GEN-END:|1-updateLayerManager|1|1-postUpdate
        // write post-update user code here
    }//GEN-BEGIN:|1-updateLayerManager|2|
//GEN-END:|1-updateLayerManager|2|

    public Image getLv1() throws java.io.IOException {//GEN-BEGIN:|2-getter|0|2-preInit
        if (lv1 == null) {//GEN-END:|2-getter|0|2-preInit
            // write pre-init user code here
            lv1 = Image.createImage("/picture/lv1.png");//GEN-BEGIN:|2-getter|1|2-postInit
        }//GEN-END:|2-getter|1|2-postInit
        // write post-init user code here
        return this.lv1;//GEN-BEGIN:|2-getter|2|
    }
//GEN-END:|2-getter|2|

    public TiledLayer getLv1GD() throws java.io.IOException {//GEN-BEGIN:|3-getter|0|3-preInit
        if (lv1GD == null) {//GEN-END:|3-getter|0|3-preInit
            // write pre-init user code here
            lv1GD = new TiledLayer(1, 1, getLv1(), 240, 320);//GEN-BEGIN:|3-getter|1|3-midInit
            int[][] tiles = {
                { 1 }
            };//GEN-END:|3-getter|1|3-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++) {//GEN-BEGIN:|3-getter|2|3-postInit
                for (int col = 0; col < 1; col++) {
                    lv1GD.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|3-getter|2|3-postInit
        // write post-init user code here
        return lv1GD;//GEN-BEGIN:|3-getter|3|
    }
//GEN-END:|3-getter|3|

    public Image getMod() throws java.io.IOException {//GEN-BEGIN:|8-getter|0|8-preInit
        if (mod == null) {//GEN-END:|8-getter|0|8-preInit
            // write pre-init user code here
            mod = Image.createImage("/picture/mod.png");//GEN-BEGIN:|8-getter|1|8-postInit
        }//GEN-END:|8-getter|1|8-postInit
        // write post-init user code here
        return this.mod;//GEN-BEGIN:|8-getter|2|
    }
//GEN-END:|8-getter|2|

    public Sprite getScop() throws java.io.IOException {//GEN-BEGIN:|9-getter|0|9-preInit
        if (Scop == null) {//GEN-END:|9-getter|0|9-preInit
            // write pre-init user code here
            Scop = new Sprite(getMod(), 60, 60);//GEN-BEGIN:|9-getter|1|9-postInit
            Scop.setFrameSequence(Scopseq001);//GEN-END:|9-getter|1|9-postInit
            // write post-init user code here
        }//GEN-BEGIN:|9-getter|2|
        return Scop;
    }
//GEN-END:|9-getter|2|

}
