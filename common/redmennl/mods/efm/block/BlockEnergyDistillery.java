package redmennl.mods.efm.block;

import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileEnergyDistillery;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyDistillery extends BlockEmc
{
    
    public BlockEnergyDistillery(int par1)
    {
        super(par1, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.ENERGY_DISTILLERY_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEnergyDistillery();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
}
