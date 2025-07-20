package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.patches.infest.InfestCardFieldPatch;

import java.util.List;

public class InfestUtils {
    public static void InfestMonster(AbstractMonster m, WrithingCard c) {
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(m);
        int infestCapacity = GetInfestCapacity(m.maxHealth);
        infestedCards.add(0, c);
        c.setOwner(m);

        if (infestedCards.size() > infestCapacity) {
            WrithingCard oldestCard = infestedCards.remove(infestedCards.size() - 1);
            oldestCard.setOwner(AbstractDungeon.player);
            AbstractDungeon.player.hand.addToTop(oldestCard);
            AbstractDungeon.player.hand.refreshHandLayout();
        }
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
