package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

// Make bleed damage work with poison
@SpirePatch2(
        clz= PoisonLoseHpAction.class,
        method="update"
)
public class PoisonBleedPatch {
    @SpireInstrumentPatch
    public static ExprEditor patchDamageCall()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {
                /* We only care about   this.target.damage(...)
                 * So we check the receiver’s class *and* the method name.
                 */
                if (m.getClassName().equals(AbstractCreature.class.getName())
                        && m.getMethodName().equals("damage"))
                {
                    /*
                     * $0  – the object the original call was on  (== this.target)
                     * this – still refers to the current LoseHPAction instance,
                     *        so we can access its fields (source, amount, target).
                     *
                     * Because AbstractCreature#damage returns void, there is no $_ assignment.
                     * Fully-qualified class names keep the class loader happy.
                     */
                    m.replace(
                            "{                                                   " +
                                    "    com.megacrit.cardcrawl.cards.DamageInfo info =  " +
                                    "        new com.megacrit.cardcrawl.cards.DamageInfo(" +
                                    "                this.source,                        " +
                                    "                this.amount,                        " +
                                    "                com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS);" +
                                    "    info.applyPowers(this.target, this.target);     " +
                                    "    $0.damage(info);                                " +
                                    "}" );
                }
            }
        };
    }
}
