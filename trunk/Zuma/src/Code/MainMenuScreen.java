package Code;


import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public class MainMenuScreen extends  List implements CommandListener {
    private StartMidlet Midlet;
    private Command select = new Command("Select", Command.ITEM,1);
    private Command exit = new Command("Exit", Command.EXIT,1);
    
    public MainMenuScreen( StartMidlet Midlet) {
        super("SaveTheNature",Choice.IMPLICIT);
        this.Midlet = Midlet;
        append("New Game", null);
        append("Exit", null);
        addCommand(exit);
        addCommand(select);
    }

    public void commandAction(Command c, Displayable d) {
        if(c == exit) 
            Midlet.destroyApp(true);
        else if ( c == select ) {
            try {
                processMenu();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processMenu() throws Exception {
        List down = (List)Midlet.display.getCurrent();
        switch (down.getSelectedIndex()) {
            case 0: Midlet.gameScreenShow(); break;
            case 1: Midlet.destroyApp(true);
        };
    }

}