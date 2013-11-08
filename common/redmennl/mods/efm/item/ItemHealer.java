package redmennl.mods.efm.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.lib.Strings;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class ItemHealer extends ItemProtableEmcCapacitor
{
    public static int timePerHealing = 10;
    
    public ItemHealer(int id)
    {
        super(id);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.HEALER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setMaxStackSize(1);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer ep, List list,
            boolean par4)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().getBoolean("isEnabled"))
            {
                list.add(EnumChatFormatting.GREEN + "Enabled");
            }
        }
        super.addInformation(stack, ep, list, par4);
    }
    
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer ep)
    {
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        super.onCreated(stack, world, ep);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world,
            EntityPlayer ep)
    {
        if (!stack.getTagCompound().getBoolean("isEnabled"))
        {
            if (getStoredEmc(EmcType.ESSENTIA, stack) > 0)
            {
                stack.getTagCompound().setBoolean("isEnabled", true);
            }
        } else
        {
            stack.getTagCompound().setBoolean("isEnabled", false);
        }
        return super.onItemRightClick(stack, world, ep);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4,
            boolean par5)
    {
        super.onUpdate(stack, world, entity, par4, par5);
        
        if (stack.getTagCompound().getBoolean("isEnabled"))
        {
            if (stack.getTagCompound().getInteger("workTime") < timePerHealing)
            {
                stack.getTagCompound().setInteger("workTime",
                        stack.getTagCompound().getInteger("workTime") + 1);
            } else
            {
                if (entity != null && entity instanceof EntityPlayer)
                {
                    EntityPlayer ep = (EntityPlayer) entity;
                    if (ep.getHealth() < ep.getMaxHealth())
                    {
                        if (useEmc(new EmcValue(10F, EmcType.ESSENTIA), stack))
                        {
                            ep.heal(1);
                        } else
                        {
                            stack.getTagCompound().setBoolean("isEnabled",
                                    false);
                        }
                    }
                }
                stack.getTagCompound().setInteger("workTime", 0);
            }
        }
    }
    
    @Override
    public boolean storesType(EmcType emcType)
    {
        return emcType == EmcType.ESSENTIA;
    }
    
}
