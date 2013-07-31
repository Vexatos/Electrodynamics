package electrodynamics.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraftforge.common.Configuration;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EDXProperty {

	/** Under which category the property will be formatted under. Either {@link Configuration#CATEGORY_GENERAL}, {@link Configuration#CATEGORY_BLOCK} or {@link Configuration#CATEGORY_ITEM}. */
	String category();

	/** The name of the property, if empty uses the field name. */
	String name() default "";

	/** The default if there isn't already a property. */
	String defaultString() default "";

	/** The default if there isn't already a property. */
	int defaultInt() default -1;

	/** The default if there isn't already a property. */
	boolean defaultBoolean() default false;

	/** The default if there isn't already a property. */
	double defaultDouble() default -1.0d;

	/** If using the present value as default. */
	boolean usePresent() default true;

	/** A text to comment your property. Empty strings won't get used. */
	String comment() default "";
	
}
