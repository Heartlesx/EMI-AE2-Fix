package com.example.emiae2fix.mixin;

import java.lang.reflect.Constructor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.emi.emi.screen.EmiScreenBase;
import dev.emi.emi.mixin.accessor.HandledScreenAccessor;
import net.minecraft.client.gui.screens.Screen;
import dev.emi.emi.api.widget.Bounds;

@Mixin(value = EmiScreenBase.class, remap = false)
public abstract class EmiScreenBaseMixin {
    @Inject(method = "of", at = @At("RETURN"), cancellable = true, remap = false)
    private static void recognizeSlotlessAe2Screen(Screen screen,
            CallbackInfoReturnable<EmiScreenBase> cir) {
        EmiScreenBase result = cir.getReturnValue();
        if (screen == null || result == null || !result.isEmpty() || !isAe2Screen(screen)) {
            return;
        }
        Bounds bounds = ae2Bounds(screen);
        if (!bounds.empty()) {
            cir.setReturnValue(construct(screen, bounds));
        }
    }

    private static boolean isAe2Screen(Screen screen) {
        String name = screen.getClass().getName();
        return name.startsWith("appeng.client.gui.") &&
                (name.contains("AEBaseScreen") || name.contains("AESubScreen") ||
                 name.contains("me.crafting."));
    }

    private static Bounds ae2Bounds(Screen screen) {
        try {
            HandledScreenAccessor accessor = (HandledScreenAccessor) screen;
            return new Bounds(accessor.getX(), accessor.getY(),
                    accessor.getBackgroundWidth(), accessor.getBackgroundHeight());
        } catch (Throwable ignored) {
            return Bounds.EMPTY;
        }
    }

    private static EmiScreenBase construct(Screen screen, Bounds bounds) {
        try {
            Constructor<EmiScreenBase> constructor = EmiScreenBase.class
                    .getDeclaredConstructor(Screen.class, Bounds.class);
            constructor.setAccessible(true);
            return constructor.newInstance(screen, bounds);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to create EMI screen base", e);
        }
    }
}
