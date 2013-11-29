package dynious.mods.efm.block;

import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCreatorInterface extends BlockEmc
{
    
    public BlockCreatorInterface(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.CREATOR_INTERFACE_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        //return new TileCreatorInterface();
        return null;
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
    
}
