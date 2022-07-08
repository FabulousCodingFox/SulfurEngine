import util.FilePathUtility;
import window.event.Event;
import window.event.Key;
import window.event.KeyEvent;
import window.Window;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.setSize(1280, 860);
        window.setResizable(false);
        window.setTitle("SulfurEngine Demo");
        window.setIcon(FilePathUtility.getResourceFilePath(Main.class, "logo.png")); // TODO: fix this
        window.create();
        window.use();

        while(!window.getShouldClose()){
            ArrayList<Event> eventQueue = window.pollEvents();
            for(Event event : eventQueue){
                if(event instanceof KeyEvent keyEvent){
                    if(keyEvent.getAction() == KeyEvent.KEY_PRESSED){
                        if(keyEvent.getKey() == Key.ESCAPE){
                            window.setShouldClose(true);
                        }
                    }
                }
            }
        }

        window.destroy();
    }
}
