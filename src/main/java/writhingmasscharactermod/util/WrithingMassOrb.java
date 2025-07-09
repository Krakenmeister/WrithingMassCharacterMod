package writhingmasscharactermod.util;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.lwjgl.util.vector.Vector2f;

import static writhingmasscharactermod.WrithingMassCharacterMod.characterPath;

public class WrithingMassOrb extends CustomEnergyOrb {
    private static final String emptyVfxPath = characterPath("energyorb/emptyVfx.png");
    private static final float ORB_IMG_SCALE;

    private static final Texture cover = ImageMaster.loadImage(characterPath("energyorb/cover.png"));
    private static final Texture[] layers = new Texture[5];

    private Vector2f eyeOffset = new Vector2f(0f, 0f);
    private Vector2f destinationOffset = new Vector2f(0f, 0f);
    private Vector2f originOffset = null;
    private float timeUntilNewDestination = 0f;
    private float t = 0f;
    private float eyeSpeed = 0.5f;

    public WrithingMassOrb() {
        super(null, null, null);

        orbVfx = ImageMaster.loadImage(emptyVfxPath);

        layers[0] = ImageMaster.loadImage(characterPath("energyorb/layer0.png"));
        layers[1] = ImageMaster.loadImage(characterPath("energyorb/layer1.png"));
        layers[2] = ImageMaster.loadImage(characterPath("energyorb/layer2.png"));
        layers[3] = ImageMaster.loadImage(characterPath("energyorb/layer3.png"));
        layers[4] = ImageMaster.loadImage(characterPath("energyorb/layer4.png"));
    }

    @Override
    public void updateOrb(int energyCount) {
        float deltaT = Gdx.graphics.getDeltaTime();
        if (timeUntilNewDestination <= 0f) {
            if (originOffset == null) {
                originOffset = new Vector2f(eyeOffset);
                destinationOffset = new Vector2f((float)Math.random() * 1.5f - 0.75f, (float)Math.random() * 1.5f - 0.75f);
                t = 0f;
            } else if (t >= 1f) {
                eyeOffset = new Vector2f(destinationOffset);
                originOffset = null;
                timeUntilNewDestination = (float)Math.random() * 5f + 1f;
            } else {
                t += deltaT * eyeSpeed * (energyCount + 0.25f);
                eyeOffset = new Vector2f(
                        originOffset.x + (t * (destinationOffset.x - originOffset.x)),
                        originOffset.y + (t * (destinationOffset.y - originOffset.y))
                );
            }
        } else {
            timeUntilNewDestination -= deltaT;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.draw(layers[0], current_x - 64.0F + (eyeOffset.x * 128f * 0),     current_y - 64.0F + (eyeOffset.y * 128f * 0),     64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.draw(layers[1], current_x - 64.0F + (eyeOffset.x * 128f * 0.03f), current_y - 64.0F + (eyeOffset.y * 128f * 0.03f), 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.draw(layers[2], current_x - 64.0F + (eyeOffset.x * 128f * 0.06f), current_y - 64.0F + (eyeOffset.y * 128f * 0.06f), 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.draw(layers[3], current_x - 64.0F + (eyeOffset.x * 128f * 0.1f),  current_y - 64.0F + (eyeOffset.y * 128f * 0.1f),  64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.draw(layers[4], current_x - 64.0F + (eyeOffset.x * 128f * 0.13f), current_y - 64.0F + (eyeOffset.y * 128f * 0.13f), 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.draw(cover, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
    }

    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;
    }
}
