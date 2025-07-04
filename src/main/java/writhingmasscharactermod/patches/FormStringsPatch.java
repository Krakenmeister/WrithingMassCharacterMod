package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.HighForm;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


@SpirePatch2(
        clz= LocalizedStrings.class,
        method= SpirePatch.CLASS
)
public class FormStringsPatch {
    //public static SpireField<AbstractForm> form = new SpireField<>(() -> new HighForm());


    public static SpireField<Map<String, StanceStrings>> form = new SpireField<>(() -> new Map<String, StanceStrings>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public StanceStrings get(Object key) {
            return null;
        }

        @Override
        public StanceStrings put(String key, StanceStrings value) {
            return null;
        }

        @Override
        public StanceStrings remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends StanceStrings> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return Collections.emptySet();
        }

        @Override
        public Collection<StanceStrings> values() {
            return Collections.emptyList();
        }

        @Override
        public Set<Entry<String, StanceStrings>> entrySet() {
            return Collections.emptySet();
        }
    });
}





