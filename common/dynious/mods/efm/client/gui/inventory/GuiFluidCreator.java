package dynious.mods.efm.client.gui.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.pahimar.ee3.emc.EmcRegistry;

import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.tileentity.TileFluidCreator;

public class GuiFluidCreator extends GuiScreen
{
    private TileFluidCreator tile;
    public GuiButton lock;
    
    public GuiFluidCreator(TileFluidCreator tile)
    {
        super();
        this.tile = tile;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void initGui()
    {
        buttonList.add(lock = new GuiButton(0, width / 2 + 16, height / 2 - 10,
                50, 20, "Locked"));
        lock.displayString = tile.lockFluid ? "Locked" : "Unlocked";
        
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 0)
        {
            tile.lockFluid = !tile.lockFluid;
            lock.displayString = tile.lockFluid ? "Locked" : "Unlocked";
        }
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawContainerBackground();
        
        super.drawScreen(i, j, f);
        
        if (FluidRegistry.getFluid(tile.fluidID) != null && !(FluidRegistry.getFluid(tile.fluidID).getBlockID() <= 0))
        {
            Block block = Block.blocksList[FluidRegistry.getFluid(tile.fluidID)
                    .getBlockID()];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(
                    TextureMap.locationBlocksTexture);
            Icon icon = block.getBlockTextureFromSide(0);
            this.drawTexturedModelRectFromIcon(width / 2 - 48, height / 2 - 16,
                    icon, 32, 32);
            
            fontRenderer.drawString(
                    block.getLocalizedName(),
                    width
                            / 2
                            - 32
                            - fontRenderer.getStringWidth(block
                                    .getLocalizedName()) / 2, height / 2 + 20,
                    0xFFFFFF);
            if (!EmcRegistry.hasEmcValue(block))
            {
                fontRenderer.drawString("This Fluid has no EMC Value!",
                        width / 2 - 70, height / 2 - 30, 0xFFFFFF);
            }
        }
        
        if (lock.func_82252_a())
        {
            List<String> list = new ArrayList<String>();
            list.add("If locked this Fluid Creator will only");
            list.add("output the selected Fluid even if");
            list.add("another block/entity asks for another one.");
            drawHoveringText(list, i, j, fontRenderer);
        }
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par1 >= width / 2 - 48 && par1 <= width / 2 - 16
                && par2 >= height / 2 - 16 && par2 <= height / 2 + 16)
        {
            if (par3 == 0)
            {
                if (FluidRegistry.getFluid(tile.fluidID + 1) != null)
                {
                    tile.fluidID++;
                }
            }
            if (par3 == 1)
            {
                if (FluidRegistry.getFluid(tile.fluidID - 1) != null)
                {
                    tile.fluidID--;
                }
            }
        }
        super.mouseClicked(par1, par2, par3);
    }
    
    protected void drawContainerBackground()
    {
        int xSize = 176;
        int ySize = 89;
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Resources.GUI_PORTABLEHOUSE);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    @SuppressWarnings("rawtypes")
    protected void drawHoveringText(List par1List, int par2, int par3,
            FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            
            while (iterator.hasNext())
            {
                String s = (String) iterator.next();
                int l = font.getStringWidth(s);
                
                if (l > k)
                {
                    k = l;
                }
            }
            
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            
            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            
            if (i1 + k > this.width)
            {
                i1 -= 28 + k;
            }
            
            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }
            
            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4,
                    l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1,
                    l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3,
                    l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3
                    - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1
                    + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2,
                    i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3,
                    j2, j2);
            
            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String) par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);
                
                if (k2 == 0)
                {
                    j1 += 2;
                }
                
                j1 += 10;
            }
            
            this.zLevel = 0.0F;
            // GL11.glEnable(GL11.GL_LIGHTING);
            // GL11.glEnable(GL11.GL_DEPTH_TEST);
            // RenderHelper.enableStandardItemLighting();
            // GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
