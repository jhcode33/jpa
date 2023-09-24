package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 10:28
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>/*, CustomOrderRepository*/ {

    Optional<Order> findById(Long orderId);

    @Query("SELECT o FROM Order o WHERE (:memberName IS NULL OR o.member.name LIKE :memberName) AND (:orderStatus IS NULL OR o.status = :orderStatus)")
    List<Order> findByMemberNameLikeAndOrderStatus(@Param("memberName") String memberName,
                                                   @Param("orderStatus") OrderStatus orderStatus);
}
