package net.oryanmat.rain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class TetraRain extends Game{
    public static final int COLUMNS = 10;
    public static final int ROWS = 20;
    public static final int SHAPE_SIZE = 5;

    public static int BLOCK_SIZE = 24;
    public static int OFFSET = 0;
    public static final int TEXTURE_SPRITE_SIZE = 24;

    public static final String SCORE_FORMAT = "%1$06d";
    public static final String LINES_FORMAT = "LINES - %1$03d";

    public static final Color veryLightGrey = new Color(1, 1, 1, 0.1f);
    public static final Color lightGrey = new Color(1, 1, 1, 0.3f);
    public static final Color halfGrey = new Color(.3f, .3f, .3f, .7f);
    public static final Color black08 = new Color(0, 0, 0, 0.8f);

    public static final double SWIPE_SPEED = 24 * 1.6;
    public static final double FLING_SPEED = 1500;

    Skin skin;
    SpriteBatch batch;
    Texture img;
    Sprite block;
    Sprite glow;

    ShapeRenderer shapeRenderer;

    BitmapFont font74;
    BitmapFont font1;
    BitmapFont.TextBounds bounds;

    @Override
    public void create() {
        createGraphics();
        createFonts();
        Sounds.play(Sounds.DROP_WAV);

        /*skin.add("default", font74);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(
                new SpriteDrawable(block), new SpriteDrawable(glow), new SpriteDrawable(glow), font74);
        skin.add("default", style);*/


        this.setScreen(new GameScreen(this));
    }

    private void createGraphics() {
        BLOCK_SIZE = Math.min(Gdx.graphics.getHeight() / ROWS, Gdx.graphics.getWidth() / COLUMNS);
        OFFSET = (Gdx.graphics.getWidth() - (COLUMNS * BLOCK_SIZE)) / 2;

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("block.png"));
        block = new Sprite(img, TEXTURE_SPRITE_SIZE, TEXTURE_SPRITE_SIZE);
        block.setSize(BLOCK_SIZE, BLOCK_SIZE);
        glow = new Sprite(img, TEXTURE_SPRITE_SIZE, 0, TEXTURE_SPRITE_SIZE, TEXTURE_SPRITE_SIZE);
        glow.setSize(BLOCK_SIZE, BLOCK_SIZE);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(veryLightGrey);
    }

    private void createFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (BLOCK_SIZE * 1.74);
        parameter.characters = "PausedLINES:1234567890";
        font74 = generator.generateFont(parameter);
        font74.setColor(halfGrey);
        bounds = font74.getBounds(String.format(SCORE_FORMAT, 0));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (BLOCK_SIZE * 1.1);
        parameter.characters = "LINES-1234567890";
        font1 = generator.generateFont(parameter);
        font1.setColor(halfGrey);

        generator.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    void renderBlock(Color color, float column, float row) {
        float x = MathUtils.floor((column * BLOCK_SIZE) + OFFSET);
        float y = MathUtils.floor(row * BLOCK_SIZE);
        block.setColor(color);
        block.setPosition(x, y);
        block.draw(batch);
        glow.setPosition(x, y);
        glow.draw(batch);
    }

    void renderGrid() {
        shapeRenderer.line(OFFSET, Gdx.graphics.getHeight(), OFFSET, 0, veryLightGrey, lightGrey);
        int x2 = Gdx.graphics.getWidth() - OFFSET + 1;
        shapeRenderer.line(x2, Gdx.graphics.getHeight(), x2, 0, veryLightGrey, lightGrey);

        for (int column = 1; column < COLUMNS; column++) {
            int x = column * BLOCK_SIZE + OFFSET + 1;
            shapeRenderer.line(x, Gdx.graphics.getHeight(), x, 0);
        }

        for (int row = 0; row < ROWS + 1; row++) {
            int y = row * BLOCK_SIZE;
            shapeRenderer.line(0, y, Gdx.graphics.getWidth(), y);
        }
    }

    @Override
    public void dispose() {
        skin.dispose();
        img.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        Sounds.dispose();
        super.dispose();
    }
}
