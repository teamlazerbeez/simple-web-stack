package com.teamlazerbeez.http.metrics;

import com.codahale.metrics.Timer;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.dispatch.RequestDispatcher;


final class TimingRequestDispatcher implements RequestDispatcher {

    private final RequestDispatcher wrappedDispatcher;

    private final Timer timer;

    TimingRequestDispatcher(RequestDispatcher wrappedDispatcher, Timer timer) {
        this.wrappedDispatcher = wrappedDispatcher;
        this.timer = timer;
    }

    @Override
    public void dispatch(Object resource, HttpContext context) {
        final Timer.Context time = timer.time();

        try {
            wrappedDispatcher.dispatch(resource, context);
        } finally {
            time.stop();
        }
    }
}
