package dynious.mods.efm.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dynious.mods.efm.lib.BlockIds;

public class CreativeTabEFM extends CreativeTabs
{
    public CreativeTabEFM(int tabID, String tabLabel)
    {
        super(tabID, tabLabel);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return BlockIds.MATTER_DISTILLERY;
    }
}
