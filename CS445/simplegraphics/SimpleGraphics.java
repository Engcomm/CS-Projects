//SimpleGraphics
//Junda Lou
//CS445 Program 1
//4/10/2018
//A program that uses LWJGL to draw primitive 2D graphics
//Test files should be provided in absolute path to avoid problems.
//LWJGL Library is imported (you might import it again on your own PC), and run configuration is changed to avoid UnsatisfiedLinkError.

package simplegraphics;

import java.io.File;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;


public class SimpleGraphics {
    
    // create a window
    public static void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
        Display.create();
    }
    
    // iniitial 
    public static void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT,
        GL_NICEST);
    }
    
    // clear the screen and draw the primitives using the test file
    public static void render(String filePath) {
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try{
                glClear(GL_COLOR_BUFFER_BIT |GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                draw(filePath);

                Display.update();
                Display.sync(60);
            } catch(Exception e) { e.printStackTrace(); }
        }
        Display.destroy();
    }
    
    // scan through the test file to draw the coresponding primitives
    public static void draw(String filePath) {
        Scanner sc = null;
        try {
            //sc = new Scanner(new File("/Users/jundalou/Downloads/coordinates.txt"));
            sc = new Scanner(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(sc.hasNext()) {
            String[] drawCmd = sc.nextLine().split(" ");
            if(drawCmd[0].compareTo("l") == 0)
                line(drawCmd);
            else if(drawCmd[0].compareTo("c") == 0)
                circle(drawCmd);
            else if(drawCmd[0].compareTo("e") == 0)
                eclipse(drawCmd);
        }
        sc.close();
        
    }
    
    // midpoint algorithm
    public static void line(String[] data) {
        String[] endPoint1 = data[1].split(",");
        String[] endPoint2 = data[2].split(",");
        float x1, x2, y1, y2;
        if(Float.valueOf(endPoint1[0]) <= Float.valueOf(endPoint2[0])) {
            x1 = Float.valueOf(endPoint1[0]);
            y1 = Float.valueOf(endPoint1[1]);
            x2 = Float.valueOf(endPoint2[0]);
            y2 = Float.valueOf(endPoint2[1]);
        } else {
            x1 = Float.valueOf(endPoint2[0]);
            y1 = Float.valueOf(endPoint2[1]);
            x2 = Float.valueOf(endPoint1[0]);
            y2 = Float.valueOf(endPoint1[1]);
        }
        
        float dx = x2 - x1, dy = y2 - y1, d = 2 * dy - dx, right = 2 * dy, upright = 2 * (dy - dx);
        glColor3f(1.0f,0.0f,0.0f);
        glPointSize(1);
        glBegin(GL_POINTS);
        glVertex2f(x1, y1);
        while(x1 < x2) {
            if(d > 0) {
                if(y1 < y2) {
                    y1++;
                    d += upright;
                } else {
                    y1--; 
                    d -= upright;
                }
                x1++;
            } else {
                x1++;
                if(y1 < y2)
                    d += right;
                else
                    d -= right;
            }
            glVertex2f(x1, y1);
        }
        glEnd();
    }
    
    // x = centerX + r * cos(theta), y = centerY + r * sin(theta)
    public static void circle(String[] data) {
        String[] center = data[1].split(",");
        float x = Float.valueOf(center[0]), y = Float.valueOf(center[1]), r = Float.valueOf(data[2]);
        
        glColor3f(0.0f,0.0f,1.0f);
        glPointSize(1);
        glBegin(GL_POINTS);
        for(double theta = 0; theta < 360; theta++)
            glVertex2f((float)Math.cos(Math.toRadians(theta)) * r + x, (float)Math.sin(Math.toRadians(theta)) * r + y);
        glEnd();
    }
    
    // x = centerX + rx * cos(theta), y = centerY + ry * sin(theta)
    public static void eclipse(String[] data) {
        String[] center = data[1].split(",");
        float x = Float.valueOf(center[0]), y = Float.valueOf(center[1]);
        String[] radius = data[2].split(",");
        float rx = Float.valueOf(radius[0]), ry = Float.valueOf(radius[1]);
        
        glColor3f(0.0f,1.0f,0.0f);
        glPointSize(1);
        glBegin(GL_POINTS);
        for(double theta = 0; theta < 360; theta++)
            glVertex2f((float)Math.cos(Math.toRadians(theta)) * rx + x, (float)Math.sin(Math.toRadians(theta)) * ry + y);
        glEnd();
    }
    
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Please provide the absolute path to the coordinate file: ");
        String filePath = kb.nextLine();
        try {
            createWindow();
            initGL();
            render(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
