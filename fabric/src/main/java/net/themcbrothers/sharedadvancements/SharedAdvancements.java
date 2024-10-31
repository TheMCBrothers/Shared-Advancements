package net.themcbrothers.sharedadvancements;

import net.fabricmc.api.ModInitializer;

public class SharedAdvancements implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
    }
}
