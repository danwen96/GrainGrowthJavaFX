package grainGrowth.model;

import grainGrowth.model.neighbourhoodImplementation.*;

public class Simulation {
    private GrainsContainer grainsContainer;
    //private int methodSet;
    private NeighbourhoodMethodImplementation neighbourhoodMethodImplementation;

    public Simulation(GrainsContainer grainsContainer)
    {
        this.grainsContainer = grainsContainer;
    }

    /**
     * Changing the calculation implementation used in nextIteration(), strategy design pattern used
     */
    public void changeNeighbourhoodMethod(String selection,String modeSelection, boolean periodically)
    {
        if(modeSelection.equals("Naive grain growth")) {
            switch (selection) {
                case "Von Neumann":
                    if (periodically)
                        neighbourhoodMethodImplementation = new VonNeumannMethodImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new VonNeumannMethodImplementation(grainsContainer);
                    break;
                case "Moore":
                    if (periodically)
                        neighbourhoodMethodImplementation = new MooreMethodImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new MooreMethodImplementation(grainsContainer);
                    break;
                case "Hexagonal left":
                    if (periodically)
                        neighbourhoodMethodImplementation = new HexagonalLeftImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new HexagonalLeftImplementation(grainsContainer);
                    break;
                case "Hexagonal right":
                    if (periodically)
                        neighbourhoodMethodImplementation = new HexagonalRightImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new HexagonalRightImplementation(grainsContainer);
                    break;
                case "Hexagonal random":
                    if (periodically)
                        neighbourhoodMethodImplementation = new HexagonalRandomImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new HexagonalRandomImplementation(grainsContainer);
                    break;
                case "Pentagonal random":
                    if (periodically)
                        neighbourhoodMethodImplementation = new PentagonalRandomImplementationPeriodically(grainsContainer);
                    else
                        neighbourhoodMethodImplementation = new PentagonalRandomImplementation(grainsContainer);
                    break;
            }
        } else if(modeSelection.equals("Monte Carlo")) {
            grainsContainer.findTheNumberOfIDs();
            if(periodically)
                neighbourhoodMethodImplementation = new MonteCarloSmoothingImplementationPeriodically(grainsContainer);
            else
                neighbourhoodMethodImplementation = new MonteCarloSmoothingImplementation(grainsContainer);
        }

    }

    /**
     * Making the calculations of the next board using the referency to proper interface
     * @see NeighbourhoodMethodImplementation
     */
    public void nextIteration()
    {
        neighbourhoodMethodImplementation.nextIteration();
    }

    public GrainsContainer getGrainsContainer() {
        return grainsContainer;
    }

    public void setGrainsContainer(GrainsContainer grainsContainer) {
        this.grainsContainer = grainsContainer;
    }
}
