package hea3ven.advenchanting.common;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAdvancedEnchantmentTable extends TileEntity implements
		IInventory {

	private ItemStack[] inv;
	private Random rand = new Random();
	private int progress;
	private int enchantmentLevel;
	private int experience;

	public TileEntityAdvancedEnchantmentTable() {
		inv = new ItemStack[2];
		progress = 0;
		enchantmentLevel = 1;
		experience = 0;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

	}

	@Override
	public String getInvName() {
		return "tco.tileentityadvancedenchantmenttable";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
						zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		progress = tagCompound.getShort("Progress");
		enchantmentLevel = tagCompound.getShort("EnchantmentLevel");
		experience = tagCompound.getInteger("Experience");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
		tagCompound.setShort("Progress", (short) progress);
		tagCompound.setShort("EnchantmentLevel", (short) enchantmentLevel);
		tagCompound.setInteger("Experience", experience);
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			if (inv[0] != null && canEnchant()) {
				progress++;
				if (progress >= 100) {
					progress = 0;

					experience -= enchantmentLevel;

					ItemStack item = inv[0];
					List enchantmentsList = EnchantmentHelper
							.buildEnchantmentList(this.rand, item,
									this.enchantmentLevel);
					boolean isBook = item.itemID == Item.book.itemID;

					if (enchantmentsList != null) {
						if (isBook)
							item.itemID = Item.field_92053_bW.itemID;

						int bookEnchant = isBook ? this.rand
								.nextInt(enchantmentsList.size()) : -1;
						for (int i = 0; i < enchantmentsList.size(); ++i) {
							EnchantmentData enchantmentData = (EnchantmentData) enchantmentsList
									.get(i);

							if (isBook || i == bookEnchant) {
								Item.field_92053_bW.func_92060_a(item,
										enchantmentData);
							} else {
								item.addEnchantment(
										enchantmentData.enchantmentobj,
										enchantmentData.enchantmentLevel);
							}
						}
						inv[1] = item;
						inv[0] = null;

						this.onInventoryChanged();
					}
				}
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
						this.zCoord);
			} else if (progress != 0) {
				progress = 0;
			}
		}
	}

	public boolean isEnchanting() {
		return progress != 0;
	}
	
	public boolean canEnchant() {
		return experience > enchantmentLevel;
	}

	public int getProgressScaled(int i) {
		return progress * i / 100;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getEnchantmentLevel() {
		return enchantmentLevel;
	}

	public void setEnchantmentLevel(int enchantmentLevel) {
		if (enchantmentLevel < 1)
			this.enchantmentLevel = 1;
		else if (enchantmentLevel > 30)
			this.enchantmentLevel = 30;
		else
			this.enchantmentLevel = enchantmentLevel;
	}

	public void addExperience(int experience) {
		this.experience += experience;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

}