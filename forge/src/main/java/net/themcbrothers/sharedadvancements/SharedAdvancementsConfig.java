package net.themcbrothers.sharedadvancements;

import com.electronwill.nightconfig.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SharedAdvancementsConfig {
    public static final ForgeConfigSpec SPEC;
    public static final SharedAdvancementsConfig INSTANCE;

    static {
        Config.setInsertionOrderPreserved(true);

        Pair<SharedAdvancementsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SharedAdvancementsConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public final ForgeConfigSpec.BooleanValue enabled;
    public final ForgeConfigSpec.BooleanValue broadcastAdvancements;

    private SharedAdvancementsConfig(ForgeConfigSpec.Builder config) {
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
