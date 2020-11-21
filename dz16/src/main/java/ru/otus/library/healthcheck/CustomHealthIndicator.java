package ru.otus.library.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (LocalTime.now().isAfter(LocalTime.of(3, 0)) &&
                LocalTime.now().isBefore(LocalTime.of(4, 0))) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("detail", "Maintenance from 3 till 4 AM")
                    .build();
        } else {
            return Health.up()
                    .withDetail("detail", "It's all good")
                    .build();
        }
    }
}
