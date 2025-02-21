package cn.tldream.ff.game.systems;

import cn.tldream.ff.game.components.MovementComponent;
import cn.tldream.ff.game.components.PositionComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class MovementSystem extends IteratingSystem {
    public MovementSystem() {
        super(Family.all(PositionComponent.class, MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        MovementComponent mov = entity.getComponent(MovementComponent.class);

        pos.position.add(mov.velocity.x * deltaTime,
            mov.velocity.y * deltaTime,
            mov.velocity.z * deltaTime);
    }
}
