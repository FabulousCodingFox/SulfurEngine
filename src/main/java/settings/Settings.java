package settings;

public class Settings {
    private int width, height, maxFPS;
    private boolean fullscreen, vsync;

    public void setResolution(int width, int height) {} // TODO: implement
    public void setFullscreen(boolean fullscreen) {} // TODO: implement
    public void setVSync(boolean vsync) {} // TODO: implement
    public void setMaxFPS(int maxFPS) {} // TODO: implement

    public int getWidth() { return 0; } // TODO: implement
    public int getHeight() { return 0; } // TODO: implement
    public boolean isFullscreen() { return false; } // TODO: implement
    public boolean isVSync() { return false; } // TODO: implement
    public int getMaxFPS() { return 0; } // TODO: implement
}