package redmennl.mods.efm.emc.recipes;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import redmennl.mods.efm.EnergyFromMatter;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pahimar.ee3.core.helper.LogHelper;
import com.pahimar.ee3.item.CustomWrappedStack;
import com.pahimar.ee3.item.OreStack;

public class RecipesIC2
{
    private static Multimap<CustomWrappedStack, List<CustomWrappedStack>> Ic2Recipes = null;

    public static Multimap<CustomWrappedStack, List<CustomWrappedStack>> getIc2Recipes() {

        if (Ic2Recipes == null) {
            init();
        }

        return Ic2Recipes;
    }

    private static void init() {

        Ic2Recipes = HashMultimap.create();
        
        if (EnergyFromMatter.hasIC2)
        {
            for (Object recipeObject : CraftingManager.getInstance().getRecipeList()) {

                if (recipeObject instanceof AdvRecipe || recipeObject instanceof AdvShapelessRecipe) {

                    IRecipe recipe = (IRecipe) recipeObject;
                    ItemStack recipeOutput = recipe.getRecipeOutput();

                    if (recipeOutput != null) {
                        
                        ArrayList<CustomWrappedStack> recipeInputs = getRecipeInputs(recipe);
                        Ic2Recipes.put(new CustomWrappedStack(recipeOutput), recipeInputs);
                    }
                }
            }
            
            for (Entry<CustomWrappedStack, List<CustomWrappedStack>> e : Ic2Recipes.entries())
            {
                LogHelper.debug(e.getKey().toString() + " ... " + e.getValue().toString());
            }
            
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.macerator.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.compressor.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.extractor.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerCutting.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerExtruding.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerRolling.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.oreWashing.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.centrifuge.getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
        }
    }
    
    private static ArrayList<CustomWrappedStack> getRecipeInputs(IRecipe recipe)
    {
        ArrayList<CustomWrappedStack> recipeInputs = new ArrayList<CustomWrappedStack>();

        if (recipe instanceof AdvRecipe) {

            AdvRecipe shapedRecipe = (AdvRecipe) recipe;

            for (int i = 0; i < shapedRecipe.input.length; i++) {

                if (shapedRecipe.input[i] instanceof ItemStack) {

                    ItemStack itemStack = ((ItemStack) shapedRecipe.input[i]).copy();
                    
                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }
                    
                    recipeInputs.add(getWrappedStack(itemStack));
                }
            }
        }
        else if (recipe instanceof AdvShapelessRecipe) {

            AdvShapelessRecipe shapelessRecipe = (AdvShapelessRecipe) recipe;

            for (Object object : shapelessRecipe.input) {

                if (object instanceof ItemStack) {

                    ItemStack itemStack = ((ItemStack) object).copy();

                    if (itemStack.stackSize > 1) {
                        itemStack.stackSize = 1;
                    }
                    
                    recipeInputs.add(getWrappedStack(itemStack));
                }
            }
        }
        
        return recipeInputs;
    }
    
    private static void addEntryToMap(Entry<IRecipeInput, RecipeOutput> entry)
    {
        List<ItemStack> recipeStackOutputs = entry.getValue().items;
        if (!(recipeStackOutputs.size() > 1))
        {
            ItemStack recipeOutput = recipeStackOutputs.get(0);
            if (recipeOutput != null)
            {
                CustomWrappedStack stackOutput = getWrappedStack(recipeOutput);
                List<ItemStack> recipeStackInputs = entry.getKey().getInputs();
                for (ItemStack recipeInput : recipeStackInputs)
                {
                    if (recipeInput != null)
                    {
                        recipeInput.stackSize = entry.getKey().getAmount();
                        CustomWrappedStack stackInput = getWrappedStack(recipeInput);
                        Ic2Recipes.put(stackOutput, Arrays.asList(stackInput));
                    }
                }
            }
        }
    }
    
    private static CustomWrappedStack getWrappedStack(ItemStack stack)
    {
        CustomWrappedStack wrappedStack;
        String outputOreName = OreDictionary.getOreName(OreDictionary.getOreID(stack));
        if (outputOreName != null && outputOreName != "Unknown")
        {
            wrappedStack = new CustomWrappedStack(new OreStack(outputOreName, stack.stackSize));
        }
        else
        {
            wrappedStack = new CustomWrappedStack(stack);
        }
        return wrappedStack;
    }
}
