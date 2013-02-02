package hea3ven.advenchanting.common;

public enum PacketType {
	CHANGE_ENCHANTMENT_LEVEL(0), ADD_EXPERIENCE(1);

	public int id;

	private PacketType(int id) {
		this.id = id;
	}

}
