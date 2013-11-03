package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.tileentity.TileCondenser;

public class BlockCondenser extends BlockEmc
{
    public BlockCondenser(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName("condenser");
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileCondenser();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(EnergyFromMatter.instance, GuiIds.CONDENSER, world, x,
                y, z);
        return true;
    }
}
