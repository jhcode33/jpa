package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by holyeye on 2014. 3. 12..
 */

@Service
@Transactional
public class OrderService {

    @Autowired MemberRepository memberRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemService itemService;
    @PersistenceContext EntityManager em;

    /**
     * 주문
     */
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemService.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }


    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElseThrow();

        //주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        String jpql = "SELECT o FROM Order o WHERE 1=1";
        Map<String, Object> params = new HashMap<>();

        if (orderSearch.getMemberName() != null && !orderSearch.getMemberName().isEmpty()) {
            jpql += " AND o.memberName LIKE :memberName";
            params.put("memberName", "%" + orderSearch.getMemberName() + "%");
        }

        if (orderSearch.getOrderStatus() != null) {
            jpql += " AND o.orderStatus = :orderStatus";
            params.put("orderStatus", orderSearch.getOrderStatus());
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }


}
