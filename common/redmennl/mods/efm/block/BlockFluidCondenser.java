package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.client.gui.inventory.GuiFluidCondenser;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileFluidCondenser;
import cpw.mods.fml.common.FMLCommonHandler;

public class BlockFluidCondenser extends BlockEmc
{
    
    public BlockFluidCondenser(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.FLUID_CONDENSER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileFluidCondenser();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        {
            if (tile != null && tile instanceof TileFluidCondenser)
            {
                FMLCommonHandler.instance().showGuiScreen(
                        new GuiFluidCondenser((TileFluidCondenser) tile));
                return true;
            }
        }
        return false;
    }
}
