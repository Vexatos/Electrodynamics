package nanocircuit.core;

import cpw.mods.fml.common.network.IGuiHandler;

import nanocircuit.core.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler 
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			default:
				return null;
		}
	}
	  
	public void registerRenderers()
	{
	}

}
