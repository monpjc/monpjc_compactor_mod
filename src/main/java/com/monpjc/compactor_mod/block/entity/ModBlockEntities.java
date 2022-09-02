package com.monpjc.compactor_mod.block.entity;

import com.monpjc.compactor_mod.CompactorMod;
import com.monpjc.compactor_mod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<CompactorBlockEntity> COMPACTOR_BLOCK_ENTITY;

    public static void registerModEntityBlocks(){
        COMPACTOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CompactorMod.MOD_ID, "compactor_block"),
                FabricBlockEntityTypeBuilder.create(CompactorBlockEntity::new,
                        ModBlocks.COMPACTOR_BLOCK).build(null));

        CompactorMod.LOGGER.info("Registering Entity Blocks");
    }
}
