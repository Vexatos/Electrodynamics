package electrodynamics.client.model;

import net.minecraft.client.model.ModelRenderer;
import electrodynamics.api.render.ModelTechne;

public class ModelGlassJar extends ModelTechne {
	ModelRenderer lid;
	ModelRenderer body;
	ModelRenderer neck;

	public ModelGlassJar() {
		textureWidth = 64;
		textureHeight = 32;

		lid = new ModelRenderer(this, 0, 13);
		lid.addBox(0F, 0F, 0F, 4, 1, 4);
		lid.setRotationPoint(-2F, 14.5F, -2F);
		lid.setTextureSize(64, 32);
		lid.mirror = true;
		setRotation(lid, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(0F, 0F, 0F, 5, 8, 5);
		body.setRotationPoint(-2.5F, 16F, -2.5F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		neck = new ModelRenderer(this, 0, 0);
		neck.addBox(0F, 0F, 0F, 3, 1, 3);
		neck.setRotationPoint(-1.5F, 15F, -1.5F);
		neck.setTextureSize(64, 32);
		neck.mirror = true;
		setRotation(neck, 0F, 0F, 0F);
	}

	public void render(float f5) {
		lid.render(f5);
		body.render(f5);
		neck.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
