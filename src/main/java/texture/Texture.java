package texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.opengl.GL46.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private int ID;
    private String filePath;
    private int width, height;
    private ByteBuffer imageData;

    public Texture(String filePath, Interpolation interpolation, TextureMapping textureMapping) {
        this.filePath = filePath;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            imageData = STBImage.stbi_load(filePath, w, h, comp, 4);
            if (imageData == null) {
                throw new IOException("Could not load image file " + filePath);
            }
            width = w.get();
            height = h.get();

            ID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, ID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, interpolation==Interpolation.NEAREST?GL_NEAREST:GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, interpolation==Interpolation.NEAREST?GL_NEAREST:GL_LINEAR);
            final int param = textureMapping == TextureMapping.REPEAT ? GL_REPEAT : textureMapping == TextureMapping.CLAMP_TO_EDGE ? GL_CLAMP_TO_EDGE : GL_MIRRORED_REPEAT;
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, param);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, param);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
            glGenerateMipmap(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Texture(String filePath) {
        this(filePath, Interpolation.LINEAR, TextureMapping.CLAMP_TO_EDGE);
    }

    public int getID() {
        return ID;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }
}
