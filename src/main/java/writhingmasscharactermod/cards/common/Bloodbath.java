package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.BleedPower;
import writhingmasscharactermod.util.CardStats;

public class Bloodbath extends BaseCard {
    public static final String ID = makeID("Bloodbath");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int SELF_VULNERABLE = 1;
    private static final int UPG_SELF_VULNERABLE = 0;

    public Bloodbath() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setCustomVar("selfvulnerable", SELF_VULNERABLE, UPG_SELF_VULNERABLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, customVar("selfvulnerable"), false)));

            for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, p, new BleedPower(monster, this.magicNumber), this.magicNumber));
                }
            }
        }
    }
}
