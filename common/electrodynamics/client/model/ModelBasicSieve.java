package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelBasicSieve extends ModelTechne {
	ModelRenderer sieveBase;
	ModelRenderer sieveRackTop;
	ModelRenderer sieveRackMiddle;
	ModelRenderer sieveRackBottom;
	ModelRenderer sievePost4;
	ModelRenderer sievePost3;
	ModelRenderer sievePost2;
	ModelRenderer sievePost1;
	ModelRenderer topBack;
	ModelRenderer topLeft;
	ModelRenderer topRight;
	ModelRenderer topFront;

	public ModelBasicSieve() {
		textureWidth = 256;
		textureHeight = 128;

		sieveBase = new ModelRenderer(this, 0, 60);
		sieveBase.addBox(0F, 0F, 0F, 16, 7, 16);
		sieveBase.setRotationPoint(-8F, 17F, -8F);
		sieveBase.setTextureSize(256, 128);
		sieveBase.mirror = true;
		sieveRackTop = new ModelRenderer(this, 0, 40);
		sieveRackTop.addBox(0F, 0F, 0F, 14, 1, 14);
		sieveRackTop.setRotationPoint(-7F, 11F, -7F);
		sieveRackTop.setTextureSize(256, 128);
		sieveRackTop.mirror = true;
		sieveRackMiddle = new ModelRenderer(this, 0, 40);
		sieveRackMiddle.addBox(0F, 0F, 0F, 14, 1, 14);
		sieveRackMiddle.setRotationPoint(-7F, 13F, -7F);
		sieveRackMiddle.setTextureSize(256, 128);
		sieveRackMiddle.mirror = true;
		sieveRackBottom = new ModelRenderer(this, 0, 40);
		sieveRackBottom.addBox(0F, 0F, 0F, 14, 1, 14);
		sieveRackBottom.setRotationPoint(-7F, 15F, -7F);
		sieveRackBottom.setTextureSize(256, 128);
		sieveRackBottom.mirror = true;
		sievePost4 = new ModelRenderer(this, 0, 25);
		sievePost4.addBox(0F, 0F, 0F, 1, 7, 1);
		sievePost4.setRotationPoint(6.5F, 10F, 6.5F);
		sievePost4.setTextureSize(256, 128);
		sievePost4.mirror = true;
		sievePost3 = new ModelRenderer(this, 0, 25);
		sievePost3.addBox(0F, 0F, 0F, 1, 7, 1);
		sievePost3.setRotationPoint(-7.5F, 10F, -7.5F);
		sievePost3.setTextureSize(256, 128);
		sievePost3.mirror = true;
		sievePost2 = new ModelRenderer(this, 0, 25);
		sievePost2.addBox(0F, 0F, 0F, 1, 7, 1);
		sievePost2.setRotationPoint(6.5F, 10F, -7.5F);
		sievePost2.setTextureSize(256, 128);
		sievePost2.mirror = true;
		sievePost1 = new ModelRenderer(this, 0, 25);
		sievePost1.addBox(0F, 0F, 0F, 1, 7, 1);
		sievePost1.setRotationPoint(-7.5F, 10F, 6.5F);
		sievePost1.setTextureSize(256, 128);
		sievePost1.mirror = true;
		topBack = new ModelRenderer(this, 26, 19);
		topBack.addBox(0F, 0F, 0F, 10, 2, 3);
		topBack.setRotationPoint(-5F, 8F, 5F);
		topBack.setTextureSize(256, 128);
		topBack.mirror = true;
		topLeft = new ModelRenderer(this, 0, 0);
		topLeft.addBox(0F, 0F, 0F, 3, 2, 16);
		topLeft.setRotationPoint(-8F, 8F, -8F);
		topLeft.setTextureSize(256, 128);
		topLeft.mirror = true;
		topRight = new ModelRenderer(this, 38, 0);
		topRight.addBox(0F, 0F, 0F, 3, 2, 16);
		topRight.setRotationPoint(5F, 8F, -8F);
		topRight.setTextureSize(256, 128);
		topRight.mirror = true;
		topFront = new ModelRenderer(this, 0, 19);
		topFront.addBox(0F, 0F, 0F, 10, 2, 3);
		topFront.setRotationPoint(-5F, 8F, -8F);
		topFront.setTextureSize(256, 128);
		topFront.mirror = true;
	}

	@Override
	public void render(float f5) {
		sieveBase.render(f5);
		sieveRackTop.render(f5);
		sieveRackMiddle.render(f5);
		sieveRackBottom.render(f5);
		sievePost4.render(f5);
		sievePost3.render(f5);
		sievePost2.render(f5);
		sievePost1.render(f5);
		topBack.render(f5);
		topLeft.render(f5);
		topRight.render(f5);
		topFront.render(f5);
	}

}
