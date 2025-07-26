package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import org.lwjgl.util.vector.Vector2f;
import writhingmasscharactermod.actions.InfestedCardUseAction;
import writhingmasscharactermod.cards.uncommon.Proliferate;
import writhingmasscharactermod.patches.infest.InfestCardFieldPatch;
import writhingmasscharactermod.powers.CannibalismPower;

import java.util.ArrayList;
import java.util.List;

public class InfestUtils {
    public static void InfestMonster(AbstractMonster m, WrithingCard c) {
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(m);
        int infestCapacity = GetInfestCapacity(m.maxHealth);
        infestedCards.add(0, c);
        c.setOwner(m);

        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnInfestCardPower) {
                ((OnInfestCardPower) p).OnInfestCard(c);
            }
        }

        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SNECKO_GLARE"));
        Vector2f newInfestedCardPosition = GetInfestedCardPositions(m).get(0);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(m, new IntimidateEffect(newInfestedCardPosition.x, newInfestedCardPosition.y), 0.5F));

        if (infestedCards.size() > infestCapacity) {
            WrithingCard oldestCard = infestedCards.remove(infestedCards.size() - 1);

            if (AbstractDungeon.player.hasPower(CannibalismPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new InfestedCardUseAction(m, oldestCard, true));
            } else {
                oldestCard.setOwner(AbstractDungeon.player);
                if (oldestCard instanceof Proliferate) {
                    AbstractDungeon.player.discardPile.addToTop(oldestCard);
                    AbstractDungeon.player.discardPile.refreshHandLayout();
                } else {
                    AbstractDungeon.player.hand.addToTop(oldestCard);
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
            }
        }

        if (c instanceof OnInfestedCard) {
            ((OnInfestedCard) c).OnInfested();
        }
    }

    public static void ExhaustInfestedCard(AbstractMonster m, WrithingCard c, boolean sendToExhaustPile) {
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        if (sendToExhaustPile) {
            AbstractDungeon.player.exhaustPile.addToTop(c);
            AbstractDungeon.player.exhaustPile.refreshHandLayout();
        }
        InfestCardFieldPatch.infestedCards.get(m).remove(c);
    }

    public static int GetNumMalignant(AbstractMonster m) {
        int numMalignant = 0;
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(m);

        for (WrithingCard infestedCard : infestedCards) {
            if (!infestedCard.isBenign) {
                numMalignant++;
            }
        }

        return numMalignant;
    }
    
    public static List<Vector2f> GetInfestedCardPositions(AbstractMonster m) {
        int capacity = GetInfestCapacity(m.maxHealth);
        List<Vector2f> cardPositions = new ArrayList<>();

        float cardWidth = GetInfestedCardWidth();
        float cardHeight = GetInfestedCardHeight();
        
        switch(capacity) {
            case 1:
                cardPositions.add(new Vector2f(m.hb.cX, m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                break;
            case 2:
                cardPositions.add(new Vector2f(m.hb.cX - (cardWidth * 2 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX + (cardWidth * 2 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                break;
            case 3:
                cardPositions.add(new Vector2f(m.hb.cX - (cardWidth * 4 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX, m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX + (cardWidth * 4 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                break;
            case 4:
                cardPositions.add(new Vector2f(m.hb.cX - (cardWidth * 2), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX - (cardWidth * 2 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX + (cardWidth * 2 / 3), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                cardPositions.add(new Vector2f(m.hb.cX + (cardWidth * 2), m.hb.cY + (m.hb.height / 2.0F) + (150.0F * Settings.scale)));
                break;
        }

        return cardPositions;
    }

    public static float GetInfestedCardWidth() {
        return 100F * Settings.scale;
    }

    public static float GetInfestedCardHeight() {
        return 139F * Settings.scale;
    }

    public static int GetInfestCapacity(int maxHP) {
        if (maxHP < 55) {
            return 1;
        } else if (maxHP < 110) {
            return 2;
        } else if (maxHP < 200) {
            return 3;
        } else {
            return 4;
        }
//        return 2;
    }
}
