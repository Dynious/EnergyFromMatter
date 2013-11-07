package redmennl.mods.efm.client.gui.inventory;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TileFluidCondenser;

import com.pahimar.ee3.emc.EmcRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFluidCondenser extends GuiScreen
{
    private TileFluidCondenser tile;
    
    public GuiFluidCondenser(TileFluidCondenser tile)
    {
        super();
        this.tile = tile;
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawContainerBackground();
        
        GL11.glDisable(GL11.GL_LIGHTING);
        this.mc.getTextureManager().bindTexture(
                TextureMap.locationBlocksTexture);
        if (!(FluidRegistry.getFluid(tile.fluidID).getBlockID() <= 0))
        {
            Block block = Block.blocksList[FluidRegistry.getFluid(tile.fluidID)
                    .getBlockID()];
            Icon icon = block.getBlockTextureFromSide(0);
            this.drawTexturedModelRectFromIcon(width / 2 - 16, height / 2 - 16,
                    icon, 32, 32);
            GL11.glEnable(GL11.GL_LIGHTING);
            if (!EmcRegistry.hasEmcValue(block))
            {
                fontRenderer.drawString("This Fluid has no EMC Value!",
                        width / 2 - 70, height / 2 - 30, 0xFFFFFF);
            }
        }
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par1 >= width / 2 - 16 && par1 <= width / 2 + 16
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
}
