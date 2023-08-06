package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

//실패했을 때 재시도 해야하므로 퍼서드 적용
@Component
public class OptimisticLockStockFacade {
    private final OptimisticLockStockService optimisticLockStockService;

    public OptimisticLockStockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(true){
            try{
                optimisticLockStockService.decrease(id, quantity);
                break;
            }catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
