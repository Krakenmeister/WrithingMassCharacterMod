package writhingmasscharactermod.patches.realtemphp;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class RealTempHPField {
    public static SpireField<Integer> realTempHp = new SpireField<>(() -> 0);
}
