package me.gravityio.enchantablebe.mixins.integration;

import atonkish.reinfshulker.block.entity.ReinforcedShulkerBoxBlockEntity;
import atonkish.reinfshulker.client.render.block.entity.ReinforcedShulkerBoxBlockEntityRenderer;
import me.gravityio.enchantablebe.random.EBUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Pseudo
@Mixin(ReinforcedShulkerBoxBlockEntityRenderer.class)
public class ReinfShulkerBoxBlockEntityRendererMixin {
    @Redirect(method = "render(Latonkish/reinfshulker/block/entity/ReinforcedShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer onRenderWithGlint(SpriteIdentifier identifier, VertexConsumerProvider provider, Function<Identifier, RenderLayer> layerFactory, ReinforcedShulkerBoxBlockEntity blockEntity) {
        return EBUtils.getVertexConsumer(identifier, provider, layerFactory, blockEntity);
    }
}
