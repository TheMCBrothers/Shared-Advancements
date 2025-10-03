package net.themcbrothers.sharedadvancements;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;
import net.themcbrothers.sharedadvancements.platform.Services;

import java.lang.reflect.Method;
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

        MinecraftServer server = getServer(player);
        Team team = player.getTeam();

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

    private static Method serverGetterMethod;

    static {
        // Try Entity first
        serverGetterMethod = findServerGetter(Entity.class);

        if (serverGetterMethod == null) {
            // Fallback to ServerLevel
            serverGetterMethod = findServerGetter(ServerLevel.class);
        }

        if (serverGetterMethod == null) {
            throw new RuntimeException("Cannot find server getter method");
        }
    }

    private static Method findServerGetter(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getParameterCount() == 0 && method.getReturnType() == MinecraftServer.class) {
                Constants.LOG.info("Server getter method: {}#{}", clazz.getName(), method.getName());
                return method;
            }
        }

        return null;
    }

    private static MinecraftServer getServer(ServerPlayer player) {
        try {
            Object target = serverGetterMethod.getDeclaringClass() == Entity.class ? player : player.level();
            return (MinecraftServer) serverGetterMethod.invoke(target);
        } catch (Exception e) {
            Constants.LOG.error("Could not get Minecraft Server!", e);
            throw new RuntimeException(e);
        }
    }
}
