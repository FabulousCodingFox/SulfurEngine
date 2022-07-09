package mesh;

import static org.lwjgl.opengl.GL46.*;

public class VertexAttrib {
    private int index, size, stride, offset;

    public VertexAttrib(int index, int size, int stride, int offset) {
        this.index = index;
        this.size = size;
        this.stride = stride;
        this.offset = offset;
    }

    public void apply(){
        glVertexAttribPointer(
            index,
            size,
            GL_FLOAT,
            false,
            stride,
            offset
        );
        glEnableVertexAttribArray(index);
    }
}
