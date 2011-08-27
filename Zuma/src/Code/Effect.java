/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

/**
 *
 * @author Admin
 */
public class Effect {
    int EffectType;
    ZumaCanvas zumaCanvas;
    int Slowite = 0;
    
    public Effect( ZumaCanvas zumaCanvas, int EffectType ) {
        this.zumaCanvas = zumaCanvas;
        this.EffectType = EffectType;
        //if ( EffectType == 1 )  SlowEf();
        //else if ( EffectType == 2 ) BackEf();
    }

    public void SlowEf() {
        System.out.println(" " + Slowite );
        if ( Slowite % 3 == 0 || Slowite % 3 == 2 ) zumaCanvas.Stop = false;
        else if ( Slowite % 3 == 1 ) zumaCanvas.Stop = true;
        Slowite++;
    }

    public void BackEf() {
        zumaCanvas.Back = true;
    }
}
