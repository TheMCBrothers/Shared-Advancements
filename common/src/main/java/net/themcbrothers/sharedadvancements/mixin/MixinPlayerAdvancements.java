package net.themcbrothers.sharedadvancements.mixin;

import net.themcbrothers.sharedadvancements.CommonClass;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerAdvancements.class)
public class MixinPlayerAdvancements {
    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void award(Advancement advancement, String criterionKey, CallbackInfoReturnable<Boolean> cir, boolean success) {
        if (success) {
            CommonClass.progressAdvancement(player, criterionKey, advancement);
        }
    }
}
