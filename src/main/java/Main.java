import util.FilePathUtility;
import window.Window;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.setSize(1280, 860);
        window.setTitle("SulfurEngine Demo");
        window.create();
        window.use();

        window.setIcon(FilePathUtility.getResourceFilePath(Main.class, "logo.png")); // TODO: fix this

        while(!window.getShouldClose()){
            window.pollEvents();
        }

        window.destroy();
    }
}
