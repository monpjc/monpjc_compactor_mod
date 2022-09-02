package com.monpjc.compactor_mod;

import com.monpjc.compactor_mod.screen.CompactorScreen;
import com.monpjc.compactor_mod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class CompactorModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.COMPACTOR_SCREEN_HANDLER, CompactorScreen::new);
    }
}
