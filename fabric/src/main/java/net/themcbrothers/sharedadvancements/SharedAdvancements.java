package net.themcbrothers.sharedadvancements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SharedAdvancements implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> CommonClass.playerJoin(handler.getPlayer()));
    }
}
