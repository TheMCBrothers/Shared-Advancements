package net.themcbrothers.sharedadvancements;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJoin);
    }

    private void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        CommonClass.playerJoin((ServerPlayer) event.getEntity());
    }
}
