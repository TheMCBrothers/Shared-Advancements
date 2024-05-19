package net.themcbrothers.sharedadvancements.platform;

import net.themcbrothers.sharedadvancements.platform.services.IConfigHelper;

public class FabricConfigHelper implements IConfigHelper {
    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public boolean broadcast() {
        return true;
    }
}
