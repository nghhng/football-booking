package nghhng.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BaseEntity{
    private List<Comment> comments;

    private Double rating;
}
