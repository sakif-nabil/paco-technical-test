package technical.test.renderer.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PageResultView<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalItems;
}
