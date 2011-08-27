package Code;


import Code.StartMidlet;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;

public class Designer {    
  


  private static byte[] dat_textFont;
    private static int[] pal_textFont;
   
    private static String charIndex = "/0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-.:;<=>?@[\\]^_`{|}~̉´ˇĐđàáảãạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰͦ";
    private static byte[] textCharWidth = {
      6, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6,
      7, 6, 6, 6, 7, 6, 6, 6, 5, 5, 6, 5, 7, 7, 7, 6, 7, 6, 6, 7, 7, 7, 7, 7, 7, 6,
      7, 7, 6, 7, 7, 6, 6, 6, 5, 5, 6, 3, 7, 6, 7, 6, 6, 6, 6, 6, 7, 6, 7, 6, 6, 6,
      3, 5, 7, 7, 8, 8, 3, 5, 5, 7, 7, 4, 6, 3,  3, 4, 6, 6, 6, 7, 8, 4, 6, 4, 5, 7, 4, 5, 3, 5, 8, 4, 4, 5, 6, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      5, 5, 5, 6, 5,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      8
    };

    public static byte[] space = {4, 6, 10,4};
    public static byte[] charTotalHeight = {15, 9, 23, 14};
    public static final byte FONT_TEXT = 0;
    public static final byte FONT_TEXT_SMALL = 3;
    public static final byte FONT_HP = 1;
    public static final byte FONT_TITLE = 2;
    public static final byte MARGIN_LEFT = 0;
    public static final byte MARGIN_CENTER = 1;
    public static final byte MARGIN_RIGHT = 2;
     
    public static int borderColor;

   
    public static int[] rgb = new int[2000];

    public static byte[] leftSideRef = { 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 6, 8, 10, 12};

    public static final byte TRANS_NONE = 0;
    public static final byte TRANS_MIRROR = 2;
    public static final byte TRANS_ROT90 = 5;
    public static final byte TRANS_ROT180 = 3;
    public static final byte TRANS_ROT270 = 6;
    
    private static final byte[] loadDatFile(String file){
        InputStream is = StartMidlet.sunnetFlash.getClass().getResourceAsStream(file);
        byte[] dat = null;
        try {
            dat = new byte[is.available()];
            is.read(dat);
            is.close();
        } catch (IOException ex) {}
        return dat;
    }

    private static final int[] loadPalFile(String file){
        InputStream is = StartMidlet.sunnetFlash.getClass().getResourceAsStream(file);
        int[] pal = null;
        try {
            pal = new int[is.available() / 4];
            byte[] b = new byte[4];
            for(int i = 0; i < pal.length; i++){
                is.read(b);
                pal[i] = toInt(b, 0, 4);
            }
            is.close();
        } catch (IOException ex) {}
        return pal;
    }

    public static final int toInt(byte[] b, int offset, int length){
        int num = 0;
        for(int i = offset, j = offset + length; i < j; i++)
            if(b[i] < 0)
                num = (num << 8) + 256 + b[i];
            else
                num = (num << 8) + b[i];
        return num;
    }
    

    public static final String toString(byte[] b){
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < b.length; i++)
            if(b[i] == -128)
                buf.append('\n');
            else if(b[i] == -127)
                buf.append(' ');
            else
                buf.append(charIndex.charAt(b[i] + 126));
        return buf.toString();
    }

    public static final void toBytesIndex(String str, byte[] b){
        int length = Math.min(str.length(), b.length);
        for(int i = 0; i < length; i++)
            switch(str.charAt(i)){
                case '\n':
                    b[i] = -128;
                    break;
                case  ' ':
                    b[i] =  -127;
                    break;
                default:
                    b[i] = (byte)(charIndex.indexOf(str.charAt(i)) - 126);
                    break;
            }
    }

    

    public static final int getCharWidth(byte b, byte type){
        if(b == -127)
            return space[type];
        if(b == -128)
            return 0;
        
        if(type == FONT_TEXT)
            return textCharWidth[b + 126];
             
        return 0;
    }

    public static final int getStringWidth(byte[] b, int offset, int length, byte type){
        int width = 0;
        for(int i = offset, l = Math.min(offset + length, b.length); i < l; i++)
            width += getCharWidth(b[i], type);
        return width;
    }

    public static final int renderTextChar(Graphics g, int index, int colorType, int x, int y){
      if(pal_textFont == null){
            pal_textFont = loadPalFile("/Code/textfont.pal");
            Runtime.getRuntime().gc();
        }
        if(dat_textFont == null){
            dat_textFont = loadDatFile("/Code/textfont.dat");
            Runtime.getRuntime().gc();
        }

        int shift = index * 120;
        int shift_color = colorType * 3;
        int temp;
        for(int i = 0; i < 15; i++)
            for(int j = 0; j < 8; j++){
                temp = i * 8 + j;
                rgb[temp] = pal_textFont[dat_textFont[temp + shift] + shift_color];
            }
        g.drawRGB(rgb, 0, 8, x, y, 8, 15, true);
        return textCharWidth[index];
    }   


    /**
     * Draw char is encoded by b
     * @param b b = true index - 126, -128 = new line, -127 = space
     * @return
     */
    public static final int drawChar(Graphics g, byte b, byte type, int colorType, int x, int y){
        if(b == -127)
            return space[type];
        if(type == FONT_TEXT)
            return renderTextChar(g, b + 126, colorType, x, y);
        return 0;
    }  

    public static final int drawString(Graphics g, byte[] b, int offset, int length, byte type, int colorType, int x, int y){
        int width = 0;
        int memo = x;
        int temp;
        for(int i = offset, l = offset + Math.min(length, b.length); i < l; i++)
            if(b[i] == -128){
                x = memo;
                y += charTotalHeight[type];
            }
            else{
                temp = drawChar(g, b[i], type, colorType, x, y);
                width += temp;
                x += temp;
            }

        return width;
    }

    public static final int drawString(Graphics g, byte[] b, int offset, int length, byte type, byte margin, boolean isWordSplit, int colorType, int x, int y, int width, int height){
        int end = y + height - charTotalHeight[type];
        int index = offset;
        int startIndex;
        int endIndex;
        int lineLength = 0;
        int charCount = 0;
        int wordLength = 0;
        length = Math.min(length + offset, b.length);
        while(y <= end && index < length){
            lineLength = 0;
            //memo = index;
            if(!isWordSplit)
                startIndex = -1;
            else
                startIndex = index;
            while(index < length){
                if(b[index] == -128){
                    index++;
                    break;
                }
                else if(b[index] == -127){
                    if(lineLength + space[type] <= width){
                        lineLength += space[type];
                        index++;
                    }
                    else
                        break;
                }
                else if(!isWordSplit){
                    if(startIndex == -1)
                        startIndex = index;
                    charCount = 0;
                    wordLength = 0;

                    while(index < length && b[index] != -127 && b[index] != -128){
                        wordLength += getCharWidth(b[index], type);
                        charCount++;
                        index++;
                    }

                    if(lineLength + wordLength <= width)
                        lineLength += wordLength;                    
                    else{
                        index -= charCount;
                        break;
                    }
                }
                else{
                    if(lineLength + getCharWidth(b[index], type) <= width){
                        lineLength += getCharWidth(b[index], type);
                        index++;
                    }
                    else
                        break;
                }
            }

            if(lineLength != 0){
                endIndex = index - 1;
                while(endIndex > startIndex && b[endIndex] == -127)
                        endIndex--;                
                if(margin == MARGIN_LEFT)
                    drawString(g, b, startIndex, endIndex - startIndex + 1, type, colorType, x, y);
                else if (margin == MARGIN_CENTER)
                    drawString(g, b, startIndex, endIndex - startIndex + 1, type, colorType, x + (width - lineLength) / 2, y);
                else if (margin == MARGIN_RIGHT)
                    drawString(g, b, startIndex, endIndex - startIndex + 1, type, colorType, x + width - lineLength, y);
            }
            y += charTotalHeight[type];
        }
        return index - offset;
    }

    public static final int drawCenterString(Graphics g, byte[] b, int offset, int length, byte type, int colorType, int x, int y){
        return drawString(g, b, offset, length, type, colorType, x - getStringWidth(b, offset, length, type) / 2, y);
    }

    /**
     * draw pull-out effect string
     * @param num number of string is pulled in one side
     * @return width of string
     */
    public static final int drawPulledString(Graphics g, byte[] b, int offset, int length, int num, int distance, byte type, int colorType, int x, int y){
        length = Math.min(b.length - offset, length);
        if(num > length / 2)
            return drawCenterString(g, b, offset, length, type, colorType, x, y);
        int w1 = 0;
        int w2 = 0;
        for(int i = offset, k = offset + num; i < k; i ++)
            w1 += getCharWidth(b[i], type);
        for(int i = offset + length - 1, k = offset + length - 1 - num; i > k; i--)
            w2 += getCharWidth(b[i], type);

        drawString(g, b, offset, num, type, colorType, x - (w1 + w2) / 2 - distance / 2, y);
        drawString(g, b, offset + length - num, num, type, colorType, x + (w1 - w2) / 2 + (distance + 1) / 2, y);
        return w1 + w2 + distance;
    }

    public static final int drawNumber(Graphics g, long num, byte type, int colorType, int x, int y){
        int width = 0;
        byte c;
        while(num != 0){
            c = (byte)(num % 10 - 125);
            num = num / 10;
            width += drawChar(g, c, type, colorType, x - width, y);
        }
        return width;
    }
}
