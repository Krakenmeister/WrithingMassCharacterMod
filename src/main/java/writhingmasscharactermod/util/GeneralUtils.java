package writhingmasscharactermod.util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GeneralUtils {
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    public static String removePrefix(String ID) {
        return ID.substring(ID.indexOf(":") + 1);
    }

    public static int applyDamageCalculations(AbstractCreature target, DamageInfo.DamageType type, int base) {
        float tmp = base;
        for (AbstractPower p : target.powers) {
            tmp = p.atDamageReceive(tmp, type);
        }
        for (AbstractPower p : target.powers) {
            tmp = p.atDamageFinalReceive(tmp, type);
        }
        if (tmp < 0.0F) {
            tmp = 0.0F;
        }
        return MathUtils.floor(tmp);
    }
}
