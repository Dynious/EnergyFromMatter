package dynious.mods.efm.emc.values;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import buildcraft.BuildCraftEnergy;

import com.pahimar.ee3.emc.EmcComponent;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;
import com.pahimar.ee3.item.OreStack;
import com.pahimar.ee3.item.WrappedStack;

import dynious.mods.efm.EnergyFromMatter;

public class EmcValuesModsDefault
{
    private static EmcValuesModsDefault emcDefaultValues = null;
    private Map<WrappedStack, EmcValue> valueMap;

    private EmcValuesModsDefault() {

        valueMap = new HashMap<WrappedStack, EmcValue>();
    }
    
    private static void lazyInit() {

        if (emcDefaultValues == null) {
            emcDefaultValues = new EmcValuesModsDefault();
            emcDefaultValues.init();
        }
    }
    
    private void init() {
        /* Industrial Craft */
        valueMap.put(new WrappedStack(new OreStack("oreUranium")), new EmcValue(4096));
        valueMap.put(new WrappedStack(new OreStack("oreCopper")), new EmcValue(85));
        valueMap.put(new WrappedStack(new OreStack("oreTin")), new EmcValue(256));
        valueMap.put(new WrappedStack(new OreStack("oreSilver")), new EmcValue(512));
        valueMap.put(new WrappedStack(new OreStack("oreLead")), new EmcValue(1024));
        
        valueMap.put(new WrappedStack(new OreStack("ingotCopper")), new EmcValue(85));
        valueMap.put(new WrappedStack(new OreStack("ingotTin")), new EmcValue(256));
        valueMap.put(new WrappedStack(new OreStack("ingotSilver")), new EmcValue(512));
        valueMap.put(new WrappedStack(new OreStack("ingotLead")), new EmcValue(1024));
        valueMap.put(new WrappedStack(new OreStack("ingotCopper")), new EmcValue(85));
        valueMap.put(new WrappedStack(new OreStack("ingotBronze")), new EmcValue(255));
        
        valueMap.put(new WrappedStack(new OreStack("itemRubber")), new EmcValue(32));
        
        /* Applied Energistics */
        valueMap.put(new WrappedStack(new OreStack("oreCertusQuartz")), new EmcValue(2560));
        
        valueMap.put(new WrappedStack(new OreStack("crystalCertusQuartz")), new EmcValue(2048));
        valueMap.put(new WrappedStack(new OreStack("dustCertusQuartz")), new EmcValue(512));
        
        /* Tinkers Construct */
        valueMap.put(new WrappedStack(new OreStack("oreAluminium")), new EmcValue(256));
        
        valueMap.put(new WrappedStack(new OreStack("ingotAluminium")), new EmcValue(256));
        
        /* Forestry */
        valueMap.put(new WrappedStack(new OreStack("oreApatite")), new EmcValue(144));
        
        valueMap.put(new WrappedStack(new OreStack("gemApatite")), new EmcValue(144, Arrays.asList(new EmcComponent(EmcType.CORPOREAL, 1), new EmcComponent(EmcType.ESSENTIA, 1))));
        
        /* Railcraft */
        valueMap.put(new WrappedStack(new OreStack("ingotSteel")), new EmcValue(288));
        
        /* BuildCraft */
        if (EnergyFromMatter.hasBC)
        {
            valueMap.put(new WrappedStack(BuildCraftEnergy.blockOil), new EmcValue(480, Arrays.asList(new EmcComponent(EmcType.KINETIC, 3), new EmcComponent(EmcType.CORPOREAL, 2), new EmcComponent(EmcType.AMORPHOUS, 1))));
            valueMap.put(new WrappedStack(BuildCraftEnergy.blockFuel), new EmcValue(4800, Arrays.asList(new EmcComponent(EmcType.KINETIC, 3), new EmcComponent(EmcType.CORPOREAL, 2), new EmcComponent(EmcType.AMORPHOUS, 1))));
        }
    }
    
    public static Map<WrappedStack, EmcValue> getDefaultValueMap() {

        lazyInit();
        return emcDefaultValues.valueMap;
    }
}
