package net.themcbrothers.sharedadvancements;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
    }
}
