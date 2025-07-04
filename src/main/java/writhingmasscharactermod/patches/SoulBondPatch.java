package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import writhingmasscharactermod.cards.common.ReactiveStrike;
import writhingmasscharactermod.cards.effects.ModularOfferingEffect;
import writhingmasscharactermod.powers.SoulBondPower;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="damage"
)
public class SoulBondPatch {

    @SpireInsertPatch(
            rloc=62
    )
    public static void applySoulBondDamage(AbstractPlayer __instance, int ___damageAmount)
    {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.hasPower(SoulBondPower.POWER_ID) && ___damageAmount > 0) {
                DamageInfo soulBondDamage = new DamageInfo(__instance, ___damageAmount, DamageInfo.DamageType.HP_LOSS);
                soulBondDamage.applyPowers(__instance, monster);
                for (int i = 0; i < monster.getPower(SoulBondPower.POWER_ID).amount; i++) {
                    monster.addToTop(new DamageAction(monster, soulBondDamage, AbstractGameAction.AttackEffect.NONE));
                }

                if (Settings.FAST_MODE) {
                    monster.addToTop(new VFXAction(new ModularOfferingEffect(monster), 0.1F));
                } else {
                    monster.addToTop(new VFXAction(new ModularOfferingEffect(monster), 0.5F));
                }
            }
        }
    }
}
