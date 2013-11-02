package redmennl.mods.efm.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.efm.item.ModItems;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TileEmc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockEmc extends BlockContainer
{
    public BlockEmc(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
            return true;
        else
        {
            TileEmc tile = (TileEmc) world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() == ModItems.linker
                        && stack.hasTagCompound())
                {
                    if (!(stack.getTagCompound().getInteger("tileX") == tile.emcCapX
                            && stack.getTagCompound().getInteger("tileY") == tile.emcCapY && stack
                            .getTagCompound().getInteger("tileZ") == tile.emcCapZ))
                    {
                        tile.setEmcCapacitor(
                                stack.getTagCompound().getInteger("tileX"),
                                stack.getTagCompound().getInteger("tileY"),
                                stack.getTagCompound().getInteger("tileZ"));
                        return true;
                    }
                }
                openGui(player, world, x, y, z);
            }
            return true;
        }
    }
    
    public abstract void openGui(EntityPlayer player, World world, int x,
            int y, int z);
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            List par3List)
    {
        for (int j = 0; j < 2; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }
    
    @SideOnly(Side.CLIENT)
    private Icon[] icons;
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[2];
        for (int i = 0; i < icons.length; i++)
        {
            icons[i] = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                    + this.getUnlocalizedName().substring(5) + i);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
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
