package me.gravityio.enchantablebe.random;

import me.gravityio.enchantablebe.mixins.interfaces.IEnchantableBE;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.function.Function;


/**
 * Utilities to make block entities Glint
 */
public class EBUtils {

    public static <T extends BlockEntity> VertexConsumer getVertexConsumer(SpriteIdentifier identifier, VertexConsumerProvider provider, Function<Identifier, RenderLayer> layerFactory, T blockEntity) {
        return shouldGlint(blockEntity) ? identifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(provider, identifier.getRenderLayer(layerFactory), false, true)) : identifier.getVertexConsumer(provider, layerFactory);
    }

    public static <T extends BlockEntity> boolean shouldGlint(T blockEntity) {
        if (!(blockEntity instanceof IEnchantableBE enchantableBE)) return false;
        return !enchantableBE.getEnchantments().isEmpty();
    }
}
