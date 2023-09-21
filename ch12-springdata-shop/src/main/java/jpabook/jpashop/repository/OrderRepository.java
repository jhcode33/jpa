package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
//import jpabook.jpashop.repository.custom.CustomOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 10:28
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>/*, CustomOrderRepository*/ {

    Optional<Order> findById(Long orderId);
//    @Query("SELECT o FROM Order o WHERE o.memberName LIKE %:memberName% AND o.orderStatus = :orderStatus")
//    List<Order> findByMemberNameLikeAndOrderStatus(
//            @Param("memberName") String memberName,
//            @Param("orderStatus") String orderStatus);
}
