package net.goose.lifesteal.Items.custom;

import net.goose.lifesteal.Configurations.ConfigHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartCoreItem extends Item {

    public static final FoodProperties HeartCore = (new FoodProperties.Builder()).alwaysEat().build();

    public HeartCoreItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {

        if(!level.isClientSide() && entity instanceof ServerPlayer serverPlayer){

            if(!ConfigHolder.SERVER.disableHeartCores.get() && entity.getHealth() < entity.getMaxHealth()){

                float maxHealth = entity.getMaxHealth();
                float amountThatWillBeHealed = (float) (maxHealth * ConfigHolder.SERVER.HeartCoreHeal.get());
                float differenceInHealth = entity.getMaxHealth() - entity.getHealth();
                if(differenceInHealth <= amountThatWillBeHealed){
                    amountThatWillBeHealed = differenceInHealth;
                }

                int oldDuration = 0;
                if(entity.hasEffect(MobEffects.REGENERATION)){
                    MobEffectInstance mobEffect = entity.getEffect(MobEffects.REGENERATION);

                    oldDuration = mobEffect.getDuration();
                }

                int tickTime = (int) ((amountThatWillBeHealed * 50) / 2) + oldDuration;
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 1));
            }else{

                if(ConfigHolder.SERVER.disableHeartCores.get()){
                    serverPlayer.displayClientMessage(Component.translatable("Heart Cores have been disabled in the configurations"), true);
                    item.shrink(-1);
                    serverPlayer.containerMenu.broadcastChanges();
                }else{
                    serverPlayer.displayClientMessage(Component.translatable("You are already at max health"), true);
                    item.shrink(-1);
                    serverPlayer.containerMenu.broadcastChanges();
                }

            }
        }
        return super.finishUsingItem(item, level, entity);
    }
}
