package redmennl.mods.efm.core.handler;

import com.pahimar.ee3.core.helper.nbt.GeneralNBTHelper;
import com.pahimar.ee3.emc.EmcValue;
import com.pahimar.ee3.item.OreStack;
import com.pahimar.ee3.lib.InterModComms;
import com.pahimar.ee3.lib.Reference;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class IMCHandler
{
    public static void init()
    {
        if (Loader.isModLoaded("IC2"))
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("oreUranium"),
                                    new EmcValue(4096)));
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("ingotCopper"),
                                    new EmcValue(85)));
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("ingotTin"),
                                    new EmcValue(256)));
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("ingotSilver"),
                                    new EmcValue(512)));
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("ingotLead"),
                                    new EmcValue(1024)));
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("itemRubber"),
                                    new EmcValue(32)));
        }
        if (Loader.isModLoaded("AppliedEnergistics"))
        {
            // FMLInterModComms.sendMessage(Reference.MOD_ID,
            // InterModComms.ASSIGN_EMC_VALUE_PRE,
            // GeneralNBTHelper.encodeEmcValueMapping(new
            // OreStack("crystalCertusQuartz"), new EmcValue(2048)));
            // FMLInterModComms.sendMessage(Reference.MOD_ID,
            // InterModComms.ASSIGN_EMC_VALUE_PRE,
            // GeneralNBTHelper.encodeEmcValueMapping(new
            // OreStack("dustCertusQuartz"), new EmcValue(512)));
        }
        if (Loader.isModLoaded("TinkersConstruct"))
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack(
                                    "ingotAluminium"), new EmcValue(256)));
        }
        if (Loader.isModLoaded("Forestry"))
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("gemApatite"),
                                    new EmcValue(144)));
        }
        if (Loader.isModLoaded("Railcraft"))
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID,
                    InterModComms.ASSIGN_EMC_VALUE_PRE, GeneralNBTHelper
                            .encodeEmcValueMapping(new OreStack("ingotSteel"),
                                    new EmcValue(288)));
        }
    }
}
