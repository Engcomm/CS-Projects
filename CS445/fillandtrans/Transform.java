/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fillandtrans;

import java.util.List;
import java.util.Stack;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Transform {
    
    public static void translate(float x, float y) {
        glTranslatef(x, y, 0f);
    }
    
    public static void rotate(float angle, float pivotX, float pivotY) {
        if(pivotX == 0 && pivotY == 0)
            glRotatef(angle, 0f, 0f, 1f);
        else {
            glTranslatef(pivotX, pivotY, 0f);
            glRotatef(angle, 0f, 0f, 1f);
            glTranslatef(-pivotX, -pivotY, 0f);
        }    
    }
    
    public static void scale(float x, float y, float pivotX, float pivotY) {
        if(pivotX == 0 && pivotY == 0)
            glScalef(x, y, 0f);
        else {
            glTranslatef(pivotX, pivotY, 0f);
            glScalef(x, y, 0f);
            glTranslatef(-pivotX, -pivotY, 0f);
        }
        
    }
    
    public static void transform(Stack<List<String>> trans_stk) {
        while(!trans_stk.isEmpty()) {
            List<String> next_trans = trans_stk.pop();
            if(next_trans.get(0).compareTo("t") == 0)
                translate(Float.valueOf(next_trans.get(1)), Float.valueOf(next_trans.get(2)));
            else if(next_trans.get(0).compareTo("r") == 0)
                rotate(Float.valueOf(next_trans.get(1)), Float.valueOf(next_trans.get(2)), Float.valueOf(next_trans.get(3)));
            else
                scale(Float.valueOf(next_trans.get(1)), Float.valueOf(next_trans.get(2)), Float.valueOf(next_trans.get(3)), Float.valueOf(next_trans.get(4))); 
        }
    }
    
}
