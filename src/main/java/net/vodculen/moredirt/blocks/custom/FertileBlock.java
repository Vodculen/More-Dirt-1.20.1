package net.vodculen.moredirt.blocks.custom;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;

public class FertileBlock extends Block {
	public static final IntProperty MOISTURE;
	protected static final VoxelShape SHAPE;
	public static final int MAX_MOISTURE = 7;
	public static int fertile = 3;
	public static Block infertileBlock;

	public FertileBlock(Settings settings, Block infertileBlock) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(MOISTURE, 0));
		FertileBlock.infertileBlock = infertileBlock;
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
			world.scheduleBlockTick(pos, this, 1);
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.up());
		return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof PistonExtensionBlock;
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Blocks.DIRT.getDefaultState() : super.getPlacementState(ctx);
	}

	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int moisture = state.get(MOISTURE);

		if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
			if (moisture > 0) {
				world.setBlockState(pos, state.with(MOISTURE, moisture - 1), 2);
			}
		} else if (moisture < 7) {
			world.setBlockState(pos, state.with(MOISTURE, 7), 2);
		}

		if (hasCrop(world, pos)) {
			fertile--;
		}

		if (fertile <= 0) {
			turnInfertile(null, state, world, pos);
		}
	}

	public static void turnInfertile(@Nullable Entity entity, BlockState state, World world, BlockPos pos) {
		BlockState blockState = pushEntitiesUpBeforeBlockChange(state, FertileBlock.infertileBlock.getDefaultState(), world, pos);

		world.setBlockState(pos, blockState);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Emitter.of(entity, blockState));
	}

	private static boolean hasCrop(BlockView world, BlockPos pos) {
		return world.getBlockState(pos.up()).isIn(BlockTags.MAINTAINS_FARMLAND);
	}

	private static boolean isWaterNearby(WorldView world, BlockPos pos) {
		Iterator<BlockPos> area = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();
		BlockPos blockPos;
		
		do {
			if (!area.hasNext()) {
				return false;
			}

			blockPos = area.next();
		} while(!world.getFluidState(blockPos).isIn(FluidTags.WATER));

		return true;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{MOISTURE});
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return true;
	}

	static {
		MOISTURE = Properties.MOISTURE;
		SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
	}
}
