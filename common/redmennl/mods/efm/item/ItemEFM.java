package redmennl.mods.efm.item;

import net.minecraft.item.Item;
import redmennl.mods.efm.lib.Reference;

public abstract class ItemEFM extends Item
{
    public ItemEFM(int id)
    {
        super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
    }
}
