package heech.server.heechtodo.core.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonSearchCondition {

    private SearchCondition searchCondition;
    private String searchKeyword;
}
