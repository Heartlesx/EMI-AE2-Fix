package com.example.emiae2fix;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.StackWithBounds;
import appeng.integration.modules.emi.EmiStackHelper;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.EmiApi;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiFavorites;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class Ae2FavoriteHandler {
    private Ae2FavoriteHandler() {
    }

    public static boolean tryFavorite(int keyCode, int scanCode) {
        if (!EmiConfig.favorite.matchesKey(keyCode, scanCode)) {
            return false;
        }

        EmiStack stack = getHoveredStack();
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        EmiFavorites.addFavorite(stack);
        EmiScreenManager.repopulatePanels(SidebarType.FAVORITES);
        EmiAe2Fix.LOGGER.info("Favorited AE2 crafting-plan stack {}", stack);
        return true;
    }

    public static boolean tryLookup(int keyCode, int scanCode) {
        boolean recipes = EmiConfig.viewRecipes.matchesKey(keyCode, scanCode);
        boolean uses = EmiConfig.viewUses.matchesKey(keyCode, scanCode);
        if (!recipes && !uses) {
            return false;
        }

        EmiStack stack = getHoveredStack();
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        if (recipes) {
            EmiApi.displayRecipes(stack);
        } else {
            EmiApi.displayUses(stack);
        }
        EmiAe2Fix.LOGGER.info("Opened EMI {} for AE2 crafting-plan stack {}",
                recipes ? "recipes" : "uses", stack);
        return true;
    }

    private static EmiStack getHoveredStack() {
        Screen screen = Minecraft.getInstance().screen;
        if (!(screen instanceof AEBaseScreen<?> aeScreen)) {
            return null;
        }

        StackWithBounds hovered = aeScreen.getStackUnderMouse(
                EmiScreenManager.lastMouseX, EmiScreenManager.lastMouseY);
        if (hovered == null) {
            return null;
        }

        if (hovered.stack().what() instanceof AEItemKey itemKey) {
            return EmiStack.of(itemKey.toStack()).setAmount(1);
        }

        EmiStack stack = EmiStackHelper.toEmiStack(hovered.stack());
        return stack == null ? null : stack.copy().setAmount(1);
    }
}
