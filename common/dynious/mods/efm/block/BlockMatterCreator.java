package dynious.mods.efm.block;

import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.lib.GuiIds;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileMatterCreator;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMatterCreator extends BlockEmc
{
    public BlockMatterCreator(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.MATTER_CREATOR_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterCreator();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(EnergyFromMatter.instance, GuiIds.MATTER_CREATOR, world,
                x, y, z);
        return true;
    }
}
