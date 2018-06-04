/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fillandtrans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

/**
 *
 * @author jundalou
 */
public class Main {
    
    // create a window
    public static void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 2");
        Display.create();
    }
    
    // iniitial 
    public static void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(-320, 320, -240, 240, 1, -1);
        //glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT,
        GL_NICEST);
    }
    
    // clear the screen and draw the primitives using the test file
    public static void render(String filePath) {
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {         
            try {               
                glClear(GL_COLOR_BUFFER_BIT |GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                
                drawPolygon(filePath);
                
                Display.update();
                Display.sync(60);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }
    
    // draw polygons in the file, and perform transformations
    public static void drawPolygon(String filePath) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filePath));
            String cmd = sc.next();
            List<Float> vertices = new ArrayList<Float>();
            Stack<List<String>> trans_stk = new Stack<>();
            while(cmd.compareTo("P") == 0 || cmd.compareTo("T") == 0) {
                if(cmd.compareTo("P") == 0) {
                    glColor3f(sc.nextFloat(), sc.nextFloat(), sc.nextFloat());
                    while(sc.hasNextFloat())
                        vertices.add(sc.nextFloat());
                    if(!sc.hasNext())
                        break;
                    cmd = sc.next();                   
                } else {
                    if(!sc.hasNext())
                        break;
                    cmd = sc.next();
                    while(cmd.compareTo("P") != 0 && cmd.compareTo("T") != 0) {
                        List<String> trans_cmd = new ArrayList<>();
                        if(cmd.compareTo("t") == 0) {
                            trans_cmd.add(cmd);
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_stk.add(trans_cmd);
                        } else if(cmd.compareTo("r") == 0) {
                            trans_cmd.add(cmd);
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_stk.add(trans_cmd);
                        } else {
                            trans_cmd.add(cmd);
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_cmd.add(sc.next());
                            trans_stk.add(trans_cmd);
                        }
                        if(sc.hasNext())
                            cmd = sc.next();
                        else
                            break;
                    }
                    glPushMatrix();
                    if(!trans_stk.isEmpty()) {
                        Transform.transform(trans_stk);
                        trans_stk.clear();
                    }
                    if(!vertices.isEmpty()) {
                        Fill.fill(vertices);
                        vertices.clear();
                    }                    
                    glPopMatrix();
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Please provide the absolute path to the coordinate file: ");
        String filePath = kb.nextLine();
        //String filePath = "/Users/jundalou/Downloads/coordinates.txt";
        try {
            createWindow();
            initGL();
            render(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        kb.close();
       
    }

}
