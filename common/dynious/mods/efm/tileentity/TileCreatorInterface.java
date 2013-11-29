package dynious.mods.efm.tileentity;

import net.minecraft.item.ItemStack;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

import com.pahimar.ee3.emc.EmcRegistry;
import com.pahimar.ee3.emc.EmcValue;
import com.pahimar.ee3.item.WrappedStack;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;

@SuppressWarnings("rawtypes")
@Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2")
public class TileCreatorInterface extends TileEmc implements IMEInventory
{
    
    @Method(modid = "appliedenergistics2")
    @Override
    public IAEStack injectItems(IAEStack input, Actionable simulate)
    {
        return input;
    }
    
    @Method(modid = "appliedenergistics2")
    @Override
    public IAEStack extractItems(IAEStack request, Actionable simulate)
    {
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null && request instanceof IAEItemStack)
        {
            ItemStack stack = ((IAEItemStack) request).getItemStack();
            if (stack != null && EmcRegistry.hasEmcValue(stack))
            {
                EmcValue emcValue = EmcRegistry.getEmcValue(stack);
                if (emcCap.hasEmc(emcValue))
                {
                    if (simulate == Actionable.MODULATE)
                    {
                        emcCap.useEmc(emcValue);
                        return request;
                    } else
                    {
                        return request;
                    }
                }
            }
        }
        return AEApi.instance().storage().createItemStack(null);
    }
    
    @SuppressWarnings("unchecked")
    @Method(modid = "appliedenergistics2")
    @Override
    public IItemList getAvailableItems(IItemList out)
    {
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            for (WrappedStack wrappedStack : EmcRegistry
                    .getStacksInRange(new EmcValue(), emcCap.getEmc()))
            {
                if (wrappedStack.getWrappedStack() instanceof ItemStack)
                {
                    out.addCrafting(AEApi.instance().storage().createItemStack((ItemStack)wrappedStack.getWrappedStack()));
                }
            }
        }
        return out;
    }
    
    @Method(modid = "appliedenergistics2")
    @Override
    public StorageChannel getChannel()
    {
        return StorageChannel.ITEMS;
    }
    
}
