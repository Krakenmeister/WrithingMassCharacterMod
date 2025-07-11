package writhingmasscharactermod.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PowerBottomAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final AbstractMonster target;
    private final CustomCard card;

    public PowerBottomAction(AbstractPlayer player, AbstractMonster target, CustomCard card) {
        this.player = player;
        this.target = target;
        this.card = card;
    }

    @Override
    public void update() {
        card.baseDamage = player.discardPile.size() * 2;
        card.calculateCardDamage(target);

        addToTop(new DamageAction(
                target,
                new DamageInfo(player, card.damage, DamageInfo.DamageType.NORMAL),
                AttackEffect.BLUNT_HEAVY
        ));

        isDone = true;
    }
}
