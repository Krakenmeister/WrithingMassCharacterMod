package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import writhingmasscharactermod.actions.MutateAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Brat extends WrithingCard {
    public static final String ID = makeID("Brat");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 5;

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Brat() {
        super(ID, info, true);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new GainBlockAction(source, block));
        addToBot(new ApplyPowerAction(target, source, new VigorPower(target, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
