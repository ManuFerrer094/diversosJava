/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication16;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.lang.Math;
import com.sun.j3d.loaders.objectfile.*;

public class JavaApplication16 {
    public JavaApplication16() {

       SimpleUniverse universe = new SimpleUniverse();
       BranchGroup group = new BranchGroup();
       int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
       
        Color3f negro = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f blanco = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f amarillo = new Color3f(1.0f, 1.0f, 0.0f);
       Appearance ap = new Appearance();
       ap.setMaterial(new Material(negro,negro,amarillo,blanco,0.3f));
       
       // Toda esta movidaca es para transformar
       Sphere sphere = new Sphere(0.2f,primflags,24,ap);
       TransformGroup migrupotransformacion = new TransformGroup();
       Transform3D mitransformacion = new Transform3D();
       Vector3f posicion = new Vector3f(0.0f,0.0f,0.0f);
       mitransformacion.setTranslation(posicion);
       migrupotransformacion.setTransform(mitransformacion);
       migrupotransformacion.addChild(sphere);
       group.addChild(migrupotransformacion);
       
 try
	{	
        Scene s = null;
   	ObjectFile f = new ObjectFile ();
        
    	f.setFlags (ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        String s1 = "C:\\java\\suzanne.obj";
	s = f.load (s1);
       
	migrupotransformacion.addChild (s.getSceneGroup ());
        Shape3D shape = (Shape3D) s.getSceneGroup ().getChild( 0 );             // Esto es como el traverse de threejs
        shape.setAppearance( ap );

	}

      	
      	catch (java.io.FileNotFoundException ex){
      	}
       
       
       
       Color3f light1Color = new Color3f(0.4f, 0.4f, 0.4f);                     // Esto es el color de la luz                     
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

        new JavaApplication16();
    }
    
}

