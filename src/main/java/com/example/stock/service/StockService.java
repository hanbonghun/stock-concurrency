package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    //@Transactional
    //synchronized : 해당 메서드에는 하나의 쓰레드만 접근 가능
    //그러나 @Transactional는 서비스를 wrapping한 클래스를 사용하는데 트랜잭션 종료 전에 다른 트랜잭션이 decrease를 호출 할 수 있음
    //@Transactional을 주석 처리하면 테스트 통과
    //즉 syncronized는 하나의 프로세스 안에서만 보장이 됨
    //보통 여러대 서버를 사용하므로 잘 사용되지 않는다.

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
        // Stock 조회
        // 재고를 감소시킨 뒤
        // 갱신 값 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
