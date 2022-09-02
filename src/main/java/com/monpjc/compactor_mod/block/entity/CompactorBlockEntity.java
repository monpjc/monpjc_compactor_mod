package com.monpjc.compactor_mod.block.entity;

import com.monpjc.compactor_mod.block.ModBlocks;
import com.monpjc.compactor_mod.item.inventory.ImplementedInventory;
import com.monpjc.compactor_mod.screen.CompactorScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;

public class CompactorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public CompactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPACTOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return CompactorBlockEntity.this.progress;
                    case 1: return CompactorBlockEntity.this.maxProgress;
                    case 2: return CompactorBlockEntity.this.fuelTime;
                    case 3: return CompactorBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: CompactorBlockEntity.this.progress = value;
                    case 1: CompactorBlockEntity.this.maxProgress = value;
                    case 2: CompactorBlockEntity.this.fuelTime = value;
                    case 3: CompactorBlockEntity.this.maxFuelTime = value;
                    default: break;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("compactor_progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("compactor_progress");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Comapctor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CompactorScreenHandler(syncId, inv,this, this.propertyDelegate);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CompactorBlockEntity entity) {
        if(world.isClient()){
            return;
        }

        if(hasRecipe(entity)){
            entity.progress++;
            markDirty(world, blockPos, blockState);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(CompactorBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i <entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        if(hasRecipe(entity)){
            entity.removeStack(1,9);
            entity.setStack(2, new ItemStack(Blocks.IRON_BLOCK, entity.getStack(2).getCount() + 1));
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(CompactorBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i <entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasInputItem = entity.getStack(1).getItem() == Items.IRON_INGOT && entity.getStack(1).getCount() >= 9;

        return hasInputItem && canInsertAmountIntoOutputSlot(inventory) && canInsertIntoOutputSlot(inventory, Items.IRON_BLOCK);
    }

    private static boolean canInsertIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}
