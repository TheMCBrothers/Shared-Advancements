package net.themcbrothers.sharedadvancements.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.entity.player.Player;

/**
 * This event is fired when a player is granted an advancement criterion.
 */
public interface CriterionCallback {
    ToggleableEvent<CriterionCallback> EVENT = ToggleableEvent.create(CriterionCallback.class,
            (listeners) -> (player, advancement, criterionKey) -> {
                for (CriterionCallback listener : listeners) {
                    listener.awardCriterion(player, advancement, criterionKey);
                }
            }
    );

    void awardCriterion(Player player, AdvancementHolder advancement, String criterionName);
}
