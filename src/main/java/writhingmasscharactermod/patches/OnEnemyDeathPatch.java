package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnEnemyDeathPower;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez = { }
)
public class OnEnemyDeathPatch {
    @SpirePostfixPatch
    public static void onDie(AbstractMonster __instance) {
        if (!__instance.isDying || __instance.halfDead || __instance.hasPower("Minion")) return;

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof OnEnemyDeathPower) {
                ((OnEnemyDeathPower) power).onEnemyDeath(__instance);
            }
        }
    }
}