package net.vodculen.moredirt.blocks.custom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransformableFromBlockNearbyBlock extends Block {
	private List<Block> nearbyBlocks = new ArrayList<>();
	private final Block transformInto;

	public TransformableFromBlockNearbyBlock(Settings settings, List<Block> nearbyBlocks, Block transformInto) {
		super(settings);

		this.transformInto = transformInto;
		this.nearbyBlocks = nearbyBlocks;
	}
	

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		if (!world.isClient && isBlockNearby(world, pos)) {
			world.setBlockState(pos, transformInto.getDefaultState());
		}

		super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
	}

	private boolean isBlockNearby(World world, BlockPos pos) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					BlockPos checkPos = pos.add(dx, dy, dz);

					if (nearbyBlocks != null) {
						for (Block nearbyBlock : nearbyBlocks) {
							if (world.getBlockState(checkPos).isOf(nearbyBlock)) {
								return true;
							}
						}
					}
					
				}
			}
		}
		return false;
	}
}
