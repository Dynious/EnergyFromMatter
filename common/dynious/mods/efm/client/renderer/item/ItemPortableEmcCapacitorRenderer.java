package dynious.mods.efm.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.emc.EmcType;

import cpw.mods.fml.client.FMLClientHandler;
import dynious.mods.efm.client.model.ModelPortableEmcCapacitor;
import dynious.mods.efm.item.ItemPortableEmcCapacitor;
import dynious.mods.efm.lib.EmcRGBValues;
import dynious.mods.efm.lib.RGBValue;
import dynious.mods.efm.lib.Resources;

public class ItemPortableEmcCapacitorRenderer implements IItemRenderer
{
    public ItemPortableEmcCapacitorRenderer()
    {
        model = new ModelPortableEmcCapacitor();
    }
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case ENTITY:
            {
                renderPortableEmcCapacitor(0.0F, 0.0F, 0.0F, 1.0F, item);
                return;
            }
            case EQUIPPED:
            {
                renderPortableEmcCapacitor(0.5F, 0.0F, 0.5F, 1.0F, item);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderPortableEmcCapacitor(0.5F, 0.2F, 0.3F, 1.0F, item);
                return;
            }
            case INVENTORY:
            {
                renderPortableEmcCapacitor(0.5F, -1.1F, 0.5F, 1.5F, item);
                return;
            }
            default:
                return;
        }
    }
    
    private void renderPortableEmcCapacitor(float x, float y, float z, float scale, ItemStack i)
    {
        if (!(i.getItem() instanceof ItemPortableEmcCapacitor))
        {
            return;
        }
        ItemPortableEmcCapacitor item = (ItemPortableEmcCapacitor)i.getItem();
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glTranslatef(x, y - 2.0F, z);
        GL11.glScalef(scale*2, scale*2, scale*2);
        
        FMLClientHandler.instance().getClient().renderEngine
        .bindTexture(Resources.MODEL_PORTABLE_EMC_CAPACITOR);
        
        if (i.hasTagCompound() && item.getEmc(i) != null)
        {
            GL11.glPushMatrix();
            
            RGBValue rgb = EmcRGBValues.getRGB(item.getEmc(i));
            GL11.glColor4f((float)rgb.colorR/255, (float)rgb.colorG/255, (float)rgb.colorB/255, item.getEmc(i).getValue()/(item.getMaxStoredEmc()*EmcType.values().length));
            //GL11.glColor4f(0, 1F, 1F, 1F);
            model.renderMiddle();
            
            GL11.glPopMatrix();
        }
        
        // Render border
        GL11.glPushMatrix();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.11F);
        
        model.renderBorder();
        
        GL11.glPopMatrix();
        
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }
    
    private ModelPortableEmcCapacitor model;
}
