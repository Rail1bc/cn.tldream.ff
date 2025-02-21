package cn.tldream.ff.components;

import cn.tldream.ff.game.components.PositionComponent;
import com.badlogic.gdx.math.Vector3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionComponentTest {
    @Test
    public void shouldInitializeAtOrigin() {
        PositionComponent pos = new PositionComponent();
        assertEquals(Vector3.Zero, pos.position);
    }
}
