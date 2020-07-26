package tfar.glowstonetorch;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class GlowstoneWallTorchBlock extends WallTorchBlock implements IWaterLoggable {
	public GlowstoneWallTorchBlock(Properties p_i241193_1_, IParticleData p_i241193_2_) {
		super(p_i241193_1_, p_i241193_2_);
		setDefaultState(getDefaultState().with(ChestBlock.WATERLOGGED,false));
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		//no
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState stateForPlacement = super.getStateForPlacement(context);
		if (stateForPlacement != null) {
			World world = context.getWorld();
			BlockPos pos = context.getPos();

			boolean water = world.getFluidState(pos).getFluid() == Fluids.WATER;
			stateForPlacement = stateForPlacement.with(ChestBlock.WATERLOGGED,water);
		}
		return stateForPlacement;
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(ChestBlock.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}


	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(ChestBlock.WATERLOGGED);
	}
}
