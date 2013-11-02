package redmennl.mods.efm.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.efm.inventory.ContainerCropRipener;
import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TileCropRipener;

public class GuiCropRipener extends GuiContainer
{
    TileCropRipener tile;
    
    public GuiCropRipener(InventoryPlayer i, TileCropRipener tile)
    {
        super(new ContainerCropRipener(i, tile));
        this.tile = tile;
        xSize = 176;
        ySize = 166;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Resources.GUI_MATTER_DISTILLERY);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
