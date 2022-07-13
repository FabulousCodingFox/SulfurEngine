import engine.Engine;
import mesh.DrawMode;
import mesh.Mesh;
import mesh.MeshCollection;
import mesh.VertexAttrib;
import texture.Interpolation;
import texture.Texture;
import texture.TextureMapping;
import util.FilePathUtility;
import window.event.*;
import window.Window;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.setSize(1280, 860);
        window.setResizable(false);
        window.setTitle("SulfurEngine Demo");
        window.setIcon(FilePathUtility.getResourceFilePath(Main.class, "logo.png"));
        window.create();

        Engine engine = new Engine(window);
        engine.setClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        engine.setDepthTestingEnabled(true);

        MeshCollection cubeCollection = new MeshCollection();

        float[] vertices = {
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };
        Mesh cube = new Mesh(false);
        cube.setMesh(vertices, new VertexAttrib[]{new VertexAttrib(0, 3, 20, 0), new VertexAttrib(1, 2, 20, 12)}, DrawMode.STATIC);

        Texture cubeTexture = new Texture(FilePathUtility.getResourceFilePath(Main.class, "logo512.png"), Interpolation.LINEAR, TextureMapping.CLAMP_TO_EDGE);
        HashMap<String, Texture> cubeTextures = new HashMap<>();
        cubeTextures.put("logoTex", cubeTexture);
        cube.setTextures(cubeTextures);

        while(!window.getShouldClose()){
            ArrayList<Event> eventQueue = window.pollEvents();
            for(Event event : eventQueue){
                if(event instanceof KeyEvent keyEvent){
                    System.out.println(keyEvent.getKey());
                    if(keyEvent.getAction() == KeyEvent.KEY_PRESSED){
                        if(keyEvent.getKey() == Key.ESCAPE){
                            window.setShouldClose(true);
                        }
                    }
                }
            }
            cubeCollection.use();

            HashMap<String, Object> uniforms = new HashMap<>();
            uniforms.put("iTime", (float) window.getGLFWTime());
            cube.render(uniforms);

            engine.mainloop();
        }

        window.destroy();
    }
}
