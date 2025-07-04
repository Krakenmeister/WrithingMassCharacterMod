package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.MalleablePower;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.powers.ResiliencePower;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method= SpirePatch.CLASS
)
public class FormFieldPatch {
    public static SpireField<AbstractForm> form = new SpireField<>(() -> new HighForm());
}