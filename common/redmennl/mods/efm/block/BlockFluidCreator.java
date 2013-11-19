package redmennl.mods.efm.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.client.gui.inventory.GuiFluidCreator;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.lib.Strings;
import redmennl.mods.efm.tileentity.TileFluidCreator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidCreator extends BlockEmc
{
    
    public BlockFluidCreator(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.FLUID_CREATOR_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileFluidCreator();
    }
    
    @Override
    public boolean openGui(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        {
            if (tile != null && tile instanceof TileFluidCreator)
            {
                FMLCommonHandler.instance().showGuiScreen(
                        new GuiFluidCreator((TileFluidCreator) tile));
                return true;
            }
        }
        return false;
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
