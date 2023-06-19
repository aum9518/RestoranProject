package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  SubCategory {
    @Id
    @GeneratedValue(generator = "subcategory_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subcategory_gen",sequenceName = "subcategory_seq", allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.MERGE})
    private Category category;
    @OneToMany(mappedBy = "subCategory")
    private List<MenuItem> menuItems;
}
