package net.vodculen.moredirt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.vodculen.moredirt.blocks.ModBlocks;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

	@Inject(method = "canPlantOnTop", at = @At("HEAD"))
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfo callbackInfo) {
		return floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.MULCH);
	}
}
