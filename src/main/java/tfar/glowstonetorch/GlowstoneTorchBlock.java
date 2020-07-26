package tfar.glowstonetorch;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.ChestBlock.WATERLOGGED;

public class GlowstoneTorchBlock extends TorchBlock implements IWaterLoggable {

	public static final BooleanProperty UP = BooleanProperty.create("up");

	protected static final VoxelShape SHAPE2 = Block.makeCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public GlowstoneTorchBlock(Properties p_i241189_1_, IParticleData p_i241189_2_) {
		super(p_i241189_1_, p_i241189_2_);
		setDefaultState(getDefaultState().with(UP,false).with(ChestBlock.WATERLOGGED,false));
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
			return super.isValidPosition(state, worldIn, pos) || hasEnoughSolidSide(worldIn, pos.up(), Direction.DOWN);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(UP) ? SHAPE2 : super.getShape(state, worldIn, pos, context);
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		//no
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();

		boolean water = world.getFluidState(pos).getFluid() == Fluids.WATER;
		if (context.getFace() == Direction.DOWN)return getDefaultState().with(UP,true).with(WATERLOGGED, water);
		if (context.getFace() == Direction.UP)return getDefaultState().with(UP,false).with(WATERLOGGED, water);
		return null;
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(UP, WATERLOGGED);
	}
}
