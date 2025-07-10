package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface OnLoseMonsterHpPower {
    int onLoseMonsterHp(DamageInfo info, int damageAmount);
}
