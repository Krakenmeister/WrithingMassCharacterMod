package writhingmasscharactermod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import writhingmasscharactermod.actions.GobbleAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Gobble extends WrithingCard {
    public static final String ID = makeID("Gobble");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Gobble() {
        this(true, true);
    }

    public Gobble(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Gobble(!isBenign, false);
        }

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void setBenign(boolean isBenign) {
        super.setBenign(isBenign);

        if (isBenign) {
            this.target = CardTarget.ENEMY;
        } else {
            this.target = CardTarget.SELF;
        }
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
        addToBot(new VFXAction(new BiteEffect(target.hb.cX, target.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        addToBot(new GobbleAction(target, new DamageInfo(source, damage, this.damageTypeForTurn)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new VFXAction(new BiteEffect(source.hb.cX, source.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        addToBot(new GobbleAction(source, new DamageInfo(source, damage, this.damageTypeForTurn)));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (!isBenign) {
            calculateCardDamage(owner);
        } else {
            if (owner instanceof AbstractPlayer) {
                calculateCardDamage((AbstractCreature) m);
            } else {
                calculateCardDamage(AbstractDungeon.player);
            }
        }
    }
}
