package dynious.mods.efm.block.item;

import java.util.List;

import dynious.mods.efm.block.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPortableHouse extends ItemBlock
{
    
    public ItemPortableHouse(int i)
    {
        super(i);
        setHasSubtypes(true);
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack i, EntityPlayer p, List l, boolean b)
    {
        
        if (i.hasTagCompound())
        {
            if (i.getTagCompound().getString("name") != "")
            {
                l.add(i.getTagCompound().getString("name"));
            }
        }
        
    }
    
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack i)
    {
        return ModBlocks.portableHouse.getUnlocalizedName() + i.getItemDamage();
    }
}