package Code;

import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

public class Designer {

    private static byte[] dat_textFont;
    private static int[] pal_textFont;
    public static final String charIndex = "/0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-.:;<=>?@[\\]^_`{|}~̉´ˇĐđàáảãạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ";
    private static byte[] textCharWidth = {
        6, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6,
        7, 6, 6, 6, 7, 6, 6, 6, 5, 5, 6, 5, 7, 7, 7, 6, 7, 6, 6, 7, 7, 7, 7, 7, 7, 6,
        7, 7, 6, 7, 7, 6, 6, 6, 5, 5, 6, 3, 7, 6, 7, 6, 6, 6, 6, 6, 7, 6, 7, 6, 6, 6,
        3, 5, 7, 7, 8, 8, 3, 5, 5, 7, 7, 4, 6, 3, 3, 4, 6, 6, 6, 7, 8, 4, 6, 4, 5, 7, 4, 5, 3, 5, 8, 4, 4, 5, 6, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        5, 5, 5, 6, 5,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        6, 6, 6, 6, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
        7, 7, 7, 7, 7,
        8
    };
    public static byte space = 4;
    public static byte charTotalHeight = 15;
    public static final byte MARGIN_LEFT = 0;
    public static final byte MARGIN_CENTER = 1;
    public static final byte MARGIN_RIGHT = 2;
    public static int[] rgb = new int[2000];
    Sprite spr;

    public static final byte TRANS_NONE = 0;
    public static final byte TRANS_MIRROR_ROT180 = 1;
    public static final byte TRANS_MIRROR = 2;
    public static final byte TRANS_ROT180 = 3;
    public static final byte TRANS_MIRROR_ROT270 = 4;
    public static final byte TRANS_ROT90 = 5;
    public static final byte TRANS_ROT270 = 6;
    public static final byte TRANS_MIRROR_ROT90 = 7;
    public static int toInt(byte[] b, int offset, int length) {
        int num = 0;
        for (int i = offset, j = offset + length; i < j; i++) {
            if (b[i] < 0) {
                num = (num << 8) + 256 + b[i];
            } else {
                num = (num << 8) + b[i];
            }
        }
        return num;
    }

    private static int[] readPal(InputStream is) {
        byte[] temp = new byte[4];
        int[] pal = null;
        try {
            //read number of color
            is.read(temp, 0, 2);
            pal = new int[toInt(temp, 0, 2)];
            for (int i = 0; i < pal.length; i++) {
                is.read(temp);
                pal[i] = toInt(temp, 0, 4);
            }
        } catch (Exception ex) {
        }
        return pal;
    }

    private static byte[] readData(InputStream is) {
        byte[] temp = new byte[4];
        byte[] data = null;
        try {
            //skip sprite size
            is.skip(4);
            //read image size
            is.read(temp);
            data = new byte[toInt(temp, 0, 4)];
            is.read(data);
        } catch (Exception ex) {
        }
        return data;
    }

    public static String toString(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (b[i] == -128) {
                buf.append('\n');
            } else if (b[i] == -127) {
                buf.append(' ');
            } else {
                buf.append(charIndex.charAt(b[i] + 126));
            }
        }
        return buf.toString();
    }

    public static void toBytesIndex(String str, byte[] b) {
        int length = Math.min(str.length(), b.length);
//        b = toByteIndex(str, length);
        System.arraycopy(toByteIndex(str, length), 0, b, 0, length);
    }

    public static byte[] toByteIndex(String str, int length) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            switch (str.charAt(i)) {
                case '\n':
                    b[i] = -128;
                    break;
                case ' ':
                    b[i] = -127;
                    break;
                default:
                    b[i] = (byte) (charIndex.indexOf(str.charAt(i)) - 126);
                    break;
            }
        }
        return b;
    }

    public static int getCharWidth(byte b) {
        if (b == -127) {
            return space;
        }
        if (b == -128) {
            return 0;
        }
        return textCharWidth[b + 126];

    }

    public static int getStringWidth(byte[] b, int offset, int length) {
        int width = 0;
        for (int i = offset, l = Math.min(offset + length, b.length); i < l; i++) {
            width += getCharWidth(b[i]);
        }
        return width;
    }

    public static int getStringWidth(byte[] b) {
        return getStringWidth(b, 0, b.length);
    }

    public static int renderTextChar(Graphics g, int index, int colorType, int x, int y) {
        if (dat_textFont == null) {
            InputStream is = InputStream.class.getResourceAsStream("/Code/textFont.cmg");
            pal_textFont = readPal(is);
            dat_textFont = readData(is);
        }

        int shift = index * 120;
        int shift_color = colorType * 3;
        int temp;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 8; j++) {
                temp = i * 8 + j;
                rgb[temp] = pal_textFont[dat_textFont[temp + shift] + shift_color];
            }
        }
        g.drawRGB(rgb, 0, 8, x, y, 8, 15, true);
        return textCharWidth[index];
    }

    /**
     * Draw char is encoded by b
     * @param g
     * @param b b = true index - 126, -128 = new line, -127 = space
     * @param colorType
     * @param x
     * @param y
     * @return
     */
    public static int drawChar(Graphics g, byte b, int colorType, int x, int y) {
        if (b == -127) {
            return space;
        }
        return renderTextChar(g, b + 126, colorType, x, y);

    }

    /**
     * type Hpfont 0:damage HP; 1:recover HP; 2:recover MP; 3:positive status; 4: negative status; 5: MISS, recover status
     * @param g
     * @param b
     * @param offset
     * @param length
     * @param colorType
     * @param x
     * @param y 
     * @return width of string
     */
    public static int drawString(Graphics g, byte[] b, int offset, int length, int colorType, int x, int y) {
        int width = 0;
        int memo = x;
        int temp;
        for (int i = offset, l = offset + Math.min(b.length - offset, length); i < l; i++) {
            if (b[i] == -128) {
                x = memo;
                y += charTotalHeight;
            } else {
                temp = drawChar(g, b[i], colorType, x, y);
                width += temp;
                x += temp;
            }
        }
        return width;
    }

    /**
     * Draw string in a region
     * @param g
     * @param b
     * @param offset
     * @param length
     * @param margin
     * @param isWordSplit true: when word is not visible then break word
     * @param ignoreExcessSpace true: reduce all excess space to one space
     * @param colorType type Hpfont 0:damage HP; 1:recover HP; 2:recover MP; 3:positive status; 4: negative status; 5: MISS, recover status
     * @param x
     * @param y
     * @param width
     * @param height 
     * @return number of char is drawn
     */
    public static int drawString(Graphics g, byte[] b, int offset, int length, byte margin, boolean isWordSplit, boolean ignoreExcessSpace, int colorType, int x, int y, int width, int height) {
        int end = y + height - charTotalHeight;
        int index = offset;
        int startIndex = offset;
        int endIndex = offset;
        int lineWidth = 0;
        int charCount = 0;
        int wordWidth = 0;
        length = Math.min(b.length, offset + length);
        while (y <= end && index < length) {
            lineWidth = 0;
            //memo = index;
            if (!isWordSplit) {
                startIndex = -1;
            } else {
                startIndex = index;
            }
            while (index < length) {
                if (b[index] == -128) {
                    index++;
                    break;
                } else if (b[index] == -127) {
                    if (lineWidth + space <= width) {
                        lineWidth += space;
                        index++;
                    } else {
                        break;
                    }
                } else if (!isWordSplit) {
                    if (startIndex == -1) {
                        startIndex = index;
                    }
                    charCount = 0;
                    wordWidth = 0;

                    while (index < length && b[index] != -127 && b[index] != -128) {
                        wordWidth += getCharWidth(b[index]);
                        charCount++;
                        index++;
                    }

                    if (lineWidth + wordWidth <= width) {
                        lineWidth += wordWidth;
                    } else {
                        index -= charCount;
                        break;
                    }
                } else {
                    if (lineWidth + getCharWidth(b[index]) <= width) {
                        lineWidth += getCharWidth(b[index]);
                        index++;
                    } else {
                        break;
                    }
                }
            }

            if (lineWidth != 0) {
                if (startIndex == -1) {
                    startIndex = endIndex;
                }
                endIndex = index - 1;
                if (ignoreExcessSpace) {
                    while (endIndex > startIndex && b[endIndex] == -127) {
                        endIndex--;
                    }
                }
                if (margin == MARGIN_LEFT) {
                    drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x, y);
                } else if (margin == MARGIN_CENTER) {
                    drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + (width - lineWidth) / 2, y);
                } else if (margin == MARGIN_RIGHT) {
                    drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + width - lineWidth, y);
                }
            }
            y += charTotalHeight;
        }
        return index - offset;
    }

    /**
     * Draw string in a region, only char in range of start, end is visible
     * @parm start start draw index
     * @param g
     * @param b
     * @param offset
     * @param length
     * @param start
     * @param end end draw index
     * @param margin
     * @param isWordSplit true: when word is not visible then break word
     * @param ignoreExcessSpace true: reduce all excess space to one space
     * @param colorType
     * @param x
     * @param y
     * @param width
     * @param height
     * @return number of char is drawn
     */
    public static int drawString(Graphics g, byte[] b, int offset, int length, int start, int end, byte margin, boolean isWordSplit, boolean ignoreExcessSpace, int colorType, int x, int y, int width, int height) {
        if (start < offset || start >= offset + length) {
            return 0;
        }
        int endY = y + height - charTotalHeight;
        int index = offset;
        int startIndex = offset;
        int endIndex = offset;
        int lineWidth = 0;
        int charCount = 0;
        int wordWidth = 0;
        length = Math.min(b.length, offset + length);
        end = Math.min(end, offset + length - 1);
        while (y <= endY && index < length) {
            lineWidth = 0;
            //memo = index;
            if (!isWordSplit) {
                startIndex = -1;
            } else {
                startIndex = index;
            }
            while (index < length) {
                if (b[index] == -128) {
                    index++;
                    break;
                } else if (b[index] == -127) {
                    if (lineWidth + space <= width) {
                        lineWidth += space;
                        index++;
                    } else {
                        break;
                    }
                } else if (!isWordSplit) {
                    if (startIndex == -1) {
                        startIndex = index;
                    }
                    charCount = 0;
                    wordWidth = 0;

                    while (index < length && b[index] != -127 && b[index] != -128) {
                        wordWidth += getCharWidth(b[index]);
                        charCount++;
                        index++;
                    }

                    if (lineWidth + wordWidth <= width) {
                        lineWidth += wordWidth;
                    } else {
                        index -= charCount;
                        break;
                    }
                } else {
                    if (lineWidth + getCharWidth(b[index]) <= width) {
                        lineWidth += getCharWidth(b[index]);
                        index++;
                    } else {
                        break;
                    }
                }
            }

            if (lineWidth != 0) {
                if (startIndex == -1) {
                    startIndex = endIndex;
                }
                if (startIndex > end) {
                    break;
                }
                endIndex = index - 1;
                if (ignoreExcessSpace) {
                    while (endIndex > startIndex && b[endIndex] == -127) {
                        endIndex--;
                    }
                }
                if (endIndex > end) {
                    endIndex = end;
                }
                if (start > startIndex && start <= endIndex) {
                    if (margin == MARGIN_LEFT) {
                        drawString(g, b, start, endIndex - start + 1, colorType, x + getStringWidth(b, startIndex, start - startIndex), y);
                    } else if (margin == MARGIN_CENTER) {
                        drawString(g, b, start, endIndex - start + 1, colorType, x + (width - lineWidth) / 2 + getStringWidth(b, startIndex, start - startIndex), y);
                    } else if (margin == MARGIN_RIGHT) {
                        drawString(g, b, start, endIndex - start + 1, colorType, x + width - lineWidth + getStringWidth(b, startIndex, start - startIndex), y);
                    }
                } else {
                    if (margin == MARGIN_LEFT) {
                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x, y);
                    } else if (margin == MARGIN_CENTER) {
                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + (width - lineWidth) / 2, y);
                    } else if (margin == MARGIN_RIGHT) {
                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + width - lineWidth, y);
                    }
                }
                if (endIndex == end) {
                    break;
                }
            }
            y += charTotalHeight;
        }
        return index - offset;
    }
    /**
     * @param isWordSplit true: when word is not visible then break word
     * @param ignoreExcessSpace true: reduce all excess space to one space
     * @param colorType type Hpfont 0:damage HP; 1:recover HP; 2:recover MP; 3:positive status; 4: negative status; 5: MISS, recover status
     * @return number of char is drawn
     */
//    public static final int drawCycleString(Graphics g, byte[] b, int offset, int length, int start, byte margin, boolean isWordSplit, boolean ignoreExcessSpace, int colorType, int x, int y, int width, int height){
//        int end = y + height - charTotalHeight;
//        int index;
//        int startIndex;
//        int endIndex;
//        int lineWidth = 0;
//        int charCount = 0;
//        int wordWidth = 0;
//        int count = 0;
//        length = Math.min(b.length - offset, length);
//        if(start < offset || start >= offset + length)
//            start = offset;
//        index = start;
//        startIndex = start;
//        endIndex = start;
//        while(y <= end && count < length){
//            lineWidth = 0;
//            //memo = index;
//            if(!isWordSplit)
//                startIndex = -1;
//            else
//                startIndex = index;
//            while(count < length){
//                if(b[index] == -128){
//                    index++;
//                    if(index >= offset + length)
//                        index = offset;
//                    count++;
//                    break;
//                }
//                else if(b[index] == -127){
//                    if(lineWidth + space <= width){
//                        lineWidth += space;
//                        index++;
//                        if(index >= offset + length)
//                            index = offset;
//                        count++;
//                    }
//                    else
//                        break;
//                }
//                else if(!isWordSplit){
//                    if(startIndex == -1)
//                        startIndex = index;
//                    charCount = 0;
//                    wordWidth = 0;
//
//                    while(count < length && b[index] != -127 && b[index] != -128){
//                        wordWidth += getCharWidth(b[index]);
//                        charCount++;
//                        index++;
//                        if(index >= offset + length)
//                            index = offset;
//                        count++;
//                    }
//
//                    if(lineWidth + wordWidth <= width)
//                        lineWidth += wordWidth;
//                    else{
//                        index -= charCount;
//                        if(index < offset)
//                            index = length + index;
//                        count -= charCount;
//                        break;
//                    }
//                }
//                else{
//                    if(lineWidth + getCharWidth(b[index]) <= width){
//                        lineWidth += getCharWidth(b[index]);
//                        index++;
//                        if(index >= offset + length)
//                            index = offset;
//                        count++;
//                    }
//                    else
//                        break;
//                }
//            }
//
//            if(lineWidth != 0){
//                if(startIndex == -1)
//                    startIndex = endIndex;
//                endIndex = index - 1;
//                if(endIndex < offset)
//                    endIndex = offset + length - 1;
//                if(ignoreExcessSpace)
//                    while(endIndex != startIndex && b[endIndex] == -127){
//                        endIndex--;
//                        if(endIndex < offset)
//                            endIndex = offset + length - 1;
//                    }
//                if(endIndex >= startIndex){
//                    if(margin == MARGIN_LEFT)
//                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x, y);
//                    else if (margin == MARGIN_CENTER)
//                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + (width - lineWidth) / 2, y);
//                    else if (margin == MARGIN_RIGHT)
//                        drawString(g, b, startIndex, endIndex - startIndex + 1, colorType, x + width - lineWidth, y);
//                }
//                else{
//                    int w;
//                    if(margin == MARGIN_LEFT){
//                        w = drawString(g, b, startIndex, offset + length - startIndex, colorType, x, y);
//                        drawString(g, b, offset, endIndex - offset + 1, colorType, x + w, y);
//                    }
//                    else if(margin == MARGIN_CENTER){
//                        w = drawString(g, b, startIndex, offset + length - startIndex, colorType, x + (width - lineWidth) / 2, y);
//                        drawString(g, b, offset, endIndex - offset + 1, colorType, x + (width - lineWidth) / 2 + w, y);
//                    }
//                    else if(margin == MARGIN_LEFT){
//                        w = drawString(g, b, startIndex, offset + length - startIndex, colorType, x + width - lineWidth, y);
//                        drawString(g, b, offset, endIndex - offset + 1, colorType, x + + width - lineWidth + w, y);
//                    }
//                }
//            }
//            y += charTotalHeight;
//        }
//        return count;
//    }
//
//    /**
//     * type Hpfont 0:damage HP; 1:recover HP; 2:recover MP; 3:positive status; 4: negative status; 5: MISS, recover status
//     * @return width of string
//     */
    public static final int drawCenterString(Graphics g, byte[] b, int offset, int length, int colorType, int x, int y){
        return drawString(g, b, offset, length, colorType, x - getStringWidth(b, offset, length) / 2, y);
    }
//
//    /**
//     * draw pull-out effect string
//     * @param num number of string is pulled in one side
//     * @return width of string
//     */
//    public static final int drawPulledString(Graphics g, byte[] b, int offset, int length, int num, int distance, int colorType, int x, int y){
//        length = Math.min(b.length - offset, length);
//        if(num > length / 2)
//            return drawCenterString(g, b, offset, length, colorType, x, y);
//        int w1 = 0;
//        int w2 = 0;
//        for(int i = offset, k = offset + num; i < k; i ++)
//            w1 += getCharWidth(b[i]);
//        for(int i = offset + length - 1, k = offset + length - 1 - num; i > k; i--)
//            w2 += getCharWidth(b[i]);
//
//        drawString(g, b, offset, num, colorType, x - (w1 + w2) / 2 - distance / 2, y);
//        drawString(g, b, offset + length - num, num, colorType, x + (w1 - w2) / 2 + (distance + 1) / 2, y);
//        return w1 + w2 + distance;
//    }
//
//    /**
//     * @return width of string
//     */
    public static final int drawNumber(Graphics g, long num, int colorType, int x, int y){
        int width = 0;
        byte c;
        while(num != 0){
            c = (byte)(num % 10 - 125);
            num = num / 10;
            width += drawChar(g, c, colorType, x - width, y);
        }
        return width;
    }
//    public static int drawString(Graphics g,String str, int offset, int length, int colorType, int x, int y){
//       byte[] b = new byte[str.length()];
//       toBytesIndex(str, b);
//       return drawString(g, b, offset, length, colorType, x, y);
//    }
//    public static int drawString(Graphics g,String str, int offset, int length, byte margin, boolean isWordSplit, boolean ignoreExcessSpace, int colorType, int x, int y, int width, int height){
//       byte[] b = new byte[str.length()];
//       toBytesIndex(str, b);
//       return drawString(g, b, offset, length, margin, isWordSplit, ignoreExcessSpace, colorType, x, y, width, height);
//
//    }
//
////
//    public static int drawString(Graphics g, String str, int offset, int length, int start, int end, byte margin, boolean isWordSplit, boolean ignoreExcessSpace, int colorType, int x, int y, int width, int height){
//       byte[] b = new byte[str.length()];
//       toBytesIndex(str, b);
//       return drawString(g, b, offset, length, start, end, margin, isWordSplit, ignoreExcessSpace, colorType, x, y, width, height);
//    }
//
//
//    public static Image createThumbnail(Image image,int width) {
//        int sourceWidth = image.getWidth();
//        int sourceHeight = image.getHeight();
//
//        int thumbWidth = width;
//        int thumbHeight =  thumbWidth * sourceHeight / sourceWidth;
//
//        Image thumb = Image.createImage(thumbWidth, thumbHeight);
//        Graphics g = thumb.getGraphics();
//        float disX = sourceWidth/thumbWidth;
//        float disY = sourceHeight/thumbHeight;
//        for (int y = 0; y < thumbHeight; y++) {
//            for (int x = 0; x < thumbWidth; x++) {
//                g.setClip(x, y, 1, 1);
//                int dx = (int)(x * sourceWidth/thumbWidth);
//                int dy = (int)(y * sourceHeight/thumbHeight);
//                g.drawImage(image, x - dx, y - dy, Graphics.LEFT | Graphics.TOP);
//            }
//        }
//        return thumb;
//    }
//
//
//    public static Image createThumbnail(Image image,int width1,int width2) {
//        if(width1>=240)
//            return image;
//        int sourceWidth = image.getWidth();
//        int thumbWidth = sourceWidth*width1/width2;
//        return createThumbnail(image, thumbWidth);
//    }
//private static final int FP_SHIFT = 13;
//  private static final int FP_ONE = 1 << FP_SHIFT;
//  private static final int FP_HALF = 1 << (FP_SHIFT - 1);
//
//  // resampling modes - valid values for the mode parameter of resizeImage()
//  // any other value will default to MODE_BOX_FILTER because of the way the conditionals are set in resizeImage()
//  public static final int MODE_POINT_SAMPLE = 0;
//  public static final int MODE_BOX_FILTER = 1;
//
//  /**
//   * getPixels
//   * Wrapper for pixel grabbing techniques.
//   * I separated this step into it's own function so that other APIs (Nokia, Motorola, Siemens, etc.) can
//   * easily substitute the MIDP 2.0 API (Image.getRGB()).
//   * @param src The source image whose pixels we are grabbing.
//   * @return An int array containing the pixels in 32 bit ARGB format.
//   */
//  static int[] getPixels(Image src) {
//    int w = src.getWidth();
//    int h = src.getHeight();
//    int[] pixels = new int[w * h];
//    src.getRGB(pixels,0,w,0,0,w,h);
//    return pixels;
//  }
//
//  /**
//   * drawPixels
//   * Wrapper for pixel drawing function.
//   * I separated this step into it's own function so that other APIs (Nokia, Motorola, Siemens, etc.) can
//   * easily substitute the MIDP 2.0 API (Image.createRGBImage()).
//   * @param pixels int array containing the pixels in 32 bit ARGB format.
//   * @param w The width of the image to be created.
//   * @param h The height of the image to be created. This parameter is actually superfluous, because it
//   * must equal pixels.length / w.
//   * @return The image created from the pixel array.
//   */
//  static Image drawPixels(int[] pixels, int w, int h) {
//    return Image.createRGBImage(pixels,w,h,true);
//  }
//
//  static Image resizeImage(Image src, float factor) {
//    return resizeImage(src, factor, MODE_BOX_FILTER);
//  }
//
//
//  static Image resizeImage(Image src,int width,int height, int mode) {
//    int srcW = src.getWidth();
//    int srcH = src.getHeight();
//    int destW = (int)width;
//    int destH = (int)height;
//
//    // create pixel arrays
//    int[] destPixels = new int[destW * destH]; // array to hold destination pixels
//    int[] srcPixels = getPixels(src); // array with source's pixels
//
//    if (mode == MODE_POINT_SAMPLE) {
//      // simple point smapled resizing
//      // loop through the destination pixels, find the matching pixel on the source and use that
//      for (int destY = 0; destY < destH; ++destY) {
//        for (int destX = 0; destX < destW; ++destX) {
//          int srcX = (destX * srcW) / destW;
//          int srcY = (destY * srcH) / destH;
//          destPixels[destX + destY * destW] = srcPixels[srcX + srcY * srcW];
//        }
//      }
//    }
//    else {
//      // precalculate src/dest ratios
//      int ratioW = (srcW << FP_SHIFT) / destW;
//      int ratioH = (srcH << FP_SHIFT) / destH;
//
//      int[] tmpPixels = new int[destW * srcH]; // temporary buffer for the horizontal resampling step
//
//      // variables to perform additive blending
//      int argb; // color extracted from source
//      int a, r, g, b; // separate channels of the color
//      int count; // number of pixels sampled for calculating the average
//
//      // the resampling will be separated into 2 steps for simplicity
//      // the first step will keep the same height and just stretch the picture horizontally
//      // the second step will take the intermediate result and stretch it vertically
//
//      // horizontal resampling
//      for (int y = 0; y < srcH; ++y) {
//        for (int destX = 0; destX < destW; ++destX) {
//          count = 0; a = 0; r = 0; b = 0; g = 0; // initialize color blending vars
//          int srcX = (destX * ratioW) >> FP_SHIFT; // calculate beginning of sample
//          int srcX2 = ((destX + 1) * ratioW) >> FP_SHIFT; // calculate end of sample
//
//          // now loop from srcX to srcX2 and add up the values for each channel
//          do {
//            argb = srcPixels[srcX + y * srcW];
//            a += ((argb & 0xff000000) >> 24); // alpha channel
//            r += ((argb & 0x00ff0000) >> 16); // red channel
//            g += ((argb & 0x0000ff00) >> 8); // green channel
//            b += (argb & 0x000000ff); // blue channel
//            ++count; // count the pixel
//            ++srcX; // move on to the next pixel
//          }
//          while (srcX <= srcX2 && srcX + y * srcW < srcPixels.length);
//
//          // average out the channel values
//          a /= count;
//          r /= count;
//          g /= count;
//          b /= count;
//
//          // recreate color from the averaged channels and place it into the temporary buffer
//          tmpPixels[destX + y * destW] = ((a << 24) | (r << 16) | (g << 8) | b);
//        }
//      }
//
//      // vertical resampling of the temporary buffer (which has been horizontally resampled)
//      for (int x = 0; x < destW; ++x) {
//        for (int destY = 0; destY < destH; ++destY) {
//          count = 0; a = 0; r = 0; b = 0; g = 0; // initialize color blending vars
//          int srcY = (destY * ratioH) >> FP_SHIFT; // calculate beginning of sample
//          int srcY2 = ((destY + 1) * ratioH) >> FP_SHIFT; // calculate end of sample
//
//          // now loop from srcY to srcY2 and add up the values for each channel
//          do {
//            argb = tmpPixels[x + srcY * destW];
//            a += ((argb & 0xff000000) >> 24); // alpha channel
//            r += ((argb & 0x00ff0000) >> 16); // red channel
//            g += ((argb & 0x0000ff00) >> 8); // green channel
//            b += (argb & 0x000000ff); // blue channel
//            ++count; // count the pixel
//            ++srcY; // move on to the next pixel
//          }
//          while (srcY <= srcY2 && x + srcY * destW < tmpPixels.length);
//
//          // average out the channel values
//          a /= count; a = (a > 255) ? 255 : a;
//          r /= count; r = (r > 255) ? 255 : r;
//          g /= count; g = (g > 255) ? 255 : g;
//          b /= count; b = (b > 255) ? 255 : b;
//
//          // recreate color from the averaged channels and place it into the destination buffer
//          destPixels[x + destY * destW] = ((a << 24) | (r << 16) | (g << 8) | b);
//        }
//      }
//    }
//
//    // return a new image created from the destination pixel buffer
//    return drawPixels(destPixels,destW,destH);
//  }
//
//
//
//  /**
//   * resizeImage
//   * Gets a source image along with new size for it and resizes it.
//   * @param src The source image.
//   * @param destW The new width for the destination image.
//   * @param destH The new heigth for the destination image.
//   * @param mode A flag indicating what type of resizing we want to do. It currently supports two type:
//   * MODE_POINT_SAMPLE - point sampled resizing, and MODE_BOX_FILTER - box filtered resizing (default).
//   * @return The resized image.
//   */
//
//  static Image resizeImage(Image src, float factor, int mode) {
//    int srcW = src.getWidth();
//    int srcH = src.getHeight();
//    int destW = (int)(srcW * factor);
//    int destH = (int)(srcH * factor);
//    return resizeImage(src, destW, destH, mode);
//  }
//
//
//  public static void gradientBox(Graphics g, int color1, int color2, int left, int top, int width, int height, int orientation, int vast) {
//        int max = orientation == 0 ? height : width;
//        for (int i = 0; i < max; i++) {
//            int color = midColor(color1, color2, max * (max - 1 - i) / (max - 1), max);
//            g.setColor(color);
//            if (orientation == 0) {
//                if(vast>0){
//                    if(i<=vast-1)
//                        g.drawLine(left+(vast-i), top + i, left + width - (vast-i), top + i);
//                    else if(i>max-1-vast){
//                        g.drawLine(left+i-max+1+vast, top + i, left + width - (i-max+1+vast), top + i);
//                    }
//                    else
//                        g.drawLine(left, top + i, left + width - 1, top + i);
//                }
//                else
//                    g.drawLine(left, top + i, left + width - 1, top + i);
//            }
//
//            else {
//                g.drawLine(left + i, top, left + i, top + height - 1);
//            }
//        }
//    }
//
//
//  static int midColor(int color1, int color2, int prop, int max) {
//        int red =
//                (((color1 >> 16) & 0xff) * prop +
//                ((color2 >> 16) & 0xff) * (max - prop)) / max;
//
//        int green =
//                (((color1 >> 8) & 0xff) * prop +
//                ((color2 >> 8) & 0xff) * (max - prop)) / max;
//
//        int blue =
//                (((color1) & 0xff) * prop +
//                ((color2) & 0xff) * (max - prop)) / max;
//
//        int color = red << 16 | green << 8 | blue;
//
//        return color;
//    }
//
//  static int makeColorAsInt(int alpha, int red, int green, int blue) {
//        return alpha << 24 | red << 16 | green << 8 | blue;
//    }
//
//
}
