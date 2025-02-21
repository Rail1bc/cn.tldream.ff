package cn.tldream.ff.systems;

import cn.tldream.ff.game.components.MovementComponent;
import cn.tldream.ff.game.components.PositionComponent;
import cn.tldream.ff.game.systems.MovementSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovementSystemTest {
    private Engine engine;
    private MovementSystem system;

    @Before
    public void setup() {
        engine = new Engine();
        system = new MovementSystem();
        engine.addSystem(system);
    }

    @Test
    public void shouldUpdatePosition() {
        Entity entity = new Entity()
            .add(new PositionComponent())
            .add(new MovementComponent());

        engine.addEntity(entity);
        system.update(1.0f); // 模拟1秒更新

        assertEquals(0, entity.getComponent(PositionComponent.class).position.x, 0.01f);
    }
}
