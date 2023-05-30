package me.gravityio.enchantablebe.mixins.mixin;

import me.gravityio.enchantablebe.mixins.interfaces.IEnchantableBE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

/**
 * Modifies the drops of a {@link IEnchantableBE} to drop itself with its previous enchantments
 */
@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "getDroppedStacks", at = @At("RETURN"))
    private void getDroppedStacksWithEnchantments(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
        if (!(blockEntity instanceof IEnchantableBE iEnchantableBlock)) return;
        NbtList enchantments = iEnchantableBlock.getEnchantments();
        Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.fromNbt(enchantments);
        for (ItemStack item : cir.getReturnValue()) {
            for (Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {
                item.addEnchantment(entry.getKey(), entry.getValue());
            }
        }
    }
}
