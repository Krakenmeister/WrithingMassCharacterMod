package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="renderPowerTips"
)
public class FormDisplayTipsPatch {
    @SpireInsertPatch(
            rloc=1,
            localvars = {"tips"}
    )
    public static void DisplayFormTip(@ByRef ArrayList<PowerTip>[] tips, AbstractPlayer __instance)
    {
        tips[0].add(new PowerTip(FormFieldPatch.form.get(__instance).name, FormFieldPatch.form.get(__instance).description));
    }
}