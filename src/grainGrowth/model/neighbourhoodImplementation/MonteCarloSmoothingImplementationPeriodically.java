package grainGrowth.model.neighbourhoodImplementation;

import grainGrowth.model.GrainsContainer;

import java.util.Random;

public class MonteCarloSmoothingImplementationPeriodically implements NeighbourhoodMethodImplementation {
    private GrainsContainer grainsContainer;

    public MonteCarloSmoothingImplementationPeriodically(GrainsContainer grainsContainer)
    {
        this.grainsContainer = grainsContainer;
    }


    @Override
    public void nextIteration() {
        int indUp;
        int indDown;
        int indLeft;
        int indRight;

        Random random = new Random();

        int energy = 0;

        for (int i = 0; i < (grainsContainer.getnX()); i++)
        {
            for (int j = 0; j < (grainsContainer.getnY()); j++)
            {
                indUp = j - 1;
                indDown = j + 1;
                indLeft = i - 1;
                indRight = i + 1;
                if (j == 0)
                    indUp = grainsContainer.getnY() - 1;
                if (j == (grainsContainer.getnY() - 1))
                    indDown = 0;
                if (i == 0)
                    indLeft = grainsContainer.getnX() - 1;
                if (i == (grainsContainer.getnX() - 1))
                    indRight = 0;

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

                grainsContainer.setTheNextEnergy(i,j,energy);
            }
        }
        grainsContainer.updateStatesBasedOnEnergyValues();

    }
}
