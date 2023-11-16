package net.themcbrothers.sharedadvancements;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();

        // Register Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SharedAdvancementsConfig.SPEC);

        // Register events
        NeoForge.EVENT_BUS.addListener(this::onCriterion);
        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);
    }

    @SubscribeEvent
    private void onCriterion(final AdvancementEvent.AdvancementProgressEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get()) {
            return;
        }

        CommonClass.progressAdvancement(event.getEntity(), event.getCriterionName(), event.getAdvancement(), SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get());
    }

    @SubscribeEvent
    private void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get()) {
            return;
        }

        CommonClass.playerJoin(((ServerPlayer) event.getEntity()), SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get());
    }
}