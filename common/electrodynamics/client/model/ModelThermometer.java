package electrodynamics.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelThermometer extends ModelBase {
	ModelRenderer back;
	ModelRenderer rod;
	ModelRenderer face;

	public ModelThermometer() {
		textureWidth = 64;
		textureHeight = 32;

		back = new ModelRenderer(this, 0, 6);
		back.addBox(0F, 0F, 0F, 3, 3, 2);
		back.setRotationPoint(-1.5F, 21F, -1.5F);
		back.setTextureSize(64, 32);
		back.mirror = true;
		setRotation(back, 0F, 0F, 0F);
		rod = new ModelRenderer(this, 12, 0);
		rod.addBox(1.5F, 2.5F, 2F, 1, 1, 5);
		rod.setRotationPoint(-2F, 20F, -2F);
		rod.setTextureSize(64, 32);
		rod.mirror = true;
		setRotation(rod, -0.2617994F, 0F, 0F);
		face = new ModelRenderer(this, 0, 0);
		face.addBox(0F, 0F, 0F, 4, 4, 2);
		face.setRotationPoint(-2F, 20F, -2F);
		face.setTextureSize(64, 32);
		face.mirror = true;
		setRotation(face, -0.2617994F, 0F, 0F);
	}

	public void render(float f5) {
		back.render(f5);
		rod.render(f5);
		face.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
