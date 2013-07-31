package electrodynamics.api.render;

public interface ICustomRender {

	/** Used for anything that renders items/blocks in EDX.
	 *  Any item/block implementing this will have the various methods called
	 *  instead of relying on standard MC rendering */
	
	public void glManipulation();
	
	public ModelTechne getCustomModel();
	
}
