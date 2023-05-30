package me.gravityio.enchantablebe.mixins.interfaces;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public interface IEnchantableBE {

    NbtList getEnchantments();
    void setEnchantments(NbtList enchantments);

    default NbtCompound toClientNbt() {
        // Only send the enchantments to the client to reduce packet size
        NbtCompound nbt = new NbtCompound();
        nbt.put("Enchantments", getEnchantments());
        return nbt;
    }

}

