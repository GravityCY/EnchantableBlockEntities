package me.gravityio.enchantablebe.mixins.mixin;

import me.gravityio.enchantablebe.mixins.interfaces.IEnchantableBE;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes all {@link BlockEntity BlockEntities} implement IEnchantableBlock
 */
@Mixin(BlockEntity.class)
public class BlockEntityMixin implements IEnchantableBE {

    private NbtList enchantments = new NbtList();

    @Override
    public NbtList getEnchantments() {
        return enchantments;
    }
    @Override
    public void setEnchantments(NbtList enchantments) {
        this.enchantments = enchantments;
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.getEnchantments().isEmpty()) return;
        nbt.put("Enchantments", this.getEnchantments());
    }
    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.enchantments = nbt.getList("Enchantments", NbtElement.COMPOUND_TYPE);
    }
    @Inject(method = "toInitialChunkDataNbt", at = @At("RETURN"))
    public void toInitialChunkDataNbt(CallbackInfoReturnable<NbtCompound> cir) {
        cir.getReturnValue().copyFrom(this.toClientNbt());
    }
    @Inject(method = "toUpdatePacket", at = @At("RETURN"), cancellable = true)
    public void toUpdatePacket(CallbackInfoReturnable<@Nullable Packet<ClientPlayPacketListener>> cir) {
        cir.setReturnValue(BlockEntityUpdateS2CPacket.create((BlockEntity) (Object) this));
    }
}
