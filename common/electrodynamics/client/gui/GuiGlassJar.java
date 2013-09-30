package electrodynamics.client.gui;

import static electrodynamics.client.gui.module.GuiModule.MouseState.MOUSE_LEFT;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.network.PacketDispatcher;
import electrodynamics.client.gui.module.GuiModule;
import electrodynamics.client.gui.module.GuiModule.MouseState;
import electrodynamics.client.gui.module.GuiModuleHotspot;
import electrodynamics.client.gui.module.GuiModuleHotspot.IHotspotCallback;
import electrodynamics.configuration.ConfigurationSettings;
import electrodynamics.core.handler.GuiHandler.GuiType;
import electrodynamics.inventory.container.ContainerGlassJar;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemDust;
import electrodynamics.item.ItemGlassJar;
import electrodynamics.network.packet.PacketHotspotCallback;
import electrodynamics.network.packet.PacketPayload;
import electrodynamics.network.packet.PacketPayload.IPayloadReceptor;
import electrodynamics.network.packet.PacketUpdateSlot;
import electrodynamics.purity.AlloyFactory;
import electrodynamics.purity.MetalData;
import electrodynamics.util.math.Rectangle;
import electrodynamics.util.render.GLColor;
import electrodynamics.util.render.IconUtil;

public class GuiGlassJar extends GuiElectrodynamics implements IHotspotCallback, IPayloadReceptor {

	public static final ResourceLocation ITEM_ATLAS = TextureMap.locationItemsTexture;
	
	public static final Rectangle GUI_JAR_DIMENSIONS = new Rectangle(62, 16, 115, 79);

	public static final int DUST_MAX = 9;
	public static final int DUST_HEIGHT = (int) Math.floor(GUI_JAR_DIMENSIONS.getHeight() / DUST_MAX);

	public EntityPlayer player;

	public ItemStack jar;
	
	private ItemStack[] storedDusts;

	public ContainerGlassJar container;
	
	private boolean mixed = true;
	
	public int lastLength = 0;
	
	public GLColor lastColor = null;
	
	public GuiGlassJar(EntityPlayer player, ContainerGlassJar container) {
		super(GuiType.GLASS_JAR, container);
		
		this.container = container;
		this.player = player;
		updateJar();
	}
	
//	@Override
//	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
//		if (this.lastColor != null) {
//			this.fontRenderer.drawString("(" + (int)lastColor.r + ", " + (int)lastColor.g + ", " + (int)lastColor.b + ")", 5, 5, 0xFFFFFF);
//		}
//	}
	
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
					GLColor color = null;
					
					try {
						color = IconUtil.getCachedColor(this.storedDusts[index]);
					} catch (ArrayIndexOutOfBoundsException ex) {
						color = GLColor.WHITE;
					}
					
					if (index == 0) {
						this.drawRect(k + rect.x1, l + rect.y1, k + rect.x2, l + rect.y2 - 1, color.toInt());
					} else {
						this.drawRect(k + rect.x1, l + rect.y1, k + rect.x2, l + rect.y2, color.toInt());
					}
					
					GLColor.WHITE.apply();
				}
			} else {
				GLColor[] colors = new GLColor[this.storedDusts.length];
				for (int index=0; index<this.storedDusts.length; index++) {
					colors[index] = IconUtil.getCachedColor(this.storedDusts[index]);
				}
				GLColor average = new GLColor(colors);
				Rectangle rect = getMixedDustDimensions();

				this.drawRect(k + rect.x1, l + rect.y1, k + rect.x2, l + rect.y2 - 1, average.toInt());
			}
		}
	}
	
	@Override
	public void onClicked(EntityPlayer player, String uuid, MouseState state, ItemStack stack) {
		PacketHotspotCallback packet = new PacketHotspotCallback(uuid, state, stack);
		PacketDispatcher.sendPacketToServer(packet.makePacket());
		
		long currentClickTime = System.currentTimeMillis();
		
		if (System.currentTimeMillis() - this.lastClickTime <= 200 && this.lastClickTime != 0L && stack == null) { // Was double click
			if (!ConfigurationSettings.OLD_SHAKING_METHOD) {
				ItemStack[] dusts = ItemGlassJar.getStoredDusts(this.jar);
				AlloyFactory factory = null;
				
				for (ItemStack stackDust : dusts) {
					if (!ItemGlassJar.isMixed(this.jar)) {
						player.inventory.addItemStackToInventory(stackDust);
					} else {
						factory = AlloyFactory.fromArray(dusts);
						factory.addMetal(stackDust.copy());
					}
				}
				
				if (factory != null) {
					ItemStack alloyStack = factory.generateItemStack(0);
					alloyStack.stackSize = dusts.length;
					player.inventory.addItemStackToInventory(alloyStack.copy());
				}
				
				PacketPayload payload = new PacketPayload(1, 0, 0, 0, 0).setByte(0, (byte) 0);
				PacketDispatcher.sendPacketToServer(payload.makePacket());
				ItemGlassJar.dumpDusts(this.jar);
				ItemGlassJar.setMixed(this.jar, false);
				PacketUpdateSlot packet2 = new PacketUpdateSlot(this.jar, this.player.inventory.currentItem);
				PacketDispatcher.sendPacketToServer(packet2.makePacket());
			}
		} else {
			this.lastClickTime = currentClickTime;
			
			if (!ItemGlassJar.isMixed(this.jar)) {
				if (ItemDust.isDust(stack) && !(stack.getItem() == EDItems.itemAlloy)) {
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
		
		if (this.storedDusts != null && this.storedDusts.length != this.lastLength) {
			GLColor[] colors = new GLColor[this.storedDusts.length];
			for (int index=0; index<this.storedDusts.length; index++) {
				colors[index] = IconUtil.getCachedColor(this.storedDusts[index]);
			}
			GLColor average = new GLColor(colors);
			this.lastColor = average;
		}
		
		if (this.storedDusts != null && this.storedDusts.length > 0) {
			if (!mixed) {
				Rectangle[] dimensions = getDustDimensions();
				
				for (int i=0; i<dimensions.length; i++) {
					final String dustName = this.storedDusts[i].getDisplayName();
					Rectangle rectangle = dimensions[i];
					GuiModule module = new GuiModule("dust"+i, rectangle.x1, rectangle.y1, rectangle.getWidth(), rectangle.getHeight() - 1) {
						@Override
						public String[] getTooltip() {
							return new String[] {dustName};
						}
					};
					this.manager.registerModule(module);
				}
			} else {
				Rectangle dim = getMixedDustDimensions();
				GuiModule module = new GuiModule("mixed", dim.x1, dim.y1, dim.getWidth(), dim.getHeight()) {
					@Override
					public String[] getTooltip() {
						AlloyFactory factory = AlloyFactory.fromArray(storedDusts);
						MetalData[] data = factory.getMetals();
						String[] tooltip = new String[data.length + 1];
						tooltip[0] = "Mixed Dust";
						for (int i=0; i<data.length; i++) {
							tooltip[i+1] = data[i].component.getDisplayName() + ": " + ((float) (Math.round(data[i].ratio * 10000.0) / 100.0)) + "%";
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
			
			rectangle.y1 += GUI_JAR_DIMENSIONS.getHeight() - ((i + 1) * DUST_HEIGHT);
			--rectangle.x2;
			rectangle.y2 = rectangle.y1 + DUST_HEIGHT;
			
			dimensions[i] = rectangle;
		}
		
		return dimensions;
	}
	
	private Rectangle getMixedDustDimensions() {
		Rectangle rect = GUI_JAR_DIMENSIONS.copy();
		
		rect.y1 = rect.y2 - (this.storedDusts.length * DUST_HEIGHT);
		--rect.x2;
		
		return rect;
	}
	
	@Override
	public void handlePayload(byte[] byteArray, int[] intArray, double[] doubleArray, float[] floatArray, String[] stringArray) {
		if (byteArray != null && byteArray.length == 0 & byteArray[0] == 0) {
			this.updateJar();
		}
	}

}
