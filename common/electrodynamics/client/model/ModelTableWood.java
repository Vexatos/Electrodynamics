package electrodynamics.client.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelTableWood extends ModelTable {
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer table;

	public ModelTableWood() {
		textureWidth = 64;
		textureHeight = 64;

		leg1 = new ModelRenderer(this, 0, 0);
		leg1.addBox(0F, 0F, 0F, 2, 2, 2);
		leg1.setRotationPoint(5F, 22F, 5F);
		leg1.setTextureSize(64, 64);
		leg1.mirror = true;
		leg2 = new ModelRenderer(this, 0, 0);
		leg2.addBox(0F, 0F, 0F, 2, 2, 2);
		leg2.setRotationPoint(5F, 22F, -7F);
		leg2.setTextureSize(64, 64);
		leg2.mirror = true;
		leg3 = new ModelRenderer(this, 0, 0);
		leg3.addBox(0F, 0F, 0F, 2, 2, 2);
		leg3.setRotationPoint(-7F, 22F, 5F);
		leg3.setTextureSize(64, 64);
		leg3.mirror = true;
		leg4 = new ModelRenderer(this, 0, 0);
		leg4.addBox(0F, 0F, 0F, 2, 2, 2);
		leg4.setRotationPoint(-7F, 22F, -7F);
		leg4.setTextureSize(64, 64);
		leg4.mirror = true;
		table = new ModelRenderer(this, 0, 28);
		table.addBox(0F, 0F, 0F, 14, 9, 14);
		table.setRotationPoint(-7F, 13F, -7F);
		table.setTextureSize(64, 64);
		table.mirror = true;
	}

	@Override
	public void render(float f5) {
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		table.render(f5);
	}

}
