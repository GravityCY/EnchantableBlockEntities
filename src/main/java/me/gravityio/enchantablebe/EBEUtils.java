package me.gravityio.enchantablebe;

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
public class EBEUtils {

    public static boolean doGlint = true;

    public static <T extends BlockEntity> VertexConsumer getVertexConsumer(SpriteIdentifier sprite, VertexConsumerProvider provider, Function<Identifier, RenderLayer> layerFactory, T blockEntity) {
        if (doGlint && shouldGlint(blockEntity))
            return sprite.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(provider, sprite.getRenderLayer(layerFactory), false, true));
        return sprite.getVertexConsumer(provider, layerFactory);
    }

    public static <T extends BlockEntity> boolean shouldGlint(T blockEntity) {
        return blockEntity instanceof IEnchantableBE enchantable && !enchantable.getEnchantments().isEmpty();
    }
}
