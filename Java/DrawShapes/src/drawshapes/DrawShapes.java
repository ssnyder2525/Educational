/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawshapes;
import javax.swing.JFrame;
/**
 *
 * @author Stephen
 */
public class DrawShapes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame shapes = new JFrame ("Shapes Shapes Shapes...");

		shapes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ShapesPanel easel = new ShapesPanel();
		
		shapes.getContentPane().add(easel);
		
		shapes.pack();
		
		shapes.setVisible(true);
    }
    
}
