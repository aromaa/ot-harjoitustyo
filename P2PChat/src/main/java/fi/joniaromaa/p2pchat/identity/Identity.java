package fi.joniaromaa.p2pchat.identity;

import javax.annotation.Nonnull;

/**
 * Represents entity that can be considered as single identity.
 */
public interface Identity {
	/**
	 * The name that should be displayed when refereeing to this {@link Identity}.
	 * 
	 * @return The name that should be displayed when refereeing to this {@link Identity}. This should never be null and suck cases should be considered as faulty implementation.
	 */
	public @Nonnull String getDisplayName();
}
