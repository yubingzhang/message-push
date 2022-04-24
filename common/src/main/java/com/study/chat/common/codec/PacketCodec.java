package com.study.chat.common.codec;

import com.study.chat.common.constants.CommonConstant;
import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import com.study.chat.common.protocol.request.HeartBeatRequestPacket;
import com.study.chat.common.protocol.request.LoginRequestPacket;
import com.study.chat.common.protocol.response.HeartBeatResponsePacket;
import com.study.chat.common.protocol.response.LoginResponsePacket;
import com.study.chat.common.serialize.Serializer;
import com.study.chat.common.serialize.SerializerType;
import com.study.chat.common.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 协议包编解码
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class PacketCodec extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final Map<Byte, Serializer> serializeMap = new ConcurrentHashMap<>(4);
    public static final Map<Byte, Class<? extends Packet>> packetMap = new ConcurrentHashMap<>(16);
    static {
        // 填充命令对应的请求包
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
        // 填充序列化方式
        serializeMap.put(SerializerType.SERIALIZE_TYPE_JSON, new JsonSerializer());
    }

    private PacketCodec() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();

        byteBuf.writeInt(CommonConstant.MAGIC_NUM);
        byteBuf.writeByte(msg.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.serializeType());
        byteBuf.writeByte(msg.getCommand());

        // 序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(msg);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 跳过4个字节的魔数
        msg.skipBytes(4);
        // 跳过1个字节版本
        msg.skipBytes(1);
        // 获取序列化方式
        byte serializeType = msg.readByte();
        // 获取命令
        byte command = msg.readByte();
        // 获取数据长度
        int len = msg.readInt();
        // 序列化的对象信息
        byte[] data = new byte[len];
        msg.readBytes(data);
        // 命令对应packet类型
        Class<? extends Packet> packet = packetMap.get(command);
        // 序列化方式
        Serializer serializer = serializeMap.get(serializeType);
        out.add(serializer.deserialize(data, packet));
    }
}
