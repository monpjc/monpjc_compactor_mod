package com.monpjc.compactor_mod.block;

import com.monpjc.compactor_mod.CompactorMod;
import com.monpjc.compactor_mod.block.custom.CompactorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block COMPACTOR_BLOCK = registerBlock("compactor_block",
            new CompactorBlock(FabricBlockSettings.of(Material.WOOD).strength(2f).requiresTool()),
            ItemGroup.MISC);


    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(CompactorMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        return Registry.register(Registry.ITEM, new Identifier(CompactorMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks(){
        CompactorMod.LOGGER.info("Registering Mod Blocks");
    }
}
