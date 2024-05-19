package net.themcbrothers.sharedadvancements;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SharedAdvancementsConfig.SPEC);
        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);
    }

    private void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        CommonClass.playerJoin((ServerPlayer) event.getEntity());
    }
}