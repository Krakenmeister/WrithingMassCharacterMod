package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SwirlyBloodEffect;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class Scarhide extends BaseCard {
    public static final String ID = makeID("Scarhide");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Scarhide() {
        super(ID, info);

        try {
            setBlock(AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
        } catch (Exception e) {
            setBlock(0);
        }

        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new SwirlyBloodEffect(m.hb.cX, m.hb.cY)));
        }

        addToBot(new GainBlockAction(p, p, block));
    }

    public void externalSetBlock(int block) {
        setBlock(block);
    }
}
