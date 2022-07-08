package window.event;

public class KeyEvent extends Event{
    private final int key;
    private final int action;

    public static int KEY_PRESSED = 0;
    public static int KEY_RELEASED = 1;
    public static int KEY_REPEATED = 2;

    public KeyEvent(int action, int key){
        this.key = key;
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }
}
