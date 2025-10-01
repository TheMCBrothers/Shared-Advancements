package net.themcbrothers.sharedadvancements;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Team;
import net.themcbrothers.sharedadvancements.platform.Services;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Contains all the platform-independent code
 */
public class CommonClass {
    private static boolean skipEvent;

    /**
     * Initialize Mod
     */
    public static void init() {
        Constants.LOG.info("Initializing {} for {}", Constants.MOD_NAME, Services.PLATFORM.getPlatformName());
    }

    /**
     * Syncs advancement progress when someone makes progress
     *
     * @param player        Player
     * @param criterionName Criterion name
     * @param advancement   Advancement holder
     */
    public static void progressAdvancement(ServerPlayer player, String criterionName, AdvancementHolder advancement) {
        handle(player, (serverPlayer, server) -> serverPlayer.getAdvancements().award(advancement, criterionName));
    }

    /**
     * Syncs advancement progress when a player joins
     *
     * @param player Server Player
     */
    public static void playerJoin(ServerPlayer player) {
        handle(player, (serverPlayer, server) -> syncCriteria(player, serverPlayer, server));
    }

    /**
     * Handles advancement sharing with a callback.
     *
     * @param player   Server player
     * @param callback This is called for each player that is
     *                 - not the given player
     *                 - and in the same team as the given player (when broadcast is disabled)
     */
    private static void handle(final ServerPlayer player, BiConsumer<ServerPlayer, MinecraftServer> callback) {
        if (skipEvent || !Services.CONFIG.enabled()) {
            return;
        }

        boolean broadcast = Services.CONFIG.broadcast();

        // noinspection resource
        MinecraftServer server = player.level().getServer();
        Team team = player.getTeam();

        // noinspection ConstantValue
        if (server != null && (broadcast || team != null)) {
            skipEvent = true;
            server.getPlayerList().getPlayers().stream()
                    .filter(serverPlayer -> !serverPlayer.equals(player))
                    .filter(serverPlayer -> broadcast || team.getPlayers().contains(serverPlayer.getScoreboardName()))
                    .forEach(serverPlayer -> callback.accept(serverPlayer, server));
            skipEvent = false;
        }
    }

    private static void syncCriteria(final ServerPlayer first, final ServerPlayer second, final MinecraftServer server) {
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
