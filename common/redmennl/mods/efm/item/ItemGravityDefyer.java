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

public class ItemGravityDefyer extends ItemPortableEmcCapacitor
{
    private EntityPlayer ep = null;
    
    public ItemGravityDefyer(int id)
    {
        super(id);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.GRAVITY_DEFYER_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer ep)
    {
        stack.getTagCompound().setBoolean("isEnabled", false);
        ep.capabilities.allowFlying = false;
        ep.capabilities.isFlying = false;
        return super.onDroppedByPlayer(stack, ep);
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
            if (getStoredEmc(EmcType.AMORPHOUS, stack) > 0)
            {
                stack.getTagCompound().setBoolean("isEnabled", true);
                ep.capabilities.allowFlying = true;
            }
        } else
        {
            stack.getTagCompound().setBoolean("isEnabled", false);
            ep.capabilities.allowFlying = false;
            ep.capabilities.isFlying = false;
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
            if (entity != null && entity instanceof EntityPlayer)
            {
                EntityPlayer ep = (EntityPlayer) entity;
                this.ep = ep;
                if (ep.capabilities.isFlying)
                {
                    if (!useEmc(new EmcValue(0.001F, EmcType.AMORPHOUS), stack))
                    {
                        stack.getTagCompound().setBoolean("isEnabled", false);
                        ep.capabilities.allowFlying = false;
                        ep.capabilities.isFlying = false;
                    }
                }
            } else if (ep != null)
            {
                stack.getTagCompound().setBoolean("isEnabled", false);
                ep.capabilities.allowFlying = false;
                ep.capabilities.isFlying = false;
            }
        }
    }
    
    @Override
    public boolean storesType(EmcType emcType)
    {
        return emcType == EmcType.AMORPHOUS;
    }
    
}
