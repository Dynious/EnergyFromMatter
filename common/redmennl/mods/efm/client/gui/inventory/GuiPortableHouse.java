package redmennl.mods.efm.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import redmennl.mods.efm.lib.Resources;
import redmennl.mods.efm.tileentity.TilePortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;
import cpw.mods.fml.common.FMLCommonHandler;

public class GuiPortableHouse extends GuiScreen
{
    
    private TileEntity tile;
    
    public GuiButton clearArea;
    public GuiTextField name;
    
    public GuiPortableHouse(TileEntity tile)
    {
        this.tile = tile;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void initGui()
    {
        buttonList.clear();
        
        if (tile instanceof TilePortableHouse)
        {
            buttonList.add(new GuiButton(1, width / 2 - 73, height / 2 - 63,
                    20, 20, "+"));
            buttonList.add(new GuiButton(2, width / 2 - 73, height / 2 - 28,
                    20, 20, "-"));
            buttonList.add(new GuiButton(3, width / 2, height / 2 - 28, 80, 20,
                    "Clear"));
            buttonList.add(new GuiButton(4, width / 2, height / 2 - 53, 80, 20,
                    "Start"));
            buttonList.add(new GuiButton(5, width / 2 - 43, height / 2 - 63,
                    20, 20, "+"));
            buttonList.add(new GuiButton(6, width / 2 - 43, height / 2 - 28,
                    20, 20, "-"));
            name = new GuiTextField(fontRenderer, width / 2, height / 2 - 73,
                    80, 15);
            name.setFocused(true);
            name.setMaxStringLength(16);
            
        }
        if (tile instanceof TilePortableHouseDeployer)
        {
            buttonList.add(new GuiButton(1, width / 2 - 40, height / 2 - 70,
                    80, 20, "Deploy"));
            buttonList.add(clearArea = new GuiButton(2, width / 2 - 40,
                    height / 2 - 30, 80, 20, "Clear Off"));
            clearArea.displayString = ((TilePortableHouseDeployer) tile).clearArea ? "Clear On"
                    : "Clear Off";
        }
        super.initGui();
    }
    
    @Override
    public void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);
        if (tile instanceof TilePortableHouse)
        {
            name.textboxKeyTyped(c, i);
            ((TilePortableHouse) tile).name = name.getText();
        }
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawContainerBackground();
        
        if (tile instanceof TilePortableHouse)
        {
            name.drawTextBox();
        }
        
        super.drawScreen(i, j, f);
        
        if (tile instanceof TilePortableHouse)
        {
            TilePortableHouse tilePH = (TilePortableHouse) tile;
            
            String numberText = tilePH.size + "x" + tilePH.size;
            fontRenderer.drawString(numberText,
                    width / 2 - fontRenderer.getStringWidth(numberText) / 2
                            - 63, height / 2 - 39, 0xFFFFFF);
            String hightText = "" + tilePH.height;
            fontRenderer
                    .drawString(hightText,
                            width / 2 - fontRenderer.getStringWidth(hightText)
                                    / 2 - 33, height / 2 - 39, 0xFFFFFF);
            fontRenderer
                    .drawString("Size",
                            width / 2 - fontRenderer.getStringWidth(hightText)
                                    / 2 - 70, height / 2 - 75, 0xFFFFFF);
            fontRenderer
                    .drawString("Hight",
                            width / 2 - fontRenderer.getStringWidth(hightText)
                                    / 2 - 40, height / 2 - 75, 0xFFFFFF);
        }
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        if (tile instanceof TilePortableHouse)
        {
            name.setText(((TilePortableHouse) tile).name);
        }
        if (tile instanceof TilePortableHouseDeployer)
        {
            clearArea.displayString = ((TilePortableHouseDeployer) tile).clearArea ? "Clear On"
                    : "Clear Off";
        }
        
    }
    
    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (tile instanceof TilePortableHouse)
        {
            TilePortableHouse tilePH = (TilePortableHouse) tile;
            if (guibutton.id == 1 && tilePH.size < 9)
            {
                tilePH.size = tilePH.size + 2;
            }
            if (guibutton.id == 2 && tilePH.size > 1)
            {
                tilePH.size = tilePH.size - 2;
            }
            if (guibutton.id == 3)
            {
                tilePH.size = 1;
                tilePH.height = 1;
                name.setText("");
            }
            if (guibutton.id == 4)
            {
                FMLCommonHandler.instance().showGuiScreen(null);
                tilePH.saveBlocks();
            }
            if (guibutton.id == 5 && tilePH.height < 5)
            {
                tilePH.height++;
            }
            if (guibutton.id == 6 && tilePH.height > 1)
            {
                tilePH.height--;
            }
        }
        if (tile instanceof TilePortableHouseDeployer)
        {
            TilePortableHouseDeployer tilePH = (TilePortableHouseDeployer) tile;
            if (guibutton.id == 1)
            {
                FMLCommonHandler.instance().showGuiScreen(null);
                tilePH.deploy();
            }
            if (guibutton.id == 2)
            {
                tilePH.clearArea = !tilePH.clearArea;
            }
        }
    }
    
    private void drawContainerBackground()
    {
        int xSize = 176;
        int ySize = 166;
        
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
