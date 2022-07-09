package mesh;

import static org.lwjgl.opengl.GL46.*;

import engine.Shader;
import texture.Texture;

import java.util.Map;

public class Mesh {
    private Map<String, Texture> textures;
    private float[] mesh, indices;
    private int vbo, ibo;
    private Shader shader;

    public Mesh() {}

    public void setTextures(Map<String, Texture> textures) {
        this.textures = textures;
    }

    public void setMesh(float[] mesh, float[] indices, DrawMode drawMode) {
        if(this.vbo == 0) this.vbo = glGenBuffers();
        if(this.ibo == 0) this.ibo = glGenBuffers();
        this.mesh = mesh;
        this.indices = indices;
        int mode = drawMode== DrawMode.STATIC ? GL_STATIC_DRAW : drawMode == DrawMode.DYNAMIC ? GL_DYNAMIC_DRAW : GL_STREAM_DRAW;
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, mesh, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

    }

    public void render(Map<String, Object> uniforms){}
}
