package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import writhingmasscharactermod.actions.DamageEveryoneAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.BleedPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.GeneralUtils;
import writhingmasscharactermod.util.WrithingCard;

public class Debauchery extends WrithingCard {
    public static final String ID = makeID("Debauchery");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL,
            1
    );

    private static final int MAGIC_NUMBER = 8;
    private static final int UPG_MAGIC_NUMBER = 3;

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public Debauchery() {
        this(true, true);
    }

    public Debauchery(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        isMultiDamage = true;

        if (previewCards) {
            cardsToPreview = new Debauchery(!isBenign, false);
        }

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    protected String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        calculateCardDamage((AbstractCreature) null);
        addToBot(new DamageEveryoneAction(source, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new HealAction(source, source, magicNumber));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        calculateCardDamage((AbstractCreature) null);
        addToBot(new DamageEveryoneAction(source, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new LoseHPAction(source, source, magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        calculateCardDamage((AbstractCreature) m);
    }
}
