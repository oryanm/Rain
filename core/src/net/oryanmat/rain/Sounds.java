package net.oryanmat.rain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class Sounds {
    public static final String BEEP_WAV = "beep.wav";
    public static final String ROTATE_WAV = "rotate.wav";
    public static final String DROP_WAV = "drop.wav";
    public static final String CLEAR_WAV = "clear.wav";
    public static final String FLIP_WAV = "flip.wav";

    static ObjectMap<String, Sound> sounds = new ObjectMap<String, Sound>();
    static float volume = 0.1f;

    static {
        sounds.put(BEEP_WAV, Gdx.audio.newSound(Gdx.files.internal(BEEP_WAV)));
        sounds.put(ROTATE_WAV, Gdx.audio.newSound(Gdx.files.internal(ROTATE_WAV)));
        sounds.put(DROP_WAV, Gdx.audio.newSound(Gdx.files.internal(DROP_WAV)));
        sounds.put(CLEAR_WAV, Gdx.audio.newSound(Gdx.files.internal(CLEAR_WAV)));
        sounds.put(FLIP_WAV, Gdx.audio.newSound(Gdx.files.internal(FLIP_WAV)));
    }

    public static void play(String sound) {
        sounds.get(sound).play(volume);
    }

    public static void dispose() {
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
    }
}
