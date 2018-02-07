/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


/**
 *
 * @author Shane
 */
public class Srbfh3Vizzy implements Visualizer {
    private final String name = "Shane's Funky Freshness";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.0;
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    
    
    private final Double startHue = 156.0;
    
    private Polygon[] shapes;
    
    public Srbfh3Vizzy() {
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        shapes = new Polygon[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Polygon shape = new Polygon();
            shape.getPoints().addAll(new Double[]{
                                        (bandWidth / 4 + bandWidth * i), 0.0,
                                        (bandWidth / 2 + bandWidth * i), 0.0,
                                        (bandWidth / 1 + bandWidth * i), 0.0});
            shape.setFill(Color.GOLDENROD);
            vizPane.getChildren().add(shape);
            shapes[i] = shape;
        }
    }
    
    @Override
    public void end() {
         if (shapes != null) {
             for (Polygon shape : shapes) {
                 vizPane.getChildren().remove(shape);
             }
            shapes = null;
        } 
    }
    
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (shapes == null) {
            return;
        }
        
        Integer num = min(shapes.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            shapes[i].getPoints().setAll(new Double[]{
                                        (bandWidth / 4 + bandWidth * i), (((60.0 + magnitudes[i])/60.0)*height / 4),
                                        (bandWidth / 2 + bandWidth * i), (((60.0 + magnitudes[i])/60.0)*height),
                                        (bandWidth / 1 + bandWidth * i), (((60.0 + magnitudes[i])/60.0)*height / 4)});
            shapes[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            shapes[i].setStroke(Color.hsb(startHue + phases[i], 1.0, 1.0, 1.0));
        }
    }
    
}
