package net.themcbrothers.sharedadvancements.platform;

import net.themcbrothers.sharedadvancements.SharedAdvancementsConfig;
import net.themcbrothers.sharedadvancements.platform.services.IConfigHelper;

public class ForgeConfigHelper implements IConfigHelper {
    @Override
    public boolean enabled() {
        return SharedAdvancementsConfig.INSTANCE.enabled.get();
    }

    @Override
    public boolean broadcast() {
        return SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get();
    }
}
