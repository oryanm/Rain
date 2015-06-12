package net.oryanmat.rain;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static net.oryanmat.rain.TetraRain.*;

public class GameScreen extends ScreenAdapter {
    TetraRain rain;

    OrthographicCamera camera;

    long lastDropTime = TimeUtils.nanoTime();
    boolean pinched = false;
    boolean backupUsed = false;
    GameStats stats = new GameStats(0, 0);

    Board board = new Board();
    Shape shape = Shape.spawn();
    Shape nextShape = Shape.spawn();
    Shape backupShape = Shape.empty();

    public GameScreen(TetraRain rain) {
        this.rain = rain;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderGeoShapes();

        rain.batch.setProjectionMatrix(camera.combined);
        rain.batch.begin();
        renderBoard();
        renderShape();
        renderNextShape();
        renderBackupShape();
        renderText();
        rain.batch.end();

        update();
    }

    void renderPaused() {
        renderBoard();
        renderShape();
    }

    void renderGeoShapes() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rain.shapeRenderer.setProjectionMatrix(camera.combined);
        rain.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        rain.renderGrid();
        rain.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderText() {
        rain.layout74.setText(rain.font74, String.format(SCORE_FORMAT, stats.score));
        float x = Gdx.graphics.getWidth() / 2 - rain.layout74.width / 2;
        float y = Gdx.graphics.getHeight() / 2 - rain.layout74.height / 2;
        rain.font74.draw(rain.batch, rain.layout74, x, y + 8 * BLOCK_SIZE);

        String linesText = String.format(LINES_FORMAT, stats.rows);
        rain.font1.draw(rain.batch, linesText, x, y + 9 * BLOCK_SIZE);
    }

    private void renderBoard() {
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                if (board.occupiedAt(column, row)) {
                    rain.renderBlock(board.colorAt(column, row), column, row);
                }
            }
        }
    }

    private void renderShape() {
        for (int column = 0; column < SHAPE_SIZE; column++) {
            for (int row = 0; row < SHAPE_SIZE; row++) {
                if (shape.occupiedAt(column, row)) {
                    rain.renderBlock(shape.color, column + shape.column, row + shape.row);
                }
            }
        }
    }

    private void renderNextShape() {
        renderAuxiliaryShape(nextShape, 1.5f, 3.5f);
    }

    private void renderBackupShape() {
        renderAuxiliaryShape(backupShape, -1.5f, 1.5f);
    }

    private void renderAuxiliaryShape(Shape other, float xOffset, float yOffset) {
        float alpha = 0.3f;
        float scale = 0.5f;
        rain.block.setScale(scale);
        rain.glow.setScale(scale);
        rain.glow.setAlpha(alpha);
        Color color = other.color.cpy();
        color.a = alpha;

        for (int column = 0; column < SHAPE_SIZE; column++) {
            for (int row = 0; row < SHAPE_SIZE; row++) {
                if (other.occupiedAt(column, row)) {
                    float x = ((column + shape.column + xOffset - (column * scale)));
                    float y = (row + shape.row + yOffset - (row * scale));
                    rain.renderBlock(color, x, y);
                }
            }
        }

        rain.block.setScale(1);
        rain.glow.setScale(1);
        rain.glow.setAlpha(1);
    }

    private void update() {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000 * stats.getSpeed()) {
            if (shape.isBlocked(board)) {
                board.setInBoard(shape);
                int fullRows = board.checkForFullRow();
                stats.update(fullRows);
                Sounds.play(fullRows > 0 ? Sounds.CLEAR_WAV : Sounds.DROP_WAV);
                shape = nextShape;
                nextShape = Shape.spawn(stats.getLevel());
                backupUsed = false;
            } else {
                shape.drop();
            }

            lastDropTime = TimeUtils.nanoTime();
        }
    }

    public void reset() {
        lastDropTime = TimeUtils.nanoTime();
        pinched = false;
        backupUsed = false;
        stats = new GameStats(0, 0);

        board = new Board();
        shape = Shape.spawn();
        nextShape = Shape.spawn();
        backupShape = Shape.empty();
    }

    @Override
    public void show() {
        createInput();
    }

    private void createInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new Gesture()));
        multiplexer.addProcessor(new Input());
        Gdx.input.setInputProcessor(multiplexer);
    }

    public class Input extends InputAdapter {
        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            // don't rotate if in the middle of a pinch gesture
            if (!pinched) {
                rotate();
            }

            // when one finger is up, the pinch gesture is done
            pinched = false;

            return true;
        }
    }

    public class Gesture extends GestureDetector.GestureAdapter {
        int rightSum = 0;
        int leftSum = 0;

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            if (deltaX > 0) {
                rightSum += deltaX;
            } else {
                leftSum += -deltaX;
            }

            if (deltaY > Math.abs(deltaX)) {
                if (!shape.isBlocked(board)) {
                    shape.drop();
                    lastDropTime = TimeUtils.nanoTime();
                }
            } else if (rightSum > SWIPE_SPEED) {
                rightSum = 0;
                if (!shape.isBlockedRight(board)) {
                    shape.right();
                    Sounds.play(Sounds.BEEP_WAV);
                }
            } else if (leftSum > SWIPE_SPEED) {
                leftSum = 0;
                if (!shape.isBlockedLeft(board)) {
                    shape.left();
                    Sounds.play(Sounds.BEEP_WAV);
                }
            }

            return true;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            rightSum = 0;
            leftSum = 0;
            return true;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (velocityY < -FLING_SPEED) {
                rain.setScreen(new Menu(rain));
                return true;
            } else if (velocityY > FLING_SPEED) {
                dropUntilBlocked();
                return true;
            }

            return false;
        }

        Vector2 current = new Vector2();
        boolean flipped = false;

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            pinched = true;

            if (!backupUsed) {
                if (gestureStart(initialPointer1)) {
                    current = initialPointer1.cpy();
                    flipped = false;
                } else if (!flipped && (shouldFlip(initialPointer1.x - initialPointer2.x, pointer1.x - pointer2.x) ||
                        shouldFlip(initialPointer1.y - initialPointer2.y, pointer1.y - pointer2.y))) {
                    flipShapes();
                    flipped = true;
                }
            }

            return true;
        }

        private boolean shouldFlip(float a, float b) {
            return !sameSign(a, b);
        }

        private boolean gestureStart(Vector2 initialPointer1) {
            return !current.equals(initialPointer1);
        }

        boolean sameSign(float a, float b) {
            return a * b >= 0.0f;
        }
    }

    void rotate() {
        Orientation orientation = shape.orientation;
        shape.rotate();

        if (shape.isOverlapping(board)) {
            shape.orientation = orientation;
        } else {
            Sounds.play(Sounds.ROTATE_WAV);
        }
    }

    void flipShapes() {
        Shape temp = nextShape;

        if (!backupShape.isEmpty()) {
            temp = backupShape;
        } else {
            nextShape = Shape.spawn(stats.getLevel());
        }

        backupShape = shape;
        shape = temp;
        shape.column = 4;
        shape.row = 17;
        backupUsed = true;
        Sounds.play(Sounds.FLIP_WAV);
    }

    void dropUntilBlocked() {
        while (!shape.isBlocked(board)) {
            shape.drop();
            stats.score += 1;
        }

        lastDropTime = 10000000000l;
    }
}