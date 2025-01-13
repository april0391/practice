package toy.board.domain.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PagedPostResponse {

    @JsonProperty(value = "posts")
    private final List<PostDto> postDtoList;
    private final PageInfo pageInfo;

    @Data
    @RequiredArgsConstructor
    public static class PageInfo {

        private final int pageNumber;
        private final int pageSize;
        private final int totalPages;
        private final long totalElements;
    }

}
