package net.themcbrothers.sharedadvancements;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
    }
}