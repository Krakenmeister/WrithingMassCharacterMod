package writhingmasscharactermod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Infection extends WrithingCard {
    public static final String ID = makeID("Infection");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 2;

    private static final int INFECTION_BOON = 1;
    private static final int UPG_INFECTION_BOON = 1;

    public Infection() {
        this(true, true);
    }

    public Infection(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Infection(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("infectionboon", INFECTION_BOON, UPG_INFECTION_BOON);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, source, new PoisonPower(target, source, magicNumber), magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot(new VFXAction(source, new VerticalAuraEffect(Color.FOREST, source.hb.cX, source.hb.cY), 0.0F));

        if (owner instanceof AbstractPlayer) {
            addToBot(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, source, new RegenPower(target, customVar("infectionboon")), customVar("infectionboon"), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(source, new VerticalAuraEffect(Color.GREEN, source.hb.cX, source.hb.cY), 0.0F));

        if (owner instanceof AbstractPlayer) {
            addToBot(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
        }
    }
}
