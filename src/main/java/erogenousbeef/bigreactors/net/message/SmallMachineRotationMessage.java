package erogenousbeef.bigreactors.net.message;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import erogenousbeef.bigreactors.common.tileentity.base.TileEntityBeefBase;
import io.netty.buffer.ByteBuf;

public class SmallMachineRotationMessage implements IMessage, IMessageHandler<SmallMachineRotationMessage, IMessage> {
    private int x, y, z, newOrientation;

    public SmallMachineRotationMessage(int x, int y, int z, int newOrientation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.newOrientation = newOrientation;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        newOrientation = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(newOrientation);
    }

    @Override
    public IMessage onMessage(SmallMachineRotationMessage message, MessageContext ctx) {
        TileEntity te = FMLClientHandler.instance().getWorldClient().getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityBeefBase) {
            ((TileEntityBeefBase)te).rotateTowards(ForgeDirection.getOrientation(newOrientation));
            FMLClientHandler.instance().getWorldClient().markBlockForUpdate(x, y, z);
        }
        return null;
    }
}