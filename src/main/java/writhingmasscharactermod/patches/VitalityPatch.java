package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;
import writhingmasscharactermod.util.OnHealCard;

@SpirePatch2(
        clz= AbstractCreature.class,
        method="heal",
        paramtypez = { int.class, boolean.class }
)
public class VitalityPatch {
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static void Insert(AbstractCreature __instance, @ByRef int[] healAmount, boolean showEffect) {
        if (__instance.isPlayer) {
            for(AbstractCard card : ((AbstractPlayer)__instance).hand.group) {
                if (card instanceof OnHealCard) {
                    healAmount[0] = ((OnHealCard)card).onHeal(healAmount[0]);
                }
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentHealth");

            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);

            if (lines.length == 0) {
                throw new RuntimeException("Could not find access to currentHealth!");
            }

            return new int[]{ lines[0] - 1 };
        }
    }
}
