package redmennl.mods.efm.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Resources;

public class ItemLinker extends ItemEFM
{
    public ItemLinker(int id)
    {
        super(id);
        this.setUnlocalizedName("linker");
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setMaxStackSize(1);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer,
            List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            int x = stack.getTagCompound().getInteger("tileX");
            int y = stack.getTagCompound().getInteger("tileY");
            int z = stack.getTagCompound().getInteger("tileZ");
            list.add("EMC Capacitor position: " + x + ", " + y + ", " + z);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        itemIcon = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                + this.getUnlocalizedName().substring(5));
    }
}
