package com.example.emiae2fix.mixin;

import com.example.emiae2fix.Ae2FavoriteHandler;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Provides a fallback for AE2's custom, slotless crafting-plan entries. */
@Mixin(value = EmiScreenManager.class, remap = false)
public abstract class EmiScreenManagerMixin {
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true, remap = false)
    private static void favoriteAe2Stack(int keyCode, int scanCode, int modifiers,
            CallbackInfoReturnable<Boolean> cir) {
        if (Ae2FavoriteHandler.tryFavorite(keyCode, scanCode)) {
            cir.setReturnValue(true);
        }
    }
}
