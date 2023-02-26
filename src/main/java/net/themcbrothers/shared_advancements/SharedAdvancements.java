package net.themcbrothers.shared_advancements;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@SuppressWarnings("deprecation")
@Mod(SharedAdvancements.MOD_ID)
public class SharedAdvancements {
    public static final String MOD_ID = "shared_advancements";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SharedAdvancements() {
        LOGGER.info("Loading Shared Advancements");
        MinecraftForge.EVENT_BUS.addListener(this::progressAdvancements);
    }

    private void progressAdvancements(final AdvancementEvent.AdvancementProgressEvent event) {
        Player player = event.getEntity();
        MinecraftServer server = player.getServer();
        Team team = player.getTeam();

        if (server != null && team != null) {
            server.getPlayerList().getPlayers().stream()
                    .filter(serverPlayer -> team.getPlayers().contains(serverPlayer.getScoreboardName()))
                    .forEach(serverPlayer -> serverPlayer.getAdvancements().award(event.getAdvancement(), event.getCriterionName()));
        }
    }
}
