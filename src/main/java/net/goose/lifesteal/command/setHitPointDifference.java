package net.goose.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.capability.CapabilityRegistry;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.IHeartCap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
public class setHitPointDifference {

    public setHitPointDifference(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                Commands.literal("setHitPointDifference")
                .requires((commandSource) -> {return commandSource.hasPermission(2);})
                        .then(Commands.argument("Player", EntityArgument.entity())
                                .then(Commands.argument("Amount", IntegerArgumentType.integer()).executes((command) -> {
                                    return setHitPoint(command.getSource(), EntityArgument.getEntity(command, "Player"), IntegerArgumentType.getInteger(command, "Amount"));}
                                ))));
    }

    private int setHitPoint(CommandSourceStack source, Entity chosenentity, int amount) throws CommandSyntaxException{

        LivingEntity playerthatsentcommand = source.getPlayer();

        CapabilityRegistry.getHeart(chosenentity).ifPresent(newHeartDifference -> newHeartDifference.setHeartDifference(amount));
        CapabilityRegistry.getHeart(chosenentity).ifPresent(IHeartCap::refreshHearts);

        if(chosenentity != playerthatsentcommand && source.isPlayer()){
            playerthatsentcommand.sendSystemMessage(Component.translatable("Set "+ chosenentity.getName().getString() +"'s HitPoint difference to "+amount));
        }else if(!source.isPlayer()){
            LifeSteal.LOGGER.info("Set "+ chosenentity.getName().getString() +"'s HitPoint difference to "+amount);
        }

        chosenentity.sendSystemMessage(Component.translatable("Your HitPoint difference has been set to "+amount));
        return 1;
    }
}
