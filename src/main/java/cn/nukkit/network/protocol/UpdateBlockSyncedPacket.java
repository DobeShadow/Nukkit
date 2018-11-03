package cn.nukkit.network.protocol;

public class UpdateBlockSyncedPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.UPDATE_BLOCK_SYNCED_PACKET;

    public long entityUniqueId = 0;
    public long uvarint64_2 = 0;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarLong(this.entityUniqueId);
        this.putUnsignedVarLong(this.uvarint64_2);
    }
}