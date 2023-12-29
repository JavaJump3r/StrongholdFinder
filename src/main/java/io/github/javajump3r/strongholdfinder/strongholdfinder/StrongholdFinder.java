package io.github.javajump3r.strongholdfinder.strongholdfinder;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class StrongholdFinder {
    List<Throw> eyeThrows = new LinkedList<>();
    public StrongholdFinder(){
        ClientCommandRegistrationCallback.EVENT.register((dispatcher,registryAccess)->{
            var builder = getNewBuilder();
            var t = argument("throw2",IntegerArgumentType.integer(0))
                    .executes(this::calculate);
            builder.then(literal("register").executes(this::registerThrow));
            builder.then(literal("calculate")
                    .then(argument("throw1", IntegerArgumentType.integer(0))
                            .then(t)));
            builder.then(literal("print")
                    .then(argument("throwid",IntegerArgumentType.integer(0))
                    .executes(this::printThrow)));
            dispatcher.register(builder);
        });
    }

    private int test(CommandContext<FabricClientCommandSource> commandContext) {
        int x1,x2,y1,y2,deg1,deg2;
        x1 = commandContext.getArgument("x1",Integer.class);
        x2 = commandContext.getArgument("x2",Integer.class);
        y1 = commandContext.getArgument("y1",Integer.class);
        y2 = commandContext.getArgument("y2",Integer.class);
        deg1 = commandContext.getArgument("deg1",Integer.class);
        deg2 = commandContext.getArgument("deg2",Integer.class);
        Line.getIntersection(x1,y1,deg1,x2,y2,deg2);
        return 0;
    }

    public LiteralArgumentBuilder<FabricClientCommandSource> getNewBuilder(){
        return literal("eyethrow");
    }
    private int printThrow(CommandContext<FabricClientCommandSource> commandContext) {
        int throwId = commandContext.getArgument("throwid",Integer.class);
        sendChatMessage(String.format("Throw %d: %s",throwId,eyeThrows.get(throwId).toString()));
        return 0;
    }

    private int calculate(CommandContext<FabricClientCommandSource> commandContext){
        int throw1id = commandContext.getArgument("throw1",Integer.class);
        int throw2id = commandContext.getArgument("throw2",Integer.class);

        var throw1 = eyeThrows.get(throw1id);
        var throw2 = eyeThrows.get(throw2id);

        var intersection = Line.getIntersection(
                throw1.pos.x,
                throw1.pos.z,
                throw1.angle,
                throw2.pos.x,
                throw2.pos.z,
                throw2.angle);

       sendChatMessage(String.format("Intersection of throws %d and %d at %s",
                throw1id,
                throw2id,
                String.format("x:%d y:%d", (int)intersection.getX(), (int)intersection.getZ())));

        return 0;
    }

    private int registerThrow(CommandContext<FabricClientCommandSource> commandContext) {
        eyeThrows.add(new Throw(MinecraftClient.getInstance().player.getPos(),MinecraftClient.getInstance().player.getYaw()));
        sendChatMessage(String.format("Registered throw %d (%s)",eyeThrows.size()-1,eyeThrows.get(eyeThrows.size()-1).toString()));
        return 0;
    }
    public void sendChatMessage(Object... messages){
        String fullMessage="";
        for(var message : messages)
            fullMessage+=(message==null)?"null": message +" ";
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(fullMessage));
    }
}
