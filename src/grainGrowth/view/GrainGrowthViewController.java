package grainGrowth.view;

import grainGrowth.model.DrawerTask;
import grainGrowth.model.GrainsContainer;
import grainGrowth.model.Simulation;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrainGrowthViewController {
    @FXML
    private Canvas canvas;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private TextField xTabSizeTextField;
    @FXML
    private TextField yTabSizeTextField;
    @FXML
    private Button changeSizeButton;
    @FXML
    private TextField numberOfGrainsTextField;
    @FXML
    private TextField radiusTextField;
    @FXML
    private Button drawButton;
    @FXML
    private ComboBox<String> neighbourhood;
    @FXML
    private CheckBox periodicallyCheckBox;
    @FXML
    private TextField dtField;
    @FXML
    private ComboBox<String> simulationType;
    @FXML
    private TextField idsPoolNumberTextField;
    @FXML
    private CheckBox pauseAfterSetNumberOfIterationsCheckBox;
    @FXML
    private TextField numberOfIterationsTextField;
    @FXML
    private Label statusLabel;

    private GraphicsContext graphicsContext;
    private BufferedImage bi;

    private int width;
    private int height;
    private GrainsContainer grainsContainer;
    private Simulation simulation = new Simulation(null);
    private int NX;
    private int NY;
    private List<Color> colorList;
    private DrawerTask drawerTask;
    private Thread drawerThread;



    public GrainGrowthViewController(){};

    /**
     * Initializing grains container size to default value and getting graphics context from canvas.
     * Also creating a new buffered image and setting the background color.
     */
    @FXML
    private void initialize() throws InterruptedException {
        neighbourhood.getItems().addAll(
                "Moore",
                "Von Neumann",
                "Hexagonal left",
                "Hexagonal right",
                "Hexagonal random",
                "Pentagonal random"
        );
        neighbourhood.setValue("Moore");

        simulationType.getItems().addAll(
                "Naive grain growth",
                "Monte Carlo"
        );
        simulationType.setValue("Naive grain growth");

        graphicsContext = canvas.getGraphicsContext2D();

        handleChangeSizeButton();

    }

    /**
     * Resetting the canvas with proper color
     * @param color color to be set as a background
     */
    public void setCanvasBackround(Color color)
    {
        for (int i = 0; i < NX*width; i++) {
            for (int j = 0; j < NY*height; j++) {
                bi.setRGB(i,j,color.getRGB());
            }
        }
        Platform.runLater(()->
                graphicsContext.drawImage(SwingFXUtils.toFXImage(bi, null), 0,0 )
        );
    }

    @FXML
    private void handleMouseClick(final MouseEvent event) {
        int indX = (int)event.getX()/width;
        int indY = (int)event.getY()/height;

        Random random = new Random();
        if(grainsContainer.getTheCellState(indX,indY) > 0) {
            grainsContainer.setCellState(indX, indY, 0);
            setCanvasBackround(colorList.get(0));
        }
        else {
            grainsContainer.setCellState(indX, indY, colorList.size());
            colorList.add(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        }
        updateCanvasShowing();
    }

    /**
     * Event handler for change size button, it changes the tab size and resets the drawings
     */
    @FXML
    public void handleChangeSizeButton() throws InterruptedException {
        handleStopButton();

        NX = Integer.parseInt(xTabSizeTextField.getText());
        NY = Integer.parseInt(yTabSizeTextField.getText());
        if(NX > 1000 || NX <1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Wrong size");
            alert.setContentText("Setting the size to 100!");

            NX = 100;
            xTabSizeTextField.setText("100");
            alert.showAndWait();
        }
        if(NY > 1000 || NY <1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Wrong size");
            alert.setContentText("Setting the size to 100!");

            NY = 100;
            yTabSizeTextField.setText("100");
            alert.showAndWait();
        }

        changeTheTabSize(NX,NY);

        colorList = new ArrayList<>();
        colorList.add(Color.GRAY);
        setCanvasBackround(colorList.get(0));
    }

    /**
     * Makes a new grains container with specified size, also creates a new bitmap and clears view
     * @param NX x size of a new grains tab
     * @param NY y size of a new grains tab
     */
    public void changeTheTabSize(int NX,int NY) {
        grainsContainer= new GrainsContainer(NX,NY);
        width = 1000/NX;
        height = 1000/NY;

        if(width > height)
            width = height;
        else
            height = width;

        simulation.setGrainsContainer(grainsContainer);
        bi = new BufferedImage(width*NX,height*NY,BufferedImage.TYPE_INT_RGB);
        canvas.setWidth(width*NX);
        canvas.setHeight(height*NY);
        handleNeighbourhoodComboBoxSelection();
    }

    /**
     * Event handler for start button, it runs drawerTask as new thread working in background
     * @see DrawerTask
     */
    @FXML
    public void handleStartButton() {
        if(drawerTask == null) {
            drawerTask = new DrawerTask(graphicsContext, simulation, bi, width, height, colorList,statusLabel);
            drawerTask.setDt(Integer.parseInt(dtField.getText()));

            if(pauseAfterSetNumberOfIterationsCheckBox.isSelected())
                drawerTask.setSimulationCounter(Integer.parseInt(numberOfIterationsTextField.getText()));
            else
                drawerTask.setSimulationCounter(-1);

            drawerThread = new Thread(drawerTask);
            drawerThread.start();
        }
        else {
            drawerTask.setDt(Integer.parseInt(dtField.getText()));

            handlePauseButton();
        }
    }


    /**
     * Event handler for pause button, it changes the boolean inside the DrawerTask class, if the thread is already waiting, this method notifies it
     */
    @FXML
    public void handlePauseButton() {
        if(drawerTask != null)
        synchronized (drawerTask) {
            if (drawerTask.changeTheEnabledBooleanValue())
                if(pauseAfterSetNumberOfIterationsCheckBox.isSelected())
                    drawerTask.setSimulationCounter(Integer.parseInt(numberOfIterationsTextField.getText()));
                else
                    drawerTask.setSimulationCounter(-1);
                drawerTask.notify();
        }
    }

    /**
     * Event handler for stop button, it stops the animation thread, waits until it finishes and then clears the tabs and canvas
     * @throws InterruptedException
     */
    @FXML
    public void handleStopButton() throws InterruptedException {

        if(drawerTask != null) {
            drawerTask.cancel();
            drawerThread.join();
            changeTheTabSize(NX, NY);
            drawerTask = null;

            colorList = new ArrayList<>();
            colorList.add(Color.GRAY);
            setCanvasBackround(colorList.get(0));
        }
    }

    /**
     * Event handler for draw button, it adds the specified number of new grains and updates the canvas with them
     */
    @FXML
    public void handleDrawButton() {
        int radius = Integer.parseInt(radiusTextField.getText());
        boolean periodically = periodicallyCheckBox.isSelected();

        grainsContainer.updateOverwritableValues(radius,periodically);

        int numberOfGrainsToDraw = Integer.parseInt(numberOfGrainsTextField.getText());
        Random random = new Random();
        int randomIndex;

        int numberOfAvailableCells;

        int counter = 0;
        int numberOfLoopsToBeMade = numberOfGrainsToDraw;

        for (int m = 0; m < numberOfLoopsToBeMade; m++) {
            numberOfAvailableCells = findNumberOfAvailableCells();

            if(numberOfAvailableCells==0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Not enough available space to draw additional grains");
                alert.setContentText("Please chose lesser number or create new simulation");
                updateCanvasShowing();

                alert.showAndWait();
                return;
            }

            randomIndex = random.nextInt(numberOfAvailableCells);
            counter = 0;

            outerLoop:
            for (int i = 0; i < grainsContainer.getnX(); i++) {
                for (int j = 0; j < grainsContainer.getnY(); j++) {
                    if(grainsContainer.isOverwritable(i,j))
                    {
                        if(randomIndex == counter)
                        {
                            grainsContainer.setCellState(i,j,colorList.size());
                            colorList.add(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                            numberOfGrainsToDraw--;
                            grainsContainer.updateOverwritableValues(radius,periodically);

                            break outerLoop;
                        }
                        counter++;
                    }
                }
            }
        }
        updateCanvasShowing();
    }

    private int findNumberOfAvailableCells()
    {
        int numberOfAvailableCells = 0;

        for (int i = 0; i < NX; i++) {
            for (int j = 0; j < NY; j++) {
                if(grainsContainer.isOverwritable(i,j))
                    numberOfAvailableCells++;
            }
        }
        return numberOfAvailableCells;
    }

    /**
     * Event handler for draw evenly button, it fills grains evenly with specified offset
     */
    @FXML
    public void handleDrawEvenlyButton(){
        int numberOfGrainsToDraw = Integer.parseInt(numberOfGrainsTextField.getText());
        int offset = Integer.parseInt(radiusTextField.getText());

        Random random = new Random();
        int counterX = 0;
        int counterY = 0;
        for(int k=0;k<numberOfGrainsToDraw;k++)
        {
            grainsContainer.setCellState(counterX,counterY,colorList.size());
            colorList.add(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            counterX+=offset;
            if(counterX >= grainsContainer.getnX())
            {
                counterY+= offset;
                counterX = 0;
                if(counterY >= grainsContainer.getnY())
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Not enough available space to draw additional grains");
                    alert.setContentText("Please chose lesser number or create new simulation");
                    updateCanvasShowing();

                    alert.showAndWait();
                    break;
                }
            }
        }

        updateCanvasShowing();
    }

    /**
     * Event handler for combo box item change, and check box selection change, it changes the referency to growth method implementation in Simulation Class
     */
    @FXML
    public void handleNeighbourhoodComboBoxSelection(){
        grainsContainer.findTheNumberOfIDs();
        simulation.changeNeighbourhoodMethod(neighbourhood.getValue(),simulationType.getValue(),periodicallyCheckBox.isSelected());
    }

    @FXML
    public void handleEntireTableButton() throws InterruptedException {
        int idsPoolNumber = Integer.parseInt(idsPoolNumberTextField.getText());
        handleChangeSizeButton();

        Random random = new Random();
        grainsContainer.fillTheEntireGrainsContainerWithRandomIDs(idsPoolNumber);
        for (int i = 0; i < idsPoolNumber; i++) {
            colorList.add(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        }

        updateCanvasShowing();
        handleNeighbourhoodComboBoxSelection();
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
                }
            }
        }
        Platform.runLater(()->
                graphicsContext.drawImage(SwingFXUtils.toFXImage(bi, null), 0,0 )
        );
    }

    /**
     * Method highlighting the set radius of a grain, used in debuging
     */
    private void showFalseBooleanCells()
    {
        for (int i = 0; i < simulation.getGrainsContainer().getnX(); i++) {
            for (int j = 0; j < simulation.getGrainsContainer().getnY(); j++) {
                if(!simulation.getGrainsContainer().isOverwritable(i,j))
                {
                    for (int k = 0; k < width; k++) {
                        for (int l = 0; l < height; l++) {
                            bi.setRGB(i*width+k,j*height+l,Color.YELLOW.getRGB());
                        }
                    }
                }
            }
        }
        //Platform.runLater(()->
                graphicsContext.drawImage(SwingFXUtils.toFXImage(bi, null), 0,0 );
        //);
    }
}
