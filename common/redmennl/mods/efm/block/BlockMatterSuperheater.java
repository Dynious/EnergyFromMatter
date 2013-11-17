package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileMatterSuperheater;

public class BlockMatterSuperheater extends BlockEmc
{
    public BlockMatterSuperheater(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.MATTER_SUPERHEATER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterSuperheater();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
}
