package hea3ven.advenchanting.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// player.
		EntityPlayer entityPlayer = (EntityPlayer) player;
		if (packet.channel.equals("AdvEnchanting")) {
			ByteArrayDataInput dataStream = ByteStreams
					.newDataInput(packet.data);
			int packetType = dataStream.readInt();
			if (packetType == PacketType.CHANGE_ENCHANTMENT_LEVEL.id) {
				int x = dataStream.readInt();
				int y = dataStream.readInt();
				int z = dataStream.readInt();
				int enchantmentLevel = dataStream.readInt();

				World world = entityPlayer.worldObj;
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				if (tileEntity instanceof TileEntityAdvancedEnchantmentTable) {
					((TileEntityAdvancedEnchantmentTable) tileEntity)
							.setEnchantmentLevel(enchantmentLevel);
				}
			} else if (packetType == PacketType.ADD_EXPERIENCE.id) {
				int x = dataStream.readInt();
				int y = dataStream.readInt();
				int z = dataStream.readInt();
				

				World world = entityPlayer.worldObj;
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				if (tileEntity instanceof TileEntityAdvancedEnchantmentTable) {
					int initialExperience = entityPlayer.experienceTotal;
					entityPlayer.addExperienceLevel(-entityPlayer.experienceLevel - 1); // This resets the levels to 0
					TileEntityAdvancedEnchantmentTable te = ((TileEntityAdvancedEnchantmentTable) tileEntity);
					te.setExperience(te.getExperience() + initialExperience - entityPlayer.experienceTotal);
				}
			}
		}
	}

	public static void sendUpdateEnchantmentLevel(
			TileEntityAdvancedEnchantmentTable tileEntity, int enchantmentLevel) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketType.CHANGE_ENCHANTMENT_LEVEL.id);
			outputStream.writeInt(tileEntity.xCoord);
			outputStream.writeInt(tileEntity.yCoord);
			outputStream.writeInt(tileEntity.zCoord);
			outputStream.writeInt(enchantmentLevel);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AdvEnchanting";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	public static void sendAddExperience(
			TileEntityAdvancedEnchantmentTable tileEntity) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketType.ADD_EXPERIENCE.id);
			outputStream.writeInt(tileEntity.xCoord);
			outputStream.writeInt(tileEntity.yCoord);
			outputStream.writeInt(tileEntity.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AdvEnchanting";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);

	}
}
