package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TilePowerLink;

public class BlockPowerLink extends BlockEmc
{
    public BlockPowerLink(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.POWER_LINK_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TilePowerLink();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
    
    @Override
    public void onNeighborTileChange(World world, int x, int y, int z,
            int tileX, int tileY, int tileZ)
    {
        super.onNeighborTileChange(world, x, y, z, tileX, tileY, tileZ);
        ForgeDirection direction = null;
        if (tileX > x && tileY == y && tileZ == z)
        {
            direction = ForgeDirection.EAST;
        } else if (tileX < x && tileY == y && tileZ == z)
        {
            direction = ForgeDirection.WEST;
        } else if (tileY > y && tileX == x && tileZ == z)
        {
            direction = ForgeDirection.UP;
        } else if (tileY < y && tileX == x && tileZ == z)
        {
            direction = ForgeDirection.DOWN;
        } else if (tileZ > z && tileX == x && tileY == y)
        {
            direction = ForgeDirection.SOUTH;
        } else if (tileZ < z && tileX == x && tileY == y)
        {
            direction = ForgeDirection.NORTH;
        }
        if (direction != null)
        {
            ((TilePowerLink) world.getBlockTileEntity(x, y, z))
                    .scanNeighbor(direction);
        }
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        ((TilePowerLink) world.getBlockTileEntity(x, y, z)).scanNeighbors();
    }
}
