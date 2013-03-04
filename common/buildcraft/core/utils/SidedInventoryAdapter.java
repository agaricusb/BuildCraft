package buildcraft.core.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

/**
 * This class is responsible for abstracting an ISidedInventory as a normal IInventory
 * 
 * @author Krapht
 * 
 */
public class SidedInventoryAdapter implements IInventory {

	private final ISidedInventory _sidedInventory;
	private final ForgeDirection _side;
	private final int _slotOffset;

	public SidedInventoryAdapter(ISidedInventory sidedInventory, ForgeDirection side) {
		_sidedInventory = sidedInventory;
		_side = side;
		_slotOffset = _sidedInventory.getStartInventorySide(side);
	}

	@Override
	public int getSizeInventory() {
		return _sidedInventory.getSizeInventorySide(_side);
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return _sidedInventory.getStackInSlot(i + _slotOffset);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return _sidedInventory.decrStackSize(i + _slotOffset, j);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		_sidedInventory.setInventorySlotContents(i + _slotOffset, itemstack);
	}

	@Override
	public String getInvName() {
		return _sidedInventory.getInvName();
	}

	@Override
	public int getInventoryStackLimit() {
		return _sidedInventory.getInventoryStackLimit();
	}

	@Override
	public void onInventoryChanged() {
		_sidedInventory.onInventoryChanged();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return _sidedInventory.isUseableByPlayer(entityplayer);
	}

	@Override
	public void openChest() {
		_sidedInventory.openChest();
	}

	@Override
	public void closeChest() {
		_sidedInventory.closeChest();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return _sidedInventory.getStackInSlotOnClosing(slot + _slotOffset);
	}

	// MCPC+ start
	public java.util.List<org.bukkit.entity.HumanEntity> transaction =
			new java.util.ArrayList<org.bukkit.entity.HumanEntity>();

	public void onOpen(org.bukkit.craftbukkit.v1_4_R1.entity.CraftHumanEntity who) {
		transaction.add(who);
	}

	public void onClose(org.bukkit.craftbukkit.v1_4_R1.entity.CraftHumanEntity who) {
		transaction.remove(who);
	}

	public java.util.List<org.bukkit.entity.HumanEntity> getViewers() {
		return transaction;
	}

	public void setMaxStackSize(int size) {}

	public ItemStack[] getContents()
	{
		ItemStack[] ret = new ItemStack[getSizeInventory()];
		for (int i = 0; i < getSizeInventory(); ++i)
			ret[i] = getStackInSlot(i);
		return ret;
	}
	// MCPC+ end
}
