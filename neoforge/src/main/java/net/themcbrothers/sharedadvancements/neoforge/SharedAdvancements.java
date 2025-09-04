package net.themcbrothers.sharedadvancements.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.themcbrothers.sharedadvancements.CommonClass;
import net.themcbrothers.sharedadvancements.Constants;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements(ModContainer modContainer) {
        CommonClass.init();
        modContainer.registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
    }
}