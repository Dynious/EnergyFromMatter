package redmennl.mods.efm.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
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
        TileEmc tile = (TileEmc) world.getBlockTileEntity(x, y, z);
        if (tile != null)
        {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() == ModItems.linker
                    && stack.hasTagCompound())
            {
                int tileX = stack.getTagCompound().getInteger("tileX");
                int tileY = stack.getTagCompound().getInteger("tileY");
                int tileZ = stack.getTagCompound().getInteger("tileZ");
                if (!(tileX == tile.emcCapX && tileY == tile.emcCapY && tileZ == tile.emcCapZ))
                {
                    if (getDistanceSq(x, y, z, tileX, tileY, tileZ) <= tile.maxRange
                            * tile.maxRange)
                    {
                        tile.setEmcCapacitor(tileX, tileY, tileZ);
                        if (!world.isRemote)
                            player.sendChatToPlayer(new ChatMessageComponent()
                                    .addText("This "
                                            + tile.getBlockType()
                                                    .getLocalizedName()
                                            + " is now linked with the EMC Capacitor at: "
                                            + tileX + ", " + tileY + ", "
                                            + tileZ));
                    } else if (!world.isRemote)
                    {
                        player.sendChatToPlayer(new ChatMessageComponent()
                                .addText("The EMC Capcitor is too far away!"));
                    }
                    return true;
                }
            }
            if (openGui(player, world, x, y, z))
            {
                return true;
            }
        }
        return false;
    }
    
    public double getDistanceSq(int x, int y, int z, int x2, int y2, int z2)
    {
        double d3 = x - x2;
        double d4 = y - y2;
        double d5 = z - z2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
    
    public abstract boolean openGui(EntityPlayer player, World world, int x,
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
    protected Icon[] icons;
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[2];
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
        switch (par2)
        {
            case 0:
                return icons[0];
            default:
                return icons[1];
        }
    }
    
}
