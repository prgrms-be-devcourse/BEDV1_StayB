package org.programmers.staybb.controller.search;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.dto.search.SearchOneResponse;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.dto.search.SearchRequestModel;
import org.programmers.staybb.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/v1/search")
@Slf4j
@RestController
public class SearchController {
    private final SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/v1/search")
    public ResponseEntity<Page<SearchAllResponse>> getAll(final @ModelAttribute SearchRequestModel searchRequestModel, Pageable pageable) {
        SearchRequest searchRequest = new SearchRequest(searchRequestModel);
        return ResponseEntity.ok(searchService.findByFilters(searchRequest, pageable));
    }

    @GetMapping("/v1/search/{id}")
    public ResponseEntity<SearchOneResponse> getOne(final @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(searchService.findOne(id));
    }
}
