package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.SynapticBurstAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class SynapticBurst extends WrithingCard {
    public static final String ID = makeID("SynapticBurst");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public SynapticBurst() {
        super(ID, info, true);

        setInert(true);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.target = CardTarget.ALL_ENEMY;
        }

        super.upgrade();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (upgraded) {
            return cardStrings.UPGRADE_DESCRIPTION;
        }
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        if (!upgraded) {
            addToBot(new SynapticBurstAction((AbstractMonster) target));
        } else {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    addToBot(new SynapticBurstAction(m));
                }
            }
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
