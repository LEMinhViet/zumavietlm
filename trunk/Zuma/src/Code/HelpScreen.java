package Code;

import Code.StartMidlet;
import javax.microedition.lcdui.*;

public class HelpScreen extends Form implements CommandListener {
  private StartMidlet startmidlet;

  private Command backCommand = new Command("Back", Command.BACK, 1);

  public HelpScreen (StartMidlet startmidlet) throws Exception {
    super("Help");
    this.startmidlet = startmidlet;
    StringItem stringItem;
    if ( startmidlet.sunnetCanvas.langId == 1 ) {
        stringItem = new StringItem(null, "Game Zuma : \n"
            + "Sử dụng các phím : \n"
            + "Trái - Phải để quay hoặc di chuyển bọ cạp ( một số bài là dùng phím Lên - Xuống ) \n\n"
            + "Lên để đổi bóng ( một số bài dùng phím Phải ) \n\n"
            + "Xuống để quay ngược bọ cạp ( một số bài dùng phím trái ) \n\n"
            + "3 quả bóng đồng màu trở lên sẽ nổ, nếu 2 bóng ở 2 đầu cùng màu thì sẽ được nối lại. "
            + "Càng nhiều combo thì sẽ được nhân lên càng nhiều điểm "
            + "Xóa bỏ toàn bộ bóng trước khi chúng đến điểm kết thúc của level !!!"
            + "\n"
            + "Có rất nhiều Item trong game để các bạn khám phá ... Chúc vui vẻ !!" );
    } else {
        stringItem = new StringItem(null, "One Two Three Game : \n"
            + "Use : \n"
            + "LEFT - RIGHT for rotating the Scorpion ( UP - DOWN with some levels \n\n"
            + "UP for exchange the color of shoot ball ( RIGHT with some levels ) \n\n"
            + "DOWN for rotate the Scorpion 180 degrees ( LEFT with some levels ) \n\n"
            + "With 3 or more balls with same color, they will disapear, if the ball at left and the ball at right of them have the same color, they will be connected \n\n"
            + "The more combo you have, the more your Point will be rised"
            + "Make all balls disapear before they reach the finish point !!!"
            + "\n"
            + "A lots of Item are waitting for your adventure .... Have Fun !!");
    }
    append(stringItem);
    addCommand(backCommand);
    setCommandListener(this);
}

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      startmidlet.display.setCurrent(startmidlet.sunnetCanvas);
      return;
    }
  }
}