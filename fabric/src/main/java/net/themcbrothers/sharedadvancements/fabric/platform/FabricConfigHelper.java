package net.themcbrothers.sharedadvancements.fabric.platform;

import net.themcbrothers.sharedadvancements.fabric.ModConfigManager;
import net.themcbrothers.sharedadvancements.platform.services.IConfigHelper;

public class FabricConfigHelper implements IConfigHelper {
    @Override
    public boolean enabled() {
        return ModConfigManager.get().enableMod;
    }

    @Override
    public boolean broadcast() {
        return ModConfigManager.get().enableBroadcast;
    }
}
