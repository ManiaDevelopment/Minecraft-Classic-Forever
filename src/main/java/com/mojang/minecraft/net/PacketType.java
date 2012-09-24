package com.mojang.minecraft.net;

public class PacketType
{
	private PacketType(Class ... classes)
	{
		opcode = (byte)(nextOpcode++);
		packets[opcode] = this;
		params = new Class[classes.length];

		int length = 0;

		for(int classNumber = 0; classNumber < classes.length; classNumber++)
		{
			Class class_ = classes[classNumber];

			params[classNumber] = class_;

			if(class_ == Long.TYPE)
			{
				length += 8;
			} else if(class_ == Integer.TYPE) {
				length += 4;
			} else if(class_ == Short.TYPE) {
				length += 2;
			} else if(class_ == Byte.TYPE) {
				++length;
			} else if(class_ == Float.TYPE) {
				length += 4;
			} else if(class_ == Double.TYPE) {
				length += 8;
			} else if(class_ == byte[].class) {
				length += 1024;
			} else if(class_ == String.class) {
				length += 64;
			}
		}

		this.length = length;
	}

	public static final PacketType[] packets = new PacketType[256];

	public static final PacketType IDENTIFICATION = new PacketType(new Class[] {Byte.TYPE, String.class, String.class, Byte.TYPE});
	public static final PacketType LEVEL_INIT;
	public static final PacketType LEVEL_DATA;
	public static final PacketType LEVEL_FINALIZE;
	public static final PacketType PLAYER_SET_BLOCK;
	public static final PacketType BLOCK_CHANGE;
	public static final PacketType SPAWN_PLAYER;
	public static final PacketType POSITION_ROTATION;
	public static final PacketType POSITION_ROTATION_UPDATE;
	public static final PacketType POSITION_UPDATE;
	public static final PacketType ROTATION_UPDATE;
	public static final PacketType DESPAWN_PLAYER;
	public static final PacketType CHAT_MESSAGE;
	public static final PacketType DISCONNECT;
	public static final PacketType UPDATE_PLAYER_TYPE;

	public int length;
	private static int nextOpcode;
	public byte opcode;
	public Class[] params;

	static
	{
		new PacketType(new Class[0]);

		LEVEL_INIT = new PacketType(new Class[0]);
		LEVEL_DATA = new PacketType(new Class[] {Short.TYPE, byte[].class, Byte.TYPE});
		LEVEL_FINALIZE = new PacketType(new Class[] {Short.TYPE, Short.TYPE, Short.TYPE});
		PLAYER_SET_BLOCK = new PacketType(new Class[] {Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
		BLOCK_CHANGE = new PacketType(new Class[] {Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE});
		SPAWN_PLAYER = new PacketType(new Class[] {Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
		POSITION_ROTATION = new PacketType(new Class[] {Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
		POSITION_ROTATION_UPDATE = new PacketType(new Class[] {Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
		POSITION_UPDATE = new PacketType(new Class[] {Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
		ROTATION_UPDATE = new PacketType(new Class[] {Byte.TYPE, Byte.TYPE, Byte.TYPE});
		DESPAWN_PLAYER = new PacketType(new Class[] {Byte.TYPE});
		CHAT_MESSAGE = new PacketType(new Class[] {Byte.TYPE, String.class});
		DISCONNECT = new PacketType(new Class[] {String.class});
		UPDATE_PLAYER_TYPE = new PacketType(new Class[] {Byte.TYPE});

		nextOpcode = 0;
	}
}
