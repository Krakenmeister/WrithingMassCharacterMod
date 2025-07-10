package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.GeneralUtils;

public class Gush extends BaseCard {
    public static final String ID = makeID("Gush");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int MAGIC_NUMBER = 4;

    private static final int GUSH_DRAW = 2;
    private static final int UPG_GUSH_DRAW = 1;

    public Gush() {
        super(ID, info);

        setMagic(MAGIC_NUMBER);
        setCustomVar("gushdraw", GUSH_DRAW, UPG_GUSH_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DrawCardAction(customVar("gushdraw")));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = GeneralUtils.applyDamageCalculations(AbstractDungeon.player, DamageInfo.DamageType.NORMAL, baseMagicNumber);
        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        magicNumber = GeneralUtils.applyDamageCalculations(AbstractDungeon.player, DamageInfo.DamageType.NORMAL, baseMagicNumber);
        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }
}
