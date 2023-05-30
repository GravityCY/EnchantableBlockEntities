package me.gravityio.enchantablebe.mixins.mixin;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow @Final public static String ENCHANTMENTS_KEY;
    @Shadow public abstract NbtCompound getOrCreateNbt();

    @Shadow public abstract Item getItem();

    @Inject(method = "addEnchantment", at = @At("TAIL"))
    private void addBlockEntityEnchants(Enchantment enchantment, int level, CallbackInfo ci) {
        if (!(this.getItem() instanceof BlockItem blockItem) || !(blockItem.getBlock() instanceof BlockWithEntity)) return;
        NbtCompound nbt = this.getOrCreateNbt();
        NbtList enchantments = nbt.getList(ENCHANTMENTS_KEY, NbtElement.COMPOUND_TYPE);
        boolean cbl = nbt.contains("BlockEntityTag");
        NbtCompound beTag = nbt.getCompound("BlockEntityTag");
        beTag.put(ENCHANTMENTS_KEY, enchantments);
        if (!cbl) nbt.put("BlockEntityTag", beTag);
    }

}
