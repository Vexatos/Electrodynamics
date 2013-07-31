package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelTreeTap extends ModelTechne {
	ModelRenderer tapBottom;
	ModelRenderer tapSide1;
	ModelRenderer tapSide2;
	ModelRenderer tapHook;

	public ModelTreeTap() {
		textureWidth = 64;
		textureHeight = 32;

		tapBottom = new ModelRenderer(this, 0, 0);
		tapBottom.addBox(-1F, -1F, 0F, 2, 1, 6);
		tapBottom.setRotationPoint(0F, 1F, 0F);
		tapBottom.setTextureSize(64, 32);
		tapBottom.mirror = true;
		setRotation(tapBottom, 0.2617994F, 0F, 0F);
		tapSide1 = new ModelRenderer(this, 0, 0);
		tapSide1.addBox(1F, -2F, 0F, 1, 2, 6);
		tapSide1.setRotationPoint(0F, 1F, 0F);
		tapSide1.setTextureSize(64, 32);
		tapSide1.mirror = true;
		setRotation(tapSide1, 0.2617994F, 0F, 0F);
		tapSide2 = new ModelRenderer(this, 0, 0);
		tapSide2.addBox(-0.5F, -1F, 0F, 1, 3, 1);
		tapSide2.setRotationPoint(0F, 1F, 0F);
		tapSide2.setTextureSize(64, 32);
		tapSide2.mirror = true;
		setRotation(tapSide2, 0F, 0F, 0F);
		tapHook = new ModelRenderer(this, 0, 0);
		tapHook.addBox(-2F, -2F, 0F, 1, 2, 6);
		tapHook.setRotationPoint(0F, 1F, 0F);
		tapHook.setTextureSize(64, 32);
		tapHook.mirror = true;
		setRotation(tapHook, 0.2617994F, 0F, 0F);
	}

	@Override
	public void render(float f5) {
		tapBottom.render(f5);
		tapSide1.render(f5);
		tapSide2.render(f5);
		tapHook.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
