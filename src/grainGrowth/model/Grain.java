package grainGrowth.model;

/**
 * A single cell represantation
 */
public class Grain {
    private int i,j;
    private int state;
    private int nextState;
    private boolean overwritable = true;
    private int energy;
    private int nextEnergy;


    /**
     * @param i x id in the table
     * @param j y id in the table
     */
    public Grain(int i,int j)
    {
        this.i = i;
        this.j = j;
    }

    /**
     * Rewriting the state from next iteration
     */
    public void setStateFromNextIteration()
    {
        this.state = this.nextState;
        //this.nextState = 0;
    }

    /**
     * Rewrites the next generation state to actual state, if the next energy is lesser in this particular grain
     */
    public void updateStateBasedOnEnergy() {
        if(nextEnergy <= energy)
            setStateFromNextIteration();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }


    public boolean isOverwritable() {
        return overwritable;
    }

    public void setOverwritable(boolean overwritable) {
        this.overwritable = overwritable;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getNextEnergy() {
        return nextEnergy;
    }

    public void setNextEnergy(int nextEnergy) {
        this.nextEnergy = nextEnergy;
    }
}
