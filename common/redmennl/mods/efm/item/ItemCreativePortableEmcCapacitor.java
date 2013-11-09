package redmennl.mods.efm.item;

import net.minecraft.item.ItemStack;
import redmennl.mods.efm.emc.IPortableEmcHolder;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class ItemCreativePortableEmcCapacitor extends ItemEFM implements IPortableEmcHolder
{

    public ItemCreativePortableEmcCapacitor(int id)
    {
        super(id);
    }

    @Override
    public boolean storesType(EmcType emcType)
    {
        return true;
    }

    @Override
    public boolean addEmc(EmcValue emcValue, ItemStack stack)
    {
        return false;
    }

    @Override
    public float neededEmc(EmcType emcType, ItemStack stack)
    {
        return 0;
    }

    @Override
    public boolean useEmc(EmcValue emcValue, ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean hasEmc(EmcValue emcValue, ItemStack stack)
    {
        return true;
    }

    @Override
    public EmcValue getEmc(ItemStack stack)
    {
        float[] components = new float[EmcType.values().length];
        for (int i = 0; i < components.length; i++)
        {
            components[i] = Float.MAX_VALUE;
        }
        return new EmcValue(components);
    }

    @Override
    public void setEmc(EmcValue emcValue, ItemStack stack)
    {}

    @Override
    public float getMaxStoredEmc()
    {
        return Float.MAX_VALUE;
    }

    @Override
    public float getStoredEmc(EmcType type, ItemStack stack)
    {
        return Float.MAX_VALUE;
    }
    
}
