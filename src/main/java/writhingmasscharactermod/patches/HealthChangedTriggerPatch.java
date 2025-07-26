package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import writhingmasscharactermod.util.OnHealthChangedCard;

@SpirePatch2(
        clz= AbstractCreature.class,
        method="healthBarUpdatedEvent"
)
public class HealthChangedTriggerPatch {
    @SpirePostfixPatch
    public static void Insert(AbstractCreature __instance) {
        if (__instance instanceof AbstractPlayer) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                for(AbstractCard c : ((AbstractPlayer) __instance).hand.group) {
                    if (c instanceof OnHealthChangedCard) {
                        ((OnHealthChangedCard) c).OnHealthChanged();
                    }
                }

                for(AbstractCard c : ((AbstractPlayer) __instance).discardPile.group) {
                    if (c instanceof OnHealthChangedCard) {
                        ((OnHealthChangedCard) c).OnHealthChanged();
                    }
                }

                for(AbstractCard c : ((AbstractPlayer) __instance).drawPile.group) {
                    if (c instanceof OnHealthChangedCard) {
                        ((OnHealthChangedCard) c).OnHealthChanged();
                    }
                }
            }
        }
    }
}
