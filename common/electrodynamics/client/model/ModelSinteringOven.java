package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelSinteringOven extends ModelTechne {
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
	ModelRenderer topRack;

	public ModelSinteringOven() {
		textureWidth = 256;
		textureHeight = 128;

		hingeTop = new ModelRenderer(this, 65, 43);
		hingeTop.addBox(0F, 0F, 0F, 1, 3, 1);
		hingeTop.setRotationPoint(-8F, 10F, 7F);
		hingeTop.setTextureSize(256, 128);
		hingeTop.mirror = true;
		hingeBottom = new ModelRenderer(this, 69, 43);
		hingeBottom.addBox(0F, 0F, 0F, 1, 3, 1);
		hingeBottom.setRotationPoint(-8F, 15F, 7F);
		hingeBottom.setTextureSize(256, 128);
		hingeBottom.mirror = true;
		ovenBase = new ModelRenderer(this, 0, 34);
		ovenBase.addBox(0F, 0F, 0F, 16, 2, 16);
		ovenBase.setRotationPoint(-8F, 22F, -8F);
		ovenBase.setTextureSize(256, 128);
		ovenBase.mirror = true;
		ovenSupport = new ModelRenderer(this, 0, 52);
		ovenSupport.addBox(0F, 0F, 0F, 14, 2, 14);
		ovenSupport.setRotationPoint(-6F, 20F, -7F);
		ovenSupport.setTextureSize(256, 128);
		ovenSupport.mirror = true;
		ovenDoorTop = new ModelRenderer(this, 65, 17);
		ovenDoorTop.addBox(0F, 0F, -14F, 1, 3, 14);
		ovenDoorTop.setRotationPoint(-8F, 8F, 7F);
		ovenDoorTop.setTextureSize(256, 128);
		ovenDoorTop.mirror = true;
		ovenDoorBottom = new ModelRenderer(this, 65, 0);
		ovenDoorBottom.addBox(0F, 9F, -14F, 1, 3, 14);
		ovenDoorBottom.setRotationPoint(-8F, 8F, 7F);
		ovenDoorBottom.setTextureSize(256, 128);
		ovenDoorBottom.mirror = true;
		ovenDoorRight = new ModelRenderer(this, 73, 34);
		ovenDoorRight.addBox(0F, 3F, -14F, 1, 6, 3);
		ovenDoorRight.setRotationPoint(-8F, 8F, 7F);
		ovenDoorRight.setTextureSize(256, 128);
		ovenDoorRight.mirror = true;
		ovenDoorLeft = new ModelRenderer(this, 65, 34);
		ovenDoorLeft.addBox(0F, 3F, -3F, 1, 6, 3);
		ovenDoorLeft.setRotationPoint(-8F, 8F, 7F);
		ovenDoorLeft.setTextureSize(256, 128);
		ovenDoorLeft.mirror = true;
		ovenBottom = new ModelRenderer(this, 0, 17);
		ovenBottom.addBox(0F, 0F, 0F, 15, 1, 16);
		ovenBottom.setRotationPoint(-7F, 19F, -8F);
		ovenBottom.setTextureSize(256, 128);
		ovenBottom.mirror = true;
		ovenBack = new ModelRenderer(this, 0, 94);
		ovenBack.addBox(0F, 0F, 0F, 2, 10, 10);
		ovenBack.setRotationPoint(6F, 9F, -5F);
		ovenBack.setTextureSize(256, 128);
		ovenBack.mirror = true;
		ovenLeft = new ModelRenderer(this, 0, 81);
		ovenLeft.addBox(0F, 0F, 0F, 15, 10, 3);
		ovenLeft.setRotationPoint(-7F, 9F, 5F);
		ovenLeft.setTextureSize(256, 128);
		ovenLeft.mirror = true;
		ovenRight = new ModelRenderer(this, 0, 68);
		ovenRight.addBox(0F, 0F, 0F, 15, 10, 3);
		ovenRight.setRotationPoint(-7F, 9F, -8F);
		ovenRight.setTextureSize(256, 128);
		ovenRight.mirror = true;
		ovenTop = new ModelRenderer(this, 0, 0);
		ovenTop.addBox(0F, 0F, 0F, 15, 1, 16);
		ovenTop.setRotationPoint(-7F, 8F, -8F);
		ovenTop.setTextureSize(256, 128);
		ovenTop.mirror = true;
		bottomRack = new ModelRenderer(this, 26, 94);
		bottomRack.addBox(0F, 0F, 0F, 13, 1, 10);
		bottomRack.setRotationPoint(-7F, 16F, -5F);
		bottomRack.setTextureSize(256, 128);
		bottomRack.mirror = true;
		topRack = new ModelRenderer(this, 26, 94);
		topRack.addBox(0F, 0F, 0F, 13, 1, 10);
		topRack.setRotationPoint(-7F, 13F, -5F);
		topRack.setTextureSize(256, 128);
		topRack.mirror = true;
	}

	public void rotateDoor(float angle) {
		ovenDoorBottom.rotateAngleY = angle;
		ovenDoorTop.rotateAngleY = angle;
		ovenDoorLeft.rotateAngleY = angle;
		ovenDoorRight.rotateAngleY = angle;
	}
	
	public void renderAll(float f5) {
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
//		topRack.render(f5);
	}
}
