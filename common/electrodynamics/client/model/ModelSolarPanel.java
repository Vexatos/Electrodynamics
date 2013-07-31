package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelSolarPanel extends ModelTechne {
	ModelRenderer solarPanelPipeConnector2;
	ModelRenderer solarPanel;
	ModelRenderer solarPanelBottom;
	ModelRenderer solarPanelConnector;
	ModelRenderer solarPanelPipeConnector;
	ModelRenderer solarPanelPipe;
	ModelRenderer solarPanelPipe2;

	public ModelSolarPanel() {
		textureWidth = 128;
		textureHeight = 64;

		solarPanelPipeConnector2 = new ModelRenderer(this, 60, 23);
		solarPanelPipeConnector2.addBox(0F, 0F, 0F, 2, 4, 4);
		solarPanelPipeConnector2.setRotationPoint(2F, 22F, -2F);
		solarPanelPipeConnector2.setTextureSize(128, 64);
		solarPanelPipeConnector2.mirror = true;
		setRotation(solarPanelPipeConnector2, 0F, 0F, 1.570796F);
		solarPanel = new ModelRenderer(this, 0, 0);
		solarPanel.addBox(-8F, -5F, -8F, 16, 2, 16);
		solarPanel.setRotationPoint(0F, 13F, 0F);
		solarPanel.setTextureSize(128, 64);
		solarPanel.mirror = true;
		setRotation(solarPanel, 0F, 0F, 0F);
		solarPanelBottom = new ModelRenderer(this, 0, 20);
		solarPanelBottom.addBox(-7F, -3F, -7F, 14, 1, 14);
		solarPanelBottom.setRotationPoint(0F, 13F, 0F);
		solarPanelBottom.setTextureSize(128, 64);
		solarPanelBottom.mirror = true;
		setRotation(solarPanelBottom, 0F, 0F, 0F);
		solarPanelConnector = new ModelRenderer(this, 0, 37);
		solarPanelConnector.addBox(-2F, -2F, -2F, 4, 2, 4);
		solarPanelConnector.setRotationPoint(0F, 13F, 0F);
		solarPanelConnector.setTextureSize(128, 64);
		solarPanelConnector.mirror = true;
		setRotation(solarPanelConnector, 0F, 0F, 0F);
		solarPanelPipeConnector = new ModelRenderer(this, 28, 37);
		solarPanelPipeConnector.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
		solarPanelPipeConnector.setRotationPoint(0F, 17.5F, 0F);
		solarPanelPipeConnector.setTextureSize(128, 64);
		solarPanelPipeConnector.mirror = true;
		setRotation(solarPanelPipeConnector, 0F, 0F, 0F);
		solarPanelPipe = new ModelRenderer(this, 18, 37);
		solarPanelPipe.addBox(-1F, -4F, -1F, 2, 4, 2);
		solarPanelPipe.setRotationPoint(0F, 16F, 0F);
		solarPanelPipe.setTextureSize(128, 64);
		solarPanelPipe.mirror = true;
		setRotation(solarPanelPipe, 0F, 0F, 0F);
		solarPanelPipe2 = new ModelRenderer(this, 42, 37);
		solarPanelPipe2.addBox(0F, 0F, 0F, 8, 2, 2);
		solarPanelPipe2.setRotationPoint(1F, 15F, -1F);
		solarPanelPipe2.setTextureSize(128, 64);
		solarPanelPipe2.mirror = true;
		setRotation(solarPanelPipe2, 0F, 0F, 1.570796F);
	}

	@Override
	public void render(float f5) {
		solarPanelPipeConnector2.render(f5);
		solarPanel.render(f5);
		solarPanelBottom.render(f5);
		solarPanelConnector.render(f5);
		solarPanelPipeConnector.render(f5);
		solarPanelPipe.render(f5);
		solarPanelPipe2.render(f5);
	}

	public void renderPipe(float scale) {
		solarPanelPipeConnector.render(scale);
		solarPanelPipe.render(scale);
	}
	
	public void renderPipeBase(float scale) {
		solarPanelPipe2.render(scale);		
	}

	public void renderPipeBaseConnector(float scale) {
		solarPanelPipeConnector2.render(scale);
	}
	
	public void renderPanel(float scale) {
		solarPanel.render(scale);
		solarPanelBottom.render(scale);
		solarPanelConnector.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void rotatePanel(float x, float y, float z) {
		solarPanel.rotateAngleX = x;
		solarPanel.rotateAngleY = y;
		solarPanel.rotateAngleZ = z;
		solarPanelBottom.rotateAngleX = x;
		solarPanelBottom.rotateAngleY = y;
		solarPanelBottom.rotateAngleZ = z;
		solarPanelConnector.rotateAngleX = x;
		solarPanelConnector.rotateAngleY = y;
		solarPanelConnector.rotateAngleZ = z;
	}
	
}
