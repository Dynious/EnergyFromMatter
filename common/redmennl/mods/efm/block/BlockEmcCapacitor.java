package redmennl.mods.efm.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.item.ModItems;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TileEmcCapacitor;

public class BlockEmcCapacitor extends BlockContainer
{
    protected BlockEmcCapacitor(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName("emcCapacitor");
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEmcCapacitor();
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
            return true;
        else
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() == ModItems.linker)
                {
                    if (!stack.hasTagCompound())
                    {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    if (!(stack.getTagCompound().getInteger("tileX") == x
                            && stack.getTagCompound().getInteger("tileY") == y && stack
                            .getTagCompound().getInteger("tileZ") == z))
                    {
                        stack.getTagCompound().setInteger("tileX", x);
                        stack.getTagCompound().setInteger("tileY", y);
                        stack.getTagCompound().setInteger("tileZ", z);
                        return true;
                    }
                }
                player.openGui(EnergyFromMatter.instance, GuiIds.EMC_CAPACITOR,
                        world, x, y, z);
            }
            return true;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                + this.getUnlocalizedName().substring(5));
    }
}
