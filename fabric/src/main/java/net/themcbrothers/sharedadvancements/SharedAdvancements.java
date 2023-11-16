package net.themcbrothers.sharedadvancements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.themcbrothers.sharedadvancements.event.CriterionCallback;

public class SharedAdvancements implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        // Register callback listeners
        CriterionCallback.EVENT.register(this::onCriterion);
        ServerPlayConnectionEvents.JOIN.register(this::onPlayerLogin);
    }

    private void onCriterion(Player player, AdvancementHolder advancement, String criterionName) {
        CommonClass.progressAdvancement(player, criterionName, advancement, true);
    }

    private void onPlayerLogin(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        CommonClass.playerJoin(handler.getPlayer(), true);
    }
}
