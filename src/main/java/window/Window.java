package window;

import org.lwjgl.glfw.*;
import util.ImageParser;
import window.event.*;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long windowHandle;
    private boolean WINDOW_CREATED = false;
    private static boolean GLFW_INITIALIZED = false;

    private String title;
    private int width, height, refreshRate, posX, posY;
    private boolean fullscreen, vsync, shown, resizable, mouseLocked;
    private String iconPath;

    private final ArrayList<Event> eventQueue;

    public Window(){
        this.title = "<SulfurEngine>";
        this.width = 640;
        this.height = 480;
        this.fullscreen = false;
        this.vsync = false;
        this.refreshRate = 60;
        this.mouseLocked = false;
        this.shown = true;
        this.resizable = true;
        this.posX = 0;
        this.posY = 0;
        this.eventQueue = new ArrayList<>();
        this.iconPath = "";
    }

    public void setTitle(String title){
        this.title = title;
        if(WINDOW_CREATED){
            glfwSetWindowTitle(windowHandle, title);
        }
    }
    public String getTitle(){
        return title;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        if(WINDOW_CREATED){
            glfwSetWindowSize(windowHandle, width, height);
        }
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void setRefreshRate(int refreshRate){
        this.refreshRate = refreshRate;
        if(WINDOW_CREATED){
            glfwSetWindowMonitor(
                    windowHandle,
                    fullscreen ? glfwGetPrimaryMonitor() : NULL,
                    getWindowPositionX(),
                    getWindowPositionY(),
                    getWidth(),
                    getHeight(),
                    getRefreshRate()
            );

        }
    }
    public int getRefreshRate(){
        return refreshRate;
    }

    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;
        if(WINDOW_CREATED){
            glfwSetWindowMonitor(
                    windowHandle,
                    fullscreen ? glfwGetPrimaryMonitor() : NULL,
                    getWindowPositionX(),
                    getWindowPositionY(),
                    getWidth(),
                    getHeight(),
                    getRefreshRate()
            );
        }
    }
    public boolean getFullscreen(){
        return fullscreen;
    }

    public void setWindowPosition(int x, int y){
        this.posX = x;
        this.posY = y;
        if(WINDOW_CREATED){
            glfwSetWindowPos(windowHandle, x, y);
        }
    }
    public int getWindowPositionX(){
        return posX;
    }
    public int getWindowPositionY(){
        return posY;
    }

    public void setVSync(boolean vSync){
        this.vsync = vSync;
        if(WINDOW_CREATED){
            glfwSwapInterval(vsync ? 1 : 0);
        }
    }
    public boolean getVSync(){
        return vsync;
    }

    public void setVisible(boolean shown){
        this.shown = shown;
        if(WINDOW_CREATED){
            if(shown) glfwShowWindow(windowHandle);
            if(!shown) glfwHideWindow(windowHandle);
        }
    }
    public boolean getVisible(){
        return shown;
    }

    public void setResizable(boolean resizable){
        this.resizable = resizable;
        if(WINDOW_CREATED){
            glfwSetWindowAttrib(windowHandle, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        }
    }
    public boolean getResizable(){
        return resizable;
    }

    public void setMouseLocked(boolean locked){
        this.mouseLocked = locked;
        if(WINDOW_CREATED){
            glfwSetInputMode(windowHandle, GLFW_CURSOR, mouseLocked ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
        }
    }
    public boolean getMouseLocked(){
        return mouseLocked;
    }



    public void setIcon(String path){
        this.iconPath = path;
        if(WINDOW_CREATED){
            glfwSetWindowIcon(windowHandle, ImageParser.getImageBuffer(path, false));
        }
    }

    public boolean getShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }

    public void create(){
        if(WINDOW_CREATED) return;

        if(!GLFW_INITIALIZED){
            GLFWErrorCallback.createPrint(System.err).set();
            if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
            GLFW_INITIALIZED = true;
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, getResizable() ? GLFW_TRUE : GLFW_FALSE);
        //glfwWindowHint(GLFW_CURSOR, getMouseLocked() ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL); //TODO: GLFW_INVALID_ENUM error: Invalid window hint 0x00033001
        glfwWindowHint(GLFW_REFRESH_RATE, getRefreshRate());
        this.windowHandle = glfwCreateWindow(getWidth(), getHeight(), getTitle(), getFullscreen() ? glfwGetPrimaryMonitor() : NULL, NULL);
        if(windowHandle == NULL) throw new RuntimeException("Failed to create GLFW window");
        WINDOW_CREATED = true;

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if(action == GLFW_PRESS){
                eventQueue.add(new KeyEvent(KeyEvent.KEY_PRESSED, key));
            }else if(action == GLFW_RELEASE){
                eventQueue.add(new KeyEvent(KeyEvent.KEY_RELEASED, key));
            }else if(action == GLFW_REPEAT){
                eventQueue.add(new KeyEvent(KeyEvent.KEY_REPEATED, key));
            }
        });

        glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            if(action == GLFW_PRESS){
                eventQueue.add(new MouseButtonEvent(MouseButtonEvent.BUTTON_PRESSED, button));
            }else if(action == GLFW_RELEASE){
                eventQueue.add(new MouseButtonEvent(MouseButtonEvent.BUTTON_RELEASED, button));
            }else if(action == GLFW_REPEAT){
                eventQueue.add(new MouseButtonEvent(MouseButtonEvent.BUTTON_REPEATED, button));
            }
        });

        glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            eventQueue.add(new MouseMoveEvent(xpos, ypos));
        });

        glfwSetScrollCallback(windowHandle, (window, xoffset, yoffset) -> {
            eventQueue.add(new MouseScrollEvent(xoffset, yoffset));
        });

        stackPush();
        glfwSwapInterval(vsync ? 1 : 0);
        if(!this.iconPath.equals("")) setIcon(this.iconPath);
        if(getVisible()) glfwShowWindow(windowHandle);
    }

    public void use(){
        glfwMakeContextCurrent(windowHandle);
    }

    public void swapBuffers(){
        if(WINDOW_CREATED) glfwSwapBuffers(windowHandle);
    }

    public ArrayList<Event> pollEvents(){
        if(WINDOW_CREATED) glfwPollEvents();
        ArrayList<Event> events = new ArrayList<>(eventQueue);
        eventQueue.clear();
        return events;
    }

    public void setShouldClose(boolean shouldClose){
        if(WINDOW_CREATED) glfwSetWindowShouldClose(windowHandle, shouldClose);
    }

    public void destroy(){
        if(!WINDOW_CREATED) return;
        glfwDestroyWindow(windowHandle);
        WINDOW_CREATED = false;
    }

    public double getGLFWTime(){
        return glfwGetTime();
    }
}
