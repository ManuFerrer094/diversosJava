
package javaapplication14;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.BranchGroup;

public class JavaApplication14 {
    public JavaApplication14()
    {
       SimpleUniverse universe = new SimpleUniverse();                          // Esto es como la scene3d de threejs
       BranchGroup group = new BranchGroup();                                   // Un branch group es un rama jerarquica
       group.addChild(new ColorCube(0.3));                                      // Al grupo le añado un cubo de 0.3 de radio
       universe.getViewingPlatform().setNominalViewingTransform();              // Cojo el punto de vista por defecto
       universe.addBranchGraph(group);                                          // Al universo le añado el grupo
    }
    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");                // Añado una propiedad de sistema que es fondo opaco
        new JavaApplication14();                                                // Arranco el bucle
    }
    
}
