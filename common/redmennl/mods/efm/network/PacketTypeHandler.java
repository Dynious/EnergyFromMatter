package redmennl.mods.efm.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import redmennl.mods.efm.lib.Reference;
import redmennl.mods.efm.network.packet.PacketEFM;
import redmennl.mods.efm.network.packet.PacketEmcValue;
import redmennl.mods.efm.network.packet.PacketSoundCullEvent;
import redmennl.mods.efm.network.packet.PacketSoundEvent;
import redmennl.mods.efm.network.packet.PacketSpawnEmcParticle;

public enum PacketTypeHandler
{
    EMCVALUE(PacketEmcValue.class), SPAWN_EMC_PARTICLE(
            PacketSpawnEmcParticle.class),
            SOUND_EVENT(PacketSoundEvent.class),
            SOUND_CULL_EVENT(PacketSoundCullEvent.class);
    private Class<? extends PacketEFM> clazz;
    
    PacketTypeHandler(Class<? extends PacketEFM> clazz)
    {
        this.clazz = clazz;
    }
    
    public static PacketEFM buildPacket(byte[] data)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);
        PacketEFM packet = null;
        try
        {
            packet = values()[selector].clazz.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        packet.readPopulate(dis);
        return packet;
    }
    
    public static PacketEFM buildPacket(PacketTypeHandler type)
    {
        PacketEFM packet = null;
        try
        {
            packet = values()[type.ordinal()].clazz.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        return packet;
    }
    
    public static Packet populatePacket(PacketEFM PacketEFM)
    {
        byte[] data = PacketEFM.populate();
        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Reference.CHANNEL_NAME;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = PacketEFM.isChunkDataPacket;
        return packet250;
    }
}
