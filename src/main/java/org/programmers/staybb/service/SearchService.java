package org.programmers.staybb.service;

import javassist.NotFoundException;
import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.dto.search.SearchRequestModel;
import org.springframework.transaction.annotation.Transactional;
import org.programmers.staybb.dto.search.SearchOneResponse;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final RoomRepository roomRepository;

    public SearchService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    //단건조회
    @Transactional(readOnly = true)
    public SearchOneResponse findOne(final Long id) throws NotFoundException {
        return roomRepository.findById(id)
            .map(SearchOneResponse::new)
            .orElseThrow(() -> new NotFoundException("해당 room을 찾을 수 없습니다"));
    }

    //필터별조회
    @Transactional(readOnly = true)
    public Page<SearchAllResponse> findByFilters(final SearchRequest searchRequest, Pageable pageable){
        return roomRepository.findByFiltersOrderByCreatedAt(searchRequest, pageable)
            .map(SearchAllResponse::new);
    }

}
