package mesh;

import static org.lwjgl.opengl.GL46.*;

import engine.Shader;
import texture.Texture;

import java.util.Arrays;
import java.util.Map;

public class Mesh {
    private Map<String, Texture> textures;
    private float[] mesh, indices;
    private int vbo, ibo;
    private Shader shader;
    private VertexAttrib[] attributes;

    public Mesh() {}

    public void setTextures(Map<String, Texture> textures) {
        this.textures = textures;
    }

    public void setMesh(float[] mesh, float[] indices, VertexAttrib[] vertAttrib, DrawMode drawMode) {
        if(this.vbo == 0) this.vbo = glGenBuffers();
        if(this.ibo == 0) this.ibo = glGenBuffers();
        this.mesh = mesh;
        this.indices = indices;
        this.attributes = vertAttrib;
        int mode = drawMode== DrawMode.STATIC ? GL_STATIC_DRAW : drawMode == DrawMode.DYNAMIC ? GL_DYNAMIC_DRAW : GL_STREAM_DRAW;
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, mesh, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    public void render(Map<String, Object> uniforms){
        shader.use();
        shader.setUniforms(uniforms);

        int i=0;
        for(Map.Entry<String, Texture> entry : textures.entrySet()){
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, entry.getValue().getID());
            shader.setInt(entry.getKey(), i);
            i++;
        }

        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);

        Arrays.stream(vertAttrib).forEach(VertexAttrib::apply);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        textures.forEach((key, value) -> value.unbind());
        shader.unbind();
    }
}
