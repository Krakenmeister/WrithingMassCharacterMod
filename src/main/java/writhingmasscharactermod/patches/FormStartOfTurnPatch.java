package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="applyStartOfTurnRelics"
)
public class FormStartOfTurnPatch {
    @SpireInsertPatch(
            rloc=0
    )
    public static void StartOfTurnLogic(AbstractPlayer __instance)
    {
        FormFieldPatch.form.get(__instance).atStartOfTurn();
    }
}