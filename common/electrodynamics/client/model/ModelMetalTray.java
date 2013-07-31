package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelMetalTray extends ModelTechne {
	ModelRenderer traySide1;
	ModelRenderer trayBase;
	ModelRenderer traySide2;
	ModelRenderer traySide3;
	ModelRenderer traySide4;

	public ModelMetalTray() {
		textureWidth = 64;
		textureHeight = 32;

		traySide1 = new ModelRenderer(this, 0, 20);
		traySide1.addBox(0F, 0F, 0F, 1, 1, 8);
		traySide1.setRotationPoint(3F, 22F, -4F);
		traySide1.setTextureSize(64, 32);
		traySide1.mirror = true;
		setRotation(traySide1, 0F, 0F, 0F);
		trayBase = new ModelRenderer(this, 0, 0);
		trayBase.addBox(0F, 0F, 0F, 8, 1, 10);
		trayBase.setRotationPoint(-4F, 23F, -5F);
		trayBase.setTextureSize(64, 32);
		trayBase.mirror = true;
		setRotation(trayBase, 0F, 0F, 0F);
		traySide2 = new ModelRenderer(this, 18, 13);
		traySide2.addBox(0F, 0F, 0F, 8, 1, 1);
		traySide2.setRotationPoint(-4F, 22F, -5F);
		traySide2.setTextureSize(64, 32);
		traySide2.mirror = true;
		setRotation(traySide2, 0F, 0F, 0F);
		traySide3 = new ModelRenderer(this, 18, 11);
		traySide3.addBox(0F, 0F, 0F, 8, 1, 1);
		traySide3.setRotationPoint(-4F, 22F, 4F);
		traySide3.setTextureSize(64, 32);
		traySide3.mirror = true;
		setRotation(traySide3, 0F, 0F, 0F);
		traySide4 = new ModelRenderer(this, 0, 11);
		traySide4.addBox(0F, 0F, 0F, 1, 1, 8);
		traySide4.setRotationPoint(-4F, 22F, -4F);
		traySide4.setTextureSize(64, 32);
		traySide4.mirror = true;
		setRotation(traySide4, 0F, 0F, 0F);
	}

	@Override
	public void render(float f5) {
		traySide1.render(f5);
		trayBase.render(f5);
		traySide2.render(f5);
		traySide3.render(f5);
		traySide4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
