package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import writhingmasscharactermod.cards.uncommon.MassDestruction;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractCreature.class,
        method="healthBarUpdatedEvent"
)
public class MassDestructionPatch {
    @SpireInsertPatch(
            rloc=0
    )
    public static void updateMassDestructionDamage(AbstractCreature __instance)
    {
        if (__instance.equals(AbstractDungeon.player)) {
            ArrayList<CardGroup> cardsInPlay = new ArrayList<CardGroup>() { };
            cardsInPlay.add(((AbstractPlayer)__instance).hand);
            cardsInPlay.add(((AbstractPlayer)__instance).drawPile);
            cardsInPlay.add(((AbstractPlayer)__instance).discardPile);
            cardsInPlay.add(((AbstractPlayer)__instance).exhaustPile);
            cardsInPlay.add(((AbstractPlayer)__instance).masterDeck);
            for (CardGroup cardGroup : cardsInPlay) {
                for (int j = 0; j < cardGroup.getCardDeck().size(); j++) {
                    if (cardGroup.group.get(j).cardID.equals(MassDestruction.ID)) {
                        ((MassDestruction) (cardGroup.group.get(j))).externalSetDamage(((AbstractPlayer)__instance).currentHealth);
                    }
                }
            }
        }

    }
}
