package redmennl.mods.efm.item;

import java.util.List;

import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.emc.IPortableEmcHolder;
import redmennl.mods.efm.lib.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class ItemPortableEmcCapacitor extends ItemEFM implements
        IPortableEmcHolder
{
    private int maxStoredEmc = 8192;
    
    public ItemPortableEmcCapacitor(int id)
    {
        super(id);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.PORTABLE_EMC_CAPACITOR_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer ep, List list,
            boolean par4)
    {
        super.addInformation(stack, ep, list, par4);
        if (stack.hasTagCompound())
        {
            for (EmcType type : EmcType.values())
            {
                if (storesType(type))
                {
                    list.add(type.toString() + ": " + getStoredEmc(type, stack));
                }
            }
        }
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4,
            boolean par5)
    {
        super.onUpdate(stack, world, entity, par4, par5);
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
    }
    
    @Override
    public boolean addEmc(EmcValue emcValue, ItemStack stack)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (stack.getTagCompound().getFloat("EMC" + i)
                    + emcValue.components[i] > maxStoredEmc)
            {
                return false;
            }
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            stack.getTagCompound().setFloat(
                    "EMC" + i,
                    stack.getTagCompound().getFloat("EMC" + i)
                            + emcValue.components[i]);
        }
        return true;
    }
    
    @Override
    public float neededEmc(EmcType type, ItemStack stack)
    {
        return maxStoredEmc
                - stack.getTagCompound().getFloat("EMC" + type.ordinal());
    }
    
    @Override
    public boolean useEmc(EmcValue emcValue, ItemStack stack)
    {
        if (!hasEmc(emcValue, stack))
        {
            return false;
        }
        for (int i = 0; i < EmcType.values().length; i++)
        {
            stack.getTagCompound().setFloat(
                    "EMC" + i,
                    stack.getTagCompound().getFloat("EMC" + i)
                            - emcValue.components[i]);
        }
        return true;
    }
    
    @Override
    public boolean hasEmc(EmcValue emcValue, ItemStack stack)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (stack.getTagCompound().getFloat("EMC" + i)
                    - emcValue.components[i] < 0)
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean storesType(EmcType emcType)
    {
        return true;
    }
    
    @Override
    public EmcValue getEmc(ItemStack stack)
    {
        float[] components = new float[EmcType.values().length];
        for (int i = 0; i < EmcType.values().length; i++)
        {
            components[i] = stack.getTagCompound().getFloat("EMC" + i);
        }
        return new EmcValue(components);
    }
    
    @Override
    public void setEmc(EmcValue emcValue, ItemStack stack)
    {
        for (int i = 0; i < EmcType.values().length; i++)
        {
            stack.getTagCompound().setFloat("EMC" + i, emcValue.components[i]);
        }
    }
    
    @Override
    public float getMaxStoredEmc()
    {
        return maxStoredEmc;
    }
    
    @Override
    public float getStoredEmc(EmcType type, ItemStack stack)
    {
        return stack.getTagCompound().getFloat("EMC" + type.ordinal());
    }
    
}
