package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.CopyDiscardPileToHandAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class UnceasingBottom extends WrithingCard {
    public static final String ID = makeID("UnceasingBottom");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;

    public UnceasingBottom() {
        this(true);
    }

    public UnceasingBottom(boolean isBenign) {
        super(ID, info, true);

        setBenign(isBenign);

        setDamage(DAMAGE, UPG_DAMAGE);

        setExhaust(true, false);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (owner instanceof AbstractPlayer) {
            addToBot(new CopyDiscardPileToHandAction());
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
