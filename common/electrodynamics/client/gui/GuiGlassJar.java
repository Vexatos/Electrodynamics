package electrodynamics.client.gui;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.client.gui.module.GuiModule;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.core.handler.IconHandler;
import electrodynamics.inventory.container.ContainerGlassJar;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.network.packet.PacketUpdateSlot;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.purity.DynamicAlloyPurities;
import electrodynamics.purity.MetalData;
import electrodynamics.util.StringUtil;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;
import electrodynamics.util.render.RenderUtil;

public class GuiGlassJar extends GuiElectrodynamics implements IHotspotCallback {

	public static final ResourceLocation ITEM_ATLAS = TextureMap.locationItemsTexture;
	
	public static final Rectangle GUI_JAR_DIMENSIONS = new Rectangle(62, 16, 53, 63);

	public static final int DUST_MAX = 9;
	public static final int DUST_HEIGHT = (int) Math.floor(GUI_JAR_DIMENSIONS.h / DUST_MAX);
	
	public EntityPlayer player;

	public ItemStack jar;
	
	private ItemStack[] storedDusts;
	
	private boolean mixed = true;
	
	public ContainerGlassJar container;
	
	public GuiGlassJar(EntityPlayer player, ContainerGlassJar container) {
		super(GuiType.GLASS_JAR, container);
		
		this.container = container;
		this.player = player;
		updateJar();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		super.drawGuiContainerBackgroundLayer(f, i, j);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (this.storedDusts != null && this.storedDusts.length > 0) {
			if (!mixed) {
				Rectangle[] dimensions = getDustDimensions();
				
				for (int index=0; index<this.storedDusts.length; index++) {
					Rectangle rect = dimensions[index];
					
					IconUtil.getCachedColor(this.storedDusts[i]).apply();
					RenderUtil.drawItem(k + rect.x, l + rect.y, IconHandler.getInstance().getIcon("dust.dust"), rect.w, rect.h);
					GLColor.WHITE.apply();
				}
			} else {
				GLColor[] colors = new GLColor[this.storedDusts.length];
				for (int index=0; index<this.storedDusts.length; index++) {
					colors[index] = IconUtil.getCachedColor(this.storedDusts[index]);
				}
				GLColor average = new GLColor(colors);
				average.apply();
				
				Rectangle rect = getMixedDustDimensions();
				RenderUtil.drawItem(k + rect.x , l + rect.y + GUI_JAR_DIMENSIONS.h - rect.h, IconHandler.getInstance().getIcon("dust.dust"), rect.w, rect.h);
			}
		}
	}
	
	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		PacketHotspotCallback packet = new PacketHotspotCallback(uuid, state, stack);
		PacketDispatcher.sendPacketToServer(packet.makePacket());
		
		if (!ItemGlassJar.isMixed(this.jar)) {
			if (FMLClientHandler.instance().getClient().thePlayer.capabilities.isCreativeMode) {
				if (state == MouseState.MOUSE_RIGHT) {
					ItemGlassJar.setMixed(this.jar, true);
				}
			}
			
			if (ItemDust.isDust(stack) && !DynamicAlloyPurities.getIDForStack(stack).equals("unknown")) {
				if (ItemGlassJar.getStoredDusts(this.jar).length < GuiGlassJar.DUST_MAX) {
					if (state == MOUSE_LEFT) {
						ItemStack newDust = stack.copy();
						newDust.stackSize = 1;
						addDust(newDust);
						
						if (stack.stackSize > 1) {
							--stack.stackSize;
						} else {
							stack = null;
						}
					}
					this.player.inventory.setItemStack(stack);
				}
			}
		}
		
		updateJar();
	}
	
	private void addDust(ItemStack dust) {
		ItemGlassJar.addDusts(this.jar, new ItemStack[] {dust});
		this.player.setCurrentItemOrArmor(0, this.jar);
		PacketUpdateSlot packet = new PacketUpdateSlot(this.jar, this.player.inventory.currentItem);
		PacketDispatcher.sendPacketToServer(packet.makePacket());
	}
	
	private void updateJar() {
		this.jar = player.getCurrentEquippedItem();
		this.storedDusts = ItemGlassJar.getStoredDusts(this.jar);
		this.mixed = ItemGlassJar.isMixed(jar);
		this.manager.reset();
		this.manager.registerModule(new GuiModuleHotspot("dustHotspot", 62, 16, 53, 63).setCallback(this));
		
		if (this.storedDusts != null && this.storedDusts.length > 0) {
			if (!mixed) {
				Rectangle[] dimensions = getDustDimensions();
				
				for (int i=0; i<dimensions.length; i++) {
					final String dustName = this.storedDusts[i].getDisplayName();
					Rectangle rectangle = dimensions[i];
					GuiModule module = new GuiModule("dust"+i, rectangle.x, rectangle.y, rectangle.w, rectangle.h) {
						@Override
						public String[] getTooltip() {
							return new String[] {dustName};
						}
					};
					this.manager.registerModule(module);
				}
			} else {
				Rectangle dim = getMixedDustDimensions();
				GuiModule module = new GuiModule("mixed", dim.x, dim.y + GUI_JAR_DIMENSIONS.h - dim.h, dim.w, dim.h) {
					@Override
					public String[] getTooltip() {
						AlloyFactory factory = AlloyFactory.fromInventory(storedDusts);
						MetalData[] data = factory.getMetals();
						String[] tooltip = new String[data.length + 1];
						tooltip[0] = "Mixed Dust";
						for (int i=0; i<data.length; i++) {
							tooltip[i+1] = StringUtil.toTitleCase(data[i].metalID) + ": " + ((float) (Math.round(data[i].ratio * 10000.0) / 100.0)) + "%";
						}
						return tooltip;
					}
				};
				this.manager.registerModule(module);
			}
		}
	}
	
	private Rectangle[] getDustDimensions() {
		Rectangle[] dimensions = new Rectangle[this.storedDusts.length];
		
		for (int i=0; i<this.storedDusts.length; i++) {
			Rectangle rectangle = GUI_JAR_DIMENSIONS.copy();
			rectangle.y = GUI_JAR_DIMENSIONS.y + GUI_JAR_DIMENSIONS.h - (i + 1) * DUST_HEIGHT;
			rectangle.w -= 1;
			rectangle.h = DUST_HEIGHT;
			dimensions[i] = rectangle;
		}
		
		return dimensions;
	}
	
	private Rectangle getMixedDustDimensions() {
		Rectangle rect = GUI_JAR_DIMENSIONS.copy();
		rect.h = (DUST_HEIGHT * this.storedDusts.length) - 2;
		rect.w -= 1;
		return rect;
	}
	
	private static class Rectangle {
		int x, y, w, h;
		public Rectangle(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		public Rectangle copy() {
			return new Rectangle(x, y, w, h);
		}
		public String toString() {
			return ("X: " + x + " Y: " + y + " W: " + w + " H: " + h);
		}
	}

}
