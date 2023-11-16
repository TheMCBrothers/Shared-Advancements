package net.themcbrothers.sharedadvancements;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.themcbrothers.sharedadvancements.platform.Services;

import java.util.List;

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
     * @param broadcast     Broadcast to all players instead of only the team
     */
    public static void progressAdvancement(Player player, String criterionName, AdvancementHolder advancement, boolean broadcast) {
        if (skipEvent) {
            return;
        }

        MinecraftServer server = player.getServer();
        Team team = player.getTeam();

        if (server != null && (broadcast || team != null)) {
            skipEvent = true;
            server.getPlayerList().getPlayers().stream()
                    .filter(serverPlayer -> broadcast || team.getPlayers().contains(serverPlayer.getScoreboardName()))
                    .forEach(serverPlayer -> serverPlayer.getAdvancements().award(advancement, criterionName));
            skipEvent = false;
        }
    }

    /**
     * Syncs advancement progress when a player joins
     *
     * @param player    Server Player
     * @param broadcast Broadcast to all players instead of only the team
     */
    public static void playerJoin(ServerPlayer player, boolean broadcast) {
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