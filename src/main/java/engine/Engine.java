package engine;

import mesh.Mesh;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL46.*;
import window.Window;

public class Engine {
    private static boolean OpenGLinitialized = false;
    private float clearColor_r, clearColor_g, clearColor_b, clearColor_a;
    private final Window window;

    private boolean depthTestEnabled = false;

    public Engine(Window window) {
        if(!OpenGLinitialized){
            OpenGLinitialized = true;
            GL.createCapabilities();
        }
        this.window = window;
        this.clearColor_r = 0.0f;
        this.clearColor_g = 0.0f;
        this.clearColor_b = 0.0f;
        this.clearColor_a = 1.0f;

        window.use();

        glClearColor(clearColor_r, clearColor_g, clearColor_b, clearColor_a);
        if(!depthTestEnabled) glClear(GL_COLOR_BUFFER_BIT);
        if(depthTestEnabled) glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void setClearColor(float r, float g, float b, float a){
        this.clearColor_r = r;
        this.clearColor_g = g;
        this.clearColor_b = b;
        this.clearColor_a = a;
    }

    public void setDepthTestingEnabled(boolean enabled){
        this.depthTestEnabled = enabled;
        if(enabled){
            glEnable(GL_DEPTH_TEST);
        }else{
            glDisable(GL_DEPTH_TEST);
        }
    }

    public void mainloop(){
        window.swapBuffers();
        glClearColor(clearColor_r, clearColor_g, clearColor_b, clearColor_a);
        if(!depthTestEnabled) glClear(GL_COLOR_BUFFER_BIT);
        if(depthTestEnabled) glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
