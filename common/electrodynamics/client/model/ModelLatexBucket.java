package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelLatexBucket extends ModelTechne {
	ModelRenderer bucketBottom;
	ModelRenderer bucketBack;
	ModelRenderer bucketLeft;
	ModelRenderer bucketRight;
	ModelRenderer bucketFront;
	ModelRenderer handleTop;
	ModelRenderer handleLeft;
	ModelRenderer handleRight;

	public ModelLatexBucket() {
		textureWidth = 64;
		textureHeight = 32;

		bucketBottom = new ModelRenderer(this, 0, 16);
		bucketBottom.addBox(0F, 0F, 0F, 6, 1, 6);
		bucketBottom.setRotationPoint(-3F, 23F, -3F);
		bucketBottom.setTextureSize(64, 32);
		bucketBottom.mirror = true;
		setRotation(bucketBottom, 0F, 0F, 0F);
		bucketBack = new ModelRenderer(this, 0, 0);
		bucketBack.addBox(0F, 0F, 0F, 8, 6, 1);
		bucketBack.setRotationPoint(-4F, 18F, 3F);
		bucketBack.setTextureSize(64, 32);
		bucketBack.mirror = true;
		setRotation(bucketBack, 0F, 0F, 0F);
		bucketLeft = new ModelRenderer(this, 18, 0);
		bucketLeft.addBox(0F, 0F, 0F, 1, 6, 6);
		bucketLeft.setRotationPoint(-4F, 18F, -3F);
		bucketLeft.setTextureSize(64, 32);
		bucketLeft.mirror = true;
		setRotation(bucketLeft, 0F, 0F, 0F);
		bucketRight = new ModelRenderer(this, 18, 0);
		bucketRight.addBox(0F, 0F, 0F, 1, 6, 6);
		bucketRight.setRotationPoint(3F, 18F, -3F);
		bucketRight.setTextureSize(64, 32);
		bucketRight.mirror = true;
		setRotation(bucketRight, 0F, 0F, 0F);
		bucketFront = new ModelRenderer(this, 0, 0);
		bucketFront.addBox(0F, 0F, 0F, 8, 6, 1);
		bucketFront.setRotationPoint(-4F, 18F, -4F);
		bucketFront.setTextureSize(64, 32);
		bucketFront.mirror = true;
		setRotation(bucketFront, 0F, 0F, 0F);
		handleTop = new ModelRenderer(this, 4, 8);
		handleTop.addBox(0F, 0F, 0F, 1, 1, 6);
		handleTop.setRotationPoint(-0.5F, 15F, -3F);
		handleTop.setTextureSize(64, 32);
		handleTop.mirror = true;
		setRotation(handleTop, 0F, 0F, 0F);
		handleLeft = new ModelRenderer(this, 0, 8);
		handleLeft.addBox(0F, 0F, 0F, 1, 3, 1);
		handleLeft.setRotationPoint(-0.5F, 15F, -4F);
		handleLeft.setTextureSize(64, 32);
		handleLeft.mirror = true;
		setRotation(handleLeft, 0F, 0F, 0F);
		handleRight = new ModelRenderer(this, 0, 8);
		handleRight.addBox(0F, 0F, 0F, 1, 3, 1);
		handleRight.setRotationPoint(-0.5F, 15F, 3F);
		handleRight.setTextureSize(64, 32);
		handleRight.mirror = true;
		setRotation(handleRight, 0F, 0F, 0F);
	}

	@Override
	public void render(float f5) {
		bucketBottom.render(f5);
		bucketBack.render(f5);
		bucketLeft.render(f5);
		bucketRight.render(f5);
		bucketFront.render(f5);
		handleTop.render(f5);
		handleLeft.render(f5);
		handleRight.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
