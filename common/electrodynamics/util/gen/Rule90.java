package electrodynamics.util.gen;

import java.util.ArrayList;
import java.util.List;
/**
 * Basic simulation of a rule 90 cellular automaton. 
 * Might expand later to be a general-use CA class if needed. 
 * More tree types perhaps?	
 */
public class Rule90 
{
	private boolean[] init;
	private List<boolean[]> state;
	
	public Rule90(boolean[] init)
	{
		this.init = init;
		state = new ArrayList<boolean[]>();
		state.add(init);
	}
	
	public Rule90()
	{
		this.state = new ArrayList<boolean[]>();
	}
	/**
	 * Private method for calculating the steps
	 * 
	 * @param last the previous time-step
	 * @return     1D array of bools representing result
	 */
	private static boolean[] calculateNext(boolean[] last)
	{
		int size = last.length;
		
		boolean[] next = new boolean[size];
		for(int i=0; i<size; i++)
		{
			if(i==0)
			{
				next[i] = last[i+1];
			}
			else if(i==size-1)
			{
				next[i] = last[i-1];
			}
			else
			{
				next[i] = last[i-1] ^ last[i+1];
			}
		}
		
		return next;
	}
	/**
	 * Advances the internal simulation one time-step
	 * 
	 * @return the resulting array of bools
	 */
	public boolean[] next()
	{
		boolean[] next = calculateNext(state.get(state.size()-1));
		state.add(next);
		return next;
	}
	/**
	 * Returns an array of bools representing a specific time-step in the simulation
	 * 
	 * @param timeStep int representing the desired step in the simulation. 0-indexed
	 * @return         array of bools representing the state of the simulation at the desired step
	 */
	public boolean[] get(int timeStep)
	{
		if(timeStep >= state.size())
		{
			for(int i=state.size()-1; i<=timeStep; i++)
			{
				next();
			}
		}
		
		return state.get(timeStep);
	}
	/**
	 * Re-initialize internal simulation with a new starting state
	 * 
	 * @param init new initial state
	 * @return     for method chaining
	 */
	public Rule90 init(boolean[] init)
	{
		this.init = init;
		state.clear();
		state.add(init);
		
		return this;
	}
	/**
	 * 
	 * @return initial state of sim
	 */
	public boolean[] getInit()
	{
		return this.init;
	}
	/**
	 * 
	 * @param timeStep desired time step of simulation
	 * @param index    desired index in 1D array of cells
	 * @return         boolean representing the alive/dead state of cell
	 */
	public boolean testCell(int timeStep, int index)
	{
		return get(timeStep)[index];
	}

}