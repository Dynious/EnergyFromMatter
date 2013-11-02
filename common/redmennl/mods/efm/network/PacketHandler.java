package redmennl.mods.efm.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import redmennl.mods.efm.network.packet.PacketEFM;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    /***
     * Handles Packet250CustomPayload packets that are registered to an
     * Equivalent Exchange 3 network channel
     * 
     * @param manager
     *            The NetworkManager associated with the current platform
     *            (client/server)
     * @param packet
     *            The Packet250CustomPayload that was received
     * @param player
     *            The Player associated with the packet
     */
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        // Build a PacketEE object from the data contained within the
        // Packet250CustomPayload packet
        PacketEFM packetEE = PacketTypeHandler.buildPacket(packet.data);
        // Execute the appropriate actions based on the PacketEE type
        packetEE.execute(manager, player);
    }
}
