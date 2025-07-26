package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import writhingmasscharactermod.util.WrithingCard;

public class PowerBottomAction extends AbstractGameAction {
    private final WrithingCard card;

    public PowerBottomAction(AbstractCreature source, AbstractCreature target, WrithingCard card) {
        this.source = source;
        this.target = target;
        this.card = card;
    }

    @Override
    public void update() {
        card.baseDamage = AbstractDungeon.player.discardPile.size() * 2;
        card.calculateCardDamage(target);

        addToTop(new DamageAction(
                target,
                new DamageInfo(source, card.damage, DamageInfo.DamageType.NORMAL),
                AttackEffect.BLUNT_HEAVY
        ));

        isDone = true;
    }
}
