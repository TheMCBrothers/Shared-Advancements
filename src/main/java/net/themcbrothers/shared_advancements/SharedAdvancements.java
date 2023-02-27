package net.themcbrothers.shared_advancements;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@SuppressWarnings("deprecation")
@Mod(SharedAdvancements.MOD_ID)
public class SharedAdvancements {
    public static final String MOD_ID = "shared_advancements";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static boolean skipEvent;

    public SharedAdvancements() {
        LOGGER.info("Loading Shared Advancements");
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SharedAdvancementsConfig.SPEC);
    }

    @SubscribeEvent
    public void progressAdvancements(final AdvancementEvent.AdvancementProgressEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get() || skipEvent) {
            return;
        }

        boolean broadcast = SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get();

        Player player = event.getEntity();
        MinecraftServer server = player.getServer();
        Team team = player.getTeam();

        if (server != null && (broadcast || team != null)) {
            skipEvent = true;
            server.getPlayerList().getPlayers().stream()
                    .filter(serverPlayer -> broadcast || team.getPlayers().contains(serverPlayer.getScoreboardName()))
                    .forEach(serverPlayer -> serverPlayer.getAdvancements().award(event.getAdvancement(), event.getCriterionName()));
            skipEvent = false;
        }
    }
}
