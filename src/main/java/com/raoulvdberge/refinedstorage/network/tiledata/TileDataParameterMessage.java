package com.raoulvdberge.refinedstorage.network.tiledata;

import com.raoulvdberge.refinedstorage.tile.data.TileDataManager;
import com.raoulvdberge.refinedstorage.tile.data.TileDataParameter;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TileDataParameterMessage {
    private TileEntity tile;
    private TileDataParameter parameter;
    private boolean initial;

    public TileDataParameterMessage(TileEntity tile, TileDataParameter parameter, boolean initial) {
        this.tile = tile;
        this.parameter = parameter;
        this.initial = initial;
    }

    public static TileDataParameterMessage decode(PacketBuffer buf) {
        int id = buf.readInt();
        boolean initial = buf.readBoolean();

        TileDataParameter parameter = TileDataManager.getParameter(id);

        if (parameter != null) {
            try {
                parameter.setValue(initial, parameter.getSerializer().read(buf));
            } catch (Exception e) {
                // NO OP
            }
        }

        return new TileDataParameterMessage(null, null, initial);
    }

    public static void encode(TileDataParameterMessage message, PacketBuffer buf) {
        buf.writeInt(message.parameter.getId());
        buf.writeBoolean(message.initial);

        message.parameter.getSerializer().write(buf, message.parameter.getValueProducer().apply(message.tile));
    }

    public static void handle(TileDataParameterMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
    }
}
