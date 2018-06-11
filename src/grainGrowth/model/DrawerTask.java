package grainGrowth.model;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class DrawerTask extends Task {
    private GraphicsContext gc;
    private Simulation simulation;
    private BufferedImage bi;
    private List<Color> colorList;
    private int width;
    private int height;
    private int dt = 50;
    private boolean enabled = true;
    private int simulationCounter = -1;
    private Label statusLabel;

    /**
     * Creating task to do the calculations and update canvas
     * @param gc graphics context on which the animation is displayed
     * @param simulation reference to simulation class with prepared data
     * @param bi buffered image to be drawn on
     * @param width width of a grain
     * @param height height of a grain
     * @param statusLabel label to display status of the task
     */
    public DrawerTask(GraphicsContext gc, Simulation simulation, BufferedImage bi, int width,int height,List<Color> colorList, Label statusLabel){
        this.gc = gc;
        this.simulation = simulation;
        this.bi = bi;
        this.width = width;
        this.height = height;
        this.colorList = colorList;
        this.statusLabel = statusLabel;
    }

    /**
     * Method that will animate the process of grains growth on the canvas
     * @return
     * @throws Exception
     */
    @Override
    protected Object call() throws Exception {

        Platform.runLater(()->
        statusLabel.setText("During simulation")
        );
        while(true) {
            simulation.nextIteration();
            //System.out.println("Wyliczono");
            updateCanvasShowing();
            Thread.sleep(dt);
            simulationCounter--;
            if(!enabled || simulationCounter==0)
            {
                synchronized (this) {
                    enabled = false;
                    Platform.runLater(()->
                    statusLabel.setText("Paused")
                    );
                    wait();
                    //enabled = true;
                    Platform.runLater(()->
                    statusLabel.setText("During simulation")
                    );
                }
            }
            if(Thread.currentThread().isInterrupted())
            {
                //System.out.println("Ending thread");
                Platform.runLater(()->
                statusLabel.setText("Waiting")
                );
                break;
            }
        }
        return null;
    }

    /**
     * Negates the enabled boolean
     * @return The value of changed boolean
     */
    public boolean changeTheEnabledBooleanValue()
    {
        enabled = !enabled;
        return enabled;
    }

    /**
     * Updating the canvas state through the graphic context, draws the state of each grain on canvas
     */
    private void updateCanvasShowing()
    {
        for (int i = 0; i < simulation.getGrainsContainer().getnX(); i++) {
            for (int j = 0; j < simulation.getGrainsContainer().getnY(); j++) {
                if(simulation.getGrainsContainer().getTheCellState(i,j)>0)
                {
                    for (int k = 0; k < width; k++) {
                        for (int l = 0; l < height; l++) {
                            bi.setRGB(i*width+k,j*height+l,colorList.get(simulation.getGrainsContainer().getTheCellState(i,j)).getRGB());
                        }
                    }
                    //bi.setRGB(i,j,Color.BLUE.getRGB());
                }
            }
        }
        Platform.runLater(() ->
                gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0,0 )
        );

    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public void setSimulationCounter(int simulationCounter) {
        this.simulationCounter = simulationCounter;
    }
}
