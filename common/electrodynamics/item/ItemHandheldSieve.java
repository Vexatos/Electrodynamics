package electrodynamics.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import electrodynamics.api.tool.ITool;
import electrodynamics.api.tool.ToolType;
import electrodynamics.core.CreativeTabED;
import electrodynamics.core.handler.GuiHandler;
import electrodynamics.lib.core.ModInfo;

public class ItemHandheldSieve extends Item {
 
	private Icon texture;
	
	public ItemHandheldSieve(int id) {
		super(id);
		setMaxStackSize(1);
		setMaxDamage(128);
		setCreativeTab(CreativeTabED.tool);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			if (!world.isRemote) {
				GuiHandler.openGui(player, world, (int)player.posX, (int)player.posY, (int)player.posZ, GuiHandler.GuiType.HAND_SIEVE);
			}
		}
		
		return stack;
	}
	
	@Override
	public Icon getIconFromDamage(int damage) {
		return texture;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.texture = register.registerIcon(ModInfo.ICON_PREFIX + "tool/handSieveWood");
	}
	
}
