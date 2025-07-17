package net.vodculen.moredirt.blocks.custom;

import java.util.List;

import net.minecraft.block.Block;

public class FrozenBlock extends TransformableFromBlockNearbyBlock {
	public FrozenBlock(Settings settings, List<Block> nearbyBlocks, Block transformInto) {
		super(settings, nearbyBlocks, transformInto);
	}
}
