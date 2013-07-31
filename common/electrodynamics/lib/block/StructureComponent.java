package electrodynamics.lib.block;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.block.EDBlocks;
import electrodynamics.block.SubBlock;
import electrodynamics.client.model.ModelMobGrinder;
import electrodynamics.client.model.ModelTechne;
import electrodynamics.lib.client.Textures;
import electrodynamics.lib.core.ModInfo;
import electrodynamics.lib.core.Strings;
import electrodynamics.tileentity.TileEntityGeneric;
import electrodynamics.tileentity.structure.TileEntityConveyorBelt;
import electrodynamics.tileentity.structure.TileEntityHatch;
import electrodynamics.tileentity.structure.TileEntityMobGrinder;
import electrodynamics.tileentity.structure.TileEntityRedstoneConductor;
import electrodynamics.tileentity.structure.TileEntityStructure;
import electrodynamics.tileentity.structure.TileEntityValve;

public enum StructureComponent implements SubBlock {

	CONVEYOR_BELT( Strings.STRUCTURE_COMPONENT_CONVEYOR_BELT ) {
		int[][] rotations = new int[][] {
				{ 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 },
				{ 3, 0, 3, 0, 1, 2 },
				{ 0, 3, 0, 3, 2, 0 }, // last pair is wrong.
				{ 2, 2, 2, 1, 3, 0 },
				{ 1, 1, 2, 1, 0, 3 }, // middle pair is wrong.
		};

		@Override
		public String[] getLocalTextureFiles() {
			String top = "machine_conveyorTop", side = "machine_conveyorSide";
			return new String[] { top, top, top, side, top, side };
		}

		@Override
		public int[][] getRotationMatrix() {
			return rotations;
		}

		@Override
		public TileEntityGeneric createNewTileEntity(World world) {
			return new TileEntityConveyorBelt();
		}
		
	},
	MACHINE_FRAME( Strings.STRUCTURE_COMPONENT_MACHINE_FRAME ) {
		@Override
		public String[] getLocalTextureFiles() {
			return new String[] { frame, frame, frame, frame, frame, frame };
		}
	},
	FURNACE_HEATER( Strings.STRUCTURE_COMPONENT_FURNACE_HEATER ) {
		@Override
		public String[] getLocalTextureFiles() {
			String base = "sinteringFurnace_heaterBlock";
			return new String[] { base, base, base, base, base, base };
		}
	},
	
	VALVE( Strings.STRUCTURE_COMPONENT_MACHINE_VALVE ) { // Liquid/Gas output
		@Override
		public String[] getLocalTextureFiles() {
			String base = "machine_valve";
			return new String[] { frame, frame, base, frame, base, frame };
		}
		
		@Override
		public TileEntityGeneric createNewTileEntity(World world) {
			return new TileEntityValve();
		}

	},
	
	HATCH( Strings.STRUCTURE_COMPONENT_MACHINE_HATCH ) { // Item output
		@Override
		public String[] getLocalTextureFiles() {
			String base = "machine_hatch";
			return new String[] { frame, frame, base, frame, base, frame };
		}
		
		@Override
		public TileEntityGeneric createNewTileEntity(World world) {
			return new TileEntityHatch();
		}
	},
	
	RS_CONDUCTOR(Strings.STRUCTURE_COMPONENT_MACHINE_RS_CONDUCTOR) {
		@Override
		public String[] getLocalTextureFiles() {
			//TODO New texture
			return HATCH.getLocalTextureFiles();
		}
		
		@Override
		public TileEntityGeneric createNewTileEntity(World world) {
			return new TileEntityRedstoneConductor();
		}
	},
	
	FURNACE_GAUGE( Strings.STRUCTURE_COMPONENT_FURNACE_GAUGE ) {
		@Override
		public String[] getLocalTextureFiles() {
			String base = "sinteringFurnace_gauge";
			return new String[] { frame, frame, base, frame, base, frame };
		}
	},
	
	FURNACE_VENT( Strings.STRUCTURE_COMPONENT_FURNACE_VENT ) {
		@Override
		public String[] getLocalTextureFiles() {
			String base = "sinteringFurnace_vent";
			return new String[] { base, base, base, base, base, base };
		}
	},
	
	MOB_GRINDER_BLADE(Strings.STRUCTURE_COMPONENT_MOB_GRINDER_BLADE) {
		@Override
		String[] getLocalTextureFiles() {
			return null;
		}
		
		@Override
		public ModelTechne getModel() {
			return new ModelMobGrinder();
		}

		@Override
		public boolean alternativeRender() {
			((ModelMobGrinder)this.getModel()).renderBlades(0.0625F);
			return false;
		}
		
		@Override
		public ResourceLocation getModelTexture() {
			return Textures.MOB_GRINDER_CLEAN.resource;
		}

		@Override
		public void applyGLTransformations(byte renderType, TileEntityStructure tile) {
			if (renderType == 1) {
				GL11.glScaled(.5, .5, .5);
			} else if (renderType == 0) {
				GL11.glTranslated(0, -.5, 0);
				int rotation = tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
				GL11.glRotatef(90 * (rotation / 2), 0, 1, 0);
			}
		}
	};
	
	private final String unLocalizedName;

	private StructureComponent(String unLocalizedName) {
		this.unLocalizedName = unLocalizedName;
	}

	@Override
	public String getUnlocalizedName() {
		return unLocalizedName;
	}

	abstract String[] getLocalTextureFiles();

	@Override
	public String[] getTextureFiles() {
		String[] local = getLocalTextureFiles();
		if (local != null) {
			String[] textures = new String[local.length];
			for( int i = 0; i < local.length; i++ ) {
				textures[i] = ModInfo.ICON_PREFIX + "machine/mbs/" + local[i];
			}
			return textures;
		}
		return new String[0];
	}

	@Override
	public TileEntityGeneric createNewTileEntity(World world) {
		return TileEntityStructure.createNewPlaceHolderTE();
	}

	@Override
	public ItemStack toItemStack() {
		return new ItemStack( EDBlocks.blockStructureComponent, 1, ordinal() );
	}

	public static StructureComponent get(int id) {
		return StructureComponent.values()[id];
	}
	
	/**
	 * Gets the matrix that describes the texture rotations to be used for this component.
	 * The order of the values matches the order of <code>ForgeDirection</code> enum constants.
	 *
	 * By default, an array of 6x0 is returned; which indicates that no rotation is necessary.
	 * @return A 6x6 array of int.
	 */
	public int[][] getRotationMatrix() {
		return new int[6][0];
	}

	/* MODEL RENDERING */
	/**
	 * The model to render. Prevents normal block + textures from rendering if not null
	 * @return An instance of ModelTechne
	 */
	public ModelTechne getModel() {
		return null;
	}
	/**
	 * Alternative render for structure block
	 * @return Whether or not the defined model should render as well
	 */
	public boolean alternativeRender() {
		return false;
	}
	/**
	 * Texture to be applied to rendered model
	 */
	public ResourceLocation getModelTexture() {
		return null;
	}
	/**
	 * Any GL transformations that should be applied upon rendering.
	 * @param renderType 0 or 1 value indicating render type. <br />
	 * <b>0: </b> Rendering in world
	 * <b>1: </b> Rendering in inventory/hand/as entity
	 * @param structure 
	 */
	public void applyGLTransformations(byte renderType, TileEntityStructure structure) { }
	
	private static String frame = "machine_frame";

	/**
	 * Creates a new TileEntityStructure based on the the world coordinates and NBT.
	 * Special cases must be added here.
	 * By default points to the correct sub-block's <code>createNewTileEntity()</code> method.
	 *
	 * @param world    the world object.
	 * @param x        the x coordinate
	 * @param y        the x coordinate
	 * @param z        the z coordinate
	 * @param nbt      the NBT containing the data available for creating the correct TileEntity.
	 * @param subBlock the index of the sub-block.
	 */
	public static TileEntityStructure createSpecificTileEntity(World world, int x, int y, int z, NBTTagCompound nbt, int subBlock) {
		if( subBlock == StructureComponent.MACHINE_FRAME.ordinal() ) {
			if( nbt.hasKey( "mbsID" ) && nbt.getString( "mbsID" ).equals( "MobGrinder" ) ) {
				int targetX = nbt.getInteger( "targetX" );
				int targetY = nbt.getInteger( "targetY" );
				int targetZ = nbt.getInteger( "targetZ" );

				if( x == targetX && y == targetY && z == targetZ ) { // is central TE
					return new TileEntityMobGrinder();
				}
			}
		}
		return (TileEntityStructure) values()[subBlock].createNewTileEntity( world );
	}

}
