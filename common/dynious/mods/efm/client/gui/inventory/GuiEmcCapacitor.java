package dynious.mods.efm.client.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.emc.EmcType;

import dynious.mods.efm.inventory.ContainerEmcCapacitor;
import dynious.mods.efm.lib.EmcRGBValues;
import dynious.mods.efm.lib.RGBValue;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.tileentity.TileEmcCapacitor;

public class GuiEmcCapacitor extends GuiContainer
{
    TileEmcCapacitor tile;
    
    public GuiEmcCapacitor(InventoryPlayer i, TileEmcCapacitor tile)
    {
        super(new ContainerEmcCapacitor(i, tile));
        this.tile = tile;
        xSize = 176;
        ySize = 166;
    }
    
    @Override
    public void drawScreen(int x, int y, float par3)
    {
        super.drawScreen(x, y, par3);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < EmcType.values().length; i++)
        {
            if (y >= yStart + 10 && y <= yStart + 77)
            {
                if (x >= xStart + 9 + (i * 20) && x <= xStart + 28 + (i * 20))
                {
                    list.add(EmcType.values()[i].toString() + ": "
                            + Float.toString(tile.getEmc().components[i]));
                    list.add("Max: " + Float.toString(tile.getMaxStoredEmc()));
                }
            }
        }
        drawHoveringText(list, x, y, fontRenderer);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Resources.GUI_EMC_CAPACITOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        for (int i = 0; i < EmcType.values().length; i++)
        {
            RGBValue rgbvalue = EmcRGBValues.getRGB(EmcType.values()[i]);
            GL11.glColor3f((float) rgbvalue.colorR / 255,
                    (float) rgbvalue.colorG / 255,
                    (float) rgbvalue.colorB / 255);
            this.drawTexturedModalRect(
                    xStart + 10 + (i * 20),
                    yStart
                            + 77
                            - (int) (tile.getEmc().components[i]
                                    / tile.getMaxStoredEmc() * 67),
                    19,
                    167,
                    15,
                    (int) (tile.getEmc().components[i] / tile.getMaxStoredEmc() * 67));
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        for (int i = 0; i < EmcType.values().length; i++)
        {
            this.drawTexturedModalRect(xStart + 9 + (i * 20), yStart + 10, 1,
                    166, 19, 67);
        }
    }
}
