package redmennl.mods.efm.client.renderer.tileentity;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import redmennl.mods.efm.client.model.ModelPortableHouse;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;

import com.google.common.primitives.SignedBytes;

public class TilePortableHouseRenderer extends TileEntitySpecialRenderer
{
    private RenderItem itemRenderer;
    private long lastSystemTime;
    private boolean up;
    
    private static float[][] shifts = { { 0.3F, 0.7F, 0.3F },
            { 0.7F, 0.7F, 0.3F }, { 0.3F, 0.7F, 0.7F }, { 0.7F, 0.7F, 0.7F },
            { 0.3F, 0.3F, 0.3F }, { 0.7F, 0.3F, 0.3F }, { 0.3F, 0.3F, 0.7F },
            { 0.7F, 0.3F, 0.7F }, { 0.5F, 0.5F, 0.5F } };
    
    public TilePortableHouseRenderer()
    {
        model = new ModelPortableHouse();
        itemRenderer = new RenderItem()
        {
            @Override
            public byte getMiniBlockCount(ItemStack stack)
            {
                return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32,
                        15) + 1);
            }
            
            @Override
            public byte getMiniItemCount(ItemStack stack)
            {
                return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32,
                        7) + 1);
            }
            
            @Override
            public boolean shouldBob()
            {
                return false;
            }
            
            @Override
            public boolean shouldSpreadItems()
            {
                return false;
            }
        };
        itemRenderer.setRenderManager(RenderManager.instance);
    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
            double d2, float f)
    {
        this.renderAModelAt(tileentity, d0, d1, d2, f);
    }
    
    public void renderAModelAt(TileEntity tileentity, double x, double y,
            double z, float f)
    {
        
        // GL11.glDisable(GL11.GL_LIGHTING);
        // GL11.glDisable(GL11.GL_CULL_FACE);
        
        // Render frame
        GL11.glPushMatrix();
        
        tileEntityRenderer.renderEngine
                .bindTexture(Resources.MODEL_PORTABLEHOUSE);
        
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        
        model.renderFrame();
        
        GL11.glPopMatrix();
        
        if (tileentity instanceof TilePortableHouseDeployer)
        {
            TilePortableHouseDeployer tile = (TilePortableHouseDeployer) tileentity;
            float shiftX;
            float shiftY;
            float shiftZ;
            int shift = 0;
            float blockScale = 0.70F;
            float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            
            GL11.glPushMatrix();
            
            glTranslatef((float) x, (float) y, (float) z);
            EntityItem customitem = new EntityItem(tileEntityRenderer.worldObj);
            customitem.hoverStart = 0f;
            for (int i = 0; i < tile.idArr.length; i++)
            {
                int id = tile.idArr[i];
                int meta = tile.metaArr[i];
                if (id == 0)
                {
                    continue;
                }
                if (shift >= shifts.length)
                {
                    break;
                }
                ItemStack item = new ItemStack(id, 1, meta);
                shiftX = shifts[shift][0];
                shiftY = shifts[shift][1];
                shiftZ = shifts[shift][2];
                shift++;
                glPushMatrix();
                glTranslatef(shiftX, shiftY, shiftZ);
                glRotatef(timeD, 0.0F, 1.0F, 0.0F);
                glScalef(blockScale, blockScale, blockScale);
                customitem.setEntityItemStack(item);
                itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
                glPopMatrix();
            }
            glPopMatrix();
        }
        
        // Render middle
        float pulse = 0;
        if (System.currentTimeMillis() % 2000 < lastSystemTime)
        {
            up = !up;
        }
        if (up)
        {
            pulse = 0.1F + (float) (System.currentTimeMillis() % 2000) / 8000;
        } else
        {
            pulse = 0.35F - (float) (System.currentTimeMillis() % 2000) / 8000;
        }
        lastSystemTime = System.currentTimeMillis() % 2000;
        
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        tileEntityRenderer.renderEngine
                .bindTexture(Resources.MODEL_PORTABLEHOUSE);
        GL11.glColor4f(1F, 1F, 1F, pulse);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        model.renderMiddle();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        
        // GL11.glEnable(GL11.GL_CULL_FACE);
        // GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    private ModelPortableHouse model;
}
