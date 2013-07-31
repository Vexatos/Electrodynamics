package electrodynamics.client.model;

import electrodynamics.api.render.ModelTechne;
import net.minecraft.client.model.ModelRenderer;

public class ModelBasicKiln extends ModelTechne {

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
		textureHeight = 128;

		kilnRight = new ModelRenderer( this, 0, 30 );
		kilnRight.addBox( 0F, 0F, 0F, 4, 16, 14 );
		kilnRight.setRotationPoint( 4F, 8F, -6F );
		kilnRight.setTextureSize( 128, 128 );
		kilnRight.mirror = true;
		kilnLeft = new ModelRenderer( this, 0, 0 );
		kilnLeft.addBox( 0F, 0F, 0F, 4, 16, 14 );
		kilnLeft.setRotationPoint( -8F, 8F, -6F );
		kilnLeft.setTextureSize( 128, 128 );
		kilnLeft.mirror = true;
		kilnTop = new ModelRenderer( this, 40, 0 );
		kilnTop.addBox( 0F, 0F, 0F, 8, 4, 14 );
		kilnTop.setRotationPoint( -4F, 8F, -6F );
		kilnTop.setTextureSize( 128, 128 );
		kilnTop.mirror = true;
		kilnBottom = new ModelRenderer( this, 84, 0 );
		kilnBottom.addBox( 0F, 0F, 0F, 8, 4, 14 );
		kilnBottom.setRotationPoint( -4F, 20F, -6F );
		kilnBottom.setTextureSize( 128, 128 );
		kilnBottom.mirror = true;
		kilnBack = new ModelRenderer( this, 40, 18 );
		kilnBack.addBox( 0F, 0F, 0F, 8, 8, 4 );
		kilnBack.setRotationPoint( -4F, 12F, 4F );
		kilnBack.setTextureSize( 128, 128 );
		kilnBack.mirror = true;
		bottomHinge = new ModelRenderer( this, 0, 0 );
		bottomHinge.addBox( 0F, 0F, 0F, 1, 4, 1 );
		bottomHinge.setRotationPoint( -7F, 18F, -7F );
		bottomHinge.setTextureSize( 128, 128 );
		bottomHinge.mirror = true;
		topHinge = new ModelRenderer( this, 0, 0 );
		topHinge.addBox( 0F, 0F, 0F, 1, 4, 1 );
		topHinge.setRotationPoint( -7F, 10F, -7F );
		topHinge.setTextureSize( 128, 128 );
		topHinge.mirror = true;
		kilnDoorInner = new ModelRenderer( this, 40, 30 );
		kilnDoorInner.addBox( 2F, 3F, 1F, 8, 8, 1 );
		kilnDoorInner.setRotationPoint( -6F, 9F, -7F );
		kilnDoorInner.setTextureSize( 128, 128 );
		kilnDoorInner.mirror = true;
		kilnDoor = new ModelRenderer( this, 40, 39 );
		kilnDoor.addBox( 0F, 0F, 0F, 12, 14, 1 );
		kilnDoor.setRotationPoint( -6F, 9F, -7F );
		kilnDoor.setTextureSize( 128, 128 );
		kilnDoor.mirror = true;
	}

	@Override
	public void render(float f5) {
		kilnRight.render( f5 );
		kilnLeft.render( f5 );
		kilnTop.render( f5 );
		kilnBottom.render( f5 );
		kilnBack.render( f5 );
		bottomHinge.render( f5 );
		topHinge.render( f5 );
		kilnDoorInner.render( f5 );
		kilnDoor.render( f5 );
	}

	public void rotateDoor(float angle) {
		kilnDoor.rotateAngleY = angle;
		kilnDoorInner.rotateAngleY = angle;
	}

}
