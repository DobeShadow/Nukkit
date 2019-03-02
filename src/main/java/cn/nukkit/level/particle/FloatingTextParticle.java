package cn.nukkit.level.particle;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.PlayerListPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import cn.nukkit.network.protocol.types.PlayerListEntry;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 2015/11/21 by xtypr.
 * Package cn.nukkit.level.particle in project Nukkit .
 */
public class FloatingTextParticle extends Particle {
    private static final Skin EMPTY_SKIN = new Skin();

    protected UUID uuid = UUID.randomUUID();
    protected final Level level;
    protected long entityId = -1;
    protected boolean invisible = false;
    protected EntityMetadata metadata = new EntityMetadata();

    public FloatingTextParticle(Location location, String text) {
        this(location, text, "");
    }

    public FloatingTextParticle(Location location, String text, String title) {
        this(location.getLevel(), location, text, title);
    }

    public FloatingTextParticle(Vector3 pos, String text) {
        this(pos, text, "");
    }

    public FloatingTextParticle(Vector3 pos, String text, String title) {
        this(null, pos, text, title);
    }

    private FloatingTextParticle(Level level, Vector3 pos, String text, String title) {
        super(pos.x, pos.y, pos.z);
        this.level = level;

        long flags = 1l << Entity.DATA_FLAG_IMMOBILE;
        this.metadata.putLong(Entity.DATA_FLAGS, flags)
                .putLong(Entity.DATA_LEAD_HOLDER_EID,-1)
                .putString(Entity.DATA_NAMETAG, title)
                .putString(Entity.DATA_SCORE_TAG, text)
                .putFloat(Entity.DATA_SCALE, 0.01f) //zero causes problems on debug builds?
                .putFloat(Entity.DATA_BOUNDING_BOX_HEIGHT, 0.01f)
                .putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0.01f);
    }

    public String getText() {
        return this.metadata.getString(Entity.DATA_SCORE_TAG);
    }

    public void setText(String text) {
        this.metadata.putString(Entity.DATA_SCORE_TAG, text);
        this.sendMetadata();
    }

    public String getTitle() {
        return this.metadata.getString(Entity.DATA_NAMETAG);
    }

    public void setTitle(String title) {
        this.metadata.putString(Entity.DATA_NAMETAG, title);
        this.sendMetadata();
    }

    private void sendMetadata() {
        if (this.level != null) {
            SetEntityDataPacket packet = new SetEntityDataPacket();
            packet.entityRuntimeId = this.entityId;
            packet.metadata = this.metadata;
            this.level.addChunkPacket(getChunkX(), getChunkZ(), packet);
        }
    }

    public boolean isInvisible() {
        return this.invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public void setInvisible() {
        this.setInvisible(true);
    }
    
    public long getEntityId() {
        return this.entityId;   
    }

    @Override
    public DataPacket[] encode() {
        ArrayList<DataPacket> packets = new ArrayList<>();

        if (this.entityId == -1) {
            this.entityId = 1095216660480l + ThreadLocalRandom.current().nextLong(0, 0x7fffffffl);
        } else {
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.entityUniqueId = this.entityId;

            packets.add(pk);
        }

        if (!this.invisible) {
            PlayerListEntry[] entry = {new PlayerListEntry(this.uuid, this.entityId, this.metadata.getString(Entity.DATA_NAMETAG), EMPTY_SKIN)};
            PlayerListPacket playerAdd = new PlayerListPacket();
            playerAdd.entries = entry;
            playerAdd.type = PlayerListPacket.TYPE_ADD;
            packets.add(playerAdd);

            AddPlayerPacket pk = new AddPlayerPacket();
            pk.uuid = uuid;
            pk.username = "";
            pk.entityUniqueId = this.entityId;
            pk.entityRuntimeId = this.entityId;
            pk.x = (float) this.x;
            pk.y = (float) (this.y - 0.75);
            pk.z = (float) this.z;
            pk.speedX = 0;
            pk.speedY = 0;
            pk.speedZ = 0;
            pk.yaw = 0;
            pk.pitch = 0;
            pk.metadata = this.metadata;
            pk.item = Item.get(Item.AIR);
            packets.add(pk);

            PlayerListPacket playerRemove = new PlayerListPacket();
            playerRemove.entries = entry;
            playerRemove.type = PlayerListPacket.TYPE_REMOVE;
            packets.add(playerRemove);
        }

        return packets.toArray(new DataPacket[0]);
    }
}
