package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class EpsilonLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public EpsilonLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public EpsilonLampTileEntity()
    {
        super(ModTileEntities.EPSILON_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH, 4).offset(Direction.WEST, 4),
                    pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH, 4).offset(Direction.EAST, 4)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}