package org.programmers.staybb.service;

import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.dto.search.SearchOneResponse;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {

    private final RoomRepository roomRepository;

    public SearchService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //단건조회
    @Transactional(readOnly = true)
    public SearchOneResponse findOne(final Long id) throws EntityNotFoundException {
        return roomRepository.findById(id)
            .map(SearchOneResponse::new)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));
    }

    //필터별조회
    @Transactional(readOnly = true)
    public Page<SearchAllResponse> findByFilters(final SearchRequest searchRequest,
        Pageable pageable) {
        return roomRepository.findByFiltersOrderByCreatedAt(searchRequest, pageable)
            .map(SearchAllResponse::new);
    }
}
