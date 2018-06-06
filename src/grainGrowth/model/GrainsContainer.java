package grainGrowth.model;

public class GrainsContainer {
    private Grain[][] grains;
    private int nX;
    private int nY;

    /**
     * Constructor of a container for grains
     * @param k number of cellular elements in width
     * @param l number of cellular elements in hight
     */
    public GrainsContainer(int k, int l)
    {
        this.grains = new Grain[k][l];
        for (int i = 0; i < grains.length; i++) {
            for (int j = 0; j < grains[i].length; j++) {
                grains[i][j] = new Grain(i,j);
            }
        }
        this.nX = k;
        this.nY = l;
    }

    /**
     * Rewrite the state from the next state value for every cell
     */
    public void setGrainsStateFromNextIteration()
    {
        for (int i = 0; i < grains.length; i++) {
            for (int j = 0; j < grains[i].length; j++) {
                grains[i][j].setStateFromNextIteration();
            }
        }
    }

    /**
     * Set the state of a particular cell
     * @param i x index in table
     * @param j y index in table
     * @param state the state to be set
     */
    public void setCellState(int i,int j,int state)
    {
        grains[i][j].setState(state);
    }

    /**
     * Set the next state of a particular cell
     * @param i x index in table
     * @param j y index in table
     * @param state the state to be set
     */
    public void setTheNextCellState(int i,int j,int state)
    {
        grains[i][j].setNextState(state);
    }

    /**
     * Returning the state of a particular cell
     * @param i x index in tab
     * @param j y index in tab
     * @return the state of the cell
     */
    public int getTheCellState(int i,int j)
    {
        return grains[i][j].getState();
    }

    /**
     * Sets overwritable flag used in drawing with specified radius
     * @param i x index of the grain
     * @param j y index of the grain
     * @param overwritable the value to be set
     */
    public void setOverwritableValue(int i,int j,boolean overwritable)
    {
        grains[i][j].setOverwritable(overwritable);
    }

    /**
     * Get the value of the overwirtable flag
     * @param i x index of the grain
     * @param j y index of the grain
     * @return the value of the flag in the particular cell
     */
    public boolean isOverwritable(int i,int j)
    {
        return grains[i][j].isOverwritable();
    }

    /**
     * Updates the overwritable booleans in the cells, based on their`s state and radius
     */
    public void updateOverwritableValues(int radius,boolean periodically)
    {
        int initialRadius = radius;
        //radius += radius;
        for (int i = 0; i < nX; i++) 
            for (int j = 0; j < nY; j++)
                setOverwritableValue(i,j,true);

        if(periodically) {
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    if (getTheCellState(i, j) > 0) {
                        for (int k = 0; k < radius; k++) {
                            for (int l = 0; l < radius; l++) {
                                if ((k * k + l * l) < radius * radius) {
                                    setOverwritableValue((i + k) % nX, (j + l) % nY, false);
                                    setOverwritableValue((i + nX - k) % nX, (j + nY - l) % nY, false);
                                    setOverwritableValue((i + nX - k) % nX, (j + l) % nY, false);
                                    setOverwritableValue((i + k) % nX, (j + nY - l) % nY, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    if (getTheCellState(i, j) > 0) {
                        for (int k = 0; k < radius; k++) {
                            for (int l = 0; l < radius; l++) {
                                if ((k * k + l * l) < radius * radius) {
                                    if((i-k)>=0)
                                    {
                                        if((j-l)>=0)
                                        {
                                            setOverwritableValue(i - k, j  - l , false);
                                        }
                                        if((j+l)<nY)
                                        {
                                            setOverwritableValue(i  - k, j + l, false);
                                        }
                                    }

                                    if((i+k)<nX)
                                    {
                                        if((j-l)>=0)
                                        {
                                            setOverwritableValue(i + k, j  - l, false);
                                        }
                                        if((j+l)<nY)
                                        {
                                            setOverwritableValue(i + k, j + l, false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Make the overwritable flag false in a radius of a particular cell
     * @param i x index of the cell
     * @param j y index of the cell
     * @param radius radius in which the flag will be set
     */
    public void changeTheOverwritableValuesForASingleGrain(int i,int j,int radius)
    {
        for (int k = 0; k < radius; k++) {
            for (int l = 0; l < radius; l++) {
                if((k*k + l*l)<radius*radius)
                {
                    setOverwritableValue((i+k)%nX, (j+l)%nY, false);
                    setOverwritableValue((i+nX-k)%nX, (j+nY-l)%nY, false);
                    setOverwritableValue((i+nX-k)%nX, (j+l)%nY, false);
                    setOverwritableValue((i+k)%nX, (j+nY-l)%nY, false);
                }
            }
        }
    }

    public int getnX() {
        return nX;
    }

    public int getnY() {
        return nY;
    }
}
