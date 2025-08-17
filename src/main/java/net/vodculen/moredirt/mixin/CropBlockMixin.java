package net.vodculen.moredirt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.vodculen.moredirt.blocks.ModBlocks;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin {
	@Inject(method = "canPlantOnTop", at = @At(value = "HEAD"), cancellable = true)
	private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.FERTILE_MULCH) || floor.isOf(ModBlocks.INFERTILE_MULCH)) {
			callbackInfo.setReturnValue(true);
			callbackInfo.cancel();
		}
	}
}
