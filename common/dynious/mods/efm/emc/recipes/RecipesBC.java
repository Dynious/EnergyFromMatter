package dynious.mods.efm.emc.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import buildcraft.api.recipes.AssemblyRecipe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pahimar.ee3.item.WrappedStack;

import dynious.mods.efm.EnergyFromMatter;

public class RecipesBC
{
private static Multimap<WrappedStack, List<WrappedStack>> BcRecipes = null;
    
    public static Multimap<WrappedStack, List<WrappedStack>> getBcRecipes()
    {
        
        if (BcRecipes == null)
        {
            init();
        }
        
        return BcRecipes;
    }
    
    private static void init()
    {
        BcRecipes = HashMultimap.create();
        
        if (EnergyFromMatter.hasBC)
        {
            for (AssemblyRecipe recipe : AssemblyRecipe.assemblyRecipes)
            {
                List<WrappedStack> input = new ArrayList<WrappedStack>();
                for (ItemStack stack : recipe.input)
                {
                    input.add(new WrappedStack(stack));
                }
                if (!input.isEmpty() && recipe.output != null)
                {
                    BcRecipes.put(new WrappedStack(recipe.output), input);
                }
            }
        }
    }
}
