package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import org.lwjgl.util.vector.Vector2f;
import writhingmasscharactermod.patches.infest.InfestCardFieldPatch;

import java.util.ArrayList;
import java.util.List;

public class InfestUtils {
    public static void InfestMonster(AbstractMonster m, WrithingCard c) {
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(m);
        int infestCapacity = GetInfestCapacity(m.maxHealth);
        infestedCards.add(0, c);
        c.setOwner(m);

        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SNECKO_GLARE"));
        Vector2f newInfestedCardPosition = GetInfestedCardPositions(m).get(0);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(m, new IntimidateEffect(newInfestedCardPosition.x, newInfestedCardPosition.y), 0.5F));

        if (infestedCards.size() > infestCapacity) {
            WrithingCard oldestCard = infestedCards.remove(infestedCards.size() - 1);
            oldestCard.setOwner(AbstractDungeon.player);
            AbstractDungeon.player.hand.addToTop(oldestCard);
            AbstractDungeon.player.hand.refreshHandLayout();
        }
    }

    public static void ExhaustInfestedCard(AbstractMonster m, WrithingCard c) {
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        InfestCardFieldPatch.infestedCards.get(m).remove(c);
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
