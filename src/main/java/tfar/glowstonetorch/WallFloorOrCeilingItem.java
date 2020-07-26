package tfar.glowstonetorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class WallFloorOrCeilingItem extends WallOrFloorItem {
	public WallFloorOrCeilingItem(Block floorBlock, Block wallBlockIn, Properties propertiesIn) {
		super(floorBlock, wallBlockIn, propertiesIn);
	}

	@Nullable
	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		if (context.getFace() == Direction.DOWN) {
			BlockState blockstate = this.getBlock().getStateForPlacement(context);
			IWorldReader iworldreader = context.getWorld();
			BlockPos blockpos = context.getPos();

			if (blockstate != null && blockstate.isValidPosition(iworldreader, blockpos)) {
				return iworldreader.func_226663_a_(blockstate, blockpos, ISelectionContext.dummy()) ? blockstate : null;
			}
		}
		return super.getStateForPlacement(context);
	}
}
