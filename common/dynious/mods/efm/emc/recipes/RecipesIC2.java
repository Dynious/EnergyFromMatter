package dynious.mods.efm.emc.recipes;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;
import ic2.core.Ic2Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pahimar.ee3.item.WrappedStack;
import com.pahimar.ee3.item.OreStack;

import dynious.mods.efm.EnergyFromMatter;

public class RecipesIC2
{
    private static Multimap<WrappedStack, List<WrappedStack>> Ic2Recipes = null;
    
    public static Multimap<WrappedStack, List<WrappedStack>> getIc2Recipes()
    {
        
        if (Ic2Recipes == null)
        {
            init();
        }
        
        return Ic2Recipes;
    }
    
    private static void init()
    {
        
        Ic2Recipes = HashMultimap.create();
        
        if (EnergyFromMatter.hasIC2)
        {
            
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.macerator
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.compressor
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.extractor
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerCutting
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerExtruding
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.metalformerRolling
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.oreWashing
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            for (Entry<IRecipeInput, RecipeOutput> entry : Recipes.centrifuge
                    .getRecipes().entrySet())
            {
                addEntryToMap(entry);
            }
            
            for (Object recipeObject : CraftingManager.getInstance()
                    .getRecipeList())
            {
                
                if (recipeObject instanceof AdvRecipe
                        || recipeObject instanceof AdvShapelessRecipe)
                {
                    
                    IRecipe recipe = (IRecipe) recipeObject;
                    ItemStack recipeOutput = recipe.getRecipeOutput();
                    recipeOutput.setTagCompound(null);
                    
                    if (recipeOutput != null)
                    {
                        
                        ArrayList<WrappedStack> recipeInputs = getRecipeInputs(recipe);
                        if (recipeInputs != null && !recipeInputs.isEmpty())
                        {
                            Ic2Recipes.put(getWrappedStack(recipeOutput),
                                    recipeInputs);
                        }
                    }
                }
            }
        }
    }
    
    private static ArrayList<WrappedStack> getRecipeInputs(IRecipe recipe)
    {
        ArrayList<WrappedStack> recipeInputs = new ArrayList<WrappedStack>();
        
        if (recipe instanceof AdvRecipe)
        {
            AdvRecipe shapedRecipe = (AdvRecipe) recipe;
            
            for (int i = 0; i < shapedRecipe.input.length; i++)
            {
                
                if (shapedRecipe.input[i] instanceof ItemStack)
                {
                    
                    ItemStack itemStack = ((ItemStack) shapedRecipe.input[i])
                            .copy();
                    
                    if (itemStack.itemID == Ic2Items.cutter.itemID || itemStack.itemID == Ic2Items.ForgeHammer.itemID)
                    {
                        return null;
                    }
                    
                    if (itemStack.stackSize > 1)
                    {
                        itemStack.stackSize = 1;
                    }
                    itemStack.setTagCompound(null);
                    
                    recipeInputs.add(new WrappedStack(itemStack));
                }
                else if (shapedRecipe.input[i] instanceof String)
                {
                    OreStack stack = new OreStack((String) shapedRecipe.input[i]);
                    
                    if (stack.stackSize > 1)
                    {
                        stack.stackSize = 1;
                    }
                    
                    recipeInputs.add(new WrappedStack(stack));
                }
            }
        } else if (recipe instanceof AdvShapelessRecipe)
        {
            
            AdvShapelessRecipe shapelessRecipe = (AdvShapelessRecipe) recipe;
            
            for (Object object : shapelessRecipe.input)
            {
                
                if (object instanceof ItemStack)
                {
                    
                    ItemStack itemStack = ((ItemStack) object).copy();
                    
                    if (itemStack.itemID == Ic2Items.cutter.itemID || itemStack.itemID == Ic2Items.ForgeHammer.itemID)
                    {
                        return null;
                    }
                    
                    if (itemStack.stackSize > 1)
                    {
                        itemStack.stackSize = 1;
                    }
                    itemStack.setTagCompound(null);
                    
                    recipeInputs.add(new WrappedStack(itemStack));
                }
                else if (object instanceof String)
                {
                    OreStack stack = new OreStack((String) object);
                    
                    if (stack.stackSize > 1)
                    {
                        stack.stackSize = 1;
                    }
                    
                    recipeInputs.add(new WrappedStack(stack));
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
                WrappedStack stackOutput = getWrappedStack(recipeOutput);
                List<ItemStack> recipeStackInputs = entry.getKey().getInputs();
                for (ItemStack recipeInput : recipeStackInputs)
                {
                    if (recipeInput != null)
                    {
                        recipeInput.stackSize = entry.getKey().getAmount();
                        WrappedStack stackInput = new WrappedStack(recipeInput);
                        Ic2Recipes.put(stackOutput, Arrays.asList(stackInput));
                    }
                }
            }
        }
    }
    
    private static WrappedStack getWrappedStack(ItemStack stack)
    {
        WrappedStack wrappedStack;
        String outputOreName = OreDictionary.getOreName(OreDictionary
                .getOreID(stack));
        if (outputOreName != null && outputOreName != "Unknown")
        {
            wrappedStack = new WrappedStack(new OreStack(outputOreName,
                    stack.stackSize));
        } else
        {
            wrappedStack = new WrappedStack(stack);
        }
        return wrappedStack;
    }
}
