package redmennl.mods.efm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileEnergyCreator;

public class BlockEnergyCreator extends BlockEmc
{
    public BlockEnergyCreator(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.ENERGY_CREATOR_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEnergyCreator();
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
            ((TileEnergyCreator) world.getBlockTileEntity(x, y, z))
                    .scanNeighbor(direction);
        }
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        ((TileEnergyCreator) world.getBlockTileEntity(x, y, z)).scanNeighbors();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[3];
        for (int i = 0; i < icons.length; i++)
        {
            icons[i] = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                    + this.getUnlocalizedName().substring(9) + i);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        if (par1 == 0 || par1 == 1)
        {
            return icons[2];
        } else
        {
            switch (par2)
            {
                case 0:
                    return icons[0];
                default:
                    return icons[1];
            }
        }
    }
}
