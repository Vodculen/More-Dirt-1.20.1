package net.vodculen.moredirt.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.vodculen.moredirt.MoreDirt;
import net.vodculen.moredirt.blocks.custom.CharredBlock;
import net.vodculen.moredirt.blocks.custom.FertileBlock;
import net.vodculen.moredirt.blocks.custom.FrozenBlock;
import net.vodculen.moredirt.blocks.custom.MuskegBlock;

public class ModBlocks {
	private static List<Block> TransformablesFromNearbyBlock = new ArrayList<>();

	// Modded Dirts 
	public static final Block MUSKEG = registerBlock("muskeg", new MuskegBlock(AbstractBlock.Settings.create().strength(2F, 3.5F).dynamicBounds().solidBlock(Blocks::never).sounds(BlockSoundGroup.MUD)));
	public static final Block ARID_DIRT = registerBlock("arid_dirt", new FallingBlock(AbstractBlock.Settings.create().strength(0.5F, 2F).solid().sounds(BlockSoundGroup.SAND)));
	public static final Block MULCH = registerBlock("mulch", new FertileBlock(AbstractBlock.Settings.create().strength(0.5F, 2F).solid().sounds(BlockSoundGroup.SAND), Blocks.DIRT));
	public static final Block FROZEN_DIRT = registerBlock("frozen_dirt", new FrozenBlock(Settings.copy(Blocks.STONE).sounds(BlockSoundGroup.SAND), addFrozenDirtTransformablesFromNearbyBlock(), Blocks.DIRT));

	// Modded Grass
	public static final Block CHARRED_GRASS_BLOCK = registerBlock("charred_grass_block", new CharredBlock(AbstractBlock.Settings.create().strength(0.5F, 2F).solid().sounds(BlockSoundGroup.GRAVEL), addCharredGrassBlockTransformablesFromNearbyBlock(), Blocks.GRASS_BLOCK));
	public static final Block FROZEN_GRASS_BLOCK = registerBlock("frozen_grass_block", new FrozenBlock(Settings.copy(Blocks.STONE).sounds(BlockSoundGroup.SAND), addFrozenDirtTransformablesFromNearbyBlock(), Blocks.GRASS_BLOCK));


	// Below are helper classes that make defining Blocks easier as well as making them accessible to the entry class
	private static Block registerBlock(String name, Block block) {
		registerModBlockItem(name, block);
		return Registry.register(Registries.BLOCK, Identifier.of(MoreDirt.MOD_ID, name), block);
	}

	private static void registerModBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, Identifier.of(MoreDirt.MOD_ID, name), new BlockItem(block, new Item.Settings()));
	}

	public static void registerModBlocks() {
		MoreDirt.LOGGER.info("Registering Mod Block for " + MoreDirt.MOD_ID);
	}

	private static List<Block> addCharredGrassBlockTransformablesFromNearbyBlock() {
		TransformablesFromNearbyBlock.add(Blocks.WATER);
		TransformablesFromNearbyBlock.add(Blocks.SNOW_BLOCK);
		TransformablesFromNearbyBlock.add(Blocks.SNOW);
		TransformablesFromNearbyBlock.add(Blocks.POWDER_SNOW);
		TransformablesFromNearbyBlock.add(Blocks.ICE);
		TransformablesFromNearbyBlock.add(Blocks.PACKED_ICE);
		TransformablesFromNearbyBlock.add(Blocks.BLUE_ICE);

		List<Block> blocks = TransformablesFromNearbyBlock;

		return blocks;
	}

	private static List<Block> addFrozenDirtTransformablesFromNearbyBlock() {
		TransformablesFromNearbyBlock.add(Blocks.LAVA);
		TransformablesFromNearbyBlock.add(Blocks.FIRE);
		TransformablesFromNearbyBlock.add(Blocks.MAGMA_BLOCK);
		TransformablesFromNearbyBlock.add(Blocks.SHROOMLIGHT);
		TransformablesFromNearbyBlock.add(Blocks.GLOWSTONE);
		TransformablesFromNearbyBlock.add(Blocks.TORCH);
		TransformablesFromNearbyBlock.add(Blocks.LANTERN);
		TransformablesFromNearbyBlock.add(Blocks.REDSTONE_LAMP);

		List<Block> blocks = TransformablesFromNearbyBlock;

		return blocks;
	}
}
