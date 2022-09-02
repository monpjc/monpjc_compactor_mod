package com.monpjc.compactor_mod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<CompactorScreenHandler> COMPACTOR_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){
        COMPACTOR_SCREEN_HANDLER = new ScreenHandlerType<>(CompactorScreenHandler::new);
    }
}
