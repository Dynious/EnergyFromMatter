package dynious.mods.efm.emc;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public interface IEmcHolder
{
    public boolean storesType(EmcType emcType);
    
    public boolean addEmc(EmcValue emcValue);
    
    public float neededEmc(EmcType emcType);
    
    public boolean useEmc(EmcValue emcValue);
    
    public boolean hasEmc(EmcValue emcValue);
    
    public EmcValue getEmc();
    
    public void setEmc(EmcValue emcValue);
    
    public float getMaxStoredEmc();
}
