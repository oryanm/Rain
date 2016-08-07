package net.oryanmat.rain;

import com.badlogic.gdx.utils.ObjectMap;

@FunctionalInterface
public interface Orientation {
    boolean apply(int column, int row);

    Orientation S_HORIZONTAL = (column, row) -> column == 2 && row == 2 ||
            column == 3 && row == 2 ||
            column == 2 && row == 1 ||
            column == 1 && row == 1;

    Orientation S_VERTICAL = (column, row) -> column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 3 && row == 1 ||
            column == 3 && row == 2;

    Orientation Z_HORIZONTAL = (column, row) -> column == 2 && row == 2 ||
            column == 1 && row == 2 ||
            column == 2 && row == 1 ||
            column == 3 && row == 1;

    Orientation Z_VERTICAL = (column, row) -> column == 2 && row == 2 ||
            column == 3 && row == 2 ||
            column == 2 && row == 1 ||
            column == 3 && row == 3;

    Orientation L_RIGHT = (column, row) -> column == 2 && row == 2 ||
            column == 2 && row == 1 ||
            column == 2 && row == 3 ||
            column == 3 && row == 1;

    Orientation L_DOWN = (column, row) -> column == 2 && row == 2 ||
            column == 1 && row == 2 ||
            column == 3 && row == 2 ||
            column == 1 && row == 1;

    Orientation L_LEFT = (column, row) -> column == 2 && row == 2 ||
            column == 2 && row == 1 ||
            column == 2 && row == 3 ||
            column == 1 && row == 3;

    Orientation L_UP = (column, row) -> column == 2 && row == 2 ||
            column == 1 && row == 2 ||
            column == 3 && row == 2 ||
            column == 3 && row == 3;

    Orientation J_DOWN = (column, row) -> column == 2 && row == 2 ||
            column == 1 && row == 2 ||
            column == 3 && row == 2 ||
            column == 3 && row == 1;

    Orientation J_LEFT = (column, row) -> column == 2 && row == 3 ||
            column == 2 && row == 2 ||
            column == 2 && row == 1 ||
            column == 1 && row == 1;

    Orientation J_UP = (column, row) -> column == 1 && row == 3 ||
            column == 1 && row == 2 ||
            column == 2 && row == 2 ||
            column == 3 && row == 2;

    Orientation J_RIGHT = (column, row) -> column == 2 && row == 1 ||
            column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 3 && row == 3;

    Orientation T_DOWN = (column, row) -> column == 2 && row == 2 ||
            column == 1 && row == 2 ||
            column == 3 && row == 2 ||
            column == 2 && row == 1;

    Orientation T_LEFT = (column, row) -> column == 2 && row == 1 ||
            column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 1 && row == 2;

    Orientation T_UP = (column, row) -> column == 1 && row == 2 ||
            column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 3 && row == 2;

    Orientation T_RIGHT = (column, row) -> column == 2 && row == 1 ||
            column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 3 && row == 2;

    Orientation I_HORIZONTAL = (column, row) -> column == 0 && row == 2 ||
            column == 1 && row == 2 ||
            column == 2 && row == 2 ||
            column == 3 && row == 2;

    Orientation I_VERTICAL = (column, row) -> column == 2 && row == 1 ||
            column == 2 && row == 2 ||
            column == 2 && row == 3 ||
            column == 2 && row == 4;

    Orientation O = (column, row) -> column == 1 && row == 1 ||
            column == 1 && row == 2 ||
            column == 2 && row == 1 ||
            column == 2 && row == 2;

    Orientation NULL = (column, row) -> false;

    ObjectMap<Orientation, Orientation> ROTATION = new ObjectMap<Orientation, Orientation>() {{
        put(S_HORIZONTAL, S_VERTICAL);
        put(S_VERTICAL, S_HORIZONTAL);

        put(Z_HORIZONTAL, Z_VERTICAL);
        put(Z_VERTICAL, Z_HORIZONTAL);

        put(L_RIGHT, L_DOWN);
        put(L_DOWN, L_LEFT);
        put(L_LEFT, L_UP);
        put(L_UP, L_RIGHT);

        put(J_RIGHT, J_DOWN);
        put(J_DOWN, J_LEFT);
        put(J_LEFT, J_UP);
        put(J_UP, J_RIGHT);

        put(T_RIGHT, T_DOWN);
        put(T_DOWN, T_LEFT);
        put(T_LEFT, T_UP);
        put(T_UP, T_RIGHT);

        put(I_HORIZONTAL, I_VERTICAL);
        put(I_VERTICAL, I_HORIZONTAL);

        put(O, O);
    }};
}
