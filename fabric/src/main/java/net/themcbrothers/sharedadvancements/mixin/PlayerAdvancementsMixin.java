package net.themcbrothers.sharedadvancements.mixin;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.themcbrothers.sharedadvancements.event.CriterionCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {
    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onAward(AdvancementHolder advancement, String criterionKey, CallbackInfoReturnable<Boolean> callbackInfo, boolean success)
    {
        if (success)
        {
            CriterionCallback.EVENT.invoker().awardCriterion(player, advancement, criterionKey);
        }
    }
}
