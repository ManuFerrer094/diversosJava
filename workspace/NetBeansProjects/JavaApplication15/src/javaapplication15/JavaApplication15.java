
package javaapplication15;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.lang.Math;
public class JavaApplication15 {
    public JavaApplication15() {

       SimpleUniverse universe = new SimpleUniverse();
       BranchGroup group = new BranchGroup();
       int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
       
       
       // Toda esta movidaca es para transformar
       Sphere sphere = new Sphere(0.2f,primflags,24);
       TransformGroup migrupotransformacion = new TransformGroup();
       Transform3D mitransformacion = new Transform3D();
       Vector3f posicion = new Vector3f(0.0f,0.0f,0.0f);
       mitransformacion.setTranslation(posicion);
       migrupotransformacion.setTransform(mitransformacion);
       migrupotransformacion.addChild(sphere);
       group.addChild(migrupotransformacion);
        float x;
        float y;

       // Voy a hacer un reloj
       for(float hora = 1;hora<=12;hora++){
            x = (float)(Math.cos(hora*30*0.0174533)*0.5);
            y = (float)(Math.sin(hora*30*0.0174533)*0.5);
            // Toda esta movidaca es para transformar
            sphere = new Sphere(0.1f,primflags,24);
            migrupotransformacion = new TransformGroup();
            mitransformacion = new Transform3D();
            posicion = new Vector3f(x,y,0.0f);
            mitransformacion.setTranslation(posicion);
            migrupotransformacion.setTransform(mitransformacion);
            migrupotransformacion.addChild(sphere);
            group.addChild(migrupotransformacion);
       }
       
       
       Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);                     // Esto es el color de la luz                     
       BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0); // Lo que ocupa la luz en el sentido de hasta donde llega
       Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);            // Vector 3D con la direccion de la luz
       DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);// Creo una luz con un color y un vector de direccion
       light1.setInfluencingBounds(bounds);                                     // La influencia de la luz es la variable bounds
       group.addChild(light1);                                                  // Añado la luz al grupo
       universe.getViewingPlatform().setNominalViewingTransform();              // Pongo la vista por defecto
       universe.addBranchGraph(group);                                          // Al universo le añado la rama
    }
    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");

        new JavaApplication15();
    }
    
}
