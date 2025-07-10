package writhingmasscharactermod.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.forms.AbstractForm;

public class FormUtils {
    public static void playerSwitchedForm(AbstractPlayer player) {

    }

    public static void onFormChange(AbstractPlayer player, AbstractForm form) {
        for (AbstractPower power : player.powers) {
            if (power instanceof FormChangePower) {
                ((FormChangePower)power).onFormChange(form);
            }
        }
    }

//    public static String getFormName(LocalizedStrings stringMap, String formName) {
//        return (FormStrings) stringMap.get(formName);
//        public StanceStrings getStanceString(String stanceName) {
//            return (StanceStrings)stance.get(stanceName);
//        }
//    }
}
