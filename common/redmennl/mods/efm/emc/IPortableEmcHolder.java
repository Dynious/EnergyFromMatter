package redmennl.mods.efm.emc;

import net.minecraft.item.ItemStack;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public interface IPortableEmcHolder
{
    public boolean storesType(EmcType emcType);
    
    public boolean addEmc(EmcValue emcValue, ItemStack stack);
    
    public float neededEmc(EmcType emcType, ItemStack stack);
    
    public boolean useEmc(EmcValue emcValue, ItemStack stack);
    
    public boolean hasEmc(EmcValue emcValue, ItemStack stack);
    
    public EmcValue getEmc(ItemStack stack);
    
    public void setEmc(EmcValue emcValue, ItemStack stack);
    
    public float getMaxStoredEmc();
    
    float getStoredEmc(EmcType type, ItemStack stack);
}
