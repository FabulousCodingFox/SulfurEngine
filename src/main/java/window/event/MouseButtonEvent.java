package window.event;

public class MouseButtonEvent extends Event{
    private final int button;
    private final int action;

    public static int BUTTON_PRESSED = 0;
    public static int BUTTON_RELEASED = 1;
    public static int BUTTON_REPEATED = 2;

    public MouseButtonEvent(int button, int action){
        this.button = button;
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public int getButton() {
        return button;
    }
}
