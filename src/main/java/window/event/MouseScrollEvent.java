package window.event;

public class MouseScrollEvent extends Event{
    private final double xoffset, yoffset;

    public MouseScrollEvent(double x, double y){
        this.xoffset = x;
        this.yoffset = y;
    }

    public double getX(){
        return xoffset;
    }

    public double getY(){
        return yoffset;
    }
}
