package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.tileentity.TileMatterDistillery;

public class BlockMatterDistillery extends BlockEmc
{
    public BlockMatterDistillery(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName("matterDistillery");
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterDistillery();
    }
    
    @Override
    public void openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(EnergyFromMatter.instance, GuiIds.MATTER_DISTILLERY,
                world, x, y, z);
    }
}
