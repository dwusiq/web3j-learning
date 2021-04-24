package com.wusiq.comm;

import java.security.SecureRandom;

public class CommUtils {
    public static Long randomLong() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }
}
