package me.t3sl4.factory.util.hologram.nms.v1_8_R2;

import me.t3sl4.factory.util.hologram.api.IHologram;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R2.WorldServer;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class Hologram1_8_R2 implements IHologram {
   @NotNull
   public Object spawnHologram(@NotNull String text, @NotNull Location location) {
      WorldServer world = ((CraftWorld)location.getWorld()).getHandle();
      EntityArmorStand armorStand = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());
      this.configureHologram(armorStand, text, location);
      world.addEntity(armorStand, SpawnReason.CUSTOM);
      return armorStand;
   }

   public void removeHologram(@NotNull World world, @NotNull Object entity) {
      WorldServer nmsWorld = ((CraftWorld)world).getHandle();
      nmsWorld.removeEntity((Entity)entity);
   }

   @NotNull
   public Object[] createPacket(@NotNull Location location, @NotNull String text) {
      WorldServer world = ((CraftWorld)location.getWorld()).getHandle();
      EntityArmorStand armorStand = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());
      this.configureHologram(armorStand, text, location);
      return new Object[]{new PacketPlayOutSpawnEntityLiving(armorStand), armorStand.getId()};
   }

   @NotNull
   public Object removePacket(int id) {
      return new PacketPlayOutEntityDestroy(new int[]{id});
   }

   public void configureHologram(@NotNull Object entity, @NotNull String text, @NotNull Location location) {
      EntityArmorStand nmsEntity = (EntityArmorStand)entity;
      nmsEntity.setCustomName(ChatSerializer.a("{\"text\": \"" + text + "\"}").getText());
      nmsEntity.setCustomNameVisible(true);
      nmsEntity.setGravity(true);
      nmsEntity.setLocation(location.getX(), location.getY(), location.getZ(), 0.0F, 0.0F);
      nmsEntity.setInvisible(true);
   }

   public void sendPacket(@NotNull Player player, @NotNull Object object) {
      ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)object);
   }
}
