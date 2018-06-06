package grainGrowth.model.neighbourhoodImplementation;

import grainGrowth.model.GrainsContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PentagonalRandomImplementationPeriodically implements NeighbourhoodMethodImplementation{
    private GrainsContainer grainsContainer;

    public PentagonalRandomImplementationPeriodically(GrainsContainer grainsContainer)
    {
        this.grainsContainer = grainsContainer;
    }

    @Override
    public void nextIteration() {
        int indUp;
        int indDown;
        int indLeft;
        int indRight;

        int checkSite;

        Random random = new Random();
        int[] values = new int[5];

        Map<Integer, Integer> valuesCount = new HashMap<>();

        for (int i = 0; i < (grainsContainer.getnX()); i++)
        {
            for (int j = 0; j < (grainsContainer.getnY()); j++)
            {
                if (grainsContainer.getTheCellState(i,j) > 0)
                    continue;

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

                valuesCount.clear();

                checkSite = random.nextInt(4);

                if(checkSite == 0) {
                    if (grainsContainer.getTheCellState(indLeft, indUp) > 0) {
                        values[0] = grainsContainer.getTheCellState(indLeft, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(i, indUp) > 0) {
                        values[1] = grainsContainer.getTheCellState(i, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(i, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, indUp) > 0) {
                        values[2] = grainsContainer.getTheCellState(indRight, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indLeft, j) > 0) {
                        values[3] = grainsContainer.getTheCellState(indLeft, j);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, j), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, j) > 0) {
                        values[4] = grainsContainer.getTheCellState(indRight, j);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, j), 0) + 1);
                    }
                } else if (checkSite == 1){
                    if (grainsContainer.getTheCellState(indLeft, j) > 0) {
                        values[0] = grainsContainer.getTheCellState(indLeft, j);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, j), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, j) > 0) {
                        values[1] = grainsContainer.getTheCellState(indRight, j);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, j), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indLeft, indDown) > 0) {
                        values[2] = grainsContainer.getTheCellState(indLeft, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, indDown), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(i, indDown) > 0) {
                        values[3] = grainsContainer.getTheCellState(i, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(i, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indDown), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, indDown) > 0) {
                        values[4] = grainsContainer.getTheCellState(indRight, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, indDown), 0) + 1);
                    }
                } else if (checkSite == 2){

                    if (grainsContainer.getTheCellState(i, indUp) > 0) {
                        values[0] = grainsContainer.getTheCellState(i, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(i, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, indUp) > 0) {
                        values[1] = grainsContainer.getTheCellState(indRight, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, j) > 0) {
                        values[2] = grainsContainer.getTheCellState(indRight, j);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, j), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(i, indDown) > 0) {
                        values[3] = grainsContainer.getTheCellState(i, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(i, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indDown), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indRight, indDown) > 0) {
                        values[4] = grainsContainer.getTheCellState(indRight, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(indRight, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(indRight, indDown), 0) + 1);
                    }
                } else if (checkSite == 3) {
                    if (grainsContainer.getTheCellState(indLeft, indUp) > 0) {
                        values[0] = grainsContainer.getTheCellState(indLeft, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(i, indUp) > 0) {
                        values[1] = grainsContainer.getTheCellState(i, indUp);
                        valuesCount.put(grainsContainer.getTheCellState(i, indUp), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indUp), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indLeft, j) > 0) {
                        values[2] = grainsContainer.getTheCellState(indLeft, j);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, j), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, j), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(indLeft, indDown) > 0) {
                        values[3] = grainsContainer.getTheCellState(indLeft, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(indLeft, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(indLeft, indDown), 0) + 1);
                    }

                    if (grainsContainer.getTheCellState(i, indDown) > 0) {
                        values[4] = grainsContainer.getTheCellState(i, indDown);
                        valuesCount.put(grainsContainer.getTheCellState(i, indDown), valuesCount.getOrDefault(grainsContainer.getTheCellState(i, indDown), 0) + 1);
                    }
                }

                if (valuesCount.isEmpty())
                {
                    continue;
                }

                int maxValue = 0;
                int counter = 0;
                for (int k = 0; k < values.length; k++)
                {
                    if(values[k] == 0)
                        continue;
                    int value;
                    value = valuesCount.get(values[k]);
                    if (value == maxValue)
                        counter++;
                    if (value > maxValue)
                    {
                        maxValue = value;
                        counter = 1;
                    }

                }

                int index = random.nextInt(counter);

                counter = 0;

                for (int k = 0; k < values.length; k++)
                {
                    if(values[k] == 0)
                        continue;
                    int value;
                    value = valuesCount.get(values[k]);
                    if (value == maxValue)
                    {
                        if (counter == index)
                        {
                            grainsContainer.setTheNextCellState(i,j,values[k]);
                            //tab2[i, j] = values[k];
                            break;
                        }
                        counter++;
                    }
                }

                Arrays.fill(values,0);
                //values = new int[4];
            }
        }
        grainsContainer.setGrainsStateFromNextIteration();
    }
}
