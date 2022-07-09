package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import util.FileUtils;

import java.io.IOException;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    int programID;

    public Shader(String vertexPath, String fragmentPath) {
        // 1. retrieve the vertex/fragment source code from filePath
        String vertexCode = "";
        String fragmentCode = "";
        try {
            vertexCode = FileUtils.readFile(vertexPath);
            fragmentCode = FileUtils.readFile(fragmentPath);
        }catch (IOException e) {
            e.printStackTrace();
        }

        // 2. compile shaders
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexCode);
        glCompileShader(vertexShader);
        if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(
                    glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH))
            );
            throw new RuntimeException("Vertex shader failed to compile: "+vertexPath);
        }

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentCode);
        glCompileShader(fragmentShader);
        if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(
                    glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH))
            );
            throw new RuntimeException("Fragment shader failed to compile: "+fragmentPath);
        }

        // 3. create shader program
        programID = glCreateProgram();
        glAttachShader(programID, vertexShader);
        glAttachShader(programID, fragmentShader);
        glLinkProgram(programID);
        if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE){
            System.out.println(
                    glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH))
            );
            throw new RuntimeException("Shader program failed to link: "+vertexPath+" and "+fragmentPath);
        }

        // 4. clean up
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void use() {
        glUseProgram(programID);
    }

    public void setInt(String name, int value) {
        glUniform1i(glGetUniformLocation(programID, name), value);
    }

    public void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(programID, name), value);
    }

    public void setBool(String name, boolean value) {
        glUniform1i(glGetUniformLocation(programID, name), value ? 1 : 0);
    }

    public void setMatrix4f(String name, Matrix4f value) {
        glUniformMatrix4fv(glGetUniformLocation(programID, name), false, value.get(new float[16]));
    }

    public void setVector2f(String name, Vector2f value) {
        glUniform2fv(glGetUniformLocation(programID, name),new float[]{value.x, value.y} );
    }

    public void delete() {
        glDeleteProgram(programID);
    }

    public void setUniforms(Map<String, Object> uniforms){
        for(Map.Entry<String, Object> entry : uniforms.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof Integer){
                setInt(key, (Integer)value);
            }else if(value instanceof Float){
                setFloat(key, (Float)value);
            }else if(value instanceof Boolean){
                setBool(key, (Boolean)value);
            }else if(value instanceof Matrix4f){
                setMatrix4f(key, (Matrix4f)value);
            }else if(value instanceof Vector2f){
                setVector2f(key, (Vector2f)value);
            }
        }
    }
}
