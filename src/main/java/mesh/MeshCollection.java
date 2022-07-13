package mesh;

import static org.lwjgl.opengl.GL46.*;

public class MeshCollection {
    private final int vao;

    public MeshCollection() {
        vao = glGenVertexArrays();
    }

    public void use(){
        glBindVertexArray(vao);
    }
}
