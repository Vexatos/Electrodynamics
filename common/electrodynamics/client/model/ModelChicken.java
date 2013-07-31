package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelChicken extends ModelTechne {
	ModelRenderer chicken;
	ModelRenderer chickenWingRight;
	ModelRenderer chickenWingLeft;
	ModelRenderer chickenLegLeft;
	ModelRenderer chickenBack;
	ModelRenderer chickenLegRight;

	public ModelChicken() {
		textureWidth = 32;
		textureHeight = 32;

		chicken = new ModelRenderer(this, 0, 0);
		chicken.addBox(0F, 0F, 0F, 3, 2, 3);
		chicken.setRotationPoint(-1.5F, 21F, 0F);
		chicken.setTextureSize(32, 32);
		chicken.mirror = true;
		setRotation(chicken, 0F, 0F, 0F);
		chickenWingRight = new ModelRenderer(this, 0, 0);
		chickenWingRight.addBox(0F, 0F, 0F, 1, 1, 2);
		chickenWingRight.setRotationPoint(1F, 21.5F, 0.5F);
		chickenWingRight.setTextureSize(32, 32);
		chickenWingRight.mirror = true;
		setRotation(chickenWingRight, 0F, 0F, 0F);
		chickenWingLeft = new ModelRenderer(this, 0, 0);
		chickenWingLeft.addBox(0F, 0F, 0F, 1, 1, 2);
		chickenWingLeft.setRotationPoint(-2F, 21.5F, 0.5F);
		chickenWingLeft.setTextureSize(32, 32);
		chickenWingLeft.mirror = true;
		setRotation(chickenWingLeft, 0F, 0F, 0F);
		chickenLegLeft = new ModelRenderer(this, 0, 0);
		chickenLegLeft.addBox(0F, 0F, 0F, 1, 1, 3);
		chickenLegLeft.setRotationPoint(-1F, 22F, -2.5F);
		chickenLegLeft.setTextureSize(32, 32);
		chickenLegLeft.mirror = true;
		setRotation(chickenLegLeft, 0.1745329F, -0.2617994F, 0F);
		chickenBack = new ModelRenderer(this, 0, 0);
		chickenBack.addBox(0F, 0F, 0F, 2, 1, 1);
		chickenBack.setRotationPoint(-1F, 21.2F, -1F);
		chickenBack.setTextureSize(32, 32);
		chickenBack.mirror = true;
		setRotation(chickenBack, 0F, 0F, 0F);
		chickenLegRight = new ModelRenderer(this, 0, 0);
		chickenLegRight.addBox(0F, 0F, 0F, 1, 1, 3);
		chickenLegRight.setRotationPoint(-0.5F, 22F, -2.5F);
		chickenLegRight.setTextureSize(32, 32);
		chickenLegRight.mirror = true;
		setRotation(chickenLegRight, 0.1745329F, 0.3490659F, 0F);
	}

	@Override
	public void render(float f5) {
		chicken.render(f5);
		chickenWingRight.render(f5);
		chickenWingLeft.render(f5);
		chickenLegLeft.render(f5);
		chickenBack.render(f5);
		chickenLegRight.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
