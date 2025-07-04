package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import writhingmasscharactermod.cards.common.ReactiveStrike;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="applyStartOfCombatLogic"
)
public class ReactiveStrikePatch2 {
    @SpireInsertPatch(
            rloc=0
    )
    public static void randomizeReactiveStrikes(AbstractPlayer __instance)
    {
        ArrayList<CardGroup> cardsInPlay = new ArrayList<CardGroup>() { };
        cardsInPlay.add(__instance.hand);
        cardsInPlay.add(__instance.drawPile);
        cardsInPlay.add(__instance.discardPile);
        cardsInPlay.add(__instance.exhaustPile);
        for (CardGroup cardGroup : cardsInPlay) {
            for (int j = 0; j < cardGroup.getCardDeck().size(); j++) {
                if (cardGroup.group.get(j).cardID.equals(ReactiveStrike.ID)) {
                    ((ReactiveStrike) (cardGroup.group.get(j))).randomizeDamage();
                }
            }
        }
    }
}
