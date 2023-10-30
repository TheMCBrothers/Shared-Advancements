package net.themcbrothers.shared_advancements;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import java.util.List;

@Mod(SharedAdvancements.MOD_ID)
public class SharedAdvancements {
    public static final String MOD_ID = "shared_advancements";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static boolean skipEvent;

    public SharedAdvancements() {
        LOGGER.info("Loading Shared Advancements");
        NeoForge.EVENT_BUS.register(this);
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

    @SubscribeEvent
    public void playerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!SharedAdvancementsConfig.INSTANCE.enabled.get()) {
            return;
        }

        boolean broadcast = SharedAdvancementsConfig.INSTANCE.broadcastAdvancements.get();

        ServerPlayer player = (ServerPlayer) event.getEntity();
        MinecraftServer server = player.getServer();
        Team team = player.getTeam();

        if (server != null && (broadcast || team != null)) {
            skipEvent = true;
            server.getPlayerList().getPlayers().stream()
                    .filter(serverPlayer -> broadcast || team.getPlayers().contains(serverPlayer.getScoreboardName()))
                    .forEach(serverPlayer -> syncCriteria(player, serverPlayer));
            skipEvent = false;
        }
    }

    private static void syncCriteria(final ServerPlayer first, final ServerPlayer second) {
        MinecraftServer server = first.getServer();

        if (server == null || server != second.getServer()) {
            return;
        }

        for (AdvancementHolder advancement : server.getAdvancements().getAllAdvancements()) {
            List<String> firstCompleted = (List<String>) first.getAdvancements().getOrStartProgress(advancement).getCompletedCriteria();
            List<String> secondCompleted = (List<String>) second.getAdvancements().getOrStartProgress(advancement).getCompletedCriteria();

            for (String criterion : advancement.value().criteria().keySet()) {
                if (firstCompleted.contains(criterion) && !secondCompleted.contains(criterion)) {
                    second.getAdvancements().award(advancement, criterion);
                } else if (!firstCompleted.contains(criterion) && secondCompleted.contains(criterion)) {
                    first.getAdvancements().award(advancement, criterion);
                }
            }
        }
    }
}
