/*----------------------------------------------------------------------
	FILE        : PeriodicRandomDoubleGenerator.java
	AUTHOR      : JavaApp1-Jun-2022 Group
	LAST UPDATE : 07.08.2022

	PeriodicRandomIntGenerator class

	Copyleft (c) 1993 by C and System Programmers Association (CSD)
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.util.random.generator;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PeriodicRandomDoubleGenerator {
    private int m_count;
    private int m_min;
    private int m_bound;
    private long m_delay;
    private long m_period;
    private final Timer m_timer = new Timer();

    private PeriodicRandomDoubleGenerator(int count, int min, int bound, long delay, long period)
    {
        m_count = count;
        m_min = min;
        m_bound = bound;
        m_delay = delay;
        m_period = period;
    }

    public static class Builder {
        private final PeriodicRandomDoubleGenerator m_generator;

        public Builder()
        {
            m_generator = new PeriodicRandomDoubleGenerator(10, 0, 99, 0, 1000);
        }

        public Builder setCount(int count)
        {
            m_generator.m_count = count;

            return this;
        }

        public Builder setMin(int min)
        {
            m_generator.m_min = min;

            return this;
        }

        public Builder setBound(int bound)
        {
            m_generator.m_bound = bound;

            return this;
        }

        public Builder setDelay(long delay)
        {
            m_generator.m_delay = delay;

            return this;
        }

        public Builder setPeriod(long period)
        {
            m_generator.m_period = period;

            return this;
        }

        public PeriodicRandomDoubleGenerator build()
        {
            return m_generator;
        }
    }

    public void start(IIntConsumer consumer)
    {
        var random = new Random();
        m_timer.scheduleAtFixedRate(new TimerTask() {
            public void run()
            {
                if (m_count-- != 0)
                    consumer.accept(random.nextInt(m_min, m_bound));
                else
                    m_timer.cancel();
            }
        }, m_delay, m_period);
    }

    public void stop()
    {
        m_timer.cancel();
    }
}