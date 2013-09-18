package electrodynamics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelAnvil extends ModelBase {
	ModelRenderer anvilFootR;
	ModelRenderer anvilStand;
	ModelRenderer anvilFootL;
	ModelRenderer anvilBase2;
	ModelRenderer anvilBase;
	ModelRenderer anvilBody;
	ModelRenderer end;
	ModelRenderer point2;
	ModelRenderer point1;

	public ModelAnvil() {
		textureWidth = 128;
		textureHeight = 64;

		anvilFootR = new ModelRenderer(this, 40, 18);
		anvilFootR.addBox(0F, 0F, 0F, 3, 2, 12);
		anvilFootR.setRotationPoint(4F, 22F, -6F);
		anvilFootR.setTextureSize(128, 64);
		anvilFootR.mirror = true;
		setRotation(anvilFootR, 0F, 0F, 0F);
		anvilStand = new ModelRenderer(this, 52, 0);
		anvilStand.addBox(0F, 0F, 0F, 8, 6, 5);
		anvilStand.setRotationPoint(-4F, 14F, -2.5F);
		anvilStand.setTextureSize(128, 64);
		anvilStand.mirror = true;
		setRotation(anvilStand, 0F, 0F, 0F);
		anvilFootL = new ModelRenderer(this, 40, 18);
		anvilFootL.addBox(0F, 0F, 0F, 3, 2, 12);
		anvilFootL.setRotationPoint(-7F, 22F, -6F);
		anvilFootL.setTextureSize(128, 64);
		anvilFootL.mirror = true;
		setRotation(anvilFootL, 0F, 0F, 0F);
		anvilBase2 = new ModelRenderer(this, 0, 16);
		anvilBase2.addBox(0F, 0F, 0F, 12, 2, 8);
		anvilBase2.setRotationPoint(-6F, 21F, -4F);
		anvilBase2.setTextureSize(128, 64);
		anvilBase2.mirror = true;
		setRotation(anvilBase2, 0F, 0F, 0F);
		anvilBase = new ModelRenderer(this, 52, 11);
		anvilBase.addBox(0F, 0F, 0F, 10, 1, 6);
		anvilBase.setRotationPoint(-5F, 20F, -3F);
		anvilBase.setTextureSize(128, 64);
		anvilBase.mirror = true;
		setRotation(anvilBase, 0F, 0F, 0F);
		anvilBody = new ModelRenderer(this, 0, 0);
		anvilBody.addBox(0F, 0F, 0F, 16, 6, 10);
		anvilBody.setRotationPoint(-8F, 8F, -5F);
		anvilBody.setTextureSize(128, 64);
		anvilBody.mirror = true;
		setRotation(anvilBody, 0F, 0F, 0F);
		end = new ModelRenderer(this, 78, 0);
		end.addBox(0F, 0F, 0F, 2, 4, 7);
		end.setRotationPoint(-9.5F, 9F, -3.5F);
		end.setTextureSize(128, 64);
		end.mirror = true;
		setRotation(end, 0F, 0F, 0F);
		point2 = new ModelRenderer(this, 96, 0);
		point2.addBox(0F, 0F, 0F, 5, 3, 5);
		point2.setRotationPoint(3.5F, 9F, 2F);
		point2.setTextureSize(128, 64);
		point2.mirror = true;
		setRotation(point2, 0F, 1.073377F, 0F);
		point1 = new ModelRenderer(this, 96, 0);
		point1.addBox(0F, 0F, 0F, 5, 3, 5);
		point1.setRotationPoint(3.5F, 9F, -2F);
		point1.setTextureSize(128, 64);
		point1.mirror = true;
		setRotation(point1, 0F, 0.4974188F, 0F);
	}

	public void render(float f5) {
		anvilFootR.render(f5);
		anvilStand.render(f5);
		anvilFootL.render(f5);
		anvilBase2.render(f5);
		anvilBase.render(f5);
		anvilBody.render(f5);
		end.render(f5);
		point2.render(f5);
		point1.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
