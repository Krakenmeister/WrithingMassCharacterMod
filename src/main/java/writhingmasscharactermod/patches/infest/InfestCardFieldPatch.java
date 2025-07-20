package writhingmasscharactermod.patches.infest;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.util.WrithingCard;

import java.util.ArrayList;
import java.util.List;


@SpirePatch2(
        clz= AbstractMonster.class,
        method= SpirePatch.CLASS
)
public class InfestCardFieldPatch {
    public static SpireField<List<WrithingCard>> infestedCards = new SpireField<>(ArrayList::new);
}