package com.example.emiae2fix.mixin;

import appeng.client.gui.me.crafting.CraftConfirmScreen;
import com.example.emiae2fix.Ae2FavoriteHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CraftConfirmScreen.class, remap = false)
public abstract class CraftConfirmScreenMixin {
    @Inject(method = "m_7933_", at = @At("HEAD"), cancellable = true, require = 1)
    private void favoriteMissingStack(int keyCode, int scanCode, int modifiers,
            CallbackInfoReturnable<Boolean> cir) {
        if (Ae2FavoriteHandler.tryFavorite(keyCode, scanCode)) {
            cir.setReturnValue(true);
        } else if (Ae2FavoriteHandler.tryLookup(keyCode, scanCode)) {
            cir.setReturnValue(true);
        }
    }
}
