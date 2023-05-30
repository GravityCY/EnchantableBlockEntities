package me.gravityio.enchantablebe.mixins.mixin.client;


import me.gravityio.enchantablebe.EBEUtils;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public class ShulkerBoxEntityRendererMixin {
    @Redirect(  method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
                at = @At(value = "INVOKE", target = "net/minecraft/client/util/SpriteIdentifier.getVertexConsumer (Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer getVertexConsumerWithGlint(SpriteIdentifier identifier, VertexConsumerProvider provider, Function<Identifier, RenderLayer> layerFactory, ShulkerBoxBlockEntity blockEntity) {
        return EBEUtils.getVertexConsumer(identifier, provider, layerFactory, blockEntity);
    }

}
