package electrodynamics.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import electrodynamics.lib.client.Textures;

//TODO Improve
public class EntityBeam extends Entity {
	public int particle = 16;

	boolean updated = false;
	double movX = 0.0D;
	double movY = 0.0D;
	double movZ = 0.0D;

	private float length = 0.0F;
	private float rotYaw = 0.0F;
	private float rotPitch = 0.0F;
	private float prevYaw = 0.0F;
	private float prevPitch = 0.0F;
	private Entity targetEntity = null;
	public double tX = 0.0D;
	public double tY = 0.0D;
	public double tZ = 0.0D;

	public float particleRed;
	public float particleGreen;
	public float particleBlue;
	public float particleAlpha;
	public float particleAge;
	public float particleMaxAge;
	
	private float endMod = 1.0F;

	private boolean reverse = false;

	private boolean pulse = true;

	private int rotationspeed = 5;

	private float prevSize = 0.0F;

	public EntityBeam(World world, double x, double y, double z, double tx, double ty, double tz, int age) {
		super(world);

		this.setPosition(x, y, z);
		
		//Defaults to white
		this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);

		setSize(0.02F, 0.02F);
		this.noClip = true;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.tX = tx;
		this.tY = ty;
		this.tZ = tz;
		float xd = (float) (this.posX - this.tX);
		float yd = (float) (this.posY - this.tY);
		float zd = (float) (this.posZ - this.tZ);
		this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
		double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);
		this.rotYaw = ((float) (Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
		this.rotPitch = ((float) (Math.atan2(yd, var7) * 180.0D / 3.141592653589793D));
		this.prevYaw = this.rotYaw;
		this.prevPitch = this.rotPitch;

		this.particleMaxAge = age;
		
		this.boundingBox.setBounds(this.posX, this.posY, this.posZ, this.tX, this.tY, this.tZ);
	}

	public EntityBeam(World world, double x, double y, double z, Entity entity, int age) {
		this(world, x, y, z, entity.posX, entity.posY, entity.posZ, age);
	}

	public EntityBeam(World world, TileEntity tile, double x, double y, double z, int age) {
		this(world, tile.xCoord + 0.5, tile.yCoord + 0.5, tile.zCoord + 0.5, x, y, z, age);
	}
	
	public void updateBeam(double xs, double ys, double zs, double x, double y, double z) {
		this.movX = xs;
		this.movY = ys;
		this.movZ = zs;
		this.tX = x;
		this.tY = y;
		this.tZ = z;
		
		keepAlive();
		this.updated = true;
	}

	public void keepAlive() {
		while (this.particleMaxAge - this.particleAge < 4) this.particleMaxAge += 1;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (this.updated) {
			this.posX = this.movX;
			this.posY = this.movY;
			this.posZ = this.movZ;
			
			this.boundingBox.setBounds(this.posX, this.posY, this.posZ, this.tX, this.tY, this.tZ);
			
			this.updated = false;
		}

		this.prevYaw = this.rotYaw;
		this.prevPitch = this.rotPitch;

		if (this.targetEntity != null) {
			this.tX = this.targetEntity.posX;
			this.tY = (this.targetEntity.posY + this.targetEntity.getEyeHeight() - this.targetEntity.height / 2.0F);
			this.tZ = this.targetEntity.posZ;
		}

		for (Entity entity : getEntitiesIntersectingLaser()) {
			onEntityIntersected(entity);
		}
		
		float xd = (float) (this.posX - this.tX);
		float yd = (float) (this.posY - this.tY);
		float zd = (float) (this.posZ - this.tZ);

		this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);

		double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);

		this.rotYaw = ((float) (Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
		this.rotPitch = ((float) (Math.atan2(yd, var7) * 180.0D / 3.141592653589793D));

		if (this.particleAge++ >= this.particleMaxAge) {
			setDead();
		}
	}

	public void setRGB(float r, float g, float b) {
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
	}

	public void setEndMod(float endMod) {
		this.endMod = endMod;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public void setPulse(boolean pulse) {
		this.pulse = pulse;
	}

	public void setRotationspeed(int rotationspeed) {
		this.rotationspeed = rotationspeed;
	}

	@Override
	public void setSize(float f1, float f2) {
		super.setSize(f1, f2);
	}
	
	public void setTargetEntity(Entity entity) {
		this.targetEntity = entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getEntitiesIntersectingLaser() {
		return this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
	}
	
	public void onEntityIntersected(Entity entity) {
		
	}
	
	@SideOnly(Side.CLIENT)
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		EntityLivingBase renderentity = Minecraft.getMinecraft().renderViewEntity;
		int visibleDistance = 50;
		if (!ModLoader.getMinecraftInstance().gameSettings.fancyGraphics) visibleDistance = 25;
		if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) this.particleMaxAge = 0;
		
		tessellator.draw();

		GL11.glPushMatrix();
		float var9 = 1.0F;
		float slide = this.worldObj.getTotalWorldTime();
		float rot = this.worldObj.provider.getWorldTime() % (360 / this.rotationspeed) * this.rotationspeed + this.rotationspeed * f;

		float size = 1.0F;
		if (this.pulse) {
			size = Math.min(this.particleAge / 4.0F, 1.0F);
			size = this.prevSize + (size - this.prevSize) * f;
		}

		float op = 0.4F;
		if ((this.pulse) && (this.particleMaxAge - this.particleAge <= 4)) {
			op = 0.4F - (4 - (this.particleMaxAge - this.particleAge)) * 0.1F;
		}

		Textures.BEAM.bind();
		
		GL11.glTexParameterf(3553, 10242, 10497.0F);
		GL11.glTexParameterf(3553, 10243, 10497.0F);

		GL11.glDisable(2884);

		float var11 = slide + f;
		if (this.reverse) {
			var11 *= -1.0F;
		}
		float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);

		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);
		GL11.glDepthMask(false);

		float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * f - EntityFX.interpPosX);
		float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * f - EntityFX.interpPosY);
		float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * f - EntityFX.interpPosZ);
		GL11.glTranslated(xx, yy, zz);

		float ry = this.prevYaw + (this.rotYaw - this.prevYaw) * f;
		float rp = this.prevPitch + (this.rotPitch - this.prevPitch) * f;
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
		GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);

		double var44 = -0.15D * size;
		double var17 = 0.15D * size;
		double var44b = -0.15D * size * this.endMod;
		double var17b = 0.15D * size * this.endMod;

		GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
		for (int t = 0; t < 3; t++) {
			double var29 = this.length * size * var9;
			double var31 = 0.0D;
			double var33 = 1.0D;
			double var35 = -1.0F + var12 + t / 3.0F;
			double var37 = this.length * size * var9 + var35;

			GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
			tessellator.startDrawingQuads();
			tessellator.setBrightness(200);
			tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, op);
			tessellator.addVertexWithUV(var44b, var29, 0.0D, var33, var37);
			tessellator.addVertexWithUV(var44, 0.0D, 0.0D, var33, var35);
			tessellator.addVertexWithUV(var17, 0.0D, 0.0D, var31, var35);
			tessellator.addVertexWithUV(var17b, var29, 0.0D, var31, var37);
			tessellator.draw();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glEnable(2884);

		GL11.glPopMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("/textures/particle/particles.png"));
		tessellator.startDrawingQuads();
		this.prevSize = size;
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		
	}
}