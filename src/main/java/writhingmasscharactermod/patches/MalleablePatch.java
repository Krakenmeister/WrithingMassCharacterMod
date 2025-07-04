package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.powers.MalleablePower;
import writhingmasscharactermod.powers.ResiliencePower;

@SpirePatch2(
        clz= MalleablePower.class,
        method="onAttacked"
)
public class MalleablePatch {
    @SpireInsertPatch(
            rloc=7
    )
    public static void addResilience(MalleablePower __instance, Object[] __args)
    {
        if (__instance.owner.hasPower(ResiliencePower.POWER_ID)) {
            __instance.amount += __instance.owner.getPower(ResiliencePower.POWER_ID).amount;
        }
    }
}
