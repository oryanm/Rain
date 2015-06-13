package net.oryanmat.rain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static net.oryanmat.rain.TetraRain.*;

public class Menu extends ScreenAdapter {
    TetraRain rain;
    OrthographicCamera camera;
    GameScreen gameScreen;
    GameStats stats;
    boolean gameOver;

    Stage stage;

    public Menu(TetraRain rain, GameStats stats, boolean gameOver) {
        this.rain = rain;
        this.stats = stats;
        this.gameOver = gameOver;
        gameScreen = (GameScreen) rain.getScreen();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new Gesture()));
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        stage.addActor(getTable());
    }

    private Table getTable() {
        Button restartButton = new TextButton("Restart", rain.skin);
        restartButton.addListener(getResetListener());
        restartButton.setColor(lightGrey);

        Label title = getLabel(gameOver ? "Game Over" : "Paused", BIG_FONT, lightGrey, Align.center);

        Table table = new Table(rain.skin);
        table.setFillParent(true);
        table.defaults().height(BLOCK_SIZE).expandX().pad(BLOCK_SIZE * 0.5f).fill();
        table.add(title).colspan(2).row();
        table.add(getLabel("Lines:", DEFAULT_FONT, halfGrey, Align.right)).uniform();
        table.add(String.valueOf(stats.rows), DEFAULT_FONT, halfGrey).uniform().row();
        table.add(getLabel("Score:", DEFAULT_FONT, halfGrey, Align.right));
        table.add(String.valueOf(stats.score), DEFAULT_FONT, halfGrey).row();
        table.add(restartButton).colspan(2);
        return table;
    }

    Label getLabel(String text, String font, Color color, int align) {
        Label.LabelStyle titleStyle = new Label.LabelStyle(rain.skin.getFont(font), color);
        Label title = new Label(text, titleStyle);
        title.setAlignment(align);
        return title;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderPausedGame();
        renderShade();

        stage.act(delta);
        stage.draw();
    }

    private void renderPausedGame() {
        rain.batch.setProjectionMatrix(camera.combined);
        rain.batch.begin();
        gameScreen.renderPaused();
        rain.batch.end();
    }

    private void renderShade() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rain.shapeRenderer.setProjectionMatrix(camera.combined);
        rain.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        rain.shapeRenderer.setColor(black08);
        rain.shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rain.shapeRenderer.setColor(veryLightGrey);
        rain.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private InputListener getResetListener() {
        return new InputListener() {
            // todo: don't reset when player moved finger away from button
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.reset();
                closeScreen();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
    }

    class Gesture extends GestureDetector.GestureAdapter {
        // todo: ignore buttons
        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (!gameOver && velocityY < -FLING_SPEED) {
                return closeScreen();
            }

            return super.fling(velocityX, velocityY, button);
        }
    }

    private boolean closeScreen() {
        Menu.this.rain.setScreen(gameScreen);
        dispose();
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
