package com.monpjc.compactor_mod;

import com.monpjc.compactor_mod.block.ModBlocks;
import com.monpjc.compactor_mod.block.entity.ModBlockEntities;
import com.monpjc.compactor_mod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompactorMod implements ModInitializer {
	public static final String MOD_ID = "compactor_mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModEntityBlocks();
		ModScreenHandlers.registerAllScreenHandlers();


		LOGGER.info("Monpjc's Compactor Mod Has Finished Loading");
	}
}
