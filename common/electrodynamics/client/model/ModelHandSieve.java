package electrodynamics.client.model;

import net.minecraft.client.model.ModelRenderer;
import electrodynamics.api.render.ModelTechne;

public class ModelHandSieve extends ModelTechne {
	ModelRenderer east;
	ModelRenderer south;
	ModelRenderer northEast;
	ModelRenderer southEast;
	ModelRenderer west;
	ModelRenderer southWest;
	ModelRenderer northWest;
	ModelRenderer north;
	ModelRenderer mesh;

	public ModelHandSieve() {
		textureWidth = 64;
		textureHeight = 32;

		east = new ModelRenderer(this, 0, 0);
		east.addBox(0F, 0F, -5F, 1, 2, 5);
		east.setRotationPoint(4.35F, 21F, 2.5F);
		east.setTextureSize(64, 32);
		east.mirror = true;
		setRotation(east, 0F, 0F, 0F);
		south = new ModelRenderer(this, 0, 0);
		south.addBox(0F, 0F, -1F, 5, 2, 1);
		south.setRotationPoint(-2.5F, 21F, -4.3F);
		south.setTextureSize(64, 32);
		south.mirror = true;
		setRotation(south, 0F, 0F, 0F);
		northEast = new ModelRenderer(this, 0, 0);
		northEast.addBox(0F, 0F, -1F, 4, 2, 1);
		northEast.setRotationPoint(2.5F, 21F, 5.3F);
		northEast.setTextureSize(64, 32);
		northEast.mirror = true;
		setRotation(northEast, 0F, 0.7853982F, 0F);
		southEast = new ModelRenderer(this, 0, 0);
		southEast.addBox(0F, 0F, 0F, 4, 2, 1);
		southEast.setRotationPoint(2.5F, 21F, -5.3F);
		southEast.setTextureSize(64, 32);
		southEast.mirror = true;
		setRotation(southEast, 0F, -0.7853982F, 0F);
		west = new ModelRenderer(this, 0, 0);
		west.addBox(0F, 0F, -5F, 1, 2, 5);
		west.setRotationPoint(-5.35F, 21F, 2.5F);
		west.setTextureSize(64, 32);
		west.mirror = true;
		setRotation(west, 0F, 0F, 0F);
		southWest = new ModelRenderer(this, 0, 0);
		southWest.addBox(-4F, 0F, 0F, 4, 2, 1);
		southWest.setRotationPoint(-2.5F, 21F, -5.3F);
		southWest.setTextureSize(64, 32);
		southWest.mirror = true;
		setRotation(southWest, 0F, 0.7853982F, 0F);
		northWest = new ModelRenderer(this, 0, 0);
		northWest.addBox(-4F, 0F, -1F, 4, 2, 1);
		northWest.setRotationPoint(-2.5F, 21F, 5.3F);
		northWest.setTextureSize(64, 32);
		northWest.mirror = true;
		setRotation(northWest, 0F, -0.7853982F, 0F);
		north = new ModelRenderer(this, 0, 0);
		north.addBox(0F, 0F, -1F, 5, 2, 1);
		north.setRotationPoint(-2.5F, 21F, 5.3F);
		north.setTextureSize(64, 32);
		north.mirror = true;
		setRotation(north, 0F, 0F, 0F);
		mesh = new ModelRenderer(this, 0, 8);
		mesh.addBox(0F, 0F, 0F, 10, 1, 10);
		mesh.setRotationPoint(-5F, 23.05F, -5F);
		mesh.setTextureSize(64, 32);
		mesh.mirror = true;
		setRotation(mesh, 0F, 0F, 0F);
	}

	public void render(float f5) {
		east.render(f5);
		south.render(f5);
		northEast.render(f5);
		southEast.render(f5);
		west.render(f5);
		southWest.render(f5);
		northWest.render(f5);
		north.render(f5);
		mesh.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
