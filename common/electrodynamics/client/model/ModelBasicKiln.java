package electrodynamics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBasicKiln extends ModelBase {
	ModelRenderer kilnRight;
	ModelRenderer kilnLeft;
	ModelRenderer kilnTop;
	ModelRenderer kilnBottom;
	ModelRenderer kilnBack;
	ModelRenderer bottomHinge;
	ModelRenderer topHinge;
	ModelRenderer kilnDoorInner;
	ModelRenderer kilnDoor;

	public ModelBasicKiln() {
		textureWidth = 128;
		textureHeight = 64;

		kilnRight = new ModelRenderer(this, 86, 0);
		kilnRight.addBox(0F, 0F, 0F, 4, 16, 15);
		kilnRight.setRotationPoint(4F, 8F, -7F);
		kilnRight.setTextureSize(128, 64);
		kilnRight.mirror = true;
		setRotation(kilnRight, 0F, 0F, 0F);
		kilnLeft = new ModelRenderer(this, 0, 0);
		kilnLeft.addBox(0F, 0F, 0F, 4, 16, 15);
		kilnLeft.setRotationPoint(-8F, 8F, -7F);
		kilnLeft.setTextureSize(128, 64);
		kilnLeft.mirror = true;
		setRotation(kilnLeft, 0F, 0F, 0F);
		kilnTop = new ModelRenderer(this, 40, 0);
		kilnTop.addBox(0F, 0F, 0F, 8, 4, 15);
		kilnTop.setRotationPoint(-4F, 8F, -7F);
		kilnTop.setTextureSize(128, 64);
		kilnTop.mirror = true;
		setRotation(kilnTop, 0F, 0F, 0F);
		kilnBottom = new ModelRenderer(this, 40, 19);
		kilnBottom.addBox(0F, 0F, 0F, 8, 4, 15);
		kilnBottom.setRotationPoint(-4F, 20F, -7F);
		kilnBottom.setTextureSize(128, 64);
		kilnBottom.mirror = true;
		setRotation(kilnBottom, 0F, 0F, 0F);
		kilnBack = new ModelRenderer(this, 23, 0);
		kilnBack.addBox(0F, 0F, 0F, 8, 8, 5);
		kilnBack.setRotationPoint(-4F, 12F, 3F);
		kilnBack.setTextureSize(128, 64);
		kilnBack.mirror = true;
		setRotation(kilnBack, 0F, 0F, 0F);
		bottomHinge = new ModelRenderer(this, 0, 0);
		bottomHinge.addBox(0F, 0F, 0F, 1, 4, 1);
		bottomHinge.setRotationPoint(-7F, 18F, -8F);
		bottomHinge.setTextureSize(128, 64);
		bottomHinge.mirror = true;
		setRotation(bottomHinge, 0F, 0F, 0F);
		topHinge = new ModelRenderer(this, 0, 0);
		topHinge.addBox(0F, 0F, 0F, 1, 4, 1);
		topHinge.setRotationPoint(-7F, 10F, -8F);
		topHinge.setTextureSize(128, 64);
		topHinge.mirror = true;
		setRotation(topHinge, 0F, 0F, 0F);
		kilnDoorInner = new ModelRenderer(this, 71, 0);
		kilnDoorInner.addBox(2F, 3F, 0.5F, 8, 8, 1);
		kilnDoorInner.setRotationPoint(-6F, 9F, -8F);
		kilnDoorInner.setTextureSize(128, 64);
		kilnDoorInner.mirror = true;
		setRotation(kilnDoorInner, 0F, 0F, 0F);
		kilnDoor = new ModelRenderer(this, 0, 31);
		kilnDoor.addBox(0F, 0F, 0F, 12, 14, 1);
		kilnDoor.setRotationPoint(-6F, 9F, -8F);
		kilnDoor.setTextureSize(128, 64);
		kilnDoor.mirror = true;
		setRotation(kilnDoor, 0F, 0F, 0F);
	}

	public void render(float f5) {
		kilnRight.render(f5);
		kilnLeft.render(f5);
		kilnTop.render(f5);
		kilnBottom.render(f5);
		kilnBack.render(f5);
		bottomHinge.render(f5);
		topHinge.render(f5);
		kilnDoorInner.render(f5);
		kilnDoor.render(f5);
	}

	public void rotateDoor(float angle) {
		kilnDoor.rotateAngleY = angle;
		kilnDoorInner.rotateAngleY = angle;
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
