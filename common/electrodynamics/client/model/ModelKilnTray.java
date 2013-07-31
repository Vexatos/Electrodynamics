package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelKilnTray extends ModelTechne {

	ModelRenderer handle1;
	ModelRenderer tray;
	ModelRenderer handle2;

	public ModelKilnTray() {
		textureWidth = 64;
		textureHeight = 32;

		handle1 = new ModelRenderer( this, 0, 13 );
		handle1.addBox( 0F, 0F, 0F, 1, 1, 3 );
		handle1.setRotationPoint( 0F, 20F, -6F );
		handle1.setTextureSize( 64, 32 );
		handle1.mirror = true;
		tray = new ModelRenderer( this, 0, 0 );
		tray.addBox( 0F, 0F, 0F, 7, 1, 7 );
		tray.setRotationPoint( -3F, 21F, -2F );
		tray.setTextureSize( 64, 32 );
		tray.mirror = true;
		handle2 = new ModelRenderer( this, 0, 9 );
		handle2.addBox( 0F, 0F, 0F, 1, 1, 2 );
		handle2.setRotationPoint( 0F, 20F, -3F );
		handle2.setTextureSize( 64, 32 );
		handle2.mirror = true;
	}

	public void renderAll(float f5) {
		handle1.render( f5 );
		tray.render( f5 );
		handle2.render( f5 );
	}

}
