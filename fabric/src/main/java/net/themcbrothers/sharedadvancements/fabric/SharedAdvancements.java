package net.themcbrothers.sharedadvancements.fabric;

import net.fabricmc.api.ModInitializer;
import net.themcbrothers.sharedadvancements.CommonClass;

public class SharedAdvancements implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfigManager.load();
        CommonClass.init();
    }
}
