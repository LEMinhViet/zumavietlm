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
    private TiledLayer l3;
    private Image lv3;
    private Image lv1;
    private TiledLayer lv1GD;
    private Image mod;
    private Sprite Scop;
    public int Scopseq001Delay = 200;
    public int[] Scopseq001 = {8, 0, 0};
    private Sprite fin;
    public int finseq001Delay = 200;
    public int[] finseq001 = {0, 0, 0, 0, 0};
    private Image key;
    private Image lv4;
    private Image lv15;
    private Image mod_;
    private Image lv10;
    private TiledLayer x10;
    private Sprite x6;
    public int x6seq001Delay = 200;
    public int[] x6seq001 = {0, 0, 0, 0, 0};
    private Image lv9;
    private Sprite x9;
    public int x9seq001Delay = 200;
    public int[] x9seq001 = {0, 0, 0, 0, 0};
    private Image nhan_su;
    private Image lv6;
    private Image lv4_patch;
    private Sprite nhgan;
    public int nhganseq001Delay = 200;
    public int[] nhganseq001 = {0, 0, 0, 0, 0};
    private Image lv5;
    private Image lv2;
    private TiledLayer v4;
    private Sprite x2;
    public int x2seq001Delay = 200;
    public int[] x2seq001 = {0, 0, 0, 0, 0};
    private Sprite x12;
    public int x12seq001Delay = 200;
    public int[] x12seq001 = {0, 0, 0, 0, 0};
    private Image lv12;
    private Image lv5_patch;
    private Sprite x7;
    public int x7seq001Delay = 200;
    public int[] x7seq001 = {0, 0, 0, 0, 0};
    private Image lv7;
    private Sprite x4;
    public int x4seq001Delay = 200;
    public int[] x4seq001 = {0, 0, 0, 0, 0};
    private TiledLayer v1;
    private Image lv11;
    private Image lv4_patch1;
    private Image mod_2;
    private Image boss_1;
    private Sprite boss;
    public int bossseq001Delay = 200;
    public int[] bossseq001 = {0, 0, 0, 0, 0};
    private Sprite x3;
    public int x3seq001Delay = 200;
    public int[] x3seq001 = {0, 0, 0, 0, 0};
    private Image lv14;
    private Image lv13;
    private Image lv8;
    private Sprite x8;
    public int x8seq001Delay = 200;
    public int[] x8seq001 = {0, 0, 0, 0, 0};
    private Image boss_lv2;
    private Sprite bos2;
    public int bos2seq001Delay = 200;
    public int[] bos2seq001 = {0, 0, 0, 0, 0};
    private Sprite l13;
    public int l13seq001Delay = 200;
    public int[] l13seq001 = {0, 0, 0, 0, 0};
    private Image boss_lv5;
    private Sprite bos5;
    public int bos5seq001Delay = 200;
    public int[] bos5seq001 = {0, 0, 0, 0, 0};
    //</editor-fold>//GEN-END:|fields|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    public void updateLayerManagerForLm(LayerManager lm) throws java.io.IOException {//GEN-LINE:|1-updateLayerManager|0|1-preUpdate
        // write pre-update user code here
        getBos5().setPosition(86, 276);//GEN-BEGIN:|1-updateLayerManager|1|1-postUpdate
        getBos5().setVisible(true);
        lm.append(getBos5());
        getL13().setPosition(0, 0);
        getL13().setVisible(true);
        lm.append(getL13());
        getBos2().setPosition(200, 117);
        getBos2().setVisible(true);
        lm.append(getBos2());
        getScop().setPosition(51, 206);
        getScop().setVisible(true);
        lm.append(getScop());
        getNhgan().setPosition(131, 70);
        getNhgan().setVisible(true);
        lm.append(getNhgan());
        getLv1GD().setPosition(1, -6);
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
            mod = Image.createImage("/picture/mod_.png");//GEN-BEGIN:|8-getter|1|8-postInit
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



    public Image getLv4_patch1() throws java.io.IOException {//GEN-BEGIN:|80-getter|0|80-preInit
        if (lv4_patch1 == null) {//GEN-END:|80-getter|0|80-preInit
            // write pre-init user code here
            lv4_patch1 = Image.createImage("/picture/lv4-patch1.png");//GEN-BEGIN:|80-getter|1|80-postInit
        }//GEN-END:|80-getter|1|80-postInit
        // write post-init user code here
        return this.lv4_patch1;//GEN-BEGIN:|80-getter|2|
    }
//GEN-END:|80-getter|2|



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



    public Image getMod_2() throws java.io.IOException {//GEN-BEGIN:|218-getter|0|218-preInit
        if (mod_2 == null) {//GEN-END:|218-getter|0|218-preInit
            // write pre-init user code here
            mod_2 = Image.createImage("/picture/mod_.png");//GEN-BEGIN:|218-getter|1|218-postInit
        }//GEN-END:|218-getter|1|218-postInit
        // write post-init user code here
        return this.mod_2;//GEN-BEGIN:|218-getter|2|
    }
//GEN-END:|218-getter|2|



    public TiledLayer getV1() throws java.io.IOException {//GEN-BEGIN:|221-getter|0|221-preInit
        if (v1 == null) {//GEN-END:|221-getter|0|221-preInit
            // write pre-init user code here
            v1 = new TiledLayer(1, 1, getLv1(), 240, 320);//GEN-BEGIN:|221-getter|1|221-midInit
            int[][] tiles = {
                { 1 }
            };//GEN-END:|221-getter|1|221-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++) {//GEN-BEGIN:|221-getter|2|221-postInit
                for (int col = 0; col < 1; col++) {
                    v1.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|221-getter|2|221-postInit
        // write post-init user code here
        return v1;//GEN-BEGIN:|221-getter|3|
    }
//GEN-END:|221-getter|3|

    public Image getMod_() throws java.io.IOException {//GEN-BEGIN:|270-getter|0|270-preInit
        if (mod_ == null) {//GEN-END:|270-getter|0|270-preInit
            // write pre-init user code here
            mod_ = Image.createImage("/picture/mod_.png");//GEN-BEGIN:|270-getter|1|270-postInit
        }//GEN-END:|270-getter|1|270-postInit
        // write post-init user code here
        return this.mod_;//GEN-BEGIN:|270-getter|2|
    }
//GEN-END:|270-getter|2|



    public Image getLv3() throws java.io.IOException {//GEN-BEGIN:|273-getter|0|273-preInit
        if (lv3 == null) {//GEN-END:|273-getter|0|273-preInit
            // write pre-init user code here
            lv3 = Image.createImage("/picture/lv3.png");//GEN-BEGIN:|273-getter|1|273-postInit
        }//GEN-END:|273-getter|1|273-postInit
        // write post-init user code here
        return this.lv3;//GEN-BEGIN:|273-getter|2|
    }
//GEN-END:|273-getter|2|

    public TiledLayer getL3() throws java.io.IOException {//GEN-BEGIN:|274-getter|0|274-preInit
        if (l3 == null) {//GEN-END:|274-getter|0|274-preInit
            // write pre-init user code here
            l3 = new TiledLayer(1, 1, getLv3(), 240, 320);//GEN-BEGIN:|274-getter|1|274-midInit
            int[][] tiles = {
                { 1 }
            };//GEN-END:|274-getter|1|274-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++) {//GEN-BEGIN:|274-getter|2|274-postInit
                for (int col = 0; col < 1; col++) {
                    l3.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|274-getter|2|274-postInit
        // write post-init user code here
        return l3;//GEN-BEGIN:|274-getter|3|
    }
//GEN-END:|274-getter|3|

    public void updateLayerManagerForLm2(LayerManager lm) throws java.io.IOException {//GEN-LINE:|275-updateLayerManager|0|275-preUpdate
        // write pre-update user code here
        getFin().setPosition(111, 159);//GEN-BEGIN:|275-updateLayerManager|1|275-postUpdate
        getFin().setVisible(true);
        lm.append(getFin());//GEN-END:|275-updateLayerManager|1|275-postUpdate
        // write post-update user code here
    }//GEN-BEGIN:|275-updateLayerManager|2|
//GEN-END:|275-updateLayerManager|2|

    public void updateLayerManagerForLm3(LayerManager lm) throws java.io.IOException {//GEN-LINE:|298-updateLayerManager|0|298-preUpdate
        // write pre-update user code here
        getBoss().setPosition(200, 120);//GEN-BEGIN:|298-updateLayerManager|1|298-postUpdate
        getBoss().setVisible(true);
        lm.append(getBoss());
        getNhgan().setPosition(75, 3);
        getNhgan().setVisible(true);
        lm.append(getNhgan());
        getScop().setPosition(5, 133);
        getScop().setVisible(true);
        lm.append(getScop());
        getX3().setPosition(0, 0);
        getX3().setVisible(true);
        lm.append(getX3());//GEN-END:|298-updateLayerManager|1|298-postUpdate
        // write post-update user code here
    }//GEN-BEGIN:|298-updateLayerManager|2|
//GEN-END:|298-updateLayerManager|2|

    public Image getLv10() throws java.io.IOException {//GEN-BEGIN:|307-getter|0|307-preInit
        if (lv10 == null) {//GEN-END:|307-getter|0|307-preInit
            // write pre-init user code here
            lv10 = Image.createImage("/picture/lv10.png");//GEN-BEGIN:|307-getter|1|307-postInit
        }//GEN-END:|307-getter|1|307-postInit
        // write post-init user code here
        return this.lv10;//GEN-BEGIN:|307-getter|2|
    }
//GEN-END:|307-getter|2|

    public TiledLayer getX10() throws java.io.IOException {//GEN-BEGIN:|308-getter|0|308-preInit
        if (x10 == null) {//GEN-END:|308-getter|0|308-preInit
            // write pre-init user code here
            x10 = new TiledLayer(1, 1, getLv10(), 240, 320);//GEN-BEGIN:|308-getter|1|308-midInit
            int[][] tiles = {
                { 1 }
            };//GEN-END:|308-getter|1|308-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++) {//GEN-BEGIN:|308-getter|2|308-postInit
                for (int col = 0; col < 1; col++) {
                    x10.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|308-getter|2|308-postInit
        // write post-init user code here
        return x10;//GEN-BEGIN:|308-getter|3|
    }
//GEN-END:|308-getter|3|

    public Image getLv11() throws java.io.IOException {//GEN-BEGIN:|323-getter|0|323-preInit
        if (lv11 == null) {//GEN-END:|323-getter|0|323-preInit
            // write pre-init user code here
            lv11 = Image.createImage("/picture/lv11.png");//GEN-BEGIN:|323-getter|1|323-postInit
        }//GEN-END:|323-getter|1|323-postInit
        // write post-init user code here
        return this.lv11;//GEN-BEGIN:|323-getter|2|
    }
//GEN-END:|323-getter|2|



    public Image getLv12() throws java.io.IOException {//GEN-BEGIN:|341-getter|0|341-preInit
        if (lv12 == null) {//GEN-END:|341-getter|0|341-preInit
            // write pre-init user code here
            lv12 = Image.createImage("/picture/lv12.png");//GEN-BEGIN:|341-getter|1|341-postInit
        }//GEN-END:|341-getter|1|341-postInit
        // write post-init user code here
        return this.lv12;//GEN-BEGIN:|341-getter|2|
    }
//GEN-END:|341-getter|2|

    public Sprite getX12() throws java.io.IOException {//GEN-BEGIN:|342-getter|0|342-preInit
        if (x12 == null) {//GEN-END:|342-getter|0|342-preInit
            // write pre-init user code here
            x12 = new Sprite(getLv12(), 240, 320);//GEN-BEGIN:|342-getter|1|342-postInit
            x12.setFrameSequence(x12seq001);//GEN-END:|342-getter|1|342-postInit
            // write post-init user code here
        }//GEN-BEGIN:|342-getter|2|
        return x12;
    }
//GEN-END:|342-getter|2|

    public Image getLv13() throws java.io.IOException {//GEN-BEGIN:|359-getter|0|359-preInit
        if (lv13 == null) {//GEN-END:|359-getter|0|359-preInit
            // write pre-init user code here
            lv13 = Image.createImage("/picture/lv13.png");//GEN-BEGIN:|359-getter|1|359-postInit
        }//GEN-END:|359-getter|1|359-postInit
        // write post-init user code here
        return this.lv13;//GEN-BEGIN:|359-getter|2|
    }
//GEN-END:|359-getter|2|



    public Image getLv14() throws java.io.IOException {//GEN-BEGIN:|377-getter|0|377-preInit
        if (lv14 == null) {//GEN-END:|377-getter|0|377-preInit
            // write pre-init user code here
            lv14 = Image.createImage("/picture/lv10-way.png");//GEN-BEGIN:|377-getter|1|377-postInit
        }//GEN-END:|377-getter|1|377-postInit
        // write post-init user code here
        return this.lv14;//GEN-BEGIN:|377-getter|2|
    }
//GEN-END:|377-getter|2|



    public Image getLv15() throws java.io.IOException {//GEN-BEGIN:|399-getter|0|399-preInit
        if (lv15 == null) {//GEN-END:|399-getter|0|399-preInit
            // write pre-init user code here
            lv15 = Image.createImage("/picture/lv2-way.png");//GEN-BEGIN:|399-getter|1|399-postInit
        }//GEN-END:|399-getter|1|399-postInit
        // write post-init user code here
        return this.lv15;//GEN-BEGIN:|399-getter|2|
    }
//GEN-END:|399-getter|2|



    public Image getLv2() throws java.io.IOException {//GEN-BEGIN:|417-getter|0|417-preInit
        if (lv2 == null) {//GEN-END:|417-getter|0|417-preInit
            // write pre-init user code here
            lv2 = Image.createImage("/picture/lv2.png");//GEN-BEGIN:|417-getter|1|417-postInit
        }//GEN-END:|417-getter|1|417-postInit
        // write post-init user code here
        return this.lv2;//GEN-BEGIN:|417-getter|2|
    }
//GEN-END:|417-getter|2|

    public Sprite getX2() throws java.io.IOException {//GEN-BEGIN:|418-getter|0|418-preInit
        if (x2 == null) {//GEN-END:|418-getter|0|418-preInit
            // write pre-init user code here
            x2 = new Sprite(getLv2(), 240, 320);//GEN-BEGIN:|418-getter|1|418-postInit
            x2.setFrameSequence(x2seq001);//GEN-END:|418-getter|1|418-postInit
            // write post-init user code here
        }//GEN-BEGIN:|418-getter|2|
        return x2;
    }
//GEN-END:|418-getter|2|

    public Image getLv9() throws java.io.IOException {//GEN-BEGIN:|433-getter|0|433-preInit
        if (lv9 == null) {//GEN-END:|433-getter|0|433-preInit
            // write pre-init user code here
            lv9 = Image.createImage("/picture/lv9.png");//GEN-BEGIN:|433-getter|1|433-postInit
        }//GEN-END:|433-getter|1|433-postInit
        // write post-init user code here
        return this.lv9;//GEN-BEGIN:|433-getter|2|
    }
//GEN-END:|433-getter|2|

    public Sprite getX9() throws java.io.IOException {//GEN-BEGIN:|434-getter|0|434-preInit
        if (x9 == null) {//GEN-END:|434-getter|0|434-preInit
            // write pre-init user code here
            x9 = new Sprite(getLv9(), 240, 320);//GEN-BEGIN:|434-getter|1|434-postInit
            x9.setFrameSequence(x9seq001);//GEN-END:|434-getter|1|434-postInit
            // write post-init user code here
        }//GEN-BEGIN:|434-getter|2|
        return x9;
    }
//GEN-END:|434-getter|2|

    public Image getLv7() throws java.io.IOException {//GEN-BEGIN:|469-getter|0|469-preInit
        if (lv7 == null) {//GEN-END:|469-getter|0|469-preInit
            // write pre-init user code here
            lv7 = Image.createImage("/picture/lv7.png");//GEN-BEGIN:|469-getter|1|469-postInit
        }//GEN-END:|469-getter|1|469-postInit
        // write post-init user code here
        return this.lv7;//GEN-BEGIN:|469-getter|2|
    }
//GEN-END:|469-getter|2|

    public Sprite getX7() throws java.io.IOException {//GEN-BEGIN:|470-getter|0|470-preInit
        if (x7 == null) {//GEN-END:|470-getter|0|470-preInit
            // write pre-init user code here
            x7 = new Sprite(getLv7(), 240, 320);//GEN-BEGIN:|470-getter|1|470-postInit
            x7.setFrameSequence(x7seq001);//GEN-END:|470-getter|1|470-postInit
            // write post-init user code here
        }//GEN-BEGIN:|470-getter|2|
        return x7;
    }
//GEN-END:|470-getter|2|

    public Image getLv8() throws java.io.IOException {//GEN-BEGIN:|485-getter|0|485-preInit
        if (lv8 == null) {//GEN-END:|485-getter|0|485-preInit
            // write pre-init user code here
            lv8 = Image.createImage("/picture/lv8.png");//GEN-BEGIN:|485-getter|1|485-postInit
        }//GEN-END:|485-getter|1|485-postInit
        // write post-init user code here
        return this.lv8;//GEN-BEGIN:|485-getter|2|
    }
//GEN-END:|485-getter|2|

    public Sprite getX8() throws java.io.IOException {//GEN-BEGIN:|486-getter|0|486-preInit
        if (x8 == null) {//GEN-END:|486-getter|0|486-preInit
            // write pre-init user code here
            x8 = new Sprite(getLv8(), 240, 320);//GEN-BEGIN:|486-getter|1|486-postInit
            x8.setFrameSequence(x8seq001);//GEN-END:|486-getter|1|486-postInit
            // write post-init user code here
        }//GEN-BEGIN:|486-getter|2|
        return x8;
    }
//GEN-END:|486-getter|2|

    public Sprite getX3() throws java.io.IOException {//GEN-BEGIN:|507-getter|0|507-preInit
        if (x3 == null) {//GEN-END:|507-getter|0|507-preInit
            // write pre-init user code here
            x3 = new Sprite(getLv3(), 240, 320);//GEN-BEGIN:|507-getter|1|507-postInit
            x3.setFrameSequence(x3seq001);//GEN-END:|507-getter|1|507-postInit
            // write post-init user code here
        }//GEN-BEGIN:|507-getter|2|
        return x3;
    }
//GEN-END:|507-getter|2|

    public Image getNhan_su() throws java.io.IOException {//GEN-BEGIN:|521-getter|0|521-preInit
        if (nhan_su == null) {//GEN-END:|521-getter|0|521-preInit
            // write pre-init user code here
            nhan_su = Image.createImage("/picture/nhan-su.png");//GEN-BEGIN:|521-getter|1|521-postInit
        }//GEN-END:|521-getter|1|521-postInit
        // write post-init user code here
        return this.nhan_su;//GEN-BEGIN:|521-getter|2|
    }
//GEN-END:|521-getter|2|

    public Sprite getNhgan() throws java.io.IOException {//GEN-BEGIN:|535-getter|0|535-preInit
        if (nhgan == null) {//GEN-END:|535-getter|0|535-preInit
            // write pre-init user code here
            nhgan = new Sprite(getNhan_su(), 35, 40);//GEN-BEGIN:|535-getter|1|535-postInit
            nhgan.setFrameSequence(nhganseq001);//GEN-END:|535-getter|1|535-postInit
            // write post-init user code here
        }//GEN-BEGIN:|535-getter|2|
        return nhgan;
    }
//GEN-END:|535-getter|2|

    public Image getBoss_1() throws java.io.IOException {//GEN-BEGIN:|581-getter|0|581-preInit
        if (boss_1 == null) {//GEN-END:|581-getter|0|581-preInit
            // write pre-init user code here
            boss_1 = Image.createImage("/picture/boss-1.png");//GEN-BEGIN:|581-getter|1|581-postInit
        }//GEN-END:|581-getter|1|581-postInit
        // write post-init user code here
        return this.boss_1;//GEN-BEGIN:|581-getter|2|
    }
//GEN-END:|581-getter|2|

    public Sprite getBoss() throws java.io.IOException {//GEN-BEGIN:|582-getter|0|582-preInit
        if (boss == null) {//GEN-END:|582-getter|0|582-preInit
            // write pre-init user code here
            boss = new Sprite(getBoss_1(), 39, 60);//GEN-BEGIN:|582-getter|1|582-postInit
            boss.setFrameSequence(bossseq001);//GEN-END:|582-getter|1|582-postInit
            // write post-init user code here
        }//GEN-BEGIN:|582-getter|2|
        return boss;
    }
//GEN-END:|582-getter|2|

    public Sprite getX4() throws java.io.IOException {//GEN-BEGIN:|620-getter|0|620-preInit
        if (x4 == null) {//GEN-END:|620-getter|0|620-preInit
            // write pre-init user code here
            x4 = new Sprite(getLv4(), 240, 320);//GEN-BEGIN:|620-getter|1|620-postInit
            x4.setFrameSequence(x4seq001);//GEN-END:|620-getter|1|620-postInit
            // write post-init user code here
        }//GEN-BEGIN:|620-getter|2|
        return x4;
    }
//GEN-END:|620-getter|2|

    public Image getLv6() throws java.io.IOException {//GEN-BEGIN:|643-getter|0|643-preInit
        if (lv6 == null) {//GEN-END:|643-getter|0|643-preInit
            // write pre-init user code here
            lv6 = Image.createImage("/picture/lv6.png");//GEN-BEGIN:|643-getter|1|643-postInit
        }//GEN-END:|643-getter|1|643-postInit
        // write post-init user code here
        return this.lv6;//GEN-BEGIN:|643-getter|2|
    }
//GEN-END:|643-getter|2|

    public Sprite getX6() throws java.io.IOException {//GEN-BEGIN:|644-getter|0|644-preInit
        if (x6 == null) {//GEN-END:|644-getter|0|644-preInit
            // write pre-init user code here
            x6 = new Sprite(getLv6(), 240, 320);//GEN-BEGIN:|644-getter|1|644-postInit
            x6.setFrameSequence(x6seq001);//GEN-END:|644-getter|1|644-postInit
            // write post-init user code here
        }//GEN-BEGIN:|644-getter|2|
        return x6;
    }
//GEN-END:|644-getter|2|

    public Image getKey() throws java.io.IOException {//GEN-BEGIN:|674-getter|0|674-preInit
        if (key == null) {//GEN-END:|674-getter|0|674-preInit
            // write pre-init user code here
            key = Image.createImage("/picture/key.png");//GEN-BEGIN:|674-getter|1|674-postInit
        }//GEN-END:|674-getter|1|674-postInit
        // write post-init user code here
        return this.key;//GEN-BEGIN:|674-getter|2|
    }
//GEN-END:|674-getter|2|

    public Sprite getFin() throws java.io.IOException {//GEN-BEGIN:|675-getter|0|675-preInit
        if (fin == null) {//GEN-END:|675-getter|0|675-preInit
            // write pre-init user code here
            fin = new Sprite(getKey(), 40, 40);//GEN-BEGIN:|675-getter|1|675-postInit
            fin.setFrameSequence(finseq001);//GEN-END:|675-getter|1|675-postInit
            // write post-init user code here
        }//GEN-BEGIN:|675-getter|2|
        return fin;
    }
//GEN-END:|675-getter|2|

    public Image getBoss_lv2() throws java.io.IOException {//GEN-BEGIN:|916-getter|0|916-preInit
        if (boss_lv2 == null) {//GEN-END:|916-getter|0|916-preInit
            // write pre-init user code here
            boss_lv2 = Image.createImage("/picture/boss-lv2.png");//GEN-BEGIN:|916-getter|1|916-postInit
        }//GEN-END:|916-getter|1|916-postInit
        // write post-init user code here
        return this.boss_lv2;//GEN-BEGIN:|916-getter|2|
    }
//GEN-END:|916-getter|2|

    public Sprite getBos2() throws java.io.IOException {//GEN-BEGIN:|917-getter|0|917-preInit
        if (bos2 == null) {//GEN-END:|917-getter|0|917-preInit
            // write pre-init user code here
            bos2 = new Sprite(getBoss_lv2(), 39, 60);//GEN-BEGIN:|917-getter|1|917-postInit
            bos2.setFrameSequence(bos2seq001);//GEN-END:|917-getter|1|917-postInit
            // write post-init user code here
        }//GEN-BEGIN:|917-getter|2|
        return bos2;
    }
//GEN-END:|917-getter|2|

    public Sprite getL13() throws java.io.IOException {//GEN-BEGIN:|1011-getter|0|1011-preInit
        if (l13 == null) {//GEN-END:|1011-getter|0|1011-preInit
            // write pre-init user code here
            l13 = new Sprite(getLv13(), 240, 320);//GEN-BEGIN:|1011-getter|1|1011-postInit
            l13.setFrameSequence(l13seq001);//GEN-END:|1011-getter|1|1011-postInit
            // write post-init user code here
        }//GEN-BEGIN:|1011-getter|2|
        return l13;
    }
//GEN-END:|1011-getter|2|

    public Image getBoss_lv5() throws java.io.IOException {//GEN-BEGIN:|1033-getter|0|1033-preInit
        if (boss_lv5 == null) {//GEN-END:|1033-getter|0|1033-preInit
            // write pre-init user code here
            boss_lv5 = Image.createImage("/picture/boss-lv5.png");//GEN-BEGIN:|1033-getter|1|1033-postInit
        }//GEN-END:|1033-getter|1|1033-postInit
        // write post-init user code here
        return this.boss_lv5;//GEN-BEGIN:|1033-getter|2|
    }
//GEN-END:|1033-getter|2|

    public Sprite getBos5() throws java.io.IOException {//GEN-BEGIN:|1034-getter|0|1034-preInit
        if (bos5 == null) {//GEN-END:|1034-getter|0|1034-preInit
            // write pre-init user code here
            bos5 = new Sprite(getBoss_lv5(), 60, 39);//GEN-BEGIN:|1034-getter|1|1034-postInit
            bos5.setFrameSequence(bos5seq001);//GEN-END:|1034-getter|1|1034-postInit
            // write post-init user code here
        }//GEN-BEGIN:|1034-getter|2|
        return bos5;
    }
//GEN-END:|1034-getter|2|

}
