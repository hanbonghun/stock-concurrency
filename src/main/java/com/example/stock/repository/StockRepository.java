package com.example.stock.repository;

import com.example.stock.domain.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {

    //for update를 통해 lock을 걺
    //충돌이 빈번하다면 optimistic lock보다 성능이 좋을 수 있다
    //lock을 통한 데이터 정합성 보장
    //lock을 잡으므로 성능이 떨어질 수 있음
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    Stock findByIdWithPessimisticLock(@Param("id") Long id);

    //별도의 lock을 잡지 않으므로 perssimistic lock보다 성능상 이점
    //update 실패시 로직을 개발자가 별도로 작성해 주어야 번거로움
    //충돌이 빈번 -> pessimistic lock
    //충돌이 빈번 x -> optimistic lock
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    Stock findByIdWithOptimisticLock(Long id);
}
