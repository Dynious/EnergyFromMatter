package dynious.mods.efm.core.handler;

import java.util.List;
import java.util.Map.Entry;

import com.pahimar.ee3.api.RecipeMapping;
import com.pahimar.ee3.api.StackValueMapping;
import com.pahimar.ee3.emc.EmcValue;
import com.pahimar.ee3.imc.InterModCommsOperations;
import com.pahimar.ee3.item.WrappedStack;
import com.pahimar.ee3.lib.Reference;

import cpw.mods.fml.common.event.FMLInterModComms;
import dynious.mods.efm.emc.recipes.RecipesBC;
import dynious.mods.efm.emc.recipes.RecipesIC2;
import dynious.mods.efm.emc.values.EmcValuesModsDefault;


public class IMCHandler
{
    public static void init()
    {
        for (Entry<WrappedStack, EmcValue> entry : EmcValuesModsDefault.getDefaultValueMap().entrySet())
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID, InterModCommsOperations.EMC_ASSIGN_VALUE_PRE, new StackValueMapping(entry.getKey(), entry.getValue()).toJson());
        }
        
        for (Entry<WrappedStack, List<WrappedStack>> entry : RecipesIC2.getIc2Recipes().entries())
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID, InterModCommsOperations.RECIPE_ADD, new RecipeMapping(entry.getKey(), entry.getValue()).toJson());
        }
        for (Entry<WrappedStack, List<WrappedStack>> entry : RecipesBC.getBcRecipes().entries())
        {
            FMLInterModComms.sendMessage(Reference.MOD_ID, InterModCommsOperations.RECIPE_ADD, new RecipeMapping(entry.getKey(), entry.getValue()).toJson());
        }
    }
}
