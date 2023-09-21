package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:48
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findById(Long itemID);
}

