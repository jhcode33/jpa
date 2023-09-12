package jpabook.start;

import javax.persistence.*;

@Entity
//@SequenceGenerator(
//        name = "BOARD_SEQ_GENERATOR",
//        sequenceName = "BOARD_SEQ",
//        initialValue = 1, allocationSize = 50
//)
@TableGenerator(
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        //initialValue = 1,
        pkColumnValue = "BOARD_SEQ", allocationSize = 1
)
public class Board {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;
    private String userName;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
