package electrodynamics.util.gen;

import java.util.ArrayList;
import java.util.List;

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
	
	private static boolean[] calculateNext(boolean[] last)
	{
		int size = last.length;
		
		boolean[] next = new boolean[size];
		for(int i=0; i<size; i++)
		{
			if(i==0)
			{
				if(last[i+1])
				{
					next[i] = true;
				}
			}
			else if(i==size)
			{
				if(last[i-1])
				{
					next[i] = true;
				}
			}
			else
			{
				next[i] = last[i-1] ^ last[i+1];
			}
		}
		
		return next;
	}
	
	public boolean[] next()
	{
		boolean[] next = calculateNext(state.get(state.size()));
		state.add(next);
		return next;
	}
	
	public boolean[] get(int timeStep)
	{
		if(timeStep > state.size())
		{
			for(int i=state.size(); i<timeStep; i++)
			{
				next();
			}
		}
		
		return state.get(timeStep);
	}
	
	public Rule90 init(boolean[] init)
	{
		this.init = init;
		state.clear();
		state.add(init);
		
		return this;
	}
	
	public boolean[] getInit()
	{
		return this.init;
	}

}