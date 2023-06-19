package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen",sequenceName = "cheque_seq", allocationSize = 1)
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;
    @ManyToMany(mappedBy = "cheques",cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<MenuItem> menuItems;
}
