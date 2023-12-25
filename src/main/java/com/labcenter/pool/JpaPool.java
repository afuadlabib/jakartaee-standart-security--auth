package com.labcenter.pool;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaPool {
    private final EntityManagerFactory entityManagerFactory;
    private final Queue<EntityManager> pool;
    private long startTime;
    private long maxTime;

    public JpaPool() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        this.pool = new LinkedList<>();
        this.startTime = new Date().getTime();
        this.maxTime = startTime + (1000 * 60 * 5);
        while (pool.size() < 5) {
            this.startTime = new Date().getTime();
            this.maxTime = startTime + 10000;
            addPool();
        }
    }

    public EntityManager getConnection() {

        var count = poolSize();
        var now = new Date().getTime();
        if (maxTime > now) {
            switch (count) {
                case 3:
                    while (pool.size() < 20) {
                        addPool();
                    }
                    break;
                case 10:
                    while (pool.size() < 40) {
                        addPool();
                    }
                    break;
                case 20:
                    while (pool.size() < 80) {
                        addPool();
                    }
                    break;
                case 23:
                    while (pool.size() < 200) {
                        addPool();
                    }
                    break;
            }
        } else {

            while (pool.size() < 5) {
                this.startTime = new Date().getTime();
                this.maxTime = startTime + (1000 * 60 * 5);
                addPool();
            }
        }

        return pool.poll();
    }

    public int poolSize() {
        return pool.size();
    }

    private void addPool() {
        pool.add(entityManagerFactory.createEntityManager());

    }

}
