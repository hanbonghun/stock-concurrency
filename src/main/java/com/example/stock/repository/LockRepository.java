package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


//주로 분산 lock에 사용
//timeout 구현이 쉬움
//데이터 삽입시의 정합성을 맞출 때도 사용 가능
//트랜잭션 해제 시 세션 해제 신경써야 하고 구현이 복잡할 수 있음
public interface LockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(@Param(value = "key") String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(@Param(value ="key") String key);
}
