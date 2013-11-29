package dynious.mods.efm.block;

import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileMatterCondenser;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMatterCondenser extends BlockEmc
{

    public BlockMatterCondenser(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.MATTER_CONDENSER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileMatterCondenser();
    }

    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        return false;
    }
    
}
