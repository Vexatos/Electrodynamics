package electrodynamics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSinteringOven extends ModelBase {
	ModelRenderer hingeTop;
	ModelRenderer hingeBottom;
	ModelRenderer ovenBase;
	ModelRenderer ovenSupport;
	ModelRenderer ovenDoorTop;
	ModelRenderer ovenDoorBottom;
	ModelRenderer ovenDoorRight;
	ModelRenderer ovenDoorLeft;
	ModelRenderer ovenBottom;
	ModelRenderer ovenBack;
	ModelRenderer ovenLeft;
	ModelRenderer ovenRight;
	ModelRenderer ovenTop;
	ModelRenderer bottomRack;
	ModelRenderer ovenDoorGlass;

	public ModelSinteringOven() {
		textureWidth = 256;
		textureHeight = 128;

		hingeTop = new ModelRenderer(this, 65, 43);
		hingeTop.addBox(0F, 0F, 0F, 1, 3, 1);
		hingeTop.setRotationPoint(-8F, 10F, 7F);
		hingeTop.setTextureSize(256, 128);
		hingeTop.mirror = true;
		setRotation(hingeTop, 0F, 0F, 0F);
		hingeBottom = new ModelRenderer(this, 69, 43);
		hingeBottom.addBox(0F, 0F, 0F, 1, 3, 1);
		hingeBottom.setRotationPoint(-8F, 15F, 7F);
		hingeBottom.setTextureSize(256, 128);
		hingeBottom.mirror = true;
		setRotation(hingeBottom, 0F, 0F, 0F);
		ovenBase = new ModelRenderer(this, 0, 34);
		ovenBase.addBox(0F, 0F, 0F, 16, 2, 16);
		ovenBase.setRotationPoint(-8F, 22F, -8F);
		ovenBase.setTextureSize(256, 128);
		ovenBase.mirror = true;
		setRotation(ovenBase, 0F, 0F, 0F);
		ovenSupport = new ModelRenderer(this, 0, 52);
		ovenSupport.addBox(0F, 0F, 0F, 14, 2, 14);
		ovenSupport.setRotationPoint(-6F, 20F, -7F);
		ovenSupport.setTextureSize(256, 128);
		ovenSupport.mirror = true;
		setRotation(ovenSupport, 0F, 0F, 0F);
		ovenDoorTop = new ModelRenderer(this, 65, 17);
		ovenDoorTop.addBox(0F, 0F, -14F, 1, 3, 14);
		ovenDoorTop.setRotationPoint(-8F, 8F, 7F);
		ovenDoorTop.setTextureSize(256, 128);
		ovenDoorTop.mirror = true;
		setRotation(ovenDoorTop, 0F, 0F, 0F);
		ovenDoorBottom = new ModelRenderer(this, 65, 0);
		ovenDoorBottom.addBox(0F, 9F, -14F, 1, 3, 14);
		ovenDoorBottom.setRotationPoint(-8F, 8F, 7F);
		ovenDoorBottom.setTextureSize(256, 128);
		ovenDoorBottom.mirror = true;
		setRotation(ovenDoorBottom, 0F, 0F, 0F);
		ovenDoorRight = new ModelRenderer(this, 73, 34);
		ovenDoorRight.addBox(0F, 3F, -14F, 1, 6, 3);
		ovenDoorRight.setRotationPoint(-8F, 8F, 7F);
		ovenDoorRight.setTextureSize(256, 128);
		ovenDoorRight.mirror = true;
		setRotation(ovenDoorRight, 0F, 0F, 0F);
		ovenDoorLeft = new ModelRenderer(this, 65, 34);
		ovenDoorLeft.addBox(0F, 3F, -3F, 1, 6, 3);
		ovenDoorLeft.setRotationPoint(-8F, 8F, 7F);
		ovenDoorLeft.setTextureSize(256, 128);
		ovenDoorLeft.mirror = true;
		setRotation(ovenDoorLeft, 0F, 0F, 0F);
		ovenBottom = new ModelRenderer(this, 0, 17);
		ovenBottom.addBox(0F, 0F, 0F, 15, 1, 16);
		ovenBottom.setRotationPoint(-7F, 19F, -8F);
		ovenBottom.setTextureSize(256, 128);
		ovenBottom.mirror = true;
		setRotation(ovenBottom, 0F, 0F, 0F);
		ovenBack = new ModelRenderer(this, 0, 94);
		ovenBack.addBox(0F, 0F, 0F, 2, 10, 10);
		ovenBack.setRotationPoint(6F, 9F, -5F);
		ovenBack.setTextureSize(256, 128);
		ovenBack.mirror = true;
		setRotation(ovenBack, 0F, 0F, 0F);
		ovenLeft = new ModelRenderer(this, 0, 81);
		ovenLeft.addBox(0F, 0F, 0F, 15, 10, 3);
		ovenLeft.setRotationPoint(-7F, 9F, 5F);
		ovenLeft.setTextureSize(256, 128);
		ovenLeft.mirror = true;
		setRotation(ovenLeft, 0F, 0F, 0F);
		ovenRight = new ModelRenderer(this, 0, 68);
		ovenRight.addBox(0F, 0F, 0F, 15, 10, 3);
		ovenRight.setRotationPoint(-7F, 9F, -8F);
		ovenRight.setTextureSize(256, 128);
		ovenRight.mirror = true;
		setRotation(ovenRight, 0F, 0F, 0F);
		ovenTop = new ModelRenderer(this, 0, 0);
		ovenTop.addBox(0F, 0F, 0F, 15, 1, 16);
		ovenTop.setRotationPoint(-7F, 8F, -8F);
		ovenTop.setTextureSize(256, 128);
		ovenTop.mirror = true;
		setRotation(ovenTop, 0F, 0F, 0F);
		bottomRack = new ModelRenderer(this, 26, 94);
		bottomRack.addBox(0F, 0F, 0F, 13, 1, 10);
		bottomRack.setRotationPoint(-7F, 16F, -5F);
		bottomRack.setTextureSize(256, 128);
		bottomRack.mirror = true;
		setRotation(bottomRack, 0F, 0F, 0F);
		ovenDoorGlass = new ModelRenderer(this, 36, 68);
		ovenDoorGlass.addBox(0.5F, 3F, -11F, 1, 6, 8);
		ovenDoorGlass.setRotationPoint(-8F, 8F, 7F);
		ovenDoorGlass.setTextureSize(256, 128);
		ovenDoorGlass.mirror = true;
		setRotation(ovenDoorGlass, 0F, 0F, 0F);
	}

	public void render(float f5) {
		hingeTop.render(f5);
		hingeBottom.render(f5);
		ovenBase.render(f5);
		ovenSupport.render(f5);
		ovenDoorTop.render(f5);
		ovenDoorBottom.render(f5);
		ovenDoorRight.render(f5);
		ovenDoorLeft.render(f5);
		ovenBottom.render(f5);
		ovenBack.render(f5);
		ovenLeft.render(f5);
		ovenRight.render(f5);
		ovenTop.render(f5);
		bottomRack.render(f5);
		ovenDoorGlass.render(f5);
	}

	public void rotateDoor(double angle) {
		ovenDoorBottom.rotateAngleY = (float)angle;
		ovenDoorTop.rotateAngleY = (float)angle;
		ovenDoorLeft.rotateAngleY = (float)angle;
		ovenDoorRight.rotateAngleY = (float)angle;
		ovenDoorGlass.rotateAngleY = (float)angle;
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
