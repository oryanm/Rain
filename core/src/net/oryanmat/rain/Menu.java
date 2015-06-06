package net.oryanmat.rain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static net.oryanmat.rain.TetraRain.*;

public class Menu extends ScreenAdapter {
    TetraRain rain;
    OrthographicCamera camera;
    GameScreen gameScreen;

    Stage stage;

    public Menu(TetraRain rain) {
        this.rain = rain;
        gameScreen = (GameScreen) rain.getScreen();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new Gesture()));
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Button restartButton = new TextButton("Restart", rain.skin);
        restartButton.addListener(getResetListener());

        Slider sfxSlider = new Slider(0, 1, 0.05f, false, rain.skin);

        Table table = new Table(rain.skin);
        table.setFillParent(true);
        table.defaults().height(BLOCK_SIZE * 2).expandX().pad(BLOCK_SIZE * 0.5f).fill();
        table.add(restartButton).colspan(2);
        table.row();
        table.add("Volume:");
        table.add(sfxSlider);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderPausedGame();
        renderShade();

        rain.batch.setProjectionMatrix(camera.combined);
        rain.batch.begin();
        rain.layout74.setText(rain.font74, "Paused");
        float x = Gdx.graphics.getWidth() / 2 - rain.layout74.width / 2;
        float y = Gdx.graphics.getHeight() / 2 - rain.layout74.height / 2;
        rain.font74.draw(rain.batch, rain.layout74, x, y * 1.5f);
        rain.batch.end();

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
            if (velocityY < -FLING_SPEED) {
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
