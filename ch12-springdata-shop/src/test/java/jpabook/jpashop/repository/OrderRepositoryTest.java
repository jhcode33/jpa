package jpabook.jpashop.repository;

import jpabook.jpashop.config.AppConfig;
import jpabook.jpashop.config.WebAppConfig;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, WebAppConfig.class})
@Transactional
public class OrderRepositoryTest {

    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void test() throws Exception {

        //Given
        Member member = createMember("hello");
        Book book = createItem("시골 Book", 10);
        orderService.order(member.getId(), book.getId(), 1);

        //When
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("hello");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        List<Order> search = orderService.findOrders(orderSearch);

        //Then
        assertEquals(1, search.size());
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        memberService.join(member);
        return member;
    }

    private Book createItem(String name, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }

}