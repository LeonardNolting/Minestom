package net.minestom.server.entity.type.projectile;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.type.Projectile;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EntityPotion extends ObjectEntity implements Projectile {

    private ItemStack potion;

    public EntityPotion(Position spawnPosition, ItemStack potion) {
        super(EntityType.POTION, spawnPosition);
        setBoundingBox(0.25f, 0.25f, 0.25f);
        setPotion(potion);
    }

    @NotNull
    @Override
    public Consumer<BinaryWriter> getMetadataConsumer() {
        return packet -> {
            super.getMetadataConsumer().accept(packet);
            fillMetadataIndex(packet, 7);
        };
    }

    @Override
    protected void fillMetadataIndex(@NotNull BinaryWriter packet, int index) {
        super.fillMetadataIndex(packet, index);
        if (index == 7) {
            packet.writeByte((byte) 7);
            packet.writeByte(METADATA_SLOT);
            packet.writeItemStack(potion, null);
        }
    }

    @Override
    public int getObjectData() {
        return 0;
    }

    public ItemStack getPotion() {
        return potion;
    }

    public void setPotion(ItemStack potion) {
        this.potion = potion;
        sendMetadataIndex(15);
    }
}
