package net.themcbrothers.sharedadvancements.neoforge.platform;

import net.themcbrothers.sharedadvancements.neoforge.SharedAdvancementsConfig;
import net.themcbrothers.sharedadvancements.platform.services.IConfigHelper;

public class NeoForgeConfigHelper implements IConfigHelper {
    @Override
    public boolean enabled() {
        return SharedAdvancementsConfig.INSTANCE.enabled.get();
    }

    @Override
    public boolean broadcast() {
        return SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get();
    }
}
