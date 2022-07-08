package window.event;

public class MouseMoveEvent extends Event{
    private final double x, y;

    public MouseMoveEvent(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
