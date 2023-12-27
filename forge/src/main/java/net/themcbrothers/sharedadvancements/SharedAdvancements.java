package net.themcbrothers.sharedadvancements;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class SharedAdvancements {
    public SharedAdvancements() {
        CommonClass.init();

        // Register Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SharedAdvancementsConfig.SPEC);

        // Register events
        MinecraftForge.EVENT_BUS.addListener(this::onCriterion);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJoin);
    }

    private void onCriterion(final AdvancementEvent.AdvancementProgressEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get()) {
            return;
        }

        CommonClass.progressAdvancement(event.getEntity(), event.getCriterionName(), event.getAdvancement(), SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get());
    }

    private void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get()) {
            return;
        }

        CommonClass.playerJoin(((ServerPlayer) event.getEntity()), SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get());
    }
}