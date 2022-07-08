package util;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageParser {
    public static GLFWImage.Buffer getImageBuffer(String path, boolean flip){
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(flip);
            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new IOException("Could not load image file " + path);
            }
            width = w.get();
            height = h.get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        GLFWImage img = GLFWImage.malloc();
        GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        img.set(width, height, image);
        imagebf.put(0, img);
        return imagebf;
    }
}
