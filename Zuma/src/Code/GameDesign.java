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
    private Sprite a;
    public int aseq001Delay = 200;
    public int[] aseq001 = {0, 0, 0, 0, 0};
    private Sprite ab;
    public int abseq001Delay = 200;
    public int[] abseq001 = {0, 0, 0, 0, 0};
    private Image lv4_patch;
    private Image lv4;
    private Image lv4_patch1;
    private Image lv5;
    private TiledLayer v4;
    private Image lv5_patch;
    private Sprite va;
    public int vaseq001Delay = 200;
    public int[] vaseq001 = {0, 0, 0, 0, 0};
    //</editor-fold>//GEN-END:|fields|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    public void updateLayerManagerForLm(LayerManager lm) throws java.io.IOException {//GEN-LINE:|1-updateLayerManager|0|1-preUpdate
        // write pre-update user code here
        getVa().setPosition(194, 79);//GEN-BEGIN:|1-updateLayerManager|1|1-postUpdate
        getVa().setVisible(true);
        lm.append(getVa());
        getScop().setPosition(109, 148);
        getScop().setVisible(true);
        lm.append(getScop());
        getV4().setPosition(0, 0);
        getV4().setVisible(true);
        lm.append(getV4());
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
                { 0 }
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

    public Image getLv4() throws java.io.IOException {//GEN-BEGIN:|19-getter|0|19-preInit
        if (lv4 == null) {//GEN-END:|19-getter|0|19-preInit
            // write pre-init user code here
            lv4 = Image.createImage("/picture/lv4.png");//GEN-BEGIN:|19-getter|1|19-postInit
        }//GEN-END:|19-getter|1|19-postInit
        // write post-init user code here
        return this.lv4;//GEN-BEGIN:|19-getter|2|
    }
//GEN-END:|19-getter|2|



    public Image getLv4_patch() throws java.io.IOException {//GEN-BEGIN:|45-getter|0|45-preInit
        if (lv4_patch == null) {//GEN-END:|45-getter|0|45-preInit
            // write pre-init user code here
            lv4_patch = Image.createImage("/picture/lv4-patch.png");//GEN-BEGIN:|45-getter|1|45-postInit
        }//GEN-END:|45-getter|1|45-postInit
        // write post-init user code here
        return this.lv4_patch;//GEN-BEGIN:|45-getter|2|
    }
//GEN-END:|45-getter|2|

    public Sprite getA() throws java.io.IOException {//GEN-BEGIN:|46-getter|0|46-preInit
        if (a == null) {//GEN-END:|46-getter|0|46-preInit
            // write pre-init user code here
            a = new Sprite(getLv4_patch(), 16, 11);//GEN-BEGIN:|46-getter|1|46-postInit
            a.setFrameSequence(aseq001);//GEN-END:|46-getter|1|46-postInit
            // write post-init user code here
        }//GEN-BEGIN:|46-getter|2|
        return a;
    }
//GEN-END:|46-getter|2|

    public Image getLv4_patch1() throws java.io.IOException {//GEN-BEGIN:|80-getter|0|80-preInit
        if (lv4_patch1 == null) {//GEN-END:|80-getter|0|80-preInit
            // write pre-init user code here
            lv4_patch1 = Image.createImage("/picture/lv4-patch1.png");//GEN-BEGIN:|80-getter|1|80-postInit
        }//GEN-END:|80-getter|1|80-postInit
        // write post-init user code here
        return this.lv4_patch1;//GEN-BEGIN:|80-getter|2|
    }
//GEN-END:|80-getter|2|

    public Sprite getAb() throws java.io.IOException {//GEN-BEGIN:|107-getter|0|107-preInit
        if (ab == null) {//GEN-END:|107-getter|0|107-preInit
            // write pre-init user code here
            ab = new Sprite(getLv4_patch1(), 21, 21);//GEN-BEGIN:|107-getter|1|107-postInit
            ab.setFrameSequence(abseq001);//GEN-END:|107-getter|1|107-postInit
            // write post-init user code here
        }//GEN-BEGIN:|107-getter|2|
        return ab;
    }
//GEN-END:|107-getter|2|

    public Image getLv5() throws java.io.IOException {//GEN-BEGIN:|178-getter|0|178-preInit
        if (lv5 == null) {//GEN-END:|178-getter|0|178-preInit
            // write pre-init user code here
            lv5 = Image.createImage("/picture/lv5.png");//GEN-BEGIN:|178-getter|1|178-postInit
        }//GEN-END:|178-getter|1|178-postInit
        // write post-init user code here
        return this.lv5;//GEN-BEGIN:|178-getter|2|
    }
//GEN-END:|178-getter|2|

    public TiledLayer getV4() throws java.io.IOException {//GEN-BEGIN:|179-getter|0|179-preInit
        if (v4 == null) {//GEN-END:|179-getter|0|179-preInit
            // write pre-init user code here
            v4 = new TiledLayer(1, 1, getLv5(), 240, 320);//GEN-BEGIN:|179-getter|1|179-midInit
            int[][] tiles = {
                { 1 }
            };//GEN-END:|179-getter|1|179-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++) {//GEN-BEGIN:|179-getter|2|179-postInit
                for (int col = 0; col < 1; col++) {
                    v4.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|179-getter|2|179-postInit
        // write post-init user code here
        return v4;//GEN-BEGIN:|179-getter|3|
    }
//GEN-END:|179-getter|3|

    public Image getLv5_patch() throws java.io.IOException {//GEN-BEGIN:|203-getter|0|203-preInit
        if (lv5_patch == null) {//GEN-END:|203-getter|0|203-preInit
            // write pre-init user code here
            lv5_patch = Image.createImage("/picture/lv5-patch.png");//GEN-BEGIN:|203-getter|1|203-postInit
        }//GEN-END:|203-getter|1|203-postInit
        // write post-init user code here
        return this.lv5_patch;//GEN-BEGIN:|203-getter|2|
    }
//GEN-END:|203-getter|2|

    public Sprite getVa() throws java.io.IOException {//GEN-BEGIN:|204-getter|0|204-preInit
        if (va == null) {//GEN-END:|204-getter|0|204-preInit
            // write pre-init user code here
            va = new Sprite(getLv5_patch(), 46, 45);//GEN-BEGIN:|204-getter|1|204-postInit
            va.setFrameSequence(vaseq001);//GEN-END:|204-getter|1|204-postInit
            // write post-init user code here
        }//GEN-BEGIN:|204-getter|2|
        return va;
    }
//GEN-END:|204-getter|2|

}
