package common;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AutoID;

/**
 * Created by mengf on 2017/11/21 0021.
 */
@Data
public class IDEntity {
    @AutoID
    private Long id;
}
