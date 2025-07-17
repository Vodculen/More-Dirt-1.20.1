package net.vodculen.moredirt.blocks.custom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CharredBlock extends TransformableFromBlockNearbyBlock {
	public CharredBlock(Settings settings, List<Block> nearbyBlocks, Block transformInto) {
		super(settings, nearbyBlocks, transformInto);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient && entity instanceof LivingEntity living) {
			living.damage(world.getDamageSources().hotFloor(), 0.5F);
		}

		super.onSteppedOn(world, pos, state, entity);
	}
}
