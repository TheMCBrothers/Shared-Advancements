package net.themcbrothers.sharedadvancements.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.themcbrothers.sharedadvancements.Constants;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
    }
}
