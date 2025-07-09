package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface OnAnyHealthLossPower {
    void onAnyHealthLoss(DamageInfo info, int damageAmount, boolean wasTempHpLost);
}
