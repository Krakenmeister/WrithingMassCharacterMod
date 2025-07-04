package writhingmasscharactermod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.MalleablePower;
import writhingmasscharactermod.powers.ResiliencePower;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="render"
)
public class FormRenderPatch {
    @SpireInsertPatch(
            rloc=0
    )
    public static void RenderForm(AbstractPlayer __instance, SpriteBatch sb)
    {
        FormFieldPatch.form.get(__instance).render(sb);
    }
}
