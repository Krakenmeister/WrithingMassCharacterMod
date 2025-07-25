package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.BleedPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Bloodbath extends WrithingCard {
    public static final String ID = makeID("Bloodbath");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ALL,
            0
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int SELF_VULNERABLE = 1;
    private static final int UPG_SELF_VULNERABLE = 0;

    public Bloodbath() {
        this(true, true);
    }

    public Bloodbath(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Bloodbath(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setCustomVar("selfvulnerable", SELF_VULNERABLE, UPG_SELF_VULNERABLE);
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
        addToBot(new ApplyPowerAction(source, source, new VulnerablePower(source, customVar("selfvulnerable"), false)));

        if (owner instanceof AbstractPlayer) {
            for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, source, new BleedPower(monster, this.magicNumber), this.magicNumber));
                }
            }
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BleedPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        if (owner instanceof AbstractPlayer) {
            for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, source, new VulnerablePower(source, customVar("selfvulnerable"), false)));
                }
            }
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, source, new VulnerablePower(AbstractDungeon.player, customVar("selfvulnerable"), false)));
        }

        addToBot(new ApplyPowerAction(source, source, new BleedPower(source, this.magicNumber), this.magicNumber));
    }
}
