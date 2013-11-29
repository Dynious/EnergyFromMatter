package dynious.mods.efm.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.lib.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dynious.mods.efm.inventory.ContainerMatterCreator;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.tileentity.TileMatterCreator;

@SideOnly(Side.CLIENT)
public class GuiMatterCreator extends GuiContainer
{
    public TileMatterCreator tile;
    
    public GuiMatterCreator(InventoryPlayer inventoryPlayer,
            TileMatterCreator tile)
    {
        super(new ContainerMatterCreator(inventoryPlayer, tile));
        xSize = 248;
        ySize = 195;
        this.tile = tile;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        fontRenderer.drawString(
                StatCollector.translateToLocal(Strings.CONTAINER_INVENTORY),
                44, ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Resources.GUI_MATTER_CREATOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
