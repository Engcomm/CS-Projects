/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fillandtrans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Fill {
   
    private static final int Y_MIN = 0, Y_MAX = 1, X_VAL = 2, M = 3;
    
    public static List<Float[]> getAllEdges(List<Float> vertices) {
        List<Float[]> all_edges = new ArrayList<>();
        for(int i = 0; i < vertices.size() - 2; i += 2) {
            Float[] edge_info = new Float[4];
            if(vertices.get(i + 1) < vertices.get(i + 3)) {
                edge_info[Y_MIN] = vertices.get(i + 1);
                edge_info[Y_MAX] = vertices.get(i + 3);
                edge_info[X_VAL] = vertices.get(i);             
            } else {
                edge_info[Y_MIN] = vertices.get(i + 3);
                edge_info[Y_MAX] = vertices.get(i + 1);
                edge_info[X_VAL] = vertices.get(i + 2);               
            }
            edge_info[M] = ((vertices.get(i + 2) - vertices.get(i)) / (vertices.get(i + 3) - vertices.get(i + 1)));
            all_edges.add(edge_info);
        }
        Float[] loop_back_edge = new Float[4];
        if(vertices.get(vertices.size() - 1) < vertices.get(1)) {
            loop_back_edge[Y_MIN] = vertices.get(vertices.size() - 1);
            loop_back_edge[Y_MAX] = vertices.get(1);
            loop_back_edge[X_VAL] = vertices.get(vertices.size() - 2);             
        } else {
            loop_back_edge[Y_MIN] = vertices.get(1);
            loop_back_edge[Y_MAX] = vertices.get(vertices.size() - 1);
            loop_back_edge[X_VAL] = vertices.get(0);               
        }
        loop_back_edge[M] = ((vertices.get(0) - vertices.get(vertices.size() - 2)) / (vertices.get(1) - vertices.get(vertices.size() - 1)));
        all_edges.add(loop_back_edge);
        return all_edges;
    }
    
    public static List<Float[]> getGlobalEdges(List<Float[]> all_edges) {
        List<Float[]> global_edges = new ArrayList<>();
        for(Float[] edges : all_edges) {
            if(edges[M] != Float.NEGATIVE_INFINITY && edges[M] != Float.POSITIVE_INFINITY)
                global_edges.add(edges);         
        }
        global_edges.sort(new GlobalEdgeComparator());
        return global_edges;
    }
    
    public static List<Float[]> getActiveEdges(List<Float[]> global_edges, Float scanline) {
        List<Float[]> active_edges = new ArrayList<>();
        Iterator<Float[]> it = global_edges.iterator();
        while(it.hasNext()) {
            Float[] next_entry = it.next();
            if(next_entry[Y_MIN].compareTo(scanline) == 0) {
                Float[] new_active_edge = new Float[3];
                new_active_edge[0] = next_entry[Y_MAX];
                new_active_edge[1] = next_entry[X_VAL];
                new_active_edge[2] = next_entry[M];
                active_edges.add(new_active_edge);
                it.remove();
            }
        }
        return active_edges;
    }
    
    public static void fill(List<Float> vertices) {
        List<Float[]> global_edges = getGlobalEdges(getAllEdges(vertices));
        Float scanline = global_edges.get(0)[Y_MIN];
        List<Float[]> active_edges = getActiveEdges(global_edges, scanline);
        
        while(!active_edges.isEmpty()) {
            int parity = 0;
            float x0 = 0f;
            boolean reach_max = false;
            for(Float[] edge : active_edges) {
                if(scanline.compareTo(edge[0]) == 0) {
                    reach_max = true;
                    break;
                }
                if(parity % 2 == 1) {
                    if(x0 == edge[1]) {
                        glBegin(GL_POINTS);
                        glVertex2f(x0, scanline);
                        glEnd();
                    } else {
                        while(x0 < edge[1]) {
                            glBegin(GL_POINTS);
                            glVertex2f(x0, scanline);
                            glEnd();                           
                            x0++;
                        }
                    }
                } else {
                    x0 = (float)Math.ceil(edge[1]);
                }
                parity++;
            }
            if(reach_max) {
                Iterator<Float[]> it = active_edges.iterator();
                while(it.hasNext()) {
                    if(it.next()[0].compareTo(scanline) == 0)
                        it.remove();
                }
            } else {
                scanline++;
                for(int i = 0; i < active_edges.size(); i++)
                    active_edges.get(i)[1] += active_edges.get(i)[2];
                if(!global_edges.isEmpty())
                    active_edges.addAll(getActiveEdges(global_edges, scanline));
                active_edges.sort(new ActiveEdgeComparator());    
            }    
        }         
    }

    static class GlobalEdgeComparator implements Comparator<Float[]> {
        
        @Override
        public int compare(Float[] a, Float[] b) {
            if(a[Y_MIN].compareTo(b[Y_MIN]) != 0)
                return a[Y_MIN].compareTo(b[Y_MIN]);
            else if(a[X_VAL].compareTo(b[X_VAL]) != 0)
                return a[X_VAL].compareTo(b[X_VAL]);
            else
                return a[Y_MAX].compareTo(b[Y_MAX]);
        }
       
    }
    
    static class ActiveEdgeComparator implements Comparator<Float[]> {
        
        @Override
        public int compare(Float[] a, Float[] b) {
            return a[1].compareTo(b[1]);
        }
       
    }
    
}
