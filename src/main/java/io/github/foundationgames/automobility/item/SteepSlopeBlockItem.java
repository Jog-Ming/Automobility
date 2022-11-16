package io.github.foundationgames.automobility.item;

import io.github.foundationgames.automobility.block.SteepSlopeBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class SteepSlopeBlockItem extends BlockItem {
    private final Block base;

    public SteepSlopeBlockItem(Block base, Block block, Properties settings) {
        super(block, settings);
        this.base = base;
    }

    @Nullable
    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        var hitPos = context.getClickLocation();
        var pos = new BlockPos(Math.floor(hitPos.x), Math.floor(hitPos.y), Math.floor(hitPos.z));
        var world = context.getLevel();
        if (world.getBlockState(pos).getBlock() instanceof SteepSlopeBlock) {
            var facing = world.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING);
            var playerFacing = context.getHorizontalDirection();
            var vOffset = playerFacing == facing ? Direction.DOWN : playerFacing == facing.getOpposite() ? Direction.UP : null;
            var place = pos.relative(playerFacing);
            if (vOffset != null) place = place.relative(vOffset);
            var pState = world.getBlockState(place);
            if (pState.isAir() || pState.is(Blocks.WATER)) {
                return new SlopePlacementContext(BlockPlaceContext.at(context, place, Direction.UP), facing);
            }
        }
        return super.updatePlacementContext(context);
    }

    @Override
    public String getDescriptionId() {
        return base != null ? "block.automobility.steep_slope" : super.getDescriptionId();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        if (base != null) tooltip.add(Component.translatable(base.getDescriptionId()).withStyle(ChatFormatting.BLUE));
    }
}
