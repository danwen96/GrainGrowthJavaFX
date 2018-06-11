package grainGrowth.model.neighbourhoodImplementation;

import grainGrowth.model.GrainsContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MonteCarloSmoothingImplementation implements NeighbourhoodMethodImplementation {
    private GrainsContainer grainsContainer;

    public MonteCarloSmoothingImplementation(GrainsContainer grainsContainer)
    {
        this.grainsContainer = grainsContainer;
    }

    @Override
    public void nextIteration() {
        //System.out.println("In next iteration method");
        int indUp;
        int indDown;
        int indLeft;
        int indRight;
        int counterofEdges;

        Random random = new Random();

        int energy = 0;

        for (int i = 0; i < (grainsContainer.getnX()); i++)
        {
            for (int j = 0; j < (grainsContainer.getnY()); j++)
            {
                counterofEdges = 0;
                indUp = j - 1;
                indDown = j + 1;
                indLeft = i - 1;
                indRight = i + 1;
                if (j == 0) {
                    counterofEdges++;
                    indUp = 0;
                }
                if (j == (grainsContainer.getnY() - 1)) {
                    counterofEdges++;
                    indDown = (grainsContainer.getnY() - 1);
                }
                if (i == 0) {
                    indLeft = 0;
                    counterofEdges++;
                }
                if (i == (grainsContainer.getnX() - 1)){
                    indRight = (grainsContainer.getnX() - 1);
                    counterofEdges++;
                }
                energy = 0;

                if (grainsContainer.getTheCellState(indLeft,indUp) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(i,indUp) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,indUp) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indLeft,j) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,j) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indLeft,indDown) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(i,indDown) != grainsContainer.getTheCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,indDown) != grainsContainer.getTheCellState(i,j))
                    energy++;

                grainsContainer.setTheEnergy(i,j,energy);
                grainsContainer.setTheNextCellState(i,j,random.nextInt(grainsContainer.getIdsPoolNumber()));


                energy = 0;

                if (grainsContainer.getTheCellState(indLeft,indUp) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(i,indUp) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,indUp) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indLeft,j) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,j) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indLeft,indDown) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(i,indDown) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                if (grainsContainer.getTheCellState(indRight,indDown) != grainsContainer.getTheNextCellState(i,j))
                    energy++;

                grainsContainer.setTheNextEnergy(i,j,energy-counterofEdges);
            }
        }
        grainsContainer.updateStatesBasedOnEnergyValues();

    }
}
