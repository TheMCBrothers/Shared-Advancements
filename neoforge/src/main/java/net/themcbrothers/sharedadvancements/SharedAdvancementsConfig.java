package net.themcbrothers.sharedadvancements;

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
        config.comment("Common Configuration for Shared Advancements").push("options");

        enabled = config
                .comment("Enables the entire mod")
                .define("enabled", true);
        broadcastAdvancements = config
                .comment("Set to true to share advancements with all players")
                .define("broadcast_advancements", false);

        config.pop();
    }
}
