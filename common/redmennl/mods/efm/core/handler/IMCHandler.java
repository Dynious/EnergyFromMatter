package redmennl.mods.efm.core.handler;

import com.pahimar.ee3.api.StackValueMapping;
import com.pahimar.ee3.emc.EmcValue;
import com.pahimar.ee3.item.CustomWrappedStack;
import com.pahimar.ee3.item.OreStack;
import com.pahimar.ee3.lib.InterModComms;
import com.pahimar.ee3.lib.Reference;

import cpw.mods.fml.common.event.FMLInterModComms;


public class IMCHandler
{
    public static void init()
    {
        FMLInterModComms.sendMessage(Reference.MOD_ID, InterModComms.EMC_ASSIGN_VALUE_PRE, new StackValueMapping(new CustomWrappedStack(new OreStack("ingotCopper")), new EmcValue(85)).toJson());
    }
}
