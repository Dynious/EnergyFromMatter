package dynious.mods.efm.item;

import dynious.mods.efm.lib.Reference;
import net.minecraft.item.Item;

public abstract class ItemEFM extends Item
{
    public ItemEFM(int id)
    {
        super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
    }
}
