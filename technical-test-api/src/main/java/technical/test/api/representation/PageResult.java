package technical.test.api.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalItems;

}

