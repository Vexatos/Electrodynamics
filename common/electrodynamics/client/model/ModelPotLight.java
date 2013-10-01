package electrodynamics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPotLight extends ModelBase {
	
	ModelRenderer basePlate;
	ModelRenderer frame4;
	ModelRenderer frame3;
	ModelRenderer frame2;
	ModelRenderer frame1;
	ModelRenderer bulb2;
	ModelRenderer bulb;

	public ModelPotLight() {
		textureWidth = 16;
		textureHeight = 16;

		basePlate = new ModelRenderer(this, 0, 0);
		basePlate.addBox(0F, 0F, 0F, 4, 1, 4);
		basePlate.setRotationPoint(-2F, 23.75F, -2F);
		basePlate.setTextureSize(16, 16);
		basePlate.mirror = true;
		setRotation(basePlate, 0F, 0F, 0F);
		frame4 = new ModelRenderer(this, 0, 8);
		frame4.addBox(-1F, 0F, 0F, 1, 1, 5);
		frame4.setRotationPoint(2.5F, 23.5F, -2.5F);
		frame4.setTextureSize(16, 16);
		frame4.mirror = true;
		setRotation(frame4, 0F, 0F, -0.122173F);
		frame3 = new ModelRenderer(this, 0, 8);
		frame3.addBox(0F, 0F, -1F, 5, 1, 1);
		frame3.setRotationPoint(-2.5F, 23.5F, 2.5F);
		frame3.setTextureSize(16, 16);
		frame3.mirror = true;
		setRotation(frame3, 0.122173F, 0F, 0F);
		frame2 = new ModelRenderer(this, 0, 8);
		frame2.addBox(0F, 0F, 0F, 1, 1, 5);
		frame2.setRotationPoint(-2.5F, 23.5F, -2.5F);
		frame2.setTextureSize(16, 16);
		frame2.mirror = true;
		setRotation(frame2, 0F, 0F, 0.122173F);
		frame1 = new ModelRenderer(this, 0, 8);
		frame1.addBox(0F, 0F, 0F, 5, 1, 1);
		frame1.setRotationPoint(-2.5F, 23.5F, -2.5F);
		frame1.setTextureSize(16, 16);
		frame1.mirror = true;
		setRotation(frame1, -0.122173F, 0F, 0F);
		bulb2 = new ModelRenderer(this, 8, 5);
		bulb2.addBox(0F, 0F, 0F, 1, 1, 1);
		bulb2.setRotationPoint(-0.5F, 23.55F, -0.5F);
		bulb2.setTextureSize(16, 16);
		bulb2.mirror = true;
		setRotation(bulb2, 0F, 0F, 0F);
		bulb = new ModelRenderer(this, 0, 5);
		bulb.addBox(0F, 0F, 0F, 2, 1, 2);
		bulb.setRotationPoint(-1F, 23.5F, -1F);
		bulb.setTextureSize(16, 16);
		bulb.mirror = true;
		setRotation(bulb, 0F, 0F, 0F);
	}

	public void render(float f5) {
		basePlate.render(f5);
		frame4.render(f5);
		frame3.render(f5);
		frame2.render(f5);
		frame1.render(f5);
		bulb2.render(f5);
		bulb.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
