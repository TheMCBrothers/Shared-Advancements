package net.themcbrothers.sharedadvancements.platform.services;

public interface IConfigHelper {
    /**
     * Returns TRUE if the mod is enabled in the config
     */
    boolean enabled();

    /**
     * Return TRUE if the advancements should be synced for all players, not just for the team.
     */
    boolean broadcast();
}
