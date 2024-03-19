package com.casetest.centralbank.bucketj4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

class LimitingRateTest {

    @Test
    public void testLimitingRate() {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();

        for (int i = 1; i <= 10; i++) {
            assertTrue(bucket.tryConsume(1));
        }
        assertFalse(bucket.tryConsume(1));
    }
}