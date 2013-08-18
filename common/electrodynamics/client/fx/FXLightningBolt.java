package electrodynamics.client.fx;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import electrodynamics.client.fx.FXLightningBoltCommon.Segment;
import electrodynamics.lib.client.Textures;
import electrodynamics.util.math.Vector3;

public class FXLightningBolt extends EntityFX {
	private int type = 0;
	private FXLightningBoltCommon main;

	public FXLightningBolt(World world, Vector3 jammervec, Vector3 targetvec, long seed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, jammervec, targetvec, seed);
		setupFromMain();
	}

	public FXLightningBolt(World world, Entity detonator, Entity target, long seed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, detonator, target, seed);
		setupFromMain();
	}

	public FXLightningBolt(World world, Entity detonator, Entity target, long seed, int speed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, detonator, target, seed, speed);
		setupFromMain();
	}

	public FXLightningBolt(World world, TileEntity detonator, Entity target, long seed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, detonator, target, seed);
		setupFromMain();
	}

	public FXLightningBolt(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, x1, y1, z1, x, y, z, seed, duration, multi);
		setupFromMain();
	}

	public FXLightningBolt(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, x1, y1, z1, x, y, z, seed, duration, multi, speed);
		setupFromMain();
	}

	public FXLightningBolt(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, x1, y1, z1, x, y, z, seed, duration, 1.0F);
		setupFromMain();
	}

	public FXLightningBolt(World world, TileEntity detonator, double x, double y, double z, long seed) {
		super(world, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.main = new FXLightningBoltCommon(world, detonator, x, y, z, seed);
		setupFromMain();
	}

	private void setupFromMain() {
		this.main.setWrapper(this);
		this.particleAge = this.main.particleMaxAge;
		setPosition(this.main.start.x, this.main.start.y, this.main.start.z);
		setVelocity(0.0D, 0.0D, 0.0D);
	}

	public void defaultFractal() {
		this.main.defaultFractal();
	}

	public void fractal(int splits, float amount, float splitchance, float splitlength, float splitangle) {
		this.main.fractal(splits, amount, splitchance, splitlength, splitangle);
	}

	public void finalizeBolt() {
		this.main.finalizeBolt();
		ModLoader.getMinecraftInstance().effectRenderer.addEffect(this);
	}

	public void setSourceEntity(Entity entity) {
		this.main.wrapper = entity;
	}

	public void setType(int type) {
		this.type = type;
		this.main.type = type;
	}

	public void setDamage(int dmg) {
		this.main.damage = dmg;
	}

	public void setNonLethal() {
		this.main.nonLethal = true;
	}

	public void setMultiplier(float m) {
		this.main.multiplier = m;
	}

	@Override
	public void onUpdate() {
		this.main.onUpdate();
		if (this.main.particleAge >= this.main.particleMaxAge) {
			setDead();
		}
	}

	private static Vector3 getRelativeViewVector(Vector3 pos) {
		EntityPlayer renderentity = ModLoader.getMinecraftInstance().thePlayer;
		return new Vector3((float) renderentity.posX - pos.x, (float) renderentity.posY - pos.y, (float) renderentity.posZ - pos.z);
	}

	private void renderBolt(Tessellator tessellator, float partialframe, float cosyaw, float cospitch, float sinyaw, float cossinpitch, int pass) {
		Vector3 playervec = new Vector3(sinyaw * -cospitch, -cossinpitch / cosyaw, cosyaw * cospitch);
		float boltage = this.main.particleAge >= 0 ? this.main.particleAge / this.main.particleMaxAge : 0.0F;
		float mainalpha = 1.0F;
		if (pass == 0) {
			mainalpha = (1.0F - boltage) * 0.9F;
		} else if (pass == 1) {
			mainalpha = 1.0F - boltage * 0.6F;
		} else {
			mainalpha = 1.0F - boltage * 0.3F;
		}

		int renderlength = (int) ((this.main.particleAge + partialframe + (int) (this.main.length * 3.0F)) / (int) (this.main.length * 3.0F) * this.main.numsegments0);
		for (Iterator<Segment> iterator = this.main.segments.iterator(); iterator.hasNext();) {
			FXLightningBoltCommon.Segment rendersegment = iterator.next();
			if (rendersegment.segmentno <= renderlength) {
				float width = 0.03F * (getRelativeViewVector(rendersegment.startpoint.point).length() / 10.0F + 1.0F) * (1.0F + rendersegment.light) * 0.5F;
				if (width > 0.05F)
					width = 0.05F;
				if (pass == 1)
					width += 0.025F;
				else if (pass == 1)
					width += 0.05F;
				Vector3 diff1 = Vector3.crossProduct(playervec, rendersegment.prevdiff).scale(width / rendersegment.sinprev);
				Vector3 diff2 = Vector3.crossProduct(playervec, rendersegment.nextdiff).scale(width / rendersegment.sinnext);
				Vector3 startvec = rendersegment.startpoint.point;
				Vector3 endvec = rendersegment.endpoint.point;
				float rx1 = (float) (startvec.x - interpPosX);
				float ry1 = (float) (startvec.y - interpPosY);
				float rz1 = (float) (startvec.z - interpPosZ);
				float rx2 = (float) (endvec.x - interpPosX);
				float ry2 = (float) (endvec.y - interpPosY);
				float rz2 = (float) (endvec.z - interpPosZ);
				tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, mainalpha * rendersegment.light);
				tessellator.addVertexWithUV(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z, 0.5D, 0.0D);
				tessellator.addVertexWithUV(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z, 0.5D, 0.0D);
				tessellator.addVertexWithUV(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z, 0.5D, 1.0D);
				tessellator.addVertexWithUV(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z, 0.5D, 1.0D);
				if (rendersegment.next == null) {
					Vector3 roundend = rendersegment.endpoint.point.copy().add(rendersegment.diff.copy().normalize().scale(width));
					float rx3 = (float) (roundend.x - interpPosX);
					float ry3 = (float) (roundend.y - interpPosY);
					float rz3 = (float) (roundend.z - interpPosZ);
					tessellator.addVertexWithUV(rx3 - diff2.x, ry3 - diff2.y, rz3 - diff2.z, 0.0D, 0.0D);
					tessellator.addVertexWithUV(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z, 0.5D, 0.0D);
					tessellator.addVertexWithUV(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z, 0.5D, 1.0D);
					tessellator.addVertexWithUV(rx3 + diff2.x, ry3 + diff2.y, rz3 + diff2.z, 0.0D, 1.0D);
				}
				if (rendersegment.prev == null) {
					Vector3 roundend = rendersegment.startpoint.point.copy().sub(rendersegment.diff.copy().normalize().scale(width));
					float rx3 = (float) (roundend.x - interpPosX);
					float ry3 = (float) (roundend.y - interpPosY);
					float rz3 = (float) (roundend.z - interpPosZ);
					tessellator.addVertexWithUV(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z, 0.5D, 0.0D);
					tessellator.addVertexWithUV(rx3 - diff1.x, ry3 - diff1.y, rz3 - diff1.z, 0.0D, 0.0D);
					tessellator.addVertexWithUV(rx3 + diff1.x, ry3 + diff1.y, rz3 + diff1.z, 0.0D, 1.0D);
					tessellator.addVertexWithUV(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z, 0.5D, 1.0D);
				}
			}
		}
	}

	@Override
	public void renderParticle(Tessellator tessellator, float partialframe, float cosyaw, float cospitch, float sinyaw, float sinsinpitch, float cossinpitch) {
		EntityPlayer renderentity = ModLoader.getMinecraftInstance().thePlayer;
		int visibleDistance = 100;
		if (!ModLoader.getMinecraftInstance().gameSettings.fancyGraphics)
			visibleDistance = 50;
		if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance)
			return;

		boolean wasDrawing = tessellator.isDrawing;
		if (wasDrawing)
			tessellator.draw();
		GL11.glPushMatrix();

		Minecraft.getMinecraft().func_110434_K().func_110577_a(Textures.LIGHTNING_BOLT.resource);
		
		GL11.glDepthMask(false);
		GL11.glEnable(3042);

		this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);

		int brightness = 983280;

		switch (this.type) {
		case 0:
			this.particleRed = 0.1F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.6F;
			GL11.glBlendFunc(770, 1);
			break;
		case 1:
			this.particleRed = 0.6F;
			this.particleGreen = 0.6F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 1);
			break;
		case 2:
			this.particleRed = 0.6F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.6F;
			GL11.glBlendFunc(770, 1);
			break;
		case 3:
			this.particleRed = 0.1F;
			this.particleGreen = 1.0F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 1);
			break;
		case 4:
			this.particleRed = 0.6F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 1);
			break;
		case 5:
			this.particleRed = 0.6F;
			this.particleGreen = 0.3F;
			this.particleBlue = 0.6F;
			GL11.glBlendFunc(770, 771);
			break;
		case 6:
			this.particleRed = 0.1F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 1);
			brightness = 983040;
		case 7:
			this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
			GL11.glBlendFunc(770, 1);
		}

		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		renderBolt(tessellator, partialframe, cosyaw, cospitch, sinyaw, cossinpitch, 0);
		tessellator.draw();

		brightness = 983280;

		switch (this.type) {
		case 0:
			this.particleRed = 1.0F;
			this.particleGreen = 0.6F;
			this.particleBlue = 1.0F;
			break;
		case 1:
			this.particleRed = 0.1F;
			this.particleGreen = 0.1F;
			this.particleBlue = 1.0F;
			break;
		case 2:
			this.particleRed = 0.0F;
			this.particleGreen = 0.0F;
			this.particleBlue = 0.0F;
			break;
		case 3:
			this.particleRed = 0.1F;
			this.particleGreen = 0.6F;
			this.particleBlue = 0.1F;
			break;
		case 4:
			this.particleRed = 1.0F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.1F;
			break;
		case 5:
			this.particleRed = 1.0F;
			this.particleGreen = 1.0F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 771);
			break;
		case 6:
			this.particleRed = 0.6F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.6F;
			GL11.glBlendFunc(770, 1);
			brightness = 983136;
		}

		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		renderBolt(tessellator, partialframe, cosyaw, cospitch, sinyaw, cossinpitch, 1);
		tessellator.draw();

		brightness = 983280;

		switch (this.type) {
		case 0:
			this.particleRed = 1.0F;
			this.particleGreen = 0.6F;
			this.particleBlue = 1.0F;
			break;
		case 1:
			this.particleRed = 0.1F;
			this.particleGreen = 0.1F;
			this.particleBlue = 1.0F;
			break;
		case 2:
			this.particleRed = 0.0F;
			this.particleGreen = 0.0F;
			this.particleBlue = 0.0F;
			break;
		case 3:
			this.particleRed = 0.1F;
			this.particleGreen = 0.6F;
			this.particleBlue = 0.1F;
			break;
		case 4:
			this.particleRed = 1.0F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.1F;
			break;
		case 5:
			this.particleRed = 1.0F;
			this.particleGreen = 1.0F;
			this.particleBlue = 0.1F;
			GL11.glBlendFunc(770, 771);
			break;
		case 6:
			this.particleRed = 0.6F;
			this.particleGreen = 0.1F;
			this.particleBlue = 0.6F;
			GL11.glBlendFunc(770, 1);
			brightness = 983136;
		}

		tessellator.startDrawingQuads();
		tessellator.setBrightness(brightness);
		renderBolt(tessellator, partialframe, cosyaw, cospitch, sinyaw, cossinpitch, 2);
		tessellator.draw();

		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();

		if (wasDrawing)
			tessellator.startDrawingQuads();
	}

	public int getRenderPass() {
		return 2;
	}

	public void setRandomType(int... types) {
		this.setType(getRandomType(types));
	}

	public int getRandomType(int[] types) {
		return types[this.rand.nextInt(types.length)];
	}
}