package net.themcbrothers.sharedadvancements.neoforge;

import com.electronwill.nightconfig.core.Config;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SharedAdvancementsConfig {
    public static final ModConfigSpec SPEC;
    public static final SharedAdvancementsConfig INSTANCE;

    static {
        Config.setInsertionOrderPreserved(true);

        Pair<SharedAdvancementsConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(SharedAdvancementsConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public final ModConfigSpec.BooleanValue enabled;
    public final ModConfigSpec.BooleanValue broadcastAdvancements;

    private SharedAdvancementsConfig(ModConfigSpec.Builder config) {
        enabled = config
                .comment("Enables the Shared Advancements mod")
                .define("enabled", true);
        broadcastAdvancements = config
                .comment("If set to false you only share advancements with your team instead of all players")
                .define("broadcast_advancements", true);
    }
}
