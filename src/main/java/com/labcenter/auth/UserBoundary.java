package com.labcenter.auth;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.labcenter.pool.JpaPool;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@RequestScoped
public class UserBoundary {
    @Inject
    private JpaPool emp;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    @PostConstruct
    public void init() {
        System.out.println(emp.poolSize());
        entityManager = emp.getConnection();
        entityTransaction = entityManager.getTransaction();
    }

    public void persist(User user) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> resultFuture = CompletableFuture.runAsync(() -> {
            try {
                entityTransaction.begin();

                entityManager.persist(user);
                entityManager.flush();

                entityTransaction.commit();
            } catch (Exception e) {
                if (entityTransaction != null && entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                e.printStackTrace();
            } finally {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                }
            }
        }, executorService);

        resultFuture.join();
        executorService.shutdown();
    }
}
