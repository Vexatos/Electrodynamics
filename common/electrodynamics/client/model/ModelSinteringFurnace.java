package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelSinteringFurnace extends ModelTechne {
	ModelRenderer ovenBase;
	ModelRenderer frontRight;
	ModelRenderer frontLeft;
	ModelRenderer backRight;
	ModelRenderer backLeft;
	ModelRenderer rightSide;
	ModelRenderer leftSide;
	ModelRenderer conveyor;
	ModelRenderer ovenTop;

	public ModelSinteringFurnace() {
		textureWidth = 512;
		textureHeight = 512;

		ovenBase = new ModelRenderer(this, 0, 0);
		ovenBase.addBox(0F, 0F, 0F, 112, 8, 48);
		ovenBase.setRotationPoint(-56F, 16F, -24F);
		ovenBase.setTextureSize(512, 512);
		ovenBase.mirror = true;
		setRotation(ovenBase, 0F, 0F, 0F);
		frontRight = new ModelRenderer(this, 192, 112);
		frontRight.addBox(0F, 0F, 0F, 24, 8, 8);
		frontRight.setRotationPoint(-56F, 8F, -24F);
		frontRight.setTextureSize(512, 512);
		frontRight.mirror = true;
		setRotation(frontRight, 0F, 0F, 0F);
		frontLeft = new ModelRenderer(this, 192, 95);
		frontLeft.addBox(0F, 0F, 0F, 24, 8, 8);
		frontLeft.setRotationPoint(-56F, 8F, 16F);
		frontLeft.setTextureSize(512, 512);
		frontLeft.mirror = true;
		setRotation(frontLeft, 0F, 0F, 0F);
		backRight = new ModelRenderer(this, 257, 95);
		backRight.addBox(0F, 0F, 0F, 24, 8, 8);
		backRight.setRotationPoint(32F, 8F, -24F);
		backRight.setTextureSize(512, 512);
		backRight.mirror = true;
		setRotation(backRight, 0F, 0F, 0F);
		backLeft = new ModelRenderer(this, 257, 112);
		backLeft.addBox(0F, 0F, 0F, 24, 8, 8);
		backLeft.setRotationPoint(32F, 8F, 16F);
		backLeft.setTextureSize(512, 512);
		backLeft.mirror = true;
		setRotation(backLeft, 0F, 0F, 0F);
		rightSide = new ModelRenderer(this, 320, 0);
		rightSide.mirror = true;
		rightSide.addBox(0F, 0F, 0F, 64, 24, 8);
		rightSide.setRotationPoint(-32F, -8F, -24F);
		rightSide.setTextureSize(512, 512);
		rightSide.mirror = true;
		setRotation(rightSide, 0F, 0F, 0F);
		rightSide.mirror = false;
		leftSide = new ModelRenderer(this, 0, 95);
		leftSide.addBox(0F, 0F, 0.01F, 64, 8, 32);
		leftSide.setRotationPoint(-32F, -8F, -16F);
		leftSide.setTextureSize(512, 512);
		leftSide.mirror = true;
		setRotation(leftSide, 0F, 0F, 0F);
		conveyor = new ModelRenderer(this, 0, 56);
		conveyor.addBox(0F, 0F, 0F, 110, 7, 32);
		conveyor.setRotationPoint(-55F, 9F, -16F);
		conveyor.setTextureSize(512, 512);
		conveyor.mirror = true;
		setRotation(conveyor, 0F, 0F, 0F);
		ovenTop = new ModelRenderer(this, 320, 32);
		ovenTop.addBox(0F, 0F, 0F, 64, 24, 8);
		ovenTop.setRotationPoint(-32F, -8F, 16F);
		ovenTop.setTextureSize(512, 512);
		ovenTop.mirror = true;
		setRotation(ovenTop, 0F, 0F, 0F);
	}

	@Override
	public void render(float f5) {
		ovenBase.render(f5);
		frontRight.render(f5);
		frontLeft.render(f5);
		backRight.render(f5);
		backLeft.render(f5);
		rightSide.render(f5);
		leftSide.render(f5);
		conveyor.render(f5);
		ovenTop.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
